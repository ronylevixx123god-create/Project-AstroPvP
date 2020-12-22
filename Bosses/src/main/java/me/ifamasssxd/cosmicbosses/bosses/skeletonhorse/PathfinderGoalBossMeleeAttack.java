/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.PathEntity
 *  net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory
 *  org.bukkit.event.entity.EntityTargetEvent$TargetReason
 */
package me.ifamasssxd.cosmicbosses.bosses.skeletonhorse;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathEntity;
import net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class PathfinderGoalBossMeleeAttack
extends PathfinderGoalMeleeAttack {
    World a;
    EntityCreature b;
    int c;
    double d;
    boolean e;
    PathEntity f;
    Class<?> g;
    private int h;
    private double i;
    private double j;
    private double k;

    public PathfinderGoalBossMeleeAttack(EntityCreature entitycreature, Class<?> oclass, double d0, boolean flag) {
        super(entitycreature, oclass, d0, flag);
        this.b = entitycreature;
    }

    public boolean a() {
        EntityLiving entityliving = this.b.getGoalTarget();
        if (entityliving == null) {
            return false;
        }
        if (!entityliving.isAlive()) {
            return false;
        }
        if (this.g != null && !this.g.isAssignableFrom(entityliving.getClass())) {
            return false;
        }
        this.f = this.b.getNavigation().a((Entity)entityliving);
        return this.f != null;
    }

    public boolean b() {
        EntityTargetEvent.TargetReason reason;
        EntityLiving entityliving = this.b.getGoalTarget();
        EntityTargetEvent.TargetReason targetReason = reason = this.b.bT() == null ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        if (this.b.bT() == null || this.b.bT() != null && !this.b.bT().isAlive()) {
            CraftEventFactory.callEntityTargetEvent((Entity)this.b, null, (EntityTargetEvent.TargetReason)reason);
        }
        return entityliving == null ? false : (!entityliving.isAlive() ? false : (!this.e ? !this.b.getNavigation().g() : this.b.b(MathHelper.floor((double)entityliving.locX), MathHelper.floor((double)entityliving.locY), MathHelper.floor((double)entityliving.locZ))));
    }

    public void c() {
        this.b.getNavigation().a(this.f, this.d);
        this.h = 0;
    }

    public void d() {
        this.b.getNavigation().h();
    }

    public void e() {
        EntityLiving entityliving = this.b.getGoalTarget();
        this.b.getControllerLook().a((Entity)entityliving, 30.0f, 30.0f);
        double distance = this.b.e(entityliving.locX, entityliving.boundingBox.b, entityliving.locZ);
        double d1 = this.b.width * 2.0f * this.b.width * 2.0f + entityliving.width;
        --this.h;
        if ((this.e || this.b.getEntitySenses().canSee((Entity)entityliving)) && this.h <= 0 && (this.i == 0.0 && this.j == 0.0 && this.k == 0.0 || entityliving.e(this.i, this.j, this.k) >= 1.0 || this.b.aI().nextFloat() < 0.05f)) {
            this.i = entityliving.locX;
            this.j = entityliving.boundingBox.b;
            this.k = entityliving.locZ;
            this.h = 4 + this.b.aI().nextInt(7);
            if (distance > 1024.0) {
                this.h += 10;
            } else if (distance > 256.0) {
                this.h += 5;
            }
            if (!this.b.getNavigation().a((Entity)entityliving, this.d)) {
                this.h += 15;
            }
        }
        this.c = Math.max(this.c - 1, 0);
        if (distance <= d1 && this.c <= 20) {
            this.c = 20;
            if (this.b.be() != null) {
                this.b.ba();
            }
            this.b.n((Entity)entityliving);
        }
    }
}

