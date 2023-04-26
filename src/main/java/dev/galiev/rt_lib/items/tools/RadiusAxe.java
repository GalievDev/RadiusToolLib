package dev.galiev.rt_lib.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusAxe extends AxeItem {
    private int range = 1;
    public RadiusAxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings, int range) {
        super(material, attackDamage, attackSpeed, settings);
        this.range = range;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var player = (PlayerEntity) miner;
        if (!player.isSneaking()) {
            for (BlockPos targetPos :  BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range))){
                var targetState = world.getBlockState(targetPos);

                if (targetState.isToolRequired() && this.isSuitableFor(targetState)) {
                    world.breakBlock(targetPos, true, player);
                }
                return true;
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
