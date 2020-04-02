package tsp.asuna.entities;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import tsp.asuna.Asuna;
import tsp.asuna.registry.Entities;

public class ThoronEntity extends ThrownItemEntity {
    public ThoronEntity(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
    }

    public static final Identifier ENTITY_ID = Asuna.id("thoron");


    public ThoronEntity(World world, double x, double y, double z) {
        super(Entities.THORON_ENTITY, world);
        this.updatePosition(x, y, z);
        this.updateTrackedPosition(x, y, z);
    }

    public ThoronEntity(World world, LivingEntity owner) {
        super(Entities.THORON_ENTITY, owner, world);
    }

    public ThoronEntity(World world) {
        super(Entities.THORON_ENTITY, world);

    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        packet.writeDouble(this.getX());
        packet.writeDouble(this.getY());
        packet.writeDouble(this.getZ());

        packet.writeInt(this.getEntityId());

        return ServerSidePacketRegistry.INSTANCE.toPacket(ENTITY_ID, packet);
    }

    @Override
    protected float getGravity() {
        return (float) 0;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float) 5);

        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
        }

        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }
}