package net.soularcana.common.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.soularcana.common.item.SpellGlobeItem;
import net.soularcana.common.setup.SoulArcanaEnchantments;
import net.soularcana.common.setup.SoulArcanaItems;
import net.soularcana.common.setup.SoulArcanaRecipes;

public class GlobeToStaffRecipe extends SmithingRecipe
{
    public GlobeToStaffRecipe(Identifier id)
    {
        super(id,
                Ingredient.ofItems(SoulArcanaItems.STAFF),
                createAddition(),
                new ItemStack(SoulArcanaItems.STAFF));
    }

    private static Ingredient createAddition()
    {
        return Ingredient.ofStacks(
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.ARCANE),
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.FIRE),
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.FROST),
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.CURSE),
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.POISON),
                SpellGlobeItem.getGlobeStack(SoulArcanaEnchantments.STORM)
        );
    }

    @Override
    public ItemStack craft(Inventory inventory)
    {
        var globeStack = inventory.getStack(1);

        var stack = ((SpellGlobeItem) globeStack.getItem()).getStaffStack(globeStack);
        stack.setDamage(globeStack.getDamage());
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SoulArcanaRecipes.GLOBE_TO_STAFF_SERIALIZER;
    }
}
