package net.soularcana.common.setup;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.soularcana.SoulArcana;

public class SoulArcanaBlocks
{
    public static Block SOUL_SEDIMENT;

    public static void registerBlocks()
    {
        var itemGroup = new Item.Settings().group(SoulArcana.ITEMGROUP);

        registerBlock(SOUL_SEDIMENT = new OreBlock(Settings.of(Material.SOIL)
                        .strength(0.5F), UniformIntProvider.create(2, 5)),
                itemGroup,
                "soul_sediment");
    }

    private static void registerBlock(Block block, Item.Settings settings, String name)
    {
        Identifier identifier = new Identifier(SoulArcana.MODID, name);

        Registry.register(Registry.BLOCK, identifier, block);
        BlockItem itemBlock = new BlockItem(block, settings);
        Registry.register(Registry.ITEM, identifier, itemBlock);
    }
}
