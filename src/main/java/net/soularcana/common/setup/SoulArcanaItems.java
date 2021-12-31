package net.soularcana.common.setup;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;
import net.soularcana.common.item.SoulGemItem;
import net.soularcana.common.item.SpellGlobeItem;
import net.soularcana.common.item.SpellStaffItem;

public class SoulArcanaItems
{
    public static final Tag<Item> TAG_SOUL_GEMS = TagRegistry.item(new Identifier(SoulArcana.MODID, "soul_gems"));

    public static final Item[] ITEM_STAVES = new Item[6];

    public static Item STAFF;

    public static Item ARCANE_STAFF;
    public static Item CURSE_STAFF;
    public static Item FIRE_STAFF;
    public static Item FROST_STAFF;
    public static Item POISON_STAFF;
    public static Item STORM_STAFF;

    public static Item SPELL_GLOBE;

    public static Item LESSER_SOUL_GEM;
    public static Item MIDDLING_SOUL_GEM;
    public static Item GREATER_SOUL_GEM;
    public static Item TITANIC_SOUL_GEM;

    public static Item SOUL_SHARD;

    public static void registerItems()
    {
        registerItem(ITEM_STAVES[0] = ARCANE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.ARCANE), "arcane_staff");
        registerItem(ITEM_STAVES[1] = CURSE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.CURSE), "curse_staff");
        registerItem(ITEM_STAVES[2] = FIRE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.FIRE), "fire_staff");
        registerItem(ITEM_STAVES[3] = FROST_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.FROST), "frost_staff");
        registerItem(ITEM_STAVES[4] = POISON_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.POISON), "poison_staff");
        registerItem(ITEM_STAVES[5] = STORM_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.STORM), "storm_staff");
        registerItem(STAFF = new Item(new Settings().group(SoulArcana.ITEMGROUP)), "spell_staff");


        registerItem(SPELL_GLOBE = new SpellGlobeItem(new Settings().group(SoulArcana.ITEMGROUP)), "spell_globe");

        registerItem(LESSER_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP), 8), "lesser_soul_gem");
        registerItem(MIDDLING_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP), 16), "middling_soul_gem");
        registerItem(GREATER_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP), 32), "greater_soul_gem");
        registerItem(TITANIC_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP), 64), "titanic_soul_gem");

        registerItem(SOUL_SHARD = new Item(new Settings().group(SoulArcana.ITEMGROUP)), "soul_shard");
    }

    public static void registerItem(Item item, String name)
    {
        Registry.register(Registry.ITEM, new Identifier(SoulArcana.MODID, name), item);
    }
}
