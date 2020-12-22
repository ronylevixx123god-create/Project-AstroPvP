/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.IRangedEntity
 */
package me.ifamasssxd.cosmicbosses.bosses;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IRangedEntity;

public interface MultiProjectileShooter
extends IRangedEntity {
    public void shootNearby(EntityLiving var1);
}

