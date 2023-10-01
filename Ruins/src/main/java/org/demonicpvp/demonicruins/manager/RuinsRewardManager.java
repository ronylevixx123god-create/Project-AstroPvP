package org.demonicpvp.demonicruins.manager;


import net.astropvp.lootfilter.LootFilterAPI;
import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.demonicpvp.demonicruins.DemonicRuins;
import org.demonicpvp.demonicruins.mob.MobUtil;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.demonicpvp.demonicruins.mob.MobUtil.*;

public class RuinsRewardManager implements Listener {
    private final DemonicRuins plugin;

    private LootTable ruinsLoot;

    //private LootTable leviathanBossLoottable;

    public RuinsRewardManager(DemonicRuins plugin) {
        this.plugin = plugin;

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void on(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (MobUtil.isRuinMob(entity)) {
            event.getDrops().clear();

            //boolean controllingOutpost = false;//entity.getKiller() != null && HCFOutpostAPI.isControllingOutpost(entity.getKiller(), OutpostType.RUINS);

            if (MobUtil.getRuinsMobType(entity).equals(GUARDIAN_RUINS_MOB_VALUE) && Math.random() < 0.5) {
                LootFilterAPI.giveItem(event.getEntity().getKiller(), LootTable.RUINS, LootTableAPI.getRandomLootItem(LootTable.RUINS));
            } else if (MobUtil.getRuinsMobType(entity).equals(ELITE_GUARDIAN_RUINS_MOB_VALUE)) {
                if (entity.hasMetadata(IS_ELDER_GUARDIAN_KEY)) {
                    LootFilterAPI.giveItem(event.getEntity().getKiller(), LootTable.RUINS, LootTableAPI.getRandomLootItem(LootTable.RUINS));
                } else if (Math.random() < 0.65) {
                    LootFilterAPI.giveItem(event.getEntity().getKiller(), LootTable.RUINS, LootTableAPI.getRandomLootItem(LootTable.RUINS));
                }
            }
        }
    }
}
