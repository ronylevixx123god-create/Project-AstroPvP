/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.projectiles.ProjectileSource;

public interface Projectile
extends Entity {
    @Deprecated
    public LivingEntity _INVALID_getShooter();

    public ProjectileSource getShooter();

    @Deprecated
    public void _INVALID_setShooter(LivingEntity var1);

    public void setShooter(ProjectileSource var1);

    public boolean doesBounce();

    public void setBounce(boolean var1);
}

