package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {

    /**
     * @author Duckulus
     * @reason Allow the entity to rotate when moving the camera
     */
    @Inject(method = "setAngles", at = @At("HEAD"), cancellable = true)
    public void injectSetAngles(float yaw, float pitch, CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled && this.equals(ClientPrac.mc.thePlayer)) {
            ClientPrac.INSTANCE.pracEntity.setAngles(yaw, pitch);
            ci.cancel();
        }
    }
}
