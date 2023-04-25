package dev.galiev.rt_lib.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusPickaxe extends RadiusTool {
    public RadiusPickaxe(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings, int radius) {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings, radius);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            int radius = getRadius();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            for (int i = x - 1; i <= x + radius; i++) {
                for (int j = y - 1; j <= y + radius; j++) {
                    for (int k = z - 1; k <= z + radius; k++) {
                        BlockPos targetPos = new BlockPos(i, j, k);
                        BlockState targetState = world.getBlockState(targetPos);
                        if (targetState.getHardness(world, targetPos) != 0.0F && canMine(targetState, world, targetPos, (PlayerEntity) miner)) {
                            world.breakBlock(targetPos, true, miner);
                            stack.damage(1, miner, e -> e.sendToolBreakStatus(miner.getActiveHand()));
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
