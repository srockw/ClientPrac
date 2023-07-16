package io.srock.clientprac.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}
