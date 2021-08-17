package net.soularcana.common.entity;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.soularcana.client.particle.SpellParticleEffect;
import net.voxelindustry.brokkcolor.Color;

import java.util.UUID;

public abstract class GlobeProjectileEntity extends Entity
{
    @Getter
    private UUID casterId;

    public GlobeProjectileEntity(EntityType<?> type, World world)
    {
        super(type, world);
    }

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster)
    {
        this.setPosition(x, y, z);
        this.setVelocity(vx, vy, vz);
        this.casterId = caster;
        this.velocityModified = true;
        return this;
    }

    public void tick()
    {
        var motion = this.getVelocity();
        this.setVelocity(motion.x * 0.96D, (motion.y > 0.0D ? motion.y * 0.96D : motion.y) - 0.029999999329447746D, motion.z * 0.96D);
        super.tick();
        if (!this.world.isClient())
        {
            var ray = ProjectileUtil.getCollision(this, (e) ->
                    !e.isSpectator() && e.collides() && !e.getUuid().equals(this.casterId));
            if (ray.getType() == HitResult.Type.ENTITY)
                this.onImpact(ray, ((EntityHitResult) ray).getEntity());
            else if (ray.getType() == HitResult.Type.BLOCK)
                this.onImpact(ray);
        }

        var pos = this.getPos();
        this.prevX = pos.x;
        this.prevY = pos.y;
        this.prevZ = pos.z;
        this.setPosition(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);

        var norm = motion.normalize().multiply(0.02500000037252903D);

        var count = 8;
        for (int i = 0; i < count; ++i)
        {
            double lerpX = MathHelper.lerp(i / (float) count, prevX, pos.x);
            double lerpY = MathHelper.lerp(i / (float) count, prevY, pos.y);
            double lerpZ = MathHelper.lerp(i / (float) count, prevZ, pos.z);

            world.addParticle(SpellParticleEffect.builder()
                    .fromAlpha(0.0625F)
                    .toAlpha(0)
                    .fromScale(0.625F)
                    .toScale(0)
                    .fromColor(getPrimaryColor())
                    .toColor(getFadeColor())
                    .lifetime(5)
                    .build(), lerpX, lerpY, lerpZ, -norm.x, -norm.y, -norm.z);

            world.addParticle(SpellParticleEffect.builder()
                    .fromAlpha(0.125F)
                    .toAlpha(0)
                    .fromScale(0.25F)
                    .toScale(0.125F)
                    .fromColor(getSecondaryColor())
                    .toColor(getFadeColor())
                    .lifetime(20)
                    .build(), lerpX, lerpY, lerpZ, -norm.x, -norm.y, -norm.z);
        }
    }

    protected abstract void onImpact(HitResult hitResult, Entity target);

    protected abstract void onImpact(HitResult hitResult);

    public abstract Color getPrimaryColor();

    public abstract Color getSecondaryColor();

    public abstract Color getFadeColor();

    @Override
    protected void initDataTracker()
    {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        this.casterId = nbt.contains("caster") ? nbt.getUuid("caster") : null;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {
        if (this.casterId != null)
            nbt.putUuid("caster", this.casterId);
    }

    @Override
    public Packet<?> createSpawnPacket()
    {
        return new EntitySpawnS2CPacket(this);
    }
}
