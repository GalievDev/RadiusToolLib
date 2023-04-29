package dev.galiev.rt_lib;

import com.mojang.logging.LogUtils;
import dev.galiev.rt_lib.items.tools.RadiusHoe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class RadiusToolLib implements ModInitializer {
    public static final String MOD_ID = "rt_lib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RadiusHoe RADIUS_HOE = new RadiusHoe(ToolMaterials.NETHERITE, 4, 1.0f, new FabricItemSettings(), 1);
    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + "Loaded");
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "radius_pickaxe"), RADIUS_HOE);
    }
}
