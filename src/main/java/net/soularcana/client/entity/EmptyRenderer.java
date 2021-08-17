package net.soularcana.client.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class EmptyRenderer<T extends Entity> extends EntityRenderer<T>
{
    public EmptyRenderer(EntityRendererFactory.Context context)
    {
        super(context);
    }

    @Override
    public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ)
    {
        return false;
    }

    @Override
    public Identifier getTexture(T entity)
    {
        return null;
    }
}