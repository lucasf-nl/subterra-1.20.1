package net.apixelite.subterra.util;

import net.minecraft.item.ItemStack;

public class RecombobulatorUtil {

    // Sets the rarity to the item
    public static CustomRarity getCustomRarity(ItemStack stack, CustomRarity rarity) {
        if (!hasRecombobulator(stack)) {
            return rarity;
        } else {
            switch (rarity) {
                case COMMON:
                    return rarity = CustomRarity.UNCOMMON;
                case UNCOMMON:
                    return rarity = CustomRarity.RARE;
                case RARE:
                    return rarity = CustomRarity.EPIC;
                case EPIC:
                    return rarity = CustomRarity.LEGENDARY;
                case LEGENDARY:
                    return rarity = CustomRarity.MYTHIC;
                case MYTHIC:
                    return rarity = CustomRarity.DIVINE;
                case DIVINE:
                    return rarity = CustomRarity.SPECIAL;
                case SPECIAL:
                    return rarity = CustomRarity.VERY_SPECIAL;
                default:
                    return rarity = CustomRarity.COMMON;
            }
        }
    }
    
    // Checks if a recombobulator is applied
    public static boolean hasRecombobulator(ItemStack stack) {
        if (stack.getNbt().getBoolean("subterra.has_recombobulator")) {
            return true;
        } else {
            return false;
        }
    }
}
