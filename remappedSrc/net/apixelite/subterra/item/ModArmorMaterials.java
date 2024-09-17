package net.apixelite.subterra.item;

import java.util.function.Supplier;

import net.apixelite.subterra.Subterra;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum ModArmorMaterials implements ArmorMaterial {
    ENDERITE("enderite", 43, new int[] { 7, 13, 10, 7}, 25, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
     8, 0.3f, () -> Ingredient.ofItems(ModItems.ENDERITE_INGOT)),
    ARAGONITE("aragonite", 51, new int[] { 13, 26, 20, 13}, 35, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
     15, 0.7f, () -> Ingredient.ofItems(ModItems.ARAGONITE_INGOT)),
    INFERNITE("infernite", 69, new int[] { 23, 39, 30, 21}, 52, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
     22, 1.0f, () -> Ingredient.ofItems(ModItems.INFERNITE_INGOT));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    private ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound,
            float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return Subterra.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
