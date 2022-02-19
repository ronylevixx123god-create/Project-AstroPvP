package me.astropvp.astrospawners.listeners;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.player.CosmicPlayers;
import com.compilex.CompilexPermissions.CompilexPermissions;

import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.cosmicpvp.cosmicutils.utils.*;
import com.rit.sucy.EnchantmentAPI;
import io.netty.util.internal.ThreadLocalRandom;
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
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
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
    private final ItemStack astroKey = CrateKeyItem.getItem(CrateType.ASTRO);
    private final ItemStack legKey = CrateKeyItem.getItem(CrateType.LEGENDARY);



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

            if (AstroSpawners.getInstance().getWaitingForAfkGrindingVerification().contains(e.getDamager().getUniqueId())) {
                e.setCancelled(true);
                e.getDamager().sendMessage(CC.translate("&c&l(!)&c You cannot interact with blazes until you have solved the captcha, if you think there is an issue, relog and it will resolve it."));
                return;
            }

            int remove = 1;
            if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Kill Aura") && Math.random() < 0.15) {
                remove = ThreadLocalRandom.current().nextInt(2, 5 + 1);
            }


            int amount = mobStackerUtils.getStackSize(entity);
            if (amount <= 1) {
                mobStackerUtils.removeStackedMob(entity);
                return;
            }
            amount-=remove;
            mobStackerUtils.setStackSize(entity, amount);


            Player p = (Player) e.getDamager();
            if (remove > 1) {
                p.sendMessage(CC.translate("&6&l* KILL AURA [&7-" +remove + "x&6&l] *" ));
            }
            boolean isOnCosmicClient = CosmicPlayers.get().getCosmicPlayer(p).isOnCosmicClient();


            //EconomyUtils.depositBalance(p, 27);


            double exp = 150;

            //PlayerData data = manager.getPlayerData(p);


            if (AstroArmorSetsAPI.getCurrentArmorSet(p) != null && AstroArmorSetsAPI.getCurrentArmorSet(p) == ArmorSetType.GRINDER) {
                exp += 50;
            }

            if (p.getInventory().firstEmpty() != -1) {
                while (remove != 0) {
                    blazeLoot(p);
                    remove--;
                }
            }


            if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Master Inquisitive")) {
                exp +=50;
            } else if (EnchantmentAPI.itemHasEnchantment(((Player) e.getDamager()).getItemInHand(), "Inquisitive")) {
                exp +=100;

            }



            if (isOnCosmicClient) {
                exp +=50;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.MEDIEVAL_SWORD_KING)) {
                exp +=100;
            }




            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(p), OutpostType.COURTYARD) && Math.random() < 0.1) {
                exp +=75;
            }


            p.giveExp((int) exp);

            plugin.addPlayerToAfkGrinding(p);

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
        } catch (Exception ignored) {

        }
        entity.setHealth(entity.getMaxHealth());
        e.setCancelled(true);
    }

    public void blazeLoot(Player p) {
        if (p != null) {
            double num = Math.random();
            try {
                if (num >= 0 && num <= 0.01) {
                    p.getInventory().addItem(legKey);
                }
                 else if (num >= 0 && num <= 0.08) {
                    p.getInventory().addItem(astroKey);
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
    }


}
