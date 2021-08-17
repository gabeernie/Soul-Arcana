package net.soularcana.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.gui.hud.BackgroundHelper.ColorMixer;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.soularcana.SoulArcanaClient;
import net.voxelindustry.brokkcolor.Color;

public class SpellParticle extends SpriteBillboardParticle
{
    private final float fromAlpha;
    private final float toAlpha;
    private final float fromScale;
    private final float toScale;

    private final float[] hsv1 = new float[3];
    private final float[] hsv2 = new float[3];

    public SpellParticle(ClientWorld world,
                         double x,
                         double y,
                         double z,
                         double velocityX,
                         double velocityY,
                         double velocityZ,
                         float fromAlpha,
                         float toAlpha,
                         float fromScale,
                         float toScale,
                         Color fromColor,
                         Color toColor,
                         int lifetime,
                         FabricSpriteProvider spriteProvider)
    {
        super(world, x, y, z, velocityX, velocityY, velocityZ);

        setSprite(spriteProvider);

        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;

        this.fromScale = fromScale;
        this.toScale = toScale;

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.setMaxAge(lifetime);

        java.awt.Color.RGBtoHSB((int) (255.0F * Math.min(1.0F, fromColor.getRed())), (int) (255.0F * Math.min(1.0F, fromColor.getGreen())), (int) (255.0F * Math.min(1.0F, fromColor.getBlue())), this.hsv1);
        java.awt.Color.RGBtoHSB((int) (255.0F * Math.min(1.0F, toColor.getRed())), (int) (255.0F * Math.min(1.0F, toColor.getGreen())), (int) (255.0F * Math.min(1.0F, toColor.getBlue())), this.hsv2);
        this.updateTraits();
    }

    @Override
    public ParticleTextureSheet getType()
    {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
    {
        RenderSystem.depthMask(false);
        RenderSystem.setShader(() -> SoulArcanaClient.CUSTOM_PARTICLE_SHADER);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    @Override
    protected int getBrightness(float tint)
    {
        return 15728880;
    }

    protected float getDelta()
    {
        return (float) this.age / (float) this.maxAge;
    }

    protected void updateTraits()
    {
        float delta = this.getDelta();
        this.scale = MathHelper.lerp(delta, this.fromScale, this.toScale);
        float h = MathHelper.lerpAngleDegrees(delta, 360.0F * this.hsv1[0], 360.0F * this.hsv2[0]) / 360.0F;
        float s = MathHelper.lerp(delta, this.hsv1[1], this.hsv2[1]);
        float v = MathHelper.lerp(delta, this.hsv1[2], this.hsv2[2]);

        int packed = java.awt.Color.HSBtoRGB(h, s, v);
        float r = (float) ColorMixer.getRed(packed) / 255.0F;
        float g = (float) ColorMixer.getGreen(packed) / 255.0F;
        float b = (float) ColorMixer.getBlue(packed) / 255.0F;

        this.setColor(r, g, b);
        this.setColorAlpha(MathHelper.lerp(delta, this.fromAlpha, this.toAlpha));
        this.prevAngle = this.angle;
    }

    public void tick()
    {
        this.updateTraits();
        super.tick();
    }
}
