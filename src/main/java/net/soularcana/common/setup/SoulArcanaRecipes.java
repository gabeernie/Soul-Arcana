package net.soularcana.common.setup;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.SoulArcana;
import net.soularcana.common.recipe.DurabilityAwareShapedRecipe;
import net.soularcana.common.recipe.DurabilityAwareShapelessRecipe;
import net.soularcana.common.recipe.GlobeStaffRepairRecipe;
import net.soularcana.common.recipe.GlobeToStaffRecipe.GlobeToStaffSerializer;

public class SoulArcanaRecipes
{
    public static RecipeSerializer<SmithingRecipe>         GLOBE_TO_STAFF_SERIALIZER;
    public static RecipeSerializer<GlobeStaffRepairRecipe> GLOBE_STAFF_REPAIR_SERIALIZER;
    public static RecipeSerializer<ShapelessRecipe>        DURABILITY_AWARE_SHAPELESS_SERIALIZER;
    public static RecipeSerializer<ShapedRecipe>           DURABILITY_AWARE_SHAPED_SERIALIZER;

    public static void registerRecipes()
    {
        registerSerializer(GLOBE_TO_STAFF_SERIALIZER = new GlobeToStaffSerializer(), "globe_to_staff");
        registerSerializer(GLOBE_STAFF_REPAIR_SERIALIZER = new SpecialRecipeSerializer<>(GlobeStaffRepairRecipe::new), "globe_staff_repair");

        registerSerializer(DURABILITY_AWARE_SHAPELESS_SERIALIZER = new DurabilityAwareShapelessRecipe.Serializer(), "durability_aware_shapeless");
        registerSerializer(DURABILITY_AWARE_SHAPED_SERIALIZER = new DurabilityAwareShapedRecipe.Serializer(), "durability_aware_shaped");
    }

    private static <T extends Recipe<?>> void registerSerializer(RecipeSerializer<T> serializer, String name)
    {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(SoulArcana.MODID, name), serializer);
    }
}
