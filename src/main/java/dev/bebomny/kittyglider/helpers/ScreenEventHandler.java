package dev.bebomny.kittyglider.helpers;

import dev.bebomny.kittyglider.configuration.gui.KittyGliderOptionsButtonWidget;
import dev.bebomny.kittyglider.mixin.IGridWidgetAccessor;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;

import java.util.List;

public class ScreenEventHandler {

    public static void register() {
        ScreenEvents.AFTER_INIT.register(ScreenEventHandler::afterScreenInit);
    }

    private static void afterScreenInit(MinecraftClient client, Screen screen, int i, int i1) {
        if(screen instanceof GameMenuScreen)
            afterGameScreenInit(screen);
    }

    private static void afterGameScreenInit(Screen screen) {
        ClickableWidget widget = Screens.getButtons(screen).get(0);
        if(widget instanceof GridWidget) {
            final List<ClickableWidget> buttons = ((IGridWidgetAccessor) widget).getChildren();
            buttons.add(new KittyGliderOptionsButtonWidget(screen));
        }
    }
}
