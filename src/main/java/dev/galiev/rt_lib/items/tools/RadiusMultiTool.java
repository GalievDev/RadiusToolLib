package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.util.RadiusUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiusMultiTool extends MiningToolItem {
    private int range = 1;
    private int radius = 1;
    private int radiusDamage = 1;

    public RadiusMultiTool(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings, int range, int radius, int radiusDamage) {
        super(attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings);
        this.range = range;
        this.radius = radius;
        this.radiusDamage = radiusDamage;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state);
    }



    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return RadiusUtil.breakBlocks(stack, world, pos, miner, range);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return RadiusUtil.radiusHit(attacker, radius, radiusDamage);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }
}
