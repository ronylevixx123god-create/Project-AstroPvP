/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityCaveSpider
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.World
 */
package me.ifamasssxd.cosmicbosses.entities;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.World;

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

