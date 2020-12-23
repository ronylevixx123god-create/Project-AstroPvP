/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.Items
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_8_R3.*;

final class PathfinderTrickOrTreat extends PathfinderGoal {
    private final EntityCreature creature;
    private EntityHuman target;
    private int c;

    public PathfinderTrickOrTreat(EntityCreature var1) {
        this.creature = var1;
        this.a(3);
    }

    public boolean a() {
        if (!this.creature.world.w()) {
            return false;
        }
        this.target = (EntityHuman) this.creature.world.a(EntityHuman.class, this.creature.getBoundingBox().grow(10.0, 8.0, 10.0), (Entity) this.creature);
        return this.target != null;
    }

    public boolean b() {
        return this.c > 0;
    }

    public void c() {
        this.c = 400;
        this.creature.setEquipment(0, new ItemStack(Items.LAVA_BUCKET));
    }

    public void d() {
        this.creature.setEquipment(0, new ItemStack(Blocks.AIR));
        this.target = null;
    }

    public void e() {
        this.creature.getControllerLook().a(this.target, 30.0f, 30.0f);
        --this.c;
    }
}

