package me.ifamasssxd.cosmicbosses.bosses.skeletonhorse;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;

public class PathfinderGoalBossArrowAttack extends PathfinderGoalArrowAttack {
    public EntityInsentient a;

    public IRangedEntity b;

    public EntityLiving c;

    public int d;

    public double e;

    public int f;

    public int g;

    public int h;

    public float i;

    public float j;

    public PathfinderGoalBossArrowAttack(IRangedEntity irangedentity, double d0, int i, int j, float f) {
        super(irangedentity, d0, i, j, f);
        if (!(irangedentity instanceof EntityLiving))
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        this.b = irangedentity;
        this.a = (EntityInsentient)irangedentity;
        this.e = d0;
        this.g = i;
        this.h = j;
        this.i = f;
        this.j = f * f;
        a(3);
    }

    public boolean a() {
        EntityLiving entityliving = this.a.getGoalTarget();
        if (entityliving == null)
            return false;
        this.c = entityliving;
        return true;
    }

    public boolean b() {
        return (a() || !this.a.getNavigation().g());
    }

    public void d() {
        EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        CraftEventFactory.callEntityTargetEvent((Entity)this.b, null, reason);
        this.c = null;
        this.f = 0;
        this.d = -1;
    }

    public void e() {
        double d0 = this.a.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
        boolean flag = this.a.getEntitySenses().canSee((Entity)this.c);
        if (flag) {
            this.f++;
        } else {
            this.f = 0;
        }
        if (d0 <= this.j && this.f >= 20) {
            this.a.getNavigation().h();
        } else {
            this.a.getNavigation().a((Entity)this.c, this.e);
        }
        this.a.getControllerLook().a((Entity)this.c, 30.0F, 30.0F);
        if (--this.d == 0) {
            if (d0 > this.j || !flag)
                return;
            float f = MathHelper.sqrt(d0) / this.i;
            float f1 = f;
            if (f < 0.1F)
                f1 = 0.1F;
            if (f1 > 1.0F)
                f1 = 1.0F;
            this.b.a(this.c, f1);
            this.d = 25;
        } else if (this.d < 0) {
            float f = MathHelper.sqrt(d0) / this.i;
            this.d = 25;
        }
    }
}
