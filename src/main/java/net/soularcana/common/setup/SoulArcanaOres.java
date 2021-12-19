package net.soularcana.common.setup;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.soularcana.SoulArcana;

public class SoulArcanaOres
{
    private static final PlacedFeature ORE_SOUL_SHARD_NETHER = Feature.ORE
            .configure(new OreFeatureConfig(
                    new BlockMatchRuleTest(Blocks.SOUL_SOIL),
                    SoulArcanaBlocks.SOUL_SEDIMENT.getDefaultState(),
                    9)) // Vein size
            .withPlacement(
                    CountPlacementModifier.of(20),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.aboveBottom(0), YOffset.belowTop(0))
            );

    public static void registerOres()
    {
        var oreSoulShardNether = RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier(SoulArcana.MODID, "ore_soul_shard_nether"));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, oreSoulShardNether.getValue(), ORE_SOUL_SHARD_NETHER);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.WARPED_FOREST), GenerationStep.Feature.UNDERGROUND_ORES, oreSoulShardNether);
    }
}
