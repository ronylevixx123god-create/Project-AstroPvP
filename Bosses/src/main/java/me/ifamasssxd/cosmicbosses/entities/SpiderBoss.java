/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntitySpider
 *  net.minecraft.server.v1_8_R3.GenericAttributes
 *  net.minecraft.server.v1_8_R3.World
 */
package me.ifamasssxd.cosmicbosses.entities;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;

public class SpiderBoss
extends EntitySpider {
    public SpiderBoss(World world) {
        super(world);
        this.getAttributeInstance(GenericAttributes.c).setValue(1.0);
        this.getAttributeInstance(GenericAttributes.b).setValue(20.0);
        this.getAttributeInstance(GenericAttributes.d).setValue(1.3);
    }

    public void e() {
        if (this.target != null && !this.target.isAlive()) {
            this.target = null;
        }
        this.getAttributeInstance(GenericAttributes.b).setValue(20.0);
        this.getAttributeInstance(GenericAttributes.d).setValue(2.0);
        super.e();
    }

    protected Entity findTarget() {
        if (this.target == null || !this.target.isAlive() || this.target.world != this.world || this.random.nextInt(1000) <= 10 || this.target.getBukkitEntity().getLocation().distanceSquared(this.getBukkitEntity().getLocation()) > 200.0) {
            this.target = null;
            return null;
        }
        EntityHuman human = this.world.findNearbyVulnerablePlayer((Entity)this, 32.0);
        System.out.println("Setting target");
        this.target = human;
        return human;
    }
}

