package dev.bebomny.kittyglider.mixin;

import dev.bebomny.kittyglider.client.KittyGliderClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRenderInit(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        KittyGliderClient.getINSTANCE().notifier.onRenderInit(matrices, tickDelta);
    }
}
