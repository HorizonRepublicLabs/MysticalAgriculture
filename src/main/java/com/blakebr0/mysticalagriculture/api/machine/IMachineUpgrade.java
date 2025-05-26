package com.blakebr0.mysticalagriculture.api.machine;

import net.minecraft.world.level.ItemLike;

/**
 * Implement this interface on Machine Upgrade items
 */
public interface IMachineUpgrade extends ItemLike {
    /**
     * Get the tier/group this machine upgrade belongs to
     * @return the tier of this machine upgrade
     */
    MachineUpgradeTier getTier();
}
