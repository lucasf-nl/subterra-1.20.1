package net.apixelite.subterra.datagen;

import java.util.concurrent.CompletableFuture;

import net.apixelite.subterra.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider{

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(ModBlocks.ENDERITE_ORE)
            .add(ModBlocks.ARAGONITE_ORE)
            .add(ModBlocks.INFERNITE_ORE)
            .add(ModBlocks.DRILL_UPGRADE_STATION);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.ENDERITE_ORE)
            .add(ModBlocks.ARAGONITE_ORE)
            .add(ModBlocks.INFERNITE_ORE);
    }

}
