package io.srock.clientprac.util;

import com.mojang.authlib.GameProfile;
import io.srock.clientprac.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class PracEntity extends EntityPlayerSP {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static NetHandlerPlayClient networkHandler = new NetHandlerPlayClient(
            mc, mc.currentScreen, mc.getNetHandler().getNetworkManager(), new GameProfile(UUID.randomUUID(), "PracEntity")
    ) {
        @Override
        public void addToSendQueue(Packet p_147297_1_) {}
    };

    public static ItemStack CheckpointItem = new ItemStack(Items.dye, 1, 1)
            .setStackDisplayName("§aGo to checkpoint");

    public static ItemStack SetCheckpointItem = new ItemStack(Items.emerald, 1)
            .setStackDisplayName("§aSet checkpoint");

    public static ItemStack ToggleFlyItem = new ItemStack(Items.feather, 1)
            .setStackDisplayName("§aToggle fly");

    public PracPosition checkpoint = new PracPosition(mc.thePlayer);

    private final ItemStack[] oldItems;

    public PracEntity() {
        super(mc, mc.theWorld, networkHandler, mc.thePlayer.getStatFileWriter());
        this.inventory = mc.thePlayer.inventory;

        // Store old items
        this.oldItems = this.inventory.mainInventory.clone();

        // Set the prac items
        this.inventory.setInventorySlotContents(0, CheckpointItem);
        this.inventory.setInventorySlotContents(1, SetCheckpointItem);
        this.inventory.setInventorySlotContents(2, ToggleFlyItem);

        // Copy capabilities
        this.capabilities.allowFlying = mc.thePlayer.capabilities.allowFlying;
        this.capabilities.isFlying = mc.thePlayer.capabilities.isFlying;
        this.capabilities.setPlayerWalkSpeed(mc.thePlayer.capabilities.getWalkSpeed());
        this.capabilities.setFlySpeed(mc.thePlayer.capabilities.getFlySpeed());

        // Copy potion effects
        mc.thePlayer.getActivePotionEffects().forEach(this::addPotionEffect);
    }

    public void spawn() {
        worldObj.spawnEntityInWorld(this);
        this.goToCheckpoint();
    }

    public void despawn() {
        worldObj.removeEntity(this);

        // Restore old items
        mc.thePlayer.inventory.mainInventory = oldItems;
    }

    public void goToCheckpoint() {
        this.teleport(this.checkpoint);
    }

    public void teleport(@NotNull PracPosition position) {
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;

        this.setPositionAndRotation(
                position.x,
                position.y,
                position.z,
                position.yaw,
                position.pitch
        );
    }

    public void onRightClick(ItemStack item) {
        String feedback = "";

        if (ItemStack.areItemStacksEqual(item, CheckpointItem)) {
            this.goToCheckpoint();
            feedback = "§aTeleported back to checkpoint!";
        }
        else if (ItemStack.areItemStacksEqual(item, SetCheckpointItem)) {
            this.checkpoint = new PracPosition(this);
            feedback = "§aSet new checkpoint!";
        }
        else if (ItemStack.areItemStacksEqual(item, ToggleFlyItem)) {
            this.capabilities.allowFlying = !this.capabilities.allowFlying;

            if (!this.capabilities.allowFlying) {
                this.capabilities.isFlying = false;
            }

            feedback = "§aToggled flight!";
        }

        if (Config.sendFeedback) {
            this.addChatMessage(new ChatComponentText(feedback));
        }
    }
}
