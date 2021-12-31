package net.soularcana.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.soularcana.common.setup.SoulArcanaRecipes;

public class DurabilityAwareShapelessRecipe extends ShapelessRecipe
{
    private final boolean sumInputDurability;

    public DurabilityAwareShapelessRecipe(Identifier id,
                                          String group,
                                          ItemStack output,
                                          DefaultedList<Ingredient> input,
                                          boolean sumInputDurability)
    {
        super(id, group, output, input);

        this.sumInputDurability = sumInputDurability;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory)
    {
        var result = getOutput().copy();
        if (sumInputDurability)
        {
            var damageSum = 0;

            for (int i = 0; i < craftingInventory.size(); i++)
                damageSum += craftingInventory.getStack(i).getMaxDamage() - craftingInventory.getStack(i).getDamage();
            result.setDamage(result.getMaxDamage() - damageSum);
        }
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SoulArcanaRecipes.DURABILITY_AWARE_SHAPELESS_SERIALIZER;
    }

    public static class Serializer extends ShapelessRecipe.Serializer
    {
        public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject)
        {
            var group = JsonHelper.getString(jsonObject, "group", "");
            var ingredients = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));

            if (ingredients.isEmpty())
                throw new JsonParseException("No ingredients for shapeless recipe");
            if (ingredients.size() > 9)
                throw new JsonParseException("Too many ingredients for shapeless recipe");

            var output = DurabilityIngredientUtil.outputFromJSON(JsonHelper.getObject(jsonObject, "result"));
            return new DurabilityAwareShapelessRecipe(identifier, group, output.output(), ingredients, output.sumInputDurability());
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json)
        {
            var ingredients = DefaultedList.<Ingredient>of();

            for (int i = 0; i < json.size(); ++i)
            {
                var ingredient = DurabilityIngredientUtil.ingredientFromJSON(json.get(i));
                if (!ingredient.isEmpty())
                    ingredients.add(ingredient);
            }
            return ingredients;
        }
    }
}
