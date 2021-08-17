package net.soularcana.client;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.SoulArcana;
import net.soularcana.client.particle.SpellParticleEffect;

public class SoulArcanaParticles
{
    public static ParticleType<SpellParticleEffect> SPELL_PARTICLE;

    public static void registerParticles()
    {
        Registry.register(Registry.PARTICLE_TYPE,
                new Identifier(SoulArcana.MODID, "spell_particle"), SPELL_PARTICLE = FabricParticleTypes.complex(true, SpellParticleEffect.PARAMETERS_FACTORY));

    }
}
