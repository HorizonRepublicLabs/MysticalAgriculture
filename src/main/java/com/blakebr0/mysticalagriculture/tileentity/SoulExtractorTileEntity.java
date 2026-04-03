package com.blakebr0.mysticalagriculture.tileentity;

import com.blakebr0.cucumber.energy.CEnergyStorage;
import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.cucumber.inventory.CachedRecipe;
import com.blakebr0.cucumber.inventory.OnContentsChangedFunction;
import com.blakebr0.cucumber.inventory.SidedInventoryWrapper;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.cucumber.util.Localizable;
import com.blakebr0.mysticalagriculture.api.crafting.ISoulExtractionRecipe;
import com.blakebr0.mysticalagriculture.api.machine.IUpgradeableMachine;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeItemStackHandler;
import com.blakebr0.mysticalagriculture.api.machine.MachineUpgradeTier;
import com.blakebr0.mysticalagriculture.api.util.MobSoulUtils;
import com.blakebr0.mysticalagriculture.block.SoulExtractorBlock;
import com.blakebr0.mysticalagriculture.container.SoulExtractorContainer;
import com.blakebr0.mysticalagriculture.init.ModRecipeTypes;
import com.blakebr0.mysticalagriculture.init.ModTileEntities;
import com.blakebr0.mysticalagriculture.item.SoulJarItem;
import com.blakebr0.mysticalagriculture.util.RecipeIngredientCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulExtractorTileEntity extends BaseInventoryTileEntity implements MenuProvider, IUpgradeableMachine {
    private static final int FUEL_TICK_MULTIPLIER = 20;
    public static final int OPERATION_TIME = 100;
    public static final int FUEL_USAGE = 40;
    public static final int FUEL_CAPACITY = 80000;

    private final CItemStacksHandler inventory;
    private final MachineUpgradeItemStackHandler upgradeInventory;
    private final CEnergyStorage energy;
    private final SidedInventoryWrapper[] sidedInventoryWrappers;
    private final CachedRecipe<CraftingInput, ISoulExtractionRecipe> recipe;
    private MachineUpgradeTier tier;
    private int progress;
    private int fuelLeft;
    private int fuelItemValue;
    private boolean isRunning;

    public SoulExtractorTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.SOUL_EXTRACTOR.get(), pos, state);
        this.inventory = createInventoryHandler((_, _) -> this.setChanged());
        this.upgradeInventory = new MachineUpgradeItemStackHandler();
        this.energy = new CEnergyStorage(FUEL_CAPACITY, _ -> this.setChangedFast());
        this.sidedInventoryWrappers = SidedInventoryWrapper.create(this.inventory, List.of(Direction.UP, Direction.DOWN, Direction.NORTH), this::canInsertStackSided, null);
        this.recipe = new CachedRecipe<>(ModRecipeTypes.SOUL_EXTRACTION.get());
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
        return Localizable.of("container.mysticalagriculture.soul_extractor").build();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return SoulExtractorContainer.create(id, playerInventory, this.inventory, this.upgradeInventory, this.getBlockPos());
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

    public static void tick(Level level, BlockPos pos, BlockState state, SoulExtractorTileEntity tile) {
        if (tile.energy.getEnergyStored() < tile.energy.getMaxEnergyStored()) {
            var fuel = tile.inventory.getStackInSlot(1);

            if (tile.fuelLeft <= 0 && !fuel.isEmpty()) {
                tile.fuelItemValue = fuel.getBurnTime(null);

                if (tile.fuelItemValue > 0) {
                    tile.fuelLeft = tile.fuelItemValue *= FUEL_TICK_MULTIPLIER;
                    tile.inventory.setStackInSlot(1, StackHelper.shrink(fuel, 1, true));

                    tile.setChangedFast();
                }
            }

            if (tile.fuelLeft > 0) {
                var fuelPerTick = Math.min(Math.min(tile.fuelLeft, tile.getFuelUsage() * 2), tile.energy.getMaxEnergyStored() - tile.energy.getEnergyStored());

                tile.fuelLeft -= tile.energy.receiveEnergy(fuelPerTick, false);

                if (tile.fuelLeft <= 0)
                    tile.fuelItemValue = 0;

                tile.setChangedFast();
            }
        }

        var tier = tile.getMachineTier();

        if (tier != tile.tier) {
            tile.tier = tier;

            if (tier == null) {
                tile.energy.resetMaxEnergyStorage();
            } else {
                tile.energy.setMaxEnergyStorage((int) (FUEL_CAPACITY * tier.getFuelCapacityMultiplier()));
            }

            tile.setChangedFast();
        }

        var wasRunning = tile.isRunning;
        var recipe = tile.getActiveRecipe();

        tile.isRunning = false;

        if (recipe != null) {
            if (tile.energy.getEnergyStored() >= tile.getFuelUsage()) {
                tile.isRunning = true;
                tile.progress++;
                tile.energy.extractEnergy(tile.getFuelUsage(), false);

                if (tile.progress >= tile.getOperationTime()) {
                    tile.inventory.setStackInSlot(0, StackHelper.shrink(tile.inventory.getStackInSlot(0), 1, false));
                    tile.inventory.setStackInSlot(2, recipe.assemble(tile.toCraftingInput(), level.registryAccess()));

                    tile.progress = 0;
                }

                tile.setChangedFast();
            }
        } else {
            if (tile.progress > 0) {
                tile.progress = 0;

                tile.setChangedFast();
            }
        }

        if (wasRunning != tile.isRunning) {
            level.setBlock(pos, state.setValue(SoulExtractorBlock.RUNNING, tile.isRunning), 3);

            tile.setChangedFast();
        }

        tile.dispatchIfChanged();
    }

    public static CItemStacksHandler createInventoryHandler() {
        return createInventoryHandler(null);
    }

    public static CItemStacksHandler createInventoryHandler(OnContentsChangedFunction onContentsChanged) {
        return CItemStacksHandler.create(3, onContentsChanged, builder -> {
            builder.setCanInsert((slot, stack) -> switch (slot) {
                case 1 -> FurnaceBlockEntity.isFuel(stack);
                case 2 -> stack.getItem() instanceof SoulJarItem;
                default -> true;
            });
            builder.setCanExtract(slot -> switch (slot) {
                case 1 -> !FurnaceBlockEntity.isFuel(builder.getStackInSlot(1));
                case 2 -> {
                    var stack = builder.getStackInSlot(2);
                    yield stack.getItem() instanceof SoulJarItem && MobSoulUtils.isJarFull(stack);
                }
                default -> false;
            });
        });
    }

    public ISoulExtractionRecipe getActiveRecipe() {
        if (this.level == null)
            return null;

        return this.recipe.checkAndGet(this.toCraftingInput(), this.level);
    }

    public CEnergyStorage getEnergy() {
        return this.energy;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getOperationTime() {
        if (this.tier == null)
            return OPERATION_TIME;

        return (int) (OPERATION_TIME * this.tier.getOperationTimeMultiplier());
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

    private CraftingInput toCraftingInput() {
        return this.inventory.toShapelessCraftingInput();
    }

    private boolean canInsertStackSided(int slot, ItemStack stack, Direction direction) {
        if (direction == null)
            return true;
        if (slot == 0 && direction == Direction.UP)
            return RecipeIngredientCache.INSTANCE.isValidInput(stack, ModRecipeTypes.SOUL_EXTRACTION.get());
        if (slot == 1 && direction == Direction.NORTH)
            return FurnaceBlockEntity.isFuel(stack);
        if (slot == 2 && direction == Direction.NORTH)
            return stack.getItem() instanceof SoulJarItem;

        return false;
    }
}
