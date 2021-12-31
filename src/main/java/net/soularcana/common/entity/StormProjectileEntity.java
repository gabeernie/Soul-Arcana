package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.voxelindustry.brokkcolor.Color;

public class StormProjectileEntity extends GlobeProjectileEntity
{
    private static final Color PRIMARY_COLOR   = new Color(68 / 256F, 88 / 256F, 213 / 256F);
    private static final Color SECONDARY_COLOR = new Color(12 / 256F, 0 / 256F, 90 / 256F);
    private static final Color FADE_COLOR      = new Color(127 / 256F, 164 / 256F, 241 / 256F);

    public StormProjectileEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void onImpact(HitResult hitResult, Entity target)
    {
        target.damage(
                new ProjectileDamageSource("storm_globe", this, world.getPlayerByUuid(getCasterId()))
                        .setUsesMagic()
                        .setProjectile(),
                world.isRaining() ? 6F : 3F);

        if (target instanceof LivingEntity living)
            living.takeKnockback(0.5D, target.getX() - this.getX(), target.getZ() - this.getZ());

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
