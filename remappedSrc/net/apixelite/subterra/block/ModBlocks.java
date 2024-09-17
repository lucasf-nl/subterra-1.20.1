package net.apixelite.subterra.block;

import net.apixelite.subterra.Subterra;
import net.apixelite.subterra.block.custom.DrillUpgradeStationBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks {
    public static final Block ENDERITE_ORE = registerBlock("enderite_ore", 
        new Block(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)), Rarity.UNCOMMON);
    public static final Block ARAGONITE_ORE = registerBlock("aragonite_ore", 
        new Block(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)), Rarity.RARE);
    public static final Block INFERNITE_ORE = registerBlock("infernite_ore", 
        new Block(FabricBlockSettings.copyOf(Blocks.ANCIENT_DEBRIS)), Rarity.EPIC);

    public static final Block DRILL_UPGRADE_STATION = registerBlock("drill_upgrade_station", 
        new DrillUpgradeStationBlock(FabricBlockSettings.copyOf(Blocks.SMITHING_TABLE).nonOpaque()), Rarity.COMMON);


    private static Block registerBlock(String name, Block block, Rarity rarity) {
        registerBlockItem(name, block, rarity);
        return Registry.register(Registries.BLOCK, new Identifier(Subterra.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, Rarity rarity) { 
        return Registry.register(Registries.ITEM, new Identifier(Subterra.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings().rarity(rarity)));
    }

    public static void registerModBlock() {
        Subterra.LOGGER.info("Registering Mod Blocks for " + Subterra.MOD_ID);
    }
}
