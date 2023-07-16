package io.srock.clientprac.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ClientPracConfigGui extends GuiConfig {
    public ClientPracConfigGui(GuiScreen parent) {
        super(parent, new ConfigElement(Config.getDefaultCategory()).getChildElements(), "clientprac", false, false, "ClientPrac Settings");
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Config.update();
    }
}
