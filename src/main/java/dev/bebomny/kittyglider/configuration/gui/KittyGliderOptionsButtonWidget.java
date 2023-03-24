package dev.bebomny.kittyglider.configuration.gui;

import dev.bebomny.kittyglider.configuration.ConfigurationMenu;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class KittyGliderOptionsButtonWidget extends ButtonWidget {

    public KittyGliderOptionsButtonWidget(Screen screen) {
        super(
                10, screen.height - 70,
                128, 20,
                Text.literal("KittyGlider Options"),
                button -> MinecraftClient.getInstance().setScreen(new ConfigurationMenu(screen, MinecraftClient.getInstance().options)),
                ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
    }
}
