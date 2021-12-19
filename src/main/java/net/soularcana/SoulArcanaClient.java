package net.soularcana;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.soularcana.client.SoulArcanaParticles;
import net.soularcana.client.particle.SpellParticle;
import net.soularcana.common.setup.SoulArcanaEnchantments;
import net.soularcana.common.setup.SoulArcanaEntities;
import net.soularcana.common.setup.SoulArcanaItems;

import java.io.IOException;

public class SoulArcanaClient implements ClientModInitializer
{
    public static Shader CUSTOM_PARTICLE_SHADER;

    @Override
    public void onInitializeClient()
    {
        var heldItemPredicateIdentifier = new Identifier(SoulArcana.MODID, "held_item");
        UnclampedModelPredicateProvider heldItemPredicate = (stack, world, entity, seed) ->
        {
            if (world == null || entity == null)
                return 0F;

            return 1F;
        };

        for (Item staff : SoulArcanaItems.ITEM_STAVES)
        {
            ModelPredicateProviderRegistry.register(
                    staff,
                    heldItemPredicateIdentifier,
                    heldItemPredicate);
        }

        ModelPredicateProviderRegistry.register(
                SoulArcanaItems.SPELL_GLOBE,
                new Identifier(SoulArcana.MODID, "globe"),
                (stack, world, entity, seed) ->
                {
                    if (!stack.hasEnchantments())
                        return 0F;

                    var enchantments = EnchantmentHelper.fromNbt(stack.getEnchantments());

                    if (enchantments.containsKey(SoulArcanaEnchantments.ARCANE))
                        return 0.01F;
                    else if (enchantments.containsKey(SoulArcanaEnchantments.CURSE))
                        return 0.02F;
                    else if (enchantments.containsKey(SoulArcanaEnchantments.FIRE))
                        return 0.03F;
                    else if (enchantments.containsKey(SoulArcanaEnchantments.FROST))
                        return 0.04F;
                    else if (enchantments.containsKey(SoulArcanaEnchantments.POISON))
                        return 0.05F;
                    else if (enchantments.containsKey(SoulArcanaEnchantments.STORM))
                        return 0.06F;

                    return 0F;
                });

        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.ARCANE_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.CURSE_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.FIRE_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.FROST_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.POISON_PROJECTILE, EmptyEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(SoulArcanaEntities.STORM_PROJECTILE, EmptyEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(
                SoulArcanaParticles.SPELL_PARTICLE,
                provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) ->
                        new SpellParticle(world,
                                x,
                                y,
                                z,
                                velocityX,
                                velocityY,
                                velocityZ,
                                parameters.getFromAlpha(),
                                parameters.getToAlpha(),
                                parameters.getFromScale(),
                                parameters.getToScale(),
                                parameters.getFromColor(),
                                parameters.getToColor(),
                                parameters.getLifetime(),
                                provider));

        ClientLifecycleEvents.CLIENT_STARTED.register(client ->
        {
            try
            {
                CUSTOM_PARTICLE_SHADER = new Shader(
                        id -> MinecraftClient.getInstance().getResourceManager().getResource(new Identifier(SoulArcana.MODID, id.getPath())),
                        "particle",
                        VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
