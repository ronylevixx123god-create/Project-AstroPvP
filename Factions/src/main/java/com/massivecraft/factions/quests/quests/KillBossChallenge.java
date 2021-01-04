/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package com.massivecraft.factions.quests.quests;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import java.util.List;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import com.massivecraft.factions.quests.FactionQuests;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillBossChallenge
extends IncrementableChallenge {
    private List<BossType> types;
    private boolean heroicBattlefieldBoss = false;

    public KillBossChallenge(BossType type, int countNeeded) {
        super(countNeeded);
        this.types = Lists.newArrayList(type);
    }

    public KillBossChallenge(List<BossType> types, int countNeeded) {
        super(countNeeded);
        this.types = types;
    }

    public KillBossChallenge(BossType type, boolean heroic, int countNeeded) {
        super(countNeeded);
        this.types = Lists.newArrayList(type);
        this.heroicBattlefieldBoss = heroic;
    }

    @Override
    public String getDataKey() {
        return "bKill";
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        EntityDeathEvent event;
        Boss boss;
        if (object instanceof EntityDeathEvent && (boss = (Boss)Boss.bosses.get((event = (EntityDeathEvent)object).getEntity().getUniqueId())) != null && this.types.contains((Object)boss.getBossType()) && (!this.heroicBattlefieldBoss || event.getEntity().hasMetadata("heroicEnvoyBoss"))) {
            this.incrementChallenge(parent, player, faction, 1L, object);
        }
    }

    @Override
    public String getDescription() {
        if (this.types.size() > 1) {
            return "Kill " + this.getCountNeeded() + "x Conquest Bosses";
        }
        BossType type = this.types.get(0);
        return "Kill " + (type == BossType.SKELETON_HORSE ? "an " : (type != BossType.DEMONIC_WITHER ? "a " : "")) + (this.heroicBattlefieldBoss ? "Heroic Battefield Boss" : type.getEggName());
    }
}

