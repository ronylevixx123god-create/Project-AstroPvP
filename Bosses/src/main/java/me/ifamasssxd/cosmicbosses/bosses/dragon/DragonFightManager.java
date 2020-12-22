/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityEnderCrystal
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.IEntitySelector
 *  net.minecraft.server.v1_7_R4.World
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Predicate<EntityPlayer> VALID_PLAYER = ((Predicate<EntityPlayer>)((IEntitySelector)IEntitySelector.a)::a).and(DragonFightManager.withinRange(0.0, 128.0, 0.0, 192.0)::apply);
    private final World world;
    private int ticksSinceDragonSeen;
    private int aliveCrystals;
    private int ticksSinceCrystalsScanned;
    private int ticksSinceLastPlayerScan;
    private boolean dragonKilled;
    private boolean previouslyKilled;
    private UUID dragonUniqueId;
    private List<EntityEnderCrystal> crystals;
    private EndDragon dragonBoss;

    public static <T extends Entity> com.google.common.base.Predicate<T> withinRange(double x, double y, double z, double range) {
        double d0 = range * range;
        return p_apply_1_ -> p_apply_1_ != null && p_apply_1_.f(x, y, z) <= d0;
    }

    public DragonFightManager(World worldIn) {
        this.world = worldIn;
    }

    public void tick() {
        if (++this.ticksSinceLastPlayerScan >= 20) {
            this.ticksSinceLastPlayerScan = 0;
        }
        if (this.dragonBoss == null) {
            return;
        }
        EndDragonEntity entity = this.dragonBoss.getNMSEntity();
        if (entity == null || !entity.isAlive()) {
            return;
        }
        if (!this.world.players.isEmpty() && !this.dragonKilled && ++this.ticksSinceCrystalsScanned >= 100) {
            this.findAliveCrystals();
            this.ticksSinceCrystalsScanned = 0;
        }
    }

    public void dragonUpdate(EndDragonEntity entity) {
    }

    private void loadChunks() {
        for (int i = -8; i <= 8; ++i) {
            for (int j = -8; j <= 8; ++j) {
                this.world.getChunkAt(i, j);
            }
        }
    }

    public int getNumAliveCrystals() {
        return this.aliveCrystals;
    }

    private void findAliveCrystals() {
        this.ticksSinceCrystalsScanned = 0;
        this.aliveCrystals = 0;
        this.world.entityList.forEach(ent -> {
            if (ent instanceof EntityEnderCrystal) {
                ++this.aliveCrystals;
            }
        });
        LOGGER.debug("Found {} end crystals still alive", new Object[]{this.aliveCrystals});
    }

    public boolean hasPreviouslyKilledDragon() {
        return this.previouslyKilled;
    }
}

