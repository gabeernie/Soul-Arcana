package net.soularcana.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.soularcana.common.setup.SoulArcanaItems;

public class SoulArcanaREIPlugin implements REIClientPlugin
{
    @Override
    public void registerItemComparators(ItemComparatorRegistry registry)
    {
        registry.register(this::distinctByEnchantment, SoulArcanaItems.SPELL_GLOBE);
    }

    private long distinctByEnchantment(ComparisonContext context, ItemStack stack)
    {
        return EnchantmentHelper.get(stack).hashCode();
    }
}
