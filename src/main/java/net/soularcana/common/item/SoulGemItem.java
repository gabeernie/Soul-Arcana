package net.soularcana.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import net.soularcana.SoulArcana;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulGemItem extends Item
{
    public SoulGemItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        if (!stack.hasNbt() || !stack.getNbt().contains("filled"))
            return super.getTranslationKey(stack);
        return super.getTranslationKey(stack) + ".filled";
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return stack.hasNbt() && stack.getNbt().contains("filled") || super.hasGlint(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        if (stack.hasNbt() && stack.getNbt().contains("filled"))
            tooltip.add(new TranslatableText(SoulArcana.MODID + ".soul_gem.lore", Text.Serializer.fromJson(stack.getNbt().getString("filled"))));
    }
}
