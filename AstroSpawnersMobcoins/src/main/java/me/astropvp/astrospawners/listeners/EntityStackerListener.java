package me.astropvp.astrospawners.listeners;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.player.CosmicPlayers;
import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.cosmicpvp.cosmicutils.utils.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.astrospawners.utils.StackedMobDeathEvent;
import me.astropvp.astrospawners.utils.*;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.ifamasssxd.cosmiccoinflip.struct.CoinFlipManager;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import net.astropvp.core.utils.GrindingMessagesUtil;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatExpireTask;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.github.paperspigot.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityStackerListener implements Listener {


    private final MobStackerUtils mobStackerUtils;
    private final PlayerDataManager manager;
    private final AstroSpawners plugin;
    private final ItemStack novaKey = CrateKeyItem.getItem(CrateType.NOVA);
    private final ItemStack stellarKey = CrateKeyItem.getItem(CrateType.STELLAR);


    public EntityStackerListener(AstroSpawners plugin) {
        this.plugin = plugin;
        this.mobStackerUtils = plugin.getMobStackerUtils();
        this.manager = plugin.getManager();

    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMobSpawn(SpawnerSpawnEvent event) {
        mobStackerUtils.stackMobs(event);
    }

    @EventHandler
    public void onStackedEntityDeath(StackedMobDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getDamager() instanceof Player) {


            if (!mobStackerUtils.isStackedMob(entity)) return;

            if (e.getEntity().getLastDamageCause() != null) {
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
            }

            if (plugin.getWaitingForAfkGrindingVerification().contains(e.getDamager().getUniqueId())) {
                e.setCancelled(true);
                return;
            }


            int amount = mobStackerUtils.getStackSize(entity);
            if (amount <= 1) {
                mobStackerUtils.removeStackedMob(entity);
                return;
            }
            amount--;
            mobStackerUtils.setStackSize(entity, amount);


            Player p = (Player) e.getDamager();

            boolean isOnCosmicClient = CosmicPlayers.get().getCosmicPlayer(p).isOnCosmicClient();


            EconomyUtils.depositBalance(p, 27);


            double exp = 200;

            PlayerData data = manager.getPlayerData(p);


            if (CosmicArmorSetsAPI.getArmorSet(p) != null && CosmicArmorSetsAPI.getArmorSet(p) == ArmorSet.GRINDER) {
                exp = exp * 1.5;


                if (Math.random() < 0.05) {
                    data.setCoins(data.getCoins() + 2);
                    ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&6+2 Mobcoins &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");

                } else {
                    ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&a+1 Mobcoin &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");
                    data.setCoins(data.getCoins() + 1);

                }

            } else {
                data.setCoins(data.getCoins() + 1);
                ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&a+1 Mobcoin &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");

            }

            if (p.getInventory().firstEmpty() != -1) {
                blazeLoot(p);
            }


            if (p.getItemInHand().getType() == Material.DIAMOND_SWORD && p.getItemInHand().getItemMeta().hasLore()) {
                exp = exp + 50;
            }

            if (isOnCosmicClient) {
                exp = exp * 1.5;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.MEDIEVAL_SWORD_KING)) {
                exp = exp * 2;
            }


            if (CustomBlockUtil.chunkHasCertainBlock(p, "TREASURE_CHEST")) {
                exp = exp * 1.5;
            }


            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(p), OutpostType.COURTYARD) && Math.random() < 0.1) {
                exp = exp * 1.75;
            }


            p.giveExp((int) exp);

            plugin.addPlayerToAfkGrinding(p);
            System.out.println("Added " + p.getName() + " to anti afking players list!");


        }


    }


    @EventHandler
    public void onMobDeath(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Creature)) return;

        if (e.getEntity().getLastDamageCause() != null) {
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
        }

        Creature entity = (Creature) e.getEntity();
        if (!mobStackerUtils.isStackedMob(entity)) {
            return;
        }
        ;
        StackedMobDeathEvent stackedMobDeathEvent = new StackedMobDeathEvent(entity, e.getDamager());
        try {
            Bukkit.getPluginManager().callEvent(stackedMobDeathEvent);
        } catch (Exception ex) {

        }
        entity.setHealth(entity.getMaxHealth());
        e.setCancelled(true);
    }

    public void blazeLoot(Player p) {
        if (p != null) {
            double num = Math.random();
            try {

                if (num >= 0 && num <= 0.02) {
                    p.getInventory().addItem(stellarKey);
                } else if (num >= 0 && num <= 0.05) {
                    p.getInventory().addItem(novaKey);
                }
            } catch (IllegalArgumentException ex) {
            }
        }
    }


}
