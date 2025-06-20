package net.ccbluex.liquidbounce.injection.mixins.minecraft.gui;

import net.ccbluex.liquidbounce.integration.theme.component.ComponentOverlay;
import net.ccbluex.liquidbounce.integration.theme.component.FeatureTweak;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.ExperienceBar;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBar.class)
public class MixinExperienceBar {
    @Inject(method = "renderBar", at = @At("HEAD"), cancellable = true)
    private void injectDelete(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ComponentOverlay.isTweakEnabled(FeatureTweak.DISABLE_STATUS_BAR)) {
            ci.cancel();
        }
    }
}
