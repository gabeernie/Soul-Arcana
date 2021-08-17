package net.soularcana.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;

public class SpellParticleSheet implements ParticleTextureSheet
{
    public static final SpellParticleSheet INSTANCE = new SpellParticleSheet();

    private SpellParticleSheet()
    {

    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager)
    {
/*        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
//        RenderSystem.alphaFunc(518, 0.00390625F);
        // RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
 */
        RenderSystem.depthMask(true);
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);

    }

    @Override
    public void draw(Tessellator tessellator)
    {
        tessellator.draw();
    }
}
