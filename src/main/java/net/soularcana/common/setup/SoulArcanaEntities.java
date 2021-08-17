package net.soularcana.common.setup;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;
import net.soularcana.common.entity.ArcaneProjectileEntity;
import net.soularcana.common.entity.CurseProjectileEntity;
import net.soularcana.common.entity.FireProjectileEntity;
import net.soularcana.common.entity.FrostProjectileEntity;
import net.soularcana.common.entity.GlobeProjectileEntity;
import net.soularcana.common.entity.PoisonProjectileEntity;
import net.soularcana.common.entity.StormProjectileEntity;

public class SoulArcanaEntities
{
    public static Tag<EntityType<?>> LESSER_SOULS_TAG   = TagRegistry.entityType(new Identifier(SoulArcana.MODID, "lesser_souls"));
    public static Tag<EntityType<?>> MIDDLING_SOULS_TAG = TagRegistry.entityType(new Identifier(SoulArcana.MODID, "middling_souls"));
    public static Tag<EntityType<?>> GREATER_SOULS_TAG  = TagRegistry.entityType(new Identifier(SoulArcana.MODID, "greater_souls"));
    public static Tag<EntityType<?>> TITANIC_SOULS_TAG  = TagRegistry.entityType(new Identifier(SoulArcana.MODID, "titanic_souls"));

    public static EntityType<FireProjectileEntity>   FIRE_PROJECTILE;
    public static EntityType<ArcaneProjectileEntity> ARCANE_PROJECTILE;
    public static EntityType<FrostProjectileEntity>  FROST_PROJECTILE;
    public static EntityType<StormProjectileEntity>  STORM_PROJECTILE;
    public static EntityType<PoisonProjectileEntity> POISON_PROJECTILE;
    public static EntityType<CurseProjectileEntity>  CURSE_PROJECTILE;

    public static void registerEntities()
    {
        register(FIRE_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, FireProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "fire_projectile");
        register(ARCANE_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, ArcaneProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "arcane_projectile");
        register(FROST_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, FrostProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "frost_projectile");
        register(STORM_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, StormProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "storm_projectile");
        register(POISON_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, PoisonProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "poison_projectile");
        register(CURSE_PROJECTILE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, CurseProjectileEntity::new)
                        .dimensions(EntityDimensions.fixed(0.4F, 0.4F)).build(),
                "curse_projectile");
    }

    private static void register(EntityType<?> entityType, String name)
    {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(SoulArcana.MODID, name), entityType);
    }

    public static EntityType<? extends GlobeProjectileEntity> getProjectileEntity(GlobeType type)
    {
        return switch (type)
                {
                    case ARCANE -> ARCANE_PROJECTILE;
                    case FIRE -> FIRE_PROJECTILE;
                    case FROST -> FROST_PROJECTILE;
                    case STORM -> STORM_PROJECTILE;
                    case POISON -> POISON_PROJECTILE;
                    case CURSE -> CURSE_PROJECTILE;
                };
    }
}
