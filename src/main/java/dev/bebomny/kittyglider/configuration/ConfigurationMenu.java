package dev.bebomny.kittyglider.configuration;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bebomny.kittyglider.client.KittyGliderClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class ConfigurationMenu extends Screen {

    private final Screen parent;
    private final KittyGliderClient modKittyGliderClient;
    private final GameOptions settings;
    private static final Identifier kittyTexture = new Identifier("kittyglider", "icon.png");

    public ConfigurationMenu(Screen parent, GameOptions options) {
        super(Text.literal("KittyGlider Options"));
        this.parent = parent;
        this.settings = options;
        this.modKittyGliderClient = KittyGliderClient.getINSTANCE();
    }

    @Override
    protected void init() {
        //Elytra toggle button
        ButtonWidget elytraSpeedControlButton = ButtonWidget.builder(getTextOnOff("KittyGliderSpeedControl", modKittyGliderClient.kittyGliderSpeedControl.isEnabled()),
                button -> {
                    modKittyGliderClient.kittyGliderSpeedControl.setEnabled(!modKittyGliderClient.kittyGliderSpeedControl.isEnabled());
                    button.setMessage(getTextOnOff("KittyGliderSpeedControl", modKittyGliderClient.kittyGliderSpeedControl.isEnabled()));
                }).dimensions(
                (this.width/2) - (164/2),
                20 + (24 + 20) + 20 + 4,
                164,
                20
        ).build();

        //ElytraSpeedControl instantFly
        ButtonWidget elytraSpeedControlInstantFlyButton = ButtonWidget.builder(getTextOnOff("InstantGlide", modKittyGliderClient.kittyGliderSpeedControl.isInstantFlyEnabled()),
                button -> {
                    modKittyGliderClient.kittyGliderSpeedControl.setInstantFly(!modKittyGliderClient.kittyGliderSpeedControl.isInstantFlyEnabled());
                    button.setMessage(getTextOnOff("InstantGlide", modKittyGliderClient.kittyGliderSpeedControl.isInstantFlyEnabled()));
                }).dimensions(
                (this.width/2) - (120/2),
                44 + 20 + 20 + 4 + 20 + 4 + 20 + 4 + 6 + 2,
                120,
                20
        ).build();


        //"Done" button
        ButtonWidget doneButton = ButtonWidget.builder(ScreenTexts.DONE,
                button -> this.client.setScreen(this.parent)).dimensions(
                (this.width/2) - 140,
                this.height - 50,
                280,
                20
        ).build();

        this.addDrawableChild(elytraSpeedControlButton);
        this.addDrawableChild(elytraSpeedControlInstantFlyButton);

        this.addDrawableChild(doneButton);
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        //kittyGlider enabler first line of text
        drawCenteredTextWithShadow(
                matrices, this.textRenderer,
                Text.literal("Control your elytra speed").asOrderedText(),
                this.width/2,
                20 + (24 + 20) - 2, 16777215);
        //second line of text
        drawCenteredTextWithShadow(
                matrices, this.textRenderer,
                Text.literal("with forward and backward keys").asOrderedText(),
                this.width/2,
                20 + (24 + 20) + 10, 16777215);
        //Instant glide first line of text
        drawCenteredTextWithShadow(
                matrices, this.textRenderer,
                Text.literal("Jump once").asOrderedText(),
                this.width/2,
                44 + 20 + 20 + 4 + 16 + 4 + 6 + 2 + 1 + 2 + 1, 16777215);
        //Instant Glide second line of text
        drawCenteredTextWithShadow(
                matrices, this.textRenderer,
                Text.literal("to activate your elytra!").asOrderedText(),
                this.width/2,
                44 + 20 + 20 + 4 + 20 + 4 + 6 + 6 + 2 + 1 + 1 + 2 + 1, 16777215);
        //cat image
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1, 1, 1, 0.3f);

        RenderSystem.setShaderTexture(0, kittyTexture);

        int x = 0;
        int y = 0;
        int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int fw = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int fh = MinecraftClient.getInstance().getWindow().getScaledHeight();
        float u = 0;
        float v = 0;
        drawTexture(matrices, x, y, u, v, w, h, fw, fh);

        super.render(matrices, mouseX, mouseY, delta);

    }

    @Override
    public void close() {
        assert client != null;
        client.setScreen(this.parent);
    }

    private Text getTextOnOff(String name, boolean enabled) {
        return Text.literal(name + (enabled ? " ON" : " OFF"));
    }
}
