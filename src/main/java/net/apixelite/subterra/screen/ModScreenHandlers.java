package net.apixelite.subterra.screen;

import net.apixelite.subterra.Subterra;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<DrillUpgradeScreenHandler> DRILL_UPGRADE_SCREEN_HANDLER =
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(Subterra.MOD_ID, "drill_upgrade"),
            new ExtendedScreenHandlerType<>(DrillUpgradeScreenHandler::new));
    
    public static void registerScreenHandlers() {
        Subterra.LOGGER.info("Registering Screen Handlers for " + Subterra.MOD_ID);
    }

}
