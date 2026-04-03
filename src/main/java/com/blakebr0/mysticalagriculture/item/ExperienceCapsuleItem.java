package com.blakebr0.mysticalagriculture.item;

import com.blakebr0.cucumber.item.BaseItem;
import com.blakebr0.cucumber.util.Formatting;
import com.blakebr0.mysticalagriculture.api.util.ExperienceCapsuleUtils;
import com.blakebr0.mysticalagriculture.init.ModDataComponentTypes;
import com.blakebr0.mysticalagriculture.lib.ModTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class ExperienceCapsuleItem extends BaseItem {
    public ExperienceCapsuleItem(Identifier id) {
        super(id, p -> p
                .stacksTo(1)
                .component(ModDataComponentTypes.EXPERIENCE_CAPSULE, 0)
        );
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var experience = ExperienceCapsuleUtils.getExperience(stack);

        if (player.isCrouching()) {
            if (experience > 0) {
                var xpToGive = Math.min(experience, getExperienceToGive(player));

                xpToGive -= ExperienceCapsuleUtils.removeExperienceFromCapsule(stack, xpToGive);

                giveExperiencePoints(player, xpToGive);

                return InteractionResult.SUCCESS;
            }
        } else {
            if (experience < ExperienceCapsuleUtils.MAX_XP_POINTS && player.totalExperience > 0) {
                var xpToTake = Math.min(ExperienceCapsuleUtils.MAX_XP_POINTS - experience, getExperienceToTake(player));

                xpToTake -= ExperienceCapsuleUtils.addExperienceToCapsule(stack, xpToTake);

                giveExperiencePoints(player, -xpToTake);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        var experience = ExperienceCapsuleUtils.getExperience(stack);

        builder.accept(ModTooltips.EXPERIENCE_CAPSULE.args(Formatting.number(experience), Formatting.number(ExperienceCapsuleUtils.MAX_XP_POINTS)).toComponent());
    }

    private static int getExperienceToGive(Player player) {
        int xpNeeded = player.getXpNeededForNextLevel();
        int extraXp = Math.round(xpNeeded * player.experienceProgress);

        return xpNeeded - extraXp;
    }

    private static int getExperienceToTake(Player player) {
        // if they have progress towards the next level, then we give the player their progress
        // if they don't, then we need to give them everything in their current level
        var progress = Math.round(player.experienceProgress * 10F) / 10F;
        if (progress > 0.0F) {
            var xpNeeded = player.getXpNeededForNextLevel();
            return Math.round(xpNeeded * player.experienceProgress);
        } else {
            // decrease player level by 1 temporarily to get the experience needed for the current level
            player.experienceLevel--;

            var xpNeeded = player.getXpNeededForNextLevel();

            // set the player's level back to where it was
            player.experienceLevel++;

            return xpNeeded;
        }
    }

    // copy of Player#giveExperiencePoints
    private static void giveExperiencePoints(Player player, int points) {
        player.experienceProgress += (float) points / (float) player.getXpNeededForNextLevel();
        player.totalExperience = Mth.clamp(player.totalExperience + points, 0, Integer.MAX_VALUE);

        while (player.experienceProgress < 0.0F) {
            float f = player.experienceProgress * (float) player.getXpNeededForNextLevel();
            if (player.experienceLevel > 0) {
                giveExperienceLevels(player, -1);
                player.experienceProgress = 1.0F + f / (float) player.getXpNeededForNextLevel();
            } else {
                giveExperienceLevels(player, -1);
                player.experienceProgress = 0.0F;
            }
        }

        while (player.experienceProgress >= 1.0F) {
            player.experienceProgress = (player.experienceProgress - 1.0F) * (float) player.getXpNeededForNextLevel();
            giveExperienceLevels(player, 1);
            player.experienceProgress /= (float) player.getXpNeededForNextLevel();
        }
    }

    // copy of Player#giveExperienceLevels
    private static void giveExperienceLevels(Player player, int levels) {
        player.experienceLevel += levels;

        if (player.experienceLevel < 0) {
            player.experienceLevel = 0;
            player.experienceProgress = 0.0F;
            player.totalExperience = 0;
        }
    }
}
