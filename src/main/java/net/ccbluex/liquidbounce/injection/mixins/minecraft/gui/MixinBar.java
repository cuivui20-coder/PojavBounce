package net.ccbluex.liquidbounce.injection.mixins.minecraft.gui;

import net.ccbluex.liquidbounce.integration.theme.component.ComponentOverlay;
import net.ccbluex.liquidbounce.integration.theme.component.FeatureTweak;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.Bar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bar.class)
public class MixinBar {
    @Inject(method = "drawExperienceLevel", at = @At("HEAD"), cancellable = true)
    private static void injectDisableExpBar(DrawContext context, TextRenderer textRenderer, int level, CallbackInfo ci) {
        if (ComponentOverlay.isTweakEnabled(FeatureTweak.DISABLE_STATUS_BAR)) {
            ci.cancel();
        }
    }
}
