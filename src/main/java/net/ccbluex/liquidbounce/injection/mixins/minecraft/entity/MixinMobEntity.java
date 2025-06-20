package net.ccbluex.liquidbounce.injection.mixins.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.ccbluex.liquidbounce.features.module.modules.movement.ModuleEntityControl;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public class MixinMobEntity {

    @ModifyReturnValue(method = "hasSaddleEquipped", at = @At("RETURN"))
    private boolean isSaddled(boolean original) {
        return ModuleEntityControl.getEnforceSaddled() || original;
    }

}
