package net.soularcana.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.soularcana.common.setup.SoulArcanaRecipes;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
public class GlobeToStaffRecipe extends SmithingRecipe
{
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack  result;

    public GlobeToStaffRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result)
    {
        super(id, base, addition, result);

        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public ItemStack craft(Inventory inventory)
    {
        var globeStack = inventory.getStack(1);

        var stack = result.copy();
        stack.setDamage(globeStack.getDamage());
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SoulArcanaRecipes.GLOBE_TO_STAFF_SERIALIZER;
    }

    public static class GlobeToStaffSerializer extends SmithingRecipe.Serializer
    {
        public static Map.Entry<Ingredient, Enchantment> ingredientFromJSON(@Nullable JsonElement json)
        {
            if (json != null && !json.isJsonNull())
            {
                if (json.isJsonObject())
                {
                    var jsonObject = json.getAsJsonObject();
                    if (jsonObject.has("item") && jsonObject.has("enchantment"))
                    {
                        var item = ShapedRecipe.getItem(jsonObject);
                        var stack = new ItemStack(item);

                        var enchantment = Registry.ENCHANTMENT.get(new Identifier(jsonObject.get("enchantment").getAsString()));
                        stack.addEnchantment(enchantment, 1);

                        return Map.entry(Ingredient.ofStacks(stack), enchantment);
                    }
                    else
                        throw new JsonSyntaxException("Only Item key is supported Tags are not allowed for GlobeToStaffRecipe. enchantment key is mandatory.");
                }
                else
                    throw new JsonSyntaxException("Expected item to be object");
            }
            else
                throw new JsonSyntaxException("Item cannot be null");
        }

        public SmithingRecipe read(Identifier identifier, JsonObject jsonObject)
        {
            var base = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "base"));

            var additionJSON = JsonHelper.getObject(jsonObject, "addition");
            var additionWithEnchantment = ingredientFromJSON(additionJSON);

            var result = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new GlobeToStaffRecipe(identifier, base, additionWithEnchantment.getKey(), result);
        }
    }
}
