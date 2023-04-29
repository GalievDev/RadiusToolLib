package dev.galiev.rt_lib.items.tools;

import dev.galiev.rt_lib.items.util.RadiusUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class RadiusHoe extends HoeItem {
    private int range = 1;
    public RadiusHoe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, int range) {
        super(material, attackDamage, attackSpeed, settings);
        this.range = range;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var player = context.getPlayer();
        var setBlock = Blocks.FARMLAND.getDefaultState();

        if (player != null && !player.isSneaking()) {
            RadiusUtil.setBlock(context, setBlock, player, SoundEvents.ITEM_HOE_TILL, range);

            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }
}
