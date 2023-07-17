package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import io.srock.clientprac.config.Config;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public class MixinRenderManager {
    @Inject(method = "doRenderEntity", at = @At("HEAD"), cancellable = true)
    public void onRenderEntity(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, boolean p_147939_10_, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled && Config.hideEntities) {
            cir.setReturnValue(true);
        }
    }
}
