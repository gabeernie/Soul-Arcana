package net.soularcana.common.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.soularcana.common.item.SpellGlobeItem;
import net.soularcana.common.item.SpellStaffItem;
import net.soularcana.common.setup.SoulArcanaItems;
import net.soularcana.common.setup.SoulArcanaRecipes;

import java.util.ArrayList;

public class GlobeStaffRepairRecipe extends SpecialCraftingRecipe
{
    public GlobeStaffRepairRecipe(Identifier id)
    {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world)
    {
        var matches = new ArrayList<ItemStack>();

        for (int i = 0; i < inventory.size(); i++)
        {
            var stack = inventory.getStack(i);

            if (stack.isEmpty())
                continue;

            if (SoulArcanaItems.TAG_SOUL_GEMS.contains(stack.getItem()) && stack.getDamage() != stack.getMaxDamage())
            {
                if (matches.size() == 1 && SoulArcanaItems.TAG_SOUL_GEMS.contains(matches.get(0).getItem()))
                    break;
                matches.add(stack);
            }
            else if (stack.getItem() instanceof SpellStaffItem || stack.getItem() instanceof SpellGlobeItem)
            {
                if (matches.size() == 1 && (matches.get(0).getItem() instanceof SpellStaffItem || matches.get(0).getItem() instanceof SpellGlobeItem))
                    break;
                matches.add(stack);
            }
        }
        return matches.size() == 2;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory)
    {
        var matches = new ArrayList<ItemStack>();

        for (int i = 0; i < inventory.size(); i++)
        {
            var stack = inventory.getStack(i);

            if (stack.isEmpty())
                continue;

            if (SoulArcanaItems.TAG_SOUL_GEMS.contains(stack.getItem()) && stack.getDamage() != stack.getMaxDamage())
            {
                if (matches.size() == 1 && SoulArcanaItems.TAG_SOUL_GEMS.contains(matches.get(0).getItem()))
                    return ItemStack.EMPTY;
                matches.add(stack);
            }
            else if (stack.getItem() instanceof SpellStaffItem || stack.getItem() instanceof SpellGlobeItem)
            {
                if (matches.size() == 1 && (matches.get(0).getItem() instanceof SpellStaffItem || matches.get(0).getItem() instanceof SpellGlobeItem))
                    return ItemStack.EMPTY;
                matches.add(stack);
            }
        }

        if (matches.size() != 2)
            return ItemStack.EMPTY;

        ItemStack toRepair;
        ItemStack ingredient;

        if (SoulArcanaItems.TAG_SOUL_GEMS.contains(matches.get(0).getItem()))
        {
            toRepair = matches.get(1).copy();
            ingredient = matches.get(0);
        }
        else
        {
            toRepair = matches.get(0).copy();
            ingredient = matches.get(1);
        }

        var durabilityToRepair = ingredient.getMaxDamage() - ingredient.getDamage();
        durabilityToRepair *= 4;
        toRepair.setDamage(toRepair.getDamage() - durabilityToRepair);

        return toRepair;
    }

    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory)
    {
        var remainderList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < remainderList.size(); ++i)
        {
            var item = inventory.getStack(i).getItem();
            if (item.hasRecipeRemainder())
                remainderList.set(i, new ItemStack(item.getRecipeRemainder()));
            else if (SoulArcanaItems.TAG_SOUL_GEMS.contains(item))
            {
                var soulGem = new ItemStack(item);
                var staff = ItemStack.EMPTY;

                for (int j = 0; j < inventory.size(); j++)
                {
                    var stack = inventory.getStack(j);

                    if (stack.isEmpty())
                        continue;

                    if (stack.getItem() instanceof SpellStaffItem || stack.getItem() instanceof SpellGlobeItem)
                    {
                        staff = stack;
                        break;
                    }
                }

                if (staff.isEmpty())
                    continue;

                var durabilityToRepair = staff.getDamage() / 4;
                soulGem.setDamage(Math.max(soulGem.getMaxDamage(), soulGem.getDamage() + durabilityToRepair));
                remainderList.set(i, soulGem);
            }
        }

        return remainderList;
    }

    @Override
    public boolean fits(int width, int height)
    {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SoulArcanaRecipes.GLOBE_STAFF_REPAIR_SERIALIZER;
    }
}
