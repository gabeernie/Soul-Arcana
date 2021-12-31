package net.soularcana.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class SoulGemItem extends Item
{
    public SoulGemItem(Settings settings, int maxDurability)
    {
        super(settings.maxDamage(maxDurability));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks)
    {
        if (this.isIn(group))
        {
            var soulGem = new ItemStack(this);
            soulGem.setDamage(soulGem.getMaxDamage());
            stacks.add(soulGem);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return stack.hasNbt() && stack.getDamage() != stack.getMaxDamage() || super.hasGlint(stack);
    }
}
