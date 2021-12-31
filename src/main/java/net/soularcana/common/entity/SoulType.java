package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.soularcana.common.setup.SoulArcanaEntities;
import org.jetbrains.annotations.Nullable;

public enum SoulType
{
    LESSER(SoulArcanaEntities.LESSER_SOULS_TAG, 1),
    MIDDLING(SoulArcanaEntities.MIDDLING_SOULS_TAG, 2),
    GREATER(SoulArcanaEntities.GREATER_SOULS_TAG, 4),
    TITANIC(SoulArcanaEntities.TITANIC_SOULS_TAG, 16);

    private static final SoulType[] cachedValues = values();

    private final Tag<EntityType<?>> tag;
    private final int                soulValue;

    SoulType(Tag<EntityType<?>> tag, int soulValue)
    {
        this.tag = tag;
        this.soulValue = soulValue;
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

    public int getSoulValue()
    {
        return soulValue;
    }
}
