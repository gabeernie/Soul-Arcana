package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.voxelindustry.brokkcolor.Color;

public class PoisonProjectileEntity extends GlobeProjectileEntity
{
    private static final Color PRIMARY_COLOR   = new Color(224 / 256F, 1, 224 / 256F);
    private static final Color SECONDARY_COLOR = new Color(120 / 256F, 214 / 256F, 120 / 256F);
    private static final Color FADE_COLOR      = new Color(96 / 256F, 224 / 256F, 96 / 256F);

    public PoisonProjectileEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void onImpact(HitResult hitResult, Entity target)
    {
        target.damage(
                new ProjectileDamageSource("poison_globe", this, world.getPlayerByUuid(getCasterId()))
                        .setUsesMagic()
                        .setProjectile(),
                2F);
        if (target instanceof LivingEntity living)
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40));

        onImpact(hitResult);
    }

    @Override
    protected void onImpact(HitResult hitResult)
    {
        discard();

        // TODO: Projectile OnImpact particle effects
    }

    @Override
    public Color getPrimaryColor()
    {
        return PRIMARY_COLOR;
    }

    @Override
    public Color getSecondaryColor()
    {
        return SECONDARY_COLOR;
    }

    @Override
    public Color getFadeColor()
    {
        return FADE_COLOR;
    }
}
