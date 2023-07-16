package io.srock.clientprac.util;

import net.minecraft.client.entity.EntityPlayerSP;

public class PracPosition {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;

    public PracPosition(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public PracPosition(EntityPlayerSP player) {
        this(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
    }
}
