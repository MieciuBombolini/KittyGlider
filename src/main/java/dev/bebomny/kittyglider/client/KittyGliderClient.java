package dev.bebomny.kittyglider.client;

import dev.bebomny.kittyglider.helpers.ScreenEventHandler;
import dev.bebomny.kittyglider.modules.KittyGliderSpeedControl;
import dev.bebomny.kittyglider.notifications.NotificationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KittyGliderClient implements ClientModInitializer {

    public MinecraftClient client;
    public Logger LOGGER = LoggerFactory.getLogger("KittyGlider");
    static KittyGliderClient INSTANCE;
    public NotificationHandler notifier;

    public KittyGliderSpeedControl kittyGliderSpeedControl;

    @Override
    public void onInitializeClient() {
        if(INSTANCE == null) INSTANCE = this;
        if(this.client == null) this.client = MinecraftClient.getInstance();

        //handlers
        this.notifier = new NotificationHandler(client);
        ScreenEventHandler.register();

        //modules
        kittyGliderSpeedControl = new KittyGliderSpeedControl(client, getINSTANCE());

        LOGGER.atInfo().log("KittyGlider Initialized");
    }

    public static KittyGliderClient getINSTANCE() {
        return INSTANCE;
    }
}
