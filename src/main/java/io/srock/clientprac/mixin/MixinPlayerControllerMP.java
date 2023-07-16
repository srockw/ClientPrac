package io.srock.clientprac.mixin;

import io.srock.clientprac.ClientPrac;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * @author Duckulus
 * Prevent the pracEntity from interacting with the world
 */
@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
    public void onAttackEntity(EntityPlayer p_attackEntity_1_, Entity p_attackEntity_2_, CallbackInfo ci) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "interactWithEntitySendPacket", cancellable = true)
    public void onInteract(EntityPlayer playerIn, Entity targetEntity, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onPlayerDamageBlock", cancellable = true)
    public void onAttackBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "clickBlock", cancellable = true)
    public void onClickBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "onPlayerRightClick", cancellable = true)
    public void onRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            cir.setReturnValue(false);
        }
    }

    /**
     * @reason Send right clicks to the prac entity
     */
    @Inject(method = "sendUseItem", at = @At("HEAD"), cancellable = true)
    public void onUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn, CallbackInfoReturnable<Boolean> cir) {
        if (ClientPrac.INSTANCE.pracEnabled) {
            ClientPrac.INSTANCE.pracEntity.onRightClick(itemStackIn);
            cir.cancel();
        }
    }
}
