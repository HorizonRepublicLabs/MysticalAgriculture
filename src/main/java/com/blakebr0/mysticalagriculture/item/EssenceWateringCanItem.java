package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.mysticalagriculture.init.ModDataComponentTypes;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.function.Consumer;

public class EssenceWateringCanItem extends WateringCanItem {
    private final ChatFormatting textColor;

    public EssenceWateringCanItem(Identifier id, int range, double chance, ChatFormatting textColor) {
        super(id, range, chance, p -> p.component(ModDataComponentTypes.WATERING_CAN_ACTIVE, false));
        this.textColor = textColor;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        var isActive = isActive(stack);
        if (isActive && entity instanceof Player player) {
            var selected = player.getItemBySlot(slot).equals(stack);

            if (selected) {
                var trace = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

                if (trace.getType() == HitResult.Type.BLOCK) {
                    this.doWater(stack, level, player, trace.getBlockPos(), trace.getDirection());
                } else {
                    stopPlayingSound(player);
                }
            }

            // we need to actively check if the watering can was playing the sound in any case where it's not actively
            // watering the ground
            if (!selected) {
                stopPlayingSound(player);
            }
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isActive(stack);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var trace = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (trace.getType() != HitResult.Type.BLOCK) {
            if (isFilled(stack) && player.isCrouching()) {
                flipActive(stack);
            }

            return InteractionResult.PASS;
        }

        if (isFilled(stack)) {
            return InteractionResult.PASS;
        }

        var pos = trace.getBlockPos();
        var direction = trace.getDirection();

        if (level.mayInteract(player, pos) && player.mayUseItemAt(pos.relative(direction), direction, stack)) {
            var fluid = level.getFluidState(pos);

            if (fluid.is(FluidTags.WATER)) {
                setFilled(stack, true);

                player.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if (player == null)
            return InteractionResult.FAIL;

        var hand = context.getHand();
        var stack = player.getItemInHand(hand);

        if (isActive(stack))
            return InteractionResult.PASS;

        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag advanced) {
        super.appendHoverText(stack, context, display, builder, advanced);

        var rangeString = String.valueOf(this.range);
        var rangeNumber = Component.literal(rangeString + "x" + rangeString).withStyle(this.textColor);

        builder.accept(ModTooltips.TOOL_AREA.args(rangeNumber).toComponent());
    }

    public static boolean isActive(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.WATERING_CAN_ACTIVE, false);
    }

    public static void setActive(ItemStack stack, boolean active) {
        stack.set(ModDataComponentTypes.WATERING_CAN_ACTIVE, active);
    }

    public static void flipActive(ItemStack stack) {
        var current = isActive(stack);
        setActive(stack, !current);
    }
}
