package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S07PacketRespawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    /**
     * @author Duckulus and Srock
     * @reason Disable prac when joining a world or respawning
     */
    @Inject(method = "handleRespawn", at = @At("TAIL"))
    public void onJoin(S07PacketRespawn packetIn, CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            ClientPrac.INSTANCE.togglePrac(Boolean.FALSE);
        }
    }
}
