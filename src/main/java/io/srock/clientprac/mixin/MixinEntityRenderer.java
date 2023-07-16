package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    /**
     * @author Duckulus and Srock
     * @reason Do not draw block outlines while in freecam to indicate that you are not able to place blocks
     */
    @Inject(method = "isDrawBlockOutline", at = @At("HEAD"), cancellable = true)
    public void onDrawBlockOutline(CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            cir.setReturnValue(false);
        }
    }
}
