package com.blakebr0.mysticalagriculture.tileentity;

import com.blakebr0.cucumber.energy.CEnergyStorage;
import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.CachedRecipe;
import com.blakebr0.cucumber.inventory.OnContentsChangedFunction;
import com.blakebr0.cucumber.inventory.SidedInventoryWrapper;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.cucumber.util.ContainerDataBuilder;
import com.blakebr0.mysticalagriculture.api.machine.IUpgradeableMachine;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeItemStackHandler;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeTier;
import com.blakebr0.mysticalagriculture.block.EssenceFurnaceBlock;
import com.blakebr0.mysticalagriculture.container.EssenceFurnaceContainer;
import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class EssenceFurnaceTileEntity extends BaseInventoryTileEntity implements MenuProvider, IUpgradeableMachine {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    public static final int OPERATION_TIME = 200;
    public static final int FUEL_USAGE = 20;
    public static final int FUEL_CAPACITY = 80000;

    private final CItemStacksHandler inventory;
    private final MachineUpgradeItemStackHandler upgradeInventory;
    private final CEnergyStorage energy;
    private final SidedInventoryWrapper[] sidedInventoryWrappers;
    private final CachedRecipe<SingleRecipeInput, SmeltingRecipe> recipe;
    private MachineUpgradeTier tier;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;
    private boolean isRunning;

    private final ContainerData dataAccess;

    public EssenceFurnaceTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.FURNACE.get(), pos, state);
        this.inventory = createInventoryHandler((_, _) -> this.setChanged());
        this.upgradeInventory = new MachineUpgradeItemStackHandler();
        this.energy = new CEnergyStorage(FUEL_CAPACITY, _ -> this.setChangedFast());
        this.sidedInventoryWrappers = SidedInventoryWrapper.create(this.inventory, List.of(Direction.UP, Direction.DOWN, Direction.NORTH), this::canInsertStackSided, null);
        this.recipe = new CachedRecipe<>(RecipeType.SMELTING);

        this.dataAccess = ContainerDataBuilder.builder()
                .sync(this.energy::getAmountAsInt, this.energy::set)
                .sync(this.energy::getCapacityAsInt, this.energy::setMaxCapacity)
                .sync(() -> this.progress, value -> this.progress = value)
                .sync(this::getOperationTime)
                .sync(() -> this.fuelLeft, value -> this.fuelLeft = value)
                .sync(() -> this.fuelItemValue, value -> this.fuelItemValue = value)
                .build();
    }

    @Override
    public CItemStacksHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.progress = input.getIntOr("Progress", 0);
        this.fuelLeft = input.getIntOr("FuelLeft", 0);
        this.fuelItemValue = input.getIntOr("FuelItemValue", 0);
        this.energy.deserialize(input);
        this.upgradeInventory.deserialize(input.childOrEmpty("UpgradeInventory"));
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("Progress", this.progress);
        output.putInt("FuelLeft", this.fuelLeft);
        output.putInt("FuelItemValue", this.fuelItemValue);
        this.energy.serialize(output);
        output.putChild("UpgradeInventory", this.upgradeInventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.mysticalagriculture.furnace");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new EssenceFurnaceContainer(id, playerInventory, this.inventory, this.upgradeInventory, this.dataAccess, this.getBlockPos());
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        if (this.level != null) {
            var upgrade = this.upgradeInventory.getResource(0).toStack(this.upgradeInventory.getAmountAsInt(0));

            Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), upgrade);
        }
    }

    @Override
    public MachineUpgradeItemStackHandler getUpgradeInventory() {
        return this.upgradeInventory;
    }

    public ItemStacksResourceHandler getSidedInventory(@Nullable Direction direction) {
        if (direction == null) direction = Direction.NORTH;

        return switch (direction) {
            case UP -> this.sidedInventoryWrappers[0];
            case DOWN -> this.sidedInventoryWrappers[1];
            default -> this.sidedInventoryWrappers[2];
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EssenceFurnaceTileEntity tile) {
        if (tile.energy.getAmountAsInt() < tile.energy.getCapacityAsInt()) {
            var fuel = tile.inventory.getResource(0);

            try (var tx = Transaction.openRoot()) {
                if (tile.fuelLeft <= 0 && !fuel.isEmpty()) {
                    tile.fuelItemValue = fuel.toStack().getBurnTime(null, level.fuelValues());

                    if (tile.fuelItemValue > 0) {
                        tile.fuelLeft = tile.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                        tile.inventory.extract(0, fuel, 1, tx, true);

                        tile.setChangedFast();
                    }
                }

                if (tile.fuelLeft > 0) {
                    var fuelPerTick = Math.min(Math.min(tile.fuelLeft, tile.getFuelUsage() * 2), tile.energy.getCapacityAsInt() - tile.energy.getAmountAsInt());

                    tile.fuelLeft -= tile.energy.insert(fuelPerTick, tx);

                    if (tile.fuelLeft <= 0)
                        tile.fuelItemValue = 0;

                    tile.setChangedFast();
                }

                tx.commit();
            }
        }

        var tier = tile.getMachineTier();

        if (tier != tile.tier) {
            tile.tier = tier;

            if (tier == null) {
                tile.energy.resetMaxCapacity();
            } else {
                tile.energy.setMaxCapacity(FUEL_CAPACITY * tier.getFuelCapacityMultiplier());
            }

            tile.setChangedFast();
        }

        var wasRunning = tile.isRunning;

        if (tile.energy.getAmountAsInt() >= tile.getFuelUsage()) {
            var input = tile.inventory.getResource(0);
            var output = tile.inventory.getResource(2);

            tile.isRunning = false;

            if (!input.isEmpty()) {
                var recipe = tile.getActiveRecipe();

                if (recipe != null) {
                    var recipeOutput = recipe.assemble(new SingleRecipeInput(input));
                    if (!recipeOutput.isEmpty() && (output.isEmpty() || StackHelper.canCombineStacks(output, recipeOutput))) {
                        tile.isRunning = true;
                        tile.progress++;

                        tile.energy.extractEnergy(tile.getFuelUsage(), false);

                        if (tile.progress >= tile.getOperationTime()) {
                            tile.inventory.setStackInSlot(0, StackHelper.shrink(input, 1, false));
                            tile.inventory.setStackInSlot(2, StackHelper.combineStacks(output, recipeOutput));

                            tile.progress = 0;
                        }

                        tile.setChangedFast();
                    }
                }
            } else {
                if (tile.progress > 0) {
                    tile.progress = 0;

                    tile.setChangedFast();
                }
            }
        } else {
            tile.isRunning = false;
        }

        if (wasRunning != tile.isRunning) {
            level.setBlock(pos, state.setValue(EssenceFurnaceBlock.RUNNING, tile.isRunning), 3);

            tile.setChangedFast();
        }

        tile.dispatchIfChanged();
    }

    public SmeltingRecipe getActiveRecipe() {
        return this.recipe.checkAndGet(new SingleRecipeInput(this.inventory.getStackInSlot(0)), this.level);
    }

    public CEnergyStorage getEnergy() {
        return this.energy;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getOperationTime() {
        var recipe = this.recipe.get();
        var operationTime = recipe != null ? recipe.cookingTime() : OPERATION_TIME;

        if (this.tier == null)
            return operationTime;

        return (int) (operationTime * this.tier.getOperationTimeMultiplier());
    }

    public int getFuelLeft() {
        return this.fuelLeft;
    }

    public int getFuelItemValue() {
        return this.fuelItemValue;
    }

    public int getFuelUsage() {
        if (this.tier == null)
            return FUEL_USAGE;

        return (int) (FUEL_USAGE * this.tier.getFuelUsageMultiplier());
    }

    private boolean canInsertStackSided(int slot, ItemStack stack, Direction direction) {
        if (direction == null)
            return true;
        if (slot == 0 && direction == Direction.UP)
            return true;
        if (slot == 1 && direction == Direction.NORTH)
            return this.level != null && this.level.fuelValues().isFuel(stack);

        return false;
    }

    public static CItemStacksHandler createInventoryHandler() {
        return createInventoryHandler(null, null);
    }

    public static CItemStacksHandler createInventoryHandler(@Nullable OnContentsChangedFunction onContentsChanged, @Nullable Level level) {
        return CItemStacksHandler.create(3, onContentsChanged, builder -> {
            builder.setCanInsert((slot, stack) -> switch (slot) {
                case 1 -> level != null && level.fuelValues().isFuel(stack.toStack());
                case 2 -> false;
                default -> true;
            });
            builder.setCanExtract(slot -> switch (slot) {
                case 1 -> level == null || !level.fuelValues().isFuel(builder.getResource(slot).toStack());
                case 2 -> true;
                default -> false;
            });
        });
    }
}
