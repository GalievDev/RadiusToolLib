package dev.galiev.rt_lib.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusShovel extends ShovelItem {
    private int range = 1;
    public RadiusShovel(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings, int range) {
        super(material, attackDamage, attackSpeed, settings);
        this.range = range;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var player = (PlayerEntity) miner;
        if (!player.isSneaking()) {
            for (BlockPos targetPos :  BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range))) {
                var targetState = world.getBlockState(targetPos);

                if (targetState.isIn(BlockTags.SHOVEL_MINEABLE)) {
                    if (!player.isCreative()) {
                        stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
                    }
                    world.breakBlock(targetPos, true, player);
                }
            }
            return true;
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var player = context.getPlayer();
        var blockPos = context.getBlockPos();
        var setBlock = Blocks.DIRT_PATH.getDefaultState();

        if (player != null && !player.isSneaking()) {
            int startX = blockPos.getX() - range;
            int startY = blockPos.getY();
            int startZ = blockPos.getZ() - range;
            int endX = blockPos.getX() + range;
            int endZ = blockPos.getZ() + range;

            for (int x = startX; x <= endX; x++) {
                for (int z = startZ; z <= endZ; z++) {
                    var targetPos = new BlockPos(x, startY, z);
                    var targetState = world.getBlockState(targetPos);

                    if (targetState.isIn(BlockTags.DIRT) && setBlock.canPlaceAt(world, targetPos)) {
                        world.setBlockState(targetPos, setBlock);
                        player.getMainHandStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
                    }
                }
            }
            return ActionResult.SUCCESS;

        }

        return ActionResult.PASS;
    }
}
