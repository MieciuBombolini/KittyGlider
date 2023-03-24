package dev.bebomny.kittyglider.modules;

import dev.bebomny.kittyglider.client.KittyGliderClient;
import dev.bebomny.kittyglider.notifications.Notification;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class KittyGliderSpeedControl {

    MinecraftClient client;
    KittyGliderClient modKittyGliderClient;
    boolean enabled;
    boolean instantFlyEnabled;
    private int jumpTimer;

    public KittyGliderSpeedControl(MinecraftClient client, KittyGliderClient modKittyGliderClient) {
        this.client = client;
        this.modKittyGliderClient = modKittyGliderClient;
        this.enabled = false;
        this.instantFlyEnabled = false;
        this.jumpTimer = 0;

        ClientTickEvents.END_CLIENT_TICK.register(this::onUpdate);
    }

    private void onUpdate(MinecraftClient client) {
        if(client.player == null || !isEnabled())
            return;

        if(jumpTimer > 0)
            jumpTimer--;

        ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
        if(chest.getItem() != Items.ELYTRA)
            return;

        if(client.player.isFallFlying()) {
            if(client.player.isTouchingWater()) {
                sendStartStopPacket();
                return;
            }

            float yaw = (float)Math.toRadians(client.player.getYaw());
            Vec3d forward = new Vec3d(-MathHelper.sin(yaw) * 0.05, 0, MathHelper.cos(yaw) * 0.05);

            Vec3d velocity = client.player.getVelocity();

            if(client.options.forwardKey.isPressed())
                client.player.setVelocity(velocity.add(forward));
            else if(client.options.backKey.isPressed())
                client.player.setVelocity(velocity.subtract(forward));

            return;
        }

        if(ElytraItem.isUsable(chest) && client.options.jumpKey.isPressed())
            doInstantFly();
    }

    private void doInstantFly() {
        if(!instantFlyEnabled)
            return;

        if(jumpTimer > 0) {
            jumpTimer = 20;
            client.player.setJumping(false);
            client.player.setSprinting(true);
            client.player.jump();
        }

        sendStartStopPacket();
    }

    private void sendStartStopPacket() {
        if(client.player == null) return;

        ClientCommandC2SPacket packet = new ClientCommandC2SPacket(client.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING);
        client.player.networkHandler.sendPacket(packet);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if(enabled)
            modKittyGliderClient.notifier.newNotification(new Notification(Text.literal("KittyGliderSpeedControl" + " Enabled"), new Color(0x00FF00), 60));
        else
            modKittyGliderClient.notifier.newNotification(new Notification(Text.literal("KittyGliderSpeedControl" + " Disabled"), new Color(0xFF0000), 60));
    }

    public void setInstantFly(boolean instantFly) {
        this.instantFlyEnabled = instantFly;

        if(instantFlyEnabled)
            modKittyGliderClient.notifier.newNotification(new Notification(Text.literal("InstantFly" + " Enabled"), new Color(0x00FF00), 40));
        else
            modKittyGliderClient.notifier.newNotification(new Notification(Text.literal("InstantFly" + " Disabled"), new Color(0xFF0000), 40));
    }

    public boolean isInstantFlyEnabled() {
        return instantFlyEnabled;
    }
}
