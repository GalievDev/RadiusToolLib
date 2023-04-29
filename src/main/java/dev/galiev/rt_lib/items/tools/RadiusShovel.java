package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.util.RadiusUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
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
        var player = context.getPlayer();
        var setBlock = Blocks.DIRT_PATH.getDefaultState();

        if (player != null && !player.isSneaking()) {
            RadiusUtil.setBlock(context, setBlock, player, SoundEvents.ITEM_SHOVEL_FLATTEN, range);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
