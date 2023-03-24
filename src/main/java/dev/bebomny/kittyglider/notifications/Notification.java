package dev.bebomny.kittyglider.notifications;

import dev.bebomny.kittyglider.client.KittyGliderClient;
import net.minecraft.text.Text;

import java.awt.*;

public class Notification {

    private final Text text;
    private final float offset;
    private final Color color;
    private final int duration;

    public Notification(Text text, int duration) {
        this.text = text;
        this.duration = duration;
        this.color = new Color(0xFFFFFF);
        this.offset = KittyGliderClient.getINSTANCE().client.advanceValidatingTextRenderer.getWidth(getText())/2f;
    }

    public Notification(Text text, Color color, int duration) {
        this.text = text;
        this.duration = duration;
        this.color = color;
        //this.offset = BeaverUtilsClient.getInstance().client.textRenderer.getWidth(text.toString()) / 2f;
        this.offset = KittyGliderClient.getINSTANCE().client.advanceValidatingTextRenderer.getWidth(getText())/2f;
    }

    public Text getText() {
        return text;
    }

    public float getOffset() {
        return offset;
    }

    public int getDuration() {
        return duration;
    }

    public Color getColor() {
        return color;
    }

    public int getColorAsInt() {
        return color.getRGB();
    }
}
