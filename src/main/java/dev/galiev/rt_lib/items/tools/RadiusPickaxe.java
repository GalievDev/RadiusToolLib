package dev.galiev.rt_lib.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusPickaxe extends PickaxeItem {
    private int range = 1;
    public RadiusPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, int range) {
        super(material, attackDamage, attackSpeed, settings);
        this.range = range;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var player = (PlayerEntity) miner;
        if (!player.isSneaking() && state.isToolRequired() && this.isSuitableFor(state)) {
            for (BlockPos targetPos : BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range))) {
                var targetState = world.getBlockState(targetPos);

                if (targetState.isToolRequired() && this.isSuitableFor(targetState)) {
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
}
