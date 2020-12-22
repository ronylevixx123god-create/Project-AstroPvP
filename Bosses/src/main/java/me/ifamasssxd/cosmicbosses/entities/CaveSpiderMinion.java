/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityCaveSpider
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.World
 */
package me.ifamasssxd.cosmicbosses.entities;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCaveSpider;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.World;

public class CaveSpiderMinion
extends EntityCaveSpider {
    public CaveSpiderMinion(World arg0) {
        super(arg0);
    }

    protected Entity findTarget() {
        EntityHuman human = this.world.findNearbyVulnerablePlayer((Entity)this, 16.0);
        this.target = human;
        return human;
    }
}

