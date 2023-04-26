package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.RadiusToolItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class RadiusHoe extends RadiusToolItem {
    public RadiusHoe(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings, int radius) {
        super(attackDamage, attackSpeed, material, BlockTags.HOE_MINEABLE, settings, radius);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var player = context.getPlayer();
        var blockPos = context.getBlockPos();
        var setBlock = Blocks.FARMLAND.getDefaultState();

        if (player != null && !player.isSneaking()) {
            int radius = getRadius();
            int startX = blockPos.getX() - radius;
            int startY = blockPos.getY();
            int startZ = blockPos.getZ() - radius;
            int endX = blockPos.getX() + radius;
            int endZ = blockPos.getZ() + radius;

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

        } else if (player != null && player.isSneaking()) {
            world.setBlockState(blockPos, setBlock);
            player.getMainHandStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
