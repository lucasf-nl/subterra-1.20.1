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

    private CustomRarity rarity;
    private int level;

    public DrillEngine(int level, CustomRarity rarity, Settings settings) {
        super(settings);
        this.rarity = rarity;
        this.level = level;
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
            tooltip.add(Text.literal("§7Gives: §6" + (getMiningSpeed(this.level)) + " Mining Speed"));
            tooltip.add(Text.literal(""));
        }
        tooltip.add(Text.literal("§l" + this.rarity).formatted(this.rarity.formatting));

        super.appendTooltip(stack, world, tooltip, context);
    }

    public static int getMiningSpeed(int level) {
        if (level != 0) {
            return (int) (5 + 2.5 * (level * level - level) + 5);
        } else {
            return 0;
        }
    }
}
