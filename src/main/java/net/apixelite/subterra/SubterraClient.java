package net.apixelite.subterra;

import net.apixelite.subterra.screen.DrillUpgradeScreen;
import net.apixelite.subterra.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class SubterraClient implements ClientModInitializer{
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlers.DRILL_UPGRADE_SCREEN_HANDLER, DrillUpgradeScreen::new);

    }

}
