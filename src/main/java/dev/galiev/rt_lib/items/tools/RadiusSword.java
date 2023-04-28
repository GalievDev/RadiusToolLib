package dev.galiev.rt_lib.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;

import java.util.List;

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
        if (attacker instanceof PlayerEntity player) {
            var world = player.getWorld();
            var blockPos = player.getBlockPos();

            List<Entity> entities = world.getEntitiesByClass(Entity.class, new Box(blockPos).expand(radius), entity ->
                    entity instanceof LivingEntity && entity != attacker);

            for (var entity : entities){
                entity.damage(world.getDamageSources().playerAttack(player), radiusDamage);

                world.addParticle(ParticleTypes.SPLASH, entity.getX(), entity.getY(), entity.getZ(), 0.2d,0.1d,0.3d);
            }
            return true;
        }
        return false;
    }
}
