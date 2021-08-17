package net.soularcana.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.soularcana.client.SoulArcanaParticles;
import net.voxelindustry.brokkcolor.Color;

@Builder
@Getter
@RequiredArgsConstructor
public class SpellParticleEffect implements ParticleEffect
{
    private final float fromAlpha;
    private final float toAlpha;

    private final float fromScale;
    private final float toScale;

    private final Color fromColor;
    private final Color toColor;

    private final int lifetime;

    @Override
    public ParticleType<?> getType()
    {
        return SoulArcanaParticles.SPELL_PARTICLE;
    }

    @Override
    public void write(PacketByteBuf buf)
    {
        buf.writeFloat(fromAlpha);
        buf.writeFloat(toAlpha);

        buf.writeFloat(fromScale);
        buf.writeFloat(toScale);

        buf.writeInt(fromColor.toRGBInt());
        buf.writeInt(toColor.toRGBInt());

        buf.writeInt(lifetime);
    }

    @Override
    public String asString()
    {
        // FIXME: Fill this method
        return "";
    }

    public static final ParticleEffect.Factory<SpellParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<>()
    {
        @Override
        public SpellParticleEffect read(ParticleType<SpellParticleEffect> particleType, StringReader stringReader) throws CommandSyntaxException
        {
            return new SpellParticleEffect(1, 0, 1, 0, Color.WHITE, Color.WHITE, 20);
        }

        @Override
        public SpellParticleEffect read(ParticleType<SpellParticleEffect> particleType, PacketByteBuf buffer)
        {
            return new SpellParticleEffect(
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    Color.fromRGBInt(buffer.readInt()),
                    Color.fromRGBInt(buffer.readInt()),
                    buffer.readInt()
            );
        }
    };
}
