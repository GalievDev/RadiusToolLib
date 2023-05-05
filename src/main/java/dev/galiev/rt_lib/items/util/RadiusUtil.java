package dev.galiev.rt_lib.items.util;

import dev.galiev.rt_lib.RadiusToolLib;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class RadiusUtil {
    public static boolean breakBlocks(ItemStack stack, World world, BlockPos pos, LivingEntity miner, int range) {

        return false;
    }

    public static void setBlock(ItemUsageContext context, BlockState mutableBlock, PlayerEntity player, SoundEvent sound, int range) {
        var world = context.getWorld();
        var pos = context.getBlockPos();

        if (player != null) {
            int startX = pos.getX() - range;
            int startY = pos.getY();
            int startZ = pos.getZ() - range;
            int endX = pos.getX() + range;
            int endZ = pos.getZ() + range;

            for (int x = startX; x <= endX; x++) {
                for (int z = startZ; z <= endZ; z++) {
                    var targetPos = new BlockPos(x, startY, z);
                    var targetState = world.getBlockState(targetPos);

                    if (targetState.isIn(BlockTags.DIRT) && mutableBlock.canPlaceAt(world, targetPos)) {
                        world.setBlockState(targetPos, mutableBlock);
                        world.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        if (!world.isClient) {
                            player.getMainHandStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
                        }
                    }
                }
            }
        }
    }

    public static boolean radiusHit(LivingEntity attacker, int radius, int radiusDamage) {
        if (attacker instanceof PlayerEntity player) {
            var world = player.getWorld();
            var blockPos = player.getBlockPos();
            RadiusToolLib.LOGGER.info("Is player!");
            List<Entity> entities = world.getEntitiesByClass(Entity.class, new Box(blockPos).expand(radius), entity ->
                    entity instanceof LivingEntity && entity != attacker);

            for (var entity : entities){
                entity.damage(world.getDamageSources().playerAttack(player), radiusDamage);
                RadiusToolLib.LOGGER.info("HIT");
                world.addParticle(ParticleTypes.SPLASH, entity.getX(), entity.getY(), entity.getZ(), 0.2d,0.1d,0.3d);
            }
            return true;
        }

        return false;
    }
}
