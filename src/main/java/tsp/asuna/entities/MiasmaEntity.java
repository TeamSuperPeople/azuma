package tsp.asuna.entities;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.world.World;
import tsp.asuna.registry.Entities;

import java.util.Iterator;

public class MiasmaEntity extends ThrownItemEntity  {
    public MiasmaEntity(World world) {
        super(EntityType.SNOWBALL, world);
    }



    public MiasmaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    public MiasmaEntity(World world, double x, double y, double z) {
        super(EntityType.SNOWBALL, x, y, z, world);
    }




    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    double gravity = 0.03;
    @Override
    public void tick() {
        super.tick();
        if (!(gravity <=0)) {
            gravity = gravity - 0.001;
        }
    }


    @Override
    protected float getGravity() {
        return (float) 0.03;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.CLOUD : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    Potion potion = Potions.POISON;
    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == Type.ENTITY) {
            Entity entity = ((EntityHitResult)hitResult).getEntity();
            int i = entity instanceof BlazeEntity ? 3 : 0;
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)5);


        }
        else if (hitResult.getType() == Type.BLOCK) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setOwner(this.getOwner());
            areaEffectCloudEntity.setRadius(3.0F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(3);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.setPotion(potion);
            this.world.spawnEntity(areaEffectCloudEntity);


        }

        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.remove();
        }

    }
}
