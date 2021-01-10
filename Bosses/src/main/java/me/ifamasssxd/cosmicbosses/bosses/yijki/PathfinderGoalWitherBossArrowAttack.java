/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 *  net.minecraft.server.v1_7_R4.MathHelper
 */
package me.ifamasssxd.cosmicbosses.bosses.yijki;

import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossArrowAttack;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbominationEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.MathHelper;

public class PathfinderGoalWitherBossArrowAttack
extends PathfinderGoalBossArrowAttack {
    private Boss boss;

    public PathfinderGoalWitherBossArrowAttack(IRangedEntity irangedentity, double d0, int i, int j, float f) {
        super(irangedentity, d0, i, j, f);
    }

    public PathfinderGoalWitherBossArrowAttack(Boss boss, IRangedEntity irangedentity, double d0, int i, int j, float f) {
        super(irangedentity, d0, i, j, f);
        this.boss = boss;
    }

    public void setSpeed(int speed) {
        this.g = speed;
    }

    @Override
    public void e() {
        double d0 = this.a.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
        boolean flag = this.a.getEntitySenses().canSee((Entity)this.c);
        this.f = flag ? ++this.f : 0;
        if (d0 <= (double)this.j && this.f >= 20) {
            this.a.getNavigation().h();
        } else {
            this.a.getNavigation().a(this.c, this.e);
        }
        this.a.getControllerLook().a(this.c, 30.0f, 30.0f);
        if (--this.d == 0) {
            float f;
            if (d0 > (double)this.j || !flag) {
                return;
            }
            float f1 = f = MathHelper.sqrt(d0) / this.i;
            if (f < 0.1f) {
                f1 = 0.1f;
            }
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            this.b.a(this.c, f1);
            if (this.boss != null) {
                this.d = this.boss.getPhase() == Phase.TWO ? 5 : 10;
            } else {
                int possible = Math.max(1, this.h - this.g);
                this.d = ThreadLocalRandom.current().nextInt(possible) + this.g;
            }
            if (this.b instanceof EvilAbominationEntity) {
                ((EvilAbominationEntity)this.b).shootNearby(this.c);
            }
        } else if (this.d < 0) {
            float f = MathHelper.sqrt(d0) / this.i;
            this.d = 10;
        }
    }
}

