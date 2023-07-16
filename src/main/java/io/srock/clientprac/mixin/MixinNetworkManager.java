package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    /**
     * @author Duckulus and Srock
     * @reason Cancel packets that could tell a server you're doing something bad O_O
     */
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onSendPacket(Packet packetIn, CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled && clientprac$shouldCancel(packetIn)) {
            ci.cancel();
        }
    }

    @Unique
    private boolean clientprac$shouldCancel(Packet packet) {
        return packet instanceof C07PacketPlayerDigging
                || packet instanceof C0APacketAnimation
                || packet instanceof C02PacketUseEntity;
    }
}
