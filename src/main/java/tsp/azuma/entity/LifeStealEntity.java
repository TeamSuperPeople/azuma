package tsp.azuma.entity;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import net.minecraft.entity.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;

import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;
import tsp.azuma.Azuma;
import tsp.azuma.registry.Entities;

public class LifeStealEntity extends ThrownItemEntity {

    public static final Identifier ENTITY_ID = Azuma.id("life_steal");
    private double gravity = 0.03;

    public LifeStealEntity(World world, double x, double y, double z, Entity owner) {
        super(Entities.LIFESTEAL_ENTITY, world);
        this.updatePosition(x, y, z);
        this.owner = (LivingEntity) owner;
        this.updateTrackedPosition(x, y, z);
    }

    public LifeStealEntity(World world) {
        super(Entities.LIFESTEAL_ENTITY, world);
    }

    public LifeStealEntity(World world, LivingEntity owner) {
        super(Entities.LIFESTEAL_ENTITY, world);
        this.owner = owner;
    }

    @Override
    public Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    public void tick() {
        super.tick();
        ParticleEffect particle = ParticleTypes.CLOUD;

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();

        this.world.addParticle(particle, x, y, z, 0, 0, 0);
        this.world.addParticle(particle, x, -y, z, 0, 0, 0);


    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        packet.writeDouble(this.getX());
        packet.writeDouble(this.getY());
        packet.writeDouble(this.getZ());

        packet.writeInt(this.getEntityId());
        packet.writeInt(this.getOwner().getEntityId());

        return ServerSidePacketRegistry.INSTANCE.toPacket(ENTITY_ID, packet);
    }

    @Override
    protected float getGravity() {
        return (float) 0.03;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float) 5);

            // get positions of target
            double z = entity.getZ();
            double x = entity.getX();
            double y = entity.getY();

            //get positions of player
            double z2 = owner.getZ();
            double x2 = owner.getX();
            double y2 = owner.getY() + 1;

            // paritlces

            ParticleEffect effect = ParticleTypes.HAPPY_VILLAGER;
            world.addParticle(effect, x, y, z, 0, 0, 0);

            double distancex = x2 - x;
            double distancey = y2 - y;
            double distancez = z2 - z;

            double slope = Math.sqrt(Math.pow(distancex, 2) + Math.pow(distancey, 2) + Math.pow(distancez, 2));

            Vec3d playerPos = entity.getPos();

            double divdedx = distancex / slope;
            double divdedy = distancey / slope;
            double divdedz = distancez / slope;

            for (int b = 0; b < slope; b++) {
                playerPos = playerPos.add(divdedx, divdedy, divdedz);
                this.world.addParticle(effect, playerPos.x, playerPos.y, playerPos.z, 0, 0, 0);
            }

            owner.heal(2.5F);
            ParticleEffect yeet = ParticleTypes.CLOUD;

            for (int i = 0; i < 15; i++) {
                this.world.addParticle(yeet, x + world.getRandom().nextDouble(), y + world.getRandom().nextDouble(), z + world.getRandom().nextDouble(), 0, 0, 0);
            }


            if (!this.world.isClient) {
                this.world.sendEntityStatus(this, (byte) 3);
                this.remove();
            }
        }
    }
}
