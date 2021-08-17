package net.soularcana.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;
import net.soularcana.common.setup.SoulArcanaEnchantments;
import net.soularcana.common.setup.SoulArcanaItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellGlobeItem extends Item
{
    public SpellGlobeItem(Settings settings)
    {
        super(settings.maxDamage(100));
    }

    public static ItemStack getGlobeStack(Enchantment globeEnchantment)
    {
        var stack = new ItemStack(SoulArcanaItems.SPELL_GLOBE);

        stack.addEnchantment(globeEnchantment, 1);
        return stack;
    }

    public static ItemStack getGlobeStack(GlobeType globeType)
    {
        return getGlobeStack(SoulArcanaEnchantments.getEnchantment(globeType));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText(SoulArcana.MODID + ".spell_staff_repair.lore"));
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        if (!stack.hasEnchantments())
            return super.getTranslationKey(stack);

        var enchantments = EnchantmentHelper.fromNbt(stack.getEnchantments());

        if (enchantments.containsKey(SoulArcanaEnchantments.ARCANE))
            return "item." + SoulArcana.MODID + ".arcane_globe";
        else if (enchantments.containsKey(SoulArcanaEnchantments.CURSE))
            return "item." + SoulArcana.MODID + ".curse_globe";
        else if (enchantments.containsKey(SoulArcanaEnchantments.FIRE))
            return "item." + SoulArcana.MODID + ".fire_globe";
        else if (enchantments.containsKey(SoulArcanaEnchantments.FROST))
            return "item." + SoulArcana.MODID + ".frost_globe";
        else if (enchantments.containsKey(SoulArcanaEnchantments.POISON))
            return "item." + SoulArcana.MODID + ".poison_globe";
        else if (enchantments.containsKey(SoulArcanaEnchantments.STORM))
            return "item." + SoulArcana.MODID + ".storm_globe";

        return super.getTranslationKey(stack);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks)
    {
        if (this.isIn(group))
        {
            var emptyGlobeStack = new ItemStack(this);
            emptyGlobeStack.setDamage(getMaxDamage());

            stacks.add(emptyGlobeStack);

            var arcaneGlobe = getGlobeStack(SoulArcanaEnchantments.ARCANE);
            arcaneGlobe.setDamage(getMaxDamage());
            stacks.add(arcaneGlobe);

            var frostGlobe = getGlobeStack(SoulArcanaEnchantments.FROST);
            frostGlobe.setDamage(getMaxDamage());
            stacks.add(frostGlobe);

            var fireGlobe = getGlobeStack(SoulArcanaEnchantments.FIRE);
            fireGlobe.setDamage(getMaxDamage());
            stacks.add(fireGlobe);

            var stormGlobe = getGlobeStack(SoulArcanaEnchantments.STORM);
            stormGlobe.setDamage(getMaxDamage());
            stacks.add(stormGlobe);

            var poisonGlobe = getGlobeStack(SoulArcanaEnchantments.POISON);
            poisonGlobe.setDamage(getMaxDamage());
            stacks.add(poisonGlobe);

            var curseGlobe = getGlobeStack(SoulArcanaEnchantments.CURSE);
            curseGlobe.setDamage(getMaxDamage());
            stacks.add(curseGlobe);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

    @Override
    public int getEnchantability()
    {
        return 1;
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return false;
    }

    public ItemStack getStaffStack(ItemStack globeStack)
    {
        var enchantmentOpt = EnchantmentHelper.fromNbt(globeStack.getEnchantments()).keySet().stream().filter(e -> e.type == SoulArcanaEnchantments.GLOBE_TYPE).findFirst();

        return enchantmentOpt.map(enchantment ->
        {
            if (enchantment == SoulArcanaEnchantments.ARCANE)
                return new ItemStack(SoulArcanaItems.ARCANE_STAFF);
            else if (enchantment == SoulArcanaEnchantments.FROST)
                return new ItemStack(SoulArcanaItems.FROST_STAFF);
            else if (enchantment == SoulArcanaEnchantments.FIRE)
                return new ItemStack(SoulArcanaItems.FIRE_STAFF);
            else if (enchantment == SoulArcanaEnchantments.CURSE)
                return new ItemStack(SoulArcanaItems.CURSE_STAFF);
            else if (enchantment == SoulArcanaEnchantments.POISON)
                return new ItemStack(SoulArcanaItems.POISON_STAFF);
            else if (enchantment == SoulArcanaEnchantments.STORM)
                return new ItemStack(SoulArcanaItems.STORM_STAFF);
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }
}
