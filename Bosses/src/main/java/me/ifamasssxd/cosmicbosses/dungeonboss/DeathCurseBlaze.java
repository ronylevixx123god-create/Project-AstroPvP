/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityBlaze
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntitySmallFireball
 *  net.minecraft.server.v1_8_R3.Item
 *  net.minecraft.server.v1_8_R3.MathHelper
 *  net.minecraft.server.v1_8_R3.World
 */
package me.ifamasssxd.cosmicbosses.dungeonboss;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntitySmallFireball;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;

public class DeathCurseBlaze
extends EntityBlaze {
    private int br;

    public DeathCurseBlaze(World world) {
        super(world);
    }

    protected void a(Entity var1, float var2) {
        if (this.attackTicks <= 0 && var2 < 2.0f && var1.boundingBox.e > this.boundingBox.b && var1.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.n(var1);
        } else if (var2 < 30.0f) {
            double var3 = var1.locX - this.locX;
            double var5 = var1.boundingBox.b + (double)(var1.length / 2.0f) - (this.locY + (double)(this.length / 2.0f));
            double var7 = var1.locZ - this.locZ;
            if (this.attackTicks == 0) {
                ++this.br;
                if (this.br == 1) {
                    this.attackTicks = 40;
                    this.a(true);
                } else if (this.br <= 4) {
                    this.attackTicks = 6;
                } else {
                    this.attackTicks = 80;
                    this.br = 0;
                    this.a(false);
                }
                if (this.br > 1) {
                    float var9 = MathHelper.c((float)var2) * 0.5f;
                    this.world.a((EntityHuman)null, 1009, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    for (int var10 = 0; var10 < 1; ++var10) {
                        EntitySmallFireball var11 = new EntitySmallFireball(this.world, (EntityLiving)this, var3 + this.random.nextGaussian() * (double)var9, var5, var7 + this.random.nextGaussian() * (double)var9){

                            public void setDirection(double d0, double d1, double d2) {
                                double d3 = MathHelper.sqrt((double)(d0 * d0 + d1 * d1 + d2 * d2));
                                this.dirX = d0 / d3 * 0.1;
                                this.dirY = d1 / d3 * 0.1;
                                this.dirZ = d2 / d3 * 0.1;
                            }
                        };
                        var11.locY = this.locY + (double)(this.length / 2.0f) + 0.5;
                        this.world.addEntity((Entity)var11);
                    }
                }
            }
            this.yaw = (float)(Math.atan2(var7, var3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.bn = true;
        }
    }

    protected Item getLoot() {
        return null;
    }
}

