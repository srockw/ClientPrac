package io.srock.clientprac.config;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class Config {
    public static Configuration config = new Configuration(new File("./config/clientprac.cfg"));;
    public static ConfigCategory getDefaultCategory() {
        return config.getCategory(Configuration.CATEGORY_CLIENT);
    }

    private static void initProperty(String key, String displayName, String defaultValue, Property.Type type) {
        if (!getDefaultCategory().containsKey(key))
            getDefaultCategory().put(key, new Property(displayName, defaultValue, type));

        getDefaultCategory().get(key).setDefaultValue(defaultValue);
    }

    public static void initConfig() {
       initProperty("hideEntities", "Hide Entities", "false", Property.Type.BOOLEAN);

       update();
    }

    public static void update() {
        if (config.hasChanged()) {
            config.save();
        }

        hideEntities = getDefaultCategory().get("hideEntities").getBoolean();
    }

    public static boolean hideEntities = false;
}
