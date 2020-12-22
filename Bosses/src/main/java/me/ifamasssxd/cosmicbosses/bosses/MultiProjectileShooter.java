/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 */
package me.ifamasssxd.cosmicbosses.bosses;

import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.IRangedEntity;

public interface MultiProjectileShooter
extends IRangedEntity {
    public void shootNearby(EntityLiving var1);
}

