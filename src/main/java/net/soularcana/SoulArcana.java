package net.soularcana;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.soularcana.client.SoulArcanaParticles;
import net.soularcana.common.event.EntityEvents;
import net.soularcana.common.setup.SoulArcanaBlocks;
import net.soularcana.common.setup.SoulArcanaEnchantments;
import net.soularcana.common.setup.SoulArcanaEntities;
import net.soularcana.common.setup.SoulArcanaItems;
import net.soularcana.common.setup.SoulArcanaOres;
import net.soularcana.common.setup.SoulArcanaRecipes;

public class SoulArcana implements ModInitializer
{
    public static final String MODID = "soularcana";

    public static ItemGroup ITEMGROUP = FabricItemGroupBuilder.build(
            new Identifier(MODID, "item_group"),
            () -> new ItemStack(SoulArcanaItems.ARCANE_STAFF));

    @Override
    public void onInitialize()
    {
        SoulArcanaBlocks.registerBlocks();
        SoulArcanaItems.registerItems();
        SoulArcanaEnchantments.registerEnchantments();
        SoulArcanaRecipes.registerRecipes();

        SoulArcanaEntities.registerEntities();

        SoulArcanaParticles.registerParticles();

        SoulArcanaOres.registerOres();

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(EntityEvents::onEntityKill);
    }
}
