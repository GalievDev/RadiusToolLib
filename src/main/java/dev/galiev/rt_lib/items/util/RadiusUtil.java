package dev.galiev.rt_lib.items.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusUtil {
    public static void breakBlocks(ItemStack stack, World world, BlockPos pos, LivingEntity miner, int range) {
        var player = (PlayerEntity) miner;
        for (BlockPos targetPos : BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range))) {
            if (!player.isCreative()) {
                stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
            }
            world.breakBlock(targetPos, true, player);
        }
    }

    public static void setBlock(ItemUsageContext context, BlockState mutableBlock, PlayerEntity player, int range) {
        var world = context.getWorld();
        var pos = context.getBlockPos();

        int y = pos.getY();

        if (player != null) {
            for (BlockPos targetPos : BlockPos.iterate(pos.add(-range, y, -range), pos.add(+range, y, +range))) {
                var targetState = world.getBlockState(targetPos);

                if (targetState.isIn(BlockTags.DIRT) && mutableBlock.canPlaceAt(world, targetPos)) {
                    world.setBlockState(targetPos, mutableBlock);
                    player.getMainHandStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
                }
            }
        }
    }
}
