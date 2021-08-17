package net.soularcana.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.experimental.UtilityClass;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class DurabilityIngredientUtil
{
    public ItemStack outputFromJSON(JsonObject json)
    {
        var item = ShapedRecipe.getItem(json);
        var stack = new ItemStack(item);

        if (json.has("durability"))
            stack.setDamage(stack.getMaxDamage() - json.get("durability").getAsInt());

        return stack;
    }

    public Ingredient ingredientFromJSON(@Nullable JsonElement json)
    {
        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Item cannot be null");

        if (!json.isJsonObject())
            throw new JsonSyntaxException("Expected item to be object");

        var jsonObject = json.getAsJsonObject();

        if (!jsonObject.has("item"))
            throw new JsonSyntaxException("Only Item key is supported Tags are not allowed for DurabilityAwareShapelessRecipe.");

        var item = ShapedRecipe.getItem(jsonObject);
        var stack = new ItemStack(item);

        if (jsonObject.has("durability"))
            stack.setDamage(stack.getMaxDamage() - jsonObject.get("durability").getAsInt());

        return Ingredient.ofStacks(stack);
    }
}
