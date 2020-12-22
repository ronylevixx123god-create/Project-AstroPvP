/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 *  net.minecraft.server.v1_7_R4.MathHelper
 */
package me.ifamasssxd.cosmicbosses.dungeonboss;

import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossArrowAttack;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.MathHelper;

public class PathfinderGoalDungeonWitherBossAttack
extends PathfinderGoalBossArrowAttack {
    private DungeonYijki boss;

    public PathfinderGoalDungeonWitherBossAttack(IRangedEntity irangedentity, double d0, int i, int j, float f) {
        super(irangedentity, d0, i, j, f);
    }

    public PathfinderGoalDungeonWitherBossAttack(DungeonYijki boss, IRangedEntity irangedentity, double d0, int i, int j, float f) {
        super(irangedentity, d0, i, j, f);
        this.boss = boss;
    }

    @Override
    public void e() {
        double d0 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        boolean flag = this.a.getEntitySenses().canSee((Entity)this.c);
        this.f = flag ? ++this.f : 0;
        if (d0 <= (double)this.j && this.f >= 10) {
            this.a.getNavigation().h();
        } else {
            this.a.getNavigation().a((Entity)this.c, this.e);
        }
        this.a.getControllerLook().a((Entity)this.c, 30.0f, 30.0f);
        if (--this.d == 0) {
            float f;
            if (d0 > (double)this.j || !flag) {
                return;
            }
            float f1 = f = MathHelper.sqrt((double)d0) / this.i;
            if (f < 0.1f) {
                f1 = 0.1f;
            }
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            this.b.a(this.c, f1);
            this.d = this.boss.getShootDelay();
        } else if (this.d < 0) {
            float f = MathHelper.sqrt((double)d0) / this.i;
            this.d = 5;
        }
    }
}

