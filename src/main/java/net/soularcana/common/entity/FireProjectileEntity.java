package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.voxelindustry.brokkcolor.Color;

public class FireProjectileEntity extends GlobeProjectileEntity
{
    private static final Color PRIMARY_COLOR   = new Color(1, 234 / 256F, 224 / 256F);
    private static final Color SECONDARY_COLOR = new Color(214 / 256F, 150 / 256F, 120 / 256F);
    private static final Color FADE_COLOR      = new Color(224 / 256F, 170 / 256F, 96 / 256F);

    public FireProjectileEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void onImpact(HitResult hitResult, Entity target)
    {
        target.damage(
                new ProjectileDamageSource("fire_globe", this, world.getPlayerByUuid(getCasterId()))
                        .setFire()
                        .setProjectile(),
                2F);

        if (target instanceof LivingEntity living)
            living.setOnFireFor(2);

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
