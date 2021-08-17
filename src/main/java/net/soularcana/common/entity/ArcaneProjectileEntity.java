package net.soularcana.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.voxelindustry.brokkcolor.Color;

public class ArcaneProjectileEntity extends GlobeProjectileEntity
{
    private static final Color PRIMARY_COLOR   = new Color(1, 224 / 256F, 1);
    private static final Color SECONDARY_COLOR = new Color(214 / 256F, 120 / 256F, 214 / 256F);
    private static final Color FADE_COLOR      = new Color(224 / 256F, 96 / 256F, 224 / 256F);

    public ArcaneProjectileEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    protected void onImpact(HitResult hitResult, Entity target)
    {
        target.damage(
                new ProjectileDamageSource("arcane_globe", this, world.getPlayerByUuid(getCasterId()))
                        .setUsesMagic()
                        .setBypassesArmor()
                        .setProjectile(),
                4F);
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
