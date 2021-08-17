package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.soularcana.common.setup.SoulArcanaEntities;
import org.jetbrains.annotations.Nullable;

public enum SoulType
{
    LESSER(SoulArcanaEntities.LESSER_SOULS_TAG),
    MIDDLING(SoulArcanaEntities.MIDDLING_SOULS_TAG),
    GREATER(SoulArcanaEntities.GREATER_SOULS_TAG),
    TITANIC(SoulArcanaEntities.TITANIC_SOULS_TAG);

    private static final SoulType[] cachedValues = values();

    private final Tag<EntityType<?>> tag;

    SoulType(Tag<EntityType<?>> tag)
    {
        this.tag = tag;
    }

    @Nullable
    public static SoulType getEntitySoulType(Entity entity)
    {
        for (SoulType type : cachedValues)
        {
            if (type.tag.contains(entity.getType()))
                return type;
        }
        return null;
    }
}
