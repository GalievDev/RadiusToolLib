package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.RadiusToolItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusPickaxe extends RadiusToolItem {
    public RadiusPickaxe(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings, int radius) {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings, radius);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var player = (PlayerEntity) miner;
        if (!player.isSneaking() && state.isToolRequired() && this.isSuitableFor(state)) {
            int range = getRadius();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            for (int i = x - range; i <= x + range; i++) {
                for (int j = y - range; j <= y + range; j++) {
                    for (int k = z - range; k <= z + range; k++) {

                        var targetPos = new BlockPos(i, j, k);
                        var targetState = world.getBlockState(targetPos);

                        if (targetState.isToolRequired() && this.isSuitableFor(targetState)) {
                            if (!player.isCreative()) {
                                stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
                            }

                            world.breakBlock(targetPos, true, player);
                        }
                    }
                }
            }

            return true;
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
