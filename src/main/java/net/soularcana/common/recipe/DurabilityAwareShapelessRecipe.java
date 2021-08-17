package net.soularcana.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
    public DurabilityAwareShapelessRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input)
    {
        super(id, group, output, input);
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

            var itemStack = DurabilityIngredientUtil.outputFromJSON(JsonHelper.getObject(jsonObject, "result"));
            return new DurabilityAwareShapelessRecipe(identifier, group, itemStack, ingredients);
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
