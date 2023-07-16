package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "isCurrentViewEntity", at = @At("HEAD"), cancellable = true)
    public void injectIsCurrentViewEntity(CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled && this.equals(ClientPrac.INSTANCE.pracEntity)) {
            cir.setReturnValue(true);
        }
    }

    /**
     * @author Duckulus
     * @reason Keep sending Player packets while in prac
     */
    @Redirect(
            method = "onUpdateWalkingPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"
            )
    )
    public boolean isViewEntity(EntityPlayerSP instance) {
        return instance == ClientPrac.mc.thePlayer;
    }
}
