package net.apixelite.subterra.item;

import java.util.function.Supplier;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum ModToolMaterial implements ToolMaterial{

    ENDERITE(MiningLevels.NETHERITE, 4062, 12.0f, 5, 25, () -> Ingredient.ofItems(ModItems.ENDERITE_INGOT)),
    ARAGONITE(MiningLevels.NETHERITE, 6093, 15.0f, 6, 35, () -> Ingredient.ofItems(ModItems.ARAGONITE_INGOT)),
    INFERNITE(MiningLevels.NETHERITE, 10155, 22.0f, 7, 52, () -> Ingredient.ofItems(ModItems.INFERNITE_INGOT)),
    DRILL_MATERIAL(MiningLevels.NETHERITE, 0, 12.5f, 6.5f, 25, () -> Ingredient.ofItems(Items.PRISMARINE_CRYSTALS));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage,
            int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
