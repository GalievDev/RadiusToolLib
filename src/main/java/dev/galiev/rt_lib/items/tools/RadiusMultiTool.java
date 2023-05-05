package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.util.RadiusUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
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
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        var player = (PlayerEntity) miner;
        for (BlockPos targetPos : BlockPos.iterate(pos.add(-range, -range, -range), pos.add(range, range, range))) {
            if (!player.isCreative()) {
                stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
            }
            world.breakBlock(targetPos, true, player);
            return true;
        }
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return RadiusUtil.radiusHit(attacker, radius, radiusDamage);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();
        var dirtPath = Blocks.DIRT_PATH.getDefaultState();
        var farmland = Blocks.FARMLAND.getDefaultState();

        if (player != null) {
            if (!player.isSneaking()) {
                RadiusUtil.setBlock(context, dirtPath, player, SoundEvents.ITEM_SHOVEL_FLATTEN, range);

                return ActionResult.SUCCESS;
            } else {
                RadiusUtil.setBlock(context, farmland, player, SoundEvents.ITEM_HOE_TILL, range);

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }
}
