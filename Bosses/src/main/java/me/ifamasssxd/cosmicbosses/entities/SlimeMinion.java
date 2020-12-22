/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntitySlime
 *  net.minecraft.server.v1_8_R3.PathfinderGoal
 *  net.minecraft.server.v1_8_R3.PathfinderGoalFloat
 *  net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_8_R3.PathfinderGoalSelector
 *  net.minecraft.server.v1_8_R3.World
 */
package me.ifamasssxd.cosmicbosses.entities;

import java.util.List;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

public class SlimeMinion
extends EntitySlime {
    public SlimeMinion(World world) {
        super(world);
        List list = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, (Object)this.goalSelector, "b");
        list.clear();
        List c = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, (Object)this.goalSelector, "c");
        c.clear();
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 8.0f));
        this.goalSelector.a(3, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
    }

    protected int getRandomJumpDelay() {
        return this.random.nextInt(10) + 10;
    }

    public void die() {
        super.die();
    }
}

