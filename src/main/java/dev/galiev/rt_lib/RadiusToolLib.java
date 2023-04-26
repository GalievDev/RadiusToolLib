package dev.galiev.rt_lib;

import com.mojang.logging.LogUtils;
import dev.galiev.rt_lib.items.tools.RadiusHoe;
import dev.galiev.rt_lib.items.tools.RadiusPickaxe;
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

    public static final RadiusPickaxe RADIUS_PICKAXE = new RadiusPickaxe(1.0F, 1.0F, ToolMaterials.IRON, new FabricItemSettings(), 1);
    public static final RadiusHoe RADIUS_HOE = new RadiusHoe(1.0F, 1.0F, ToolMaterials.IRON, new FabricItemSettings(), 1);

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + "loaded");

        Registry.register(Registries.ITEM, new Identifier(MOD_ID,"radius_pickaxe"), RADIUS_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID,"radius_hoe"), RADIUS_HOE);
    }
}
