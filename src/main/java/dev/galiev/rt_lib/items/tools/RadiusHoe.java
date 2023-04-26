package dev.galiev.rt_lib.items.tools;

import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class RadiusHoe extends HoeItem {
    private int range = 1;
    public RadiusHoe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, int range) {
        super(material, attackDamage, attackSpeed, settings);
        this.range = range;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var player = context.getPlayer();
        var blockPos = context.getBlockPos();
        var setBlock = Blocks.FARMLAND.getDefaultState();

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
