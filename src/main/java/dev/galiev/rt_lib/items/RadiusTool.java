package dev.galiev.rt_lib.items;

import net.minecraft.block.Block;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

public class RadiusTool extends MiningToolItem {
    private int radius = 1;

    public RadiusTool(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings, int radius) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
        this.radius = radius;
    }

    public RadiusTool(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    public int getRadius(){
        return radius;
    }
}