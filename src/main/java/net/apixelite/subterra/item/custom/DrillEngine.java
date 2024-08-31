package net.apixelite.subterra.item.custom;

import java.util.List;

import net.apixelite.subterra.item.ModItems;
import net.apixelite.subterra.util.CustomRarity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class DrillEngine extends Item {

    private float miningSpeed;
    private CustomRarity rarity;

    public DrillEngine(float miningSpeed, CustomRarity rarity, Settings settings) {
        super(settings);
        this.miningSpeed = miningSpeed;
        this.rarity = rarity;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.clear();

        tooltip.add(Text.empty().append(this.getName()).formatted(this.rarity.formatting));
        if (!stack.isOf(ModItems.DRILL_ENGINE)) {
            tooltip.add(Text.literal("§8Drill Part"));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("§7Part Type: §6Drill Engine"));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.literal("§7Gives: §6" + ((int) this.miningSpeed) + " Mining Speed"));
            tooltip.add(Text.literal(""));
        }
        tooltip.add(Text.literal("§l" + this.rarity).formatted(this.rarity.formatting));

        super.appendTooltip(stack, world, tooltip, context);
    }

    public static int getMiningSpeed(String name) {
        switch (name) {
            case "Drill Engine Tier I":
                return 10;
            case "Drill Engine Tier II":
                return 25;
            case "Drill Engine Tier III":
                return 50;
            case "Drill Engine Tier IV":
                return 100;
            case "Drill Engine Tier V":
                return 250;
            default:
                return 0;
        }
    }

}
