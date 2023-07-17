package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import io.srock.clientprac.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

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
