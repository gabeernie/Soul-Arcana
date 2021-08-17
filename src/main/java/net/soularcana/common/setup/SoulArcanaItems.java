package net.soularcana.common.setup;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;
import net.soularcana.common.entity.SoulType;
import net.soularcana.common.item.SoulGemItem;
import net.soularcana.common.item.SpellGlobeItem;
import net.soularcana.common.item.SpellStaffItem;

public class SoulArcanaItems
{
    public static final Tag<Item> TAG_SOUL_GEMS = TagRegistry.item(new Identifier(SoulArcana.MODID, "soul_gems"));

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
        registerItem(ARCANE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.ARCANE), "arcane_staff");
        registerItem(CURSE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.CURSE), "curse_staff");
        registerItem(FIRE_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.FIRE), "fire_staff");
        registerItem(FROST_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.FROST), "frost_staff");
        registerItem(POISON_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.POISON), "poison_staff");
        registerItem(STORM_STAFF = new SpellStaffItem(new Settings().group(SoulArcana.ITEMGROUP), GlobeType.STORM), "storm_staff");
        registerItem(STAFF = new Item(new Settings().group(SoulArcana.ITEMGROUP)), "spell_staff");

        registerItem(SPELL_GLOBE = new SpellGlobeItem(new Settings().group(SoulArcana.ITEMGROUP)), "spell_globe");

        registerItem(LESSER_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP)), "lesser_soul_gem");
        registerItem(MIDDLING_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP)), "middling_soul_gem");
        registerItem(GREATER_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP)), "greater_soul_gem");
        registerItem(TITANIC_SOUL_GEM = new SoulGemItem(new Settings().group(SoulArcana.ITEMGROUP)), "titanic_soul_gem");

        SoulArcanaRecipes.registerGemRepairValue(LESSER_SOUL_GEM, 12);
        SoulArcanaRecipes.registerGemRepairValue(MIDDLING_SOUL_GEM, 25);
        SoulArcanaRecipes.registerGemRepairValue(GREATER_SOUL_GEM, 50);
        SoulArcanaRecipes.registerGemRepairValue(TITANIC_SOUL_GEM, 100);

        registerItem(SOUL_SHARD = new Item(new Settings().group(SoulArcana.ITEMGROUP)), "soul_shard");
    }

    public static void registerItem(Item item, String name)
    {
        Registry.register(Registry.ITEM, new Identifier(SoulArcana.MODID, name), item);
    }

    public static Item getSoulGemForType(SoulType type)
    {
        return switch (type)
                {
                    case LESSER -> LESSER_SOUL_GEM;
                    case MIDDLING -> MIDDLING_SOUL_GEM;
                    case GREATER -> GREATER_SOUL_GEM;
                    case TITANIC -> TITANIC_SOUL_GEM;
                };
    }
}
