package io.srock.clientprac;

import io.srock.clientprac.config.Config;
import io.srock.clientprac.util.PracEntity;
import jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(
        modid = "clientprac",
        useMetadata=true,
        guiFactory = "io.srock.clientprac.config.GuiFactory"
)
public class ClientPrac {
    public static Minecraft mc;
    public static KeyBinding PracKeyBind = new KeyBinding("Toggle Prac", Keyboard.KEY_P, "ClientPrac");
    public static ClientPrac INSTANCE;

    public boolean pracEnabled = false;
    public PracEntity pracEntity;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        mc = Minecraft.getMinecraft();
        INSTANCE = this;

        ClientRegistry.registerKeyBinding(PracKeyBind);
        MinecraftForge.EVENT_BUS.register(this);

        Config.initConfig();
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (PracKeyBind.isPressed()) {
            this.togglePrac(null);
        }
    }

    public void togglePrac(@Nullable Boolean fixed) {
        pracEnabled = fixed == null ? !pracEnabled : fixed;

        if (!pracEnabled) {
            if (pracEntity != null) {
                pracEntity.despawn();

                // Bring back the movement to the player
                mc.thePlayer.movementInput = pracEntity.movementInput;
            }

            mc.setRenderViewEntity(mc.thePlayer);
        }
        else {
            pracEntity = new PracEntity();
            pracEntity.spawn();

            // Switch movement input so that the pracEntity can move
            pracEntity.movementInput = new MovementInputFromOptions(mc.gameSettings);
            mc.thePlayer.movementInput = new MovementInput();

            mc.setRenderViewEntity(pracEntity);

            // Prevent player from continue moving if prac was enabled
            // during movement
            mc.thePlayer.moveForward = 0;
            mc.thePlayer.moveStrafing = 0;
            mc.thePlayer.setJumping(false);
        }
    }
}
