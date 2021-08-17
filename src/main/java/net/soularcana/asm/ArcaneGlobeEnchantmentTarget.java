package net.soularcana.asm;

import net.minecraft.item.Item;
import net.soularcana.common.item.SpellGlobeItem;
import net.soularcana.mixin.EnchantmentTargetMixin;

public class ArcaneGlobeEnchantmentTarget extends EnchantmentTargetMixin
{
    @Override
    public boolean isAcceptableItem(Item item)
    {
        return item instanceof SpellGlobeItem;
    }
}
