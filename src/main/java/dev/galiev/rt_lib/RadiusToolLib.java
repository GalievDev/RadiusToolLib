package dev.galiev.rt_lib;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class RadiusToolLib implements ModInitializer {
    public static final String MOD_ID = "rt_lib";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + "Loaded");
    }
}
