package dev.bebomny.kittyglider.notifications;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationHandler {

    private final MinecraftClient client;
    private List<Notification> notificationQueue = new ArrayList<>();
    private int decay;
    private float yOffset;

    public NotificationHandler(MinecraftClient client) {
        this.client = client;
        this.yOffset = 65f;
        ClientTickEvents.END_CLIENT_TICK.register(this::onUpdate);
    }

    public void newNotification(Notification notification) {
        if(!(notificationQueue.isEmpty()))
            notificationQueue.remove(0);

        notificationQueue.add(notification);
        decay = notification.getDuration();
    }

    public void onRenderInit(MatrixStack matrices, float partialTicks) {
        if(!(notificationQueue.isEmpty())) {
            Notification notification = notificationQueue.get(0);

            //offset / x & y coordinates on the screen
            float offset = notification.getOffset();
            float x = (client.getWindow().getScaledWidth()/2f) - offset;
            float y = client.getWindow().getScaledHeight() - yOffset;

            //Get notification color
            Color color1 = notification.getColor();
            int color = decay < 30 ?
                    ((color1.getRed() / 30) * decay) << 16 | ((color1.getGreen() / 30) * decay) << 8 | ((color1.getBlue() / 30) * decay)
                    :
                    color1.getRed() << 16 | color1.getGreen() << 8 | color1.getBlue();

            int alpha = decay < 30 ? ((0xFF / 30) * decay) << 24 : 0xFF << 24;

            //draw the notification on the screen
            client.inGameHud.getTextRenderer().drawWithShadow(matrices, notification.getText(), x, y, color | alpha);
        }

        if(decay <= 1 && !notificationQueue.isEmpty())
            notificationQueue.remove(0);
    }

    private void onUpdate(MinecraftClient client) {
        if(!(notificationQueue.isEmpty()))
            decay--;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public float getYOffset() {
        return yOffset;
    }
}
