package net.soularcana.common.setup;

import net.minecraft.item.Item;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.SoulArcana;
import net.soularcana.common.recipe.GlobeStaffRepairRecipe;
import net.soularcana.common.recipe.GlobeToStaffRecipe;

import java.util.HashMap;
import java.util.Map;

public class SoulArcanaRecipes
{
    private static final Map<Item, Integer>                       gemRepairValueMap = new HashMap<>();
    public static        RecipeSerializer<GlobeToStaffRecipe>     GLOBE_TO_STAFF_SERIALIZER;
    public static        RecipeSerializer<GlobeStaffRepairRecipe> GLOBE_STAFF_REPAIR_SERIALIZER;

    public static void registerRecipes()
    {
        registerSerializer(GLOBE_TO_STAFF_SERIALIZER = new SpecialRecipeSerializer<>(GlobeToStaffRecipe::new), "globe_to_staff");
        registerSerializer(GLOBE_STAFF_REPAIR_SERIALIZER = new SpecialRecipeSerializer<>(GlobeStaffRepairRecipe::new), "globe_staff_repair");
    }

    private static <T extends Recipe<?>> void registerSerializer(RecipeSerializer<T> serializer, String name)
    {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(SoulArcana.MODID, name), serializer);
    }

    public static int getGemRepairValue(Item item)
    {
        return gemRepairValueMap.getOrDefault(item, 0);
    }

    public static void registerGemRepairValue(Item gemItem, int value)
    {
        gemRepairValueMap.put(gemItem, value);
    }
}