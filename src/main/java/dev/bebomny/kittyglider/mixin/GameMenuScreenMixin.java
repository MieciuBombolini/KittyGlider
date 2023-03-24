package dev.bebomny.kittyglider.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.systems.RenderSystem;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    private static final Identifier kittyTexture = new Identifier("kittyglider", "icon.png");

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At("HEAD")}, method = {"render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V"})
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
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
    }
}
