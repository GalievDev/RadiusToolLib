package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.util.RadiusUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class RadiusSword extends SwordItem {
    private int radius = 1;
    private int radiusDamage = 1;
    public RadiusSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int radius, int radiusDamage) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.radius = radius;
        this.radiusDamage = radiusDamage;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return RadiusUtil.radiusHit(attacker, radius, radiusDamage);
    }
}
