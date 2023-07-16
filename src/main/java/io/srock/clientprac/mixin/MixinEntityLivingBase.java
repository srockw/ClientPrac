package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {

    /**
     * @author Duckulus
     * @reason Cancel the client-side swing animation while in prac
     */
    @Inject(method = "swingItem", at = @At("HEAD"), cancellable = true)
    public void onSwing(CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled && this.equals(ClientPrac.mc.thePlayer)) {
            ci.cancel();
        }
    }
}
