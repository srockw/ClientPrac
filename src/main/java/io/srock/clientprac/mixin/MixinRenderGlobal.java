package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import io.srock.clientprac.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {
    /**
     * @author Duckulus
     * @reason Allow the player to be rendered while in prac
     */
    @Redirect(
            method = "renderEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;",
                    ordinal = 1
            )
    )
    public Entity getRenderViewEntity(Minecraft instance) {
        return instance.thePlayer;
    }

    /**
     * @author Srock
     * @reason Allow entities to be hidden should the player want so
     */
    @Inject(method = "renderEntities", at = @At("HEAD"), cancellable = true)
    public void injectRenderEntities(Entity renderViewEntity, ICamera camera, float partialTicks, CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled && Config.hideEntities) {
            ci.cancel();
        }
    }
}
