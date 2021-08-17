package net.soularcana.common.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.soularcana.common.setup.SoulArcanaRecipes;

import java.util.Map;

public class DurabilityAwareShapedRecipe extends ShapedRecipe
{
    public DurabilityAwareShapedRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> input, ItemStack output)
    {
        super(id, group, width, height, input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SoulArcanaRecipes.DURABILITY_AWARE_SHAPED_SERIALIZER;
    }

    public static class Serializer extends ShapedRecipe.Serializer
    {
        public ShapedRecipe read(Identifier identifier, JsonObject jsonObject)
        {
            var group = JsonHelper.getString(jsonObject, "group", "");
            var ingredientBySymbolMap = readSymbols(JsonHelper.getObject(jsonObject, "key"));
            var patterns = ShapedRecipe.removePadding(ShapedRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern")));

            int width = patterns[0].length();
            int height = patterns.length;
            var defaultedList = ShapedRecipe.createPatternMatrix(patterns, ingredientBySymbolMap, width, height);
            var result = DurabilityIngredientUtil.outputFromJSON(JsonHelper.getObject(jsonObject, "result"));

            return new ShapedRecipe(identifier, group, width, height, defaultedList, result);
        }

        private static Map<String, Ingredient> readSymbols(JsonObject json)
        {
            Map<String, Ingredient> ingredientBySymbolMap = Maps.newHashMap();

            for (var jsonEntry : json.entrySet())
            {
                if (jsonEntry.getKey().length() != 1)
                    throw new JsonSyntaxException("Invalid key entry: '" + jsonEntry.getKey() + "' is an invalid symbol (must be 1 character only).");

                if (" ".equals(jsonEntry.getKey()))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

                ingredientBySymbolMap.put(jsonEntry.getKey(), DurabilityIngredientUtil.ingredientFromJSON(jsonEntry.getValue()));
            }

            ingredientBySymbolMap.put(" ", Ingredient.EMPTY);
            return ingredientBySymbolMap;
        }
    }
}
