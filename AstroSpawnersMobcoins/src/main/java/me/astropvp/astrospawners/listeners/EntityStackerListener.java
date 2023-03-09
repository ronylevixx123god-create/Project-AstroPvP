package me.astropvp.astrospawners.listeners;

import com.cosmicpvp.cosmicutils.utils.*;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import io.netty.util.internal.ThreadLocalRandom;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.MobStackerUtils;
import me.astropvp.astrospawners.utils.StackedMobDeathEvent;
import me.astropvp.astrospawners.utils.config.ConfigManager;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.core.utils.OutpostUtil;
import net.crankedpvp.shards.MobcoinAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityStackerListener implements Listener {


    private final MobStackerUtils mobStackerUtils;
    private final AstroSpawners plugin;
    private final ItemStack grinderKey = CrateKeyItem.getItem(CrateType.GRINDER);
    private final ItemStack godlyKey = CrateKeyItem.getItem(CrateType.GODLY);

    private final Map<UUID, Map<CustomEnchantment, Integer>> cachedWeaponEnchants;

    private Map<UUID, Long> blazeCooldown;
    private final Map<UUID, Integer> playerMoneyMap;

    public EntityStackerListener(AstroSpawners plugin) {
        this.plugin = plugin;
        this.mobStackerUtils = plugin.getMobStackerUtils();
        this.blazeCooldown = new HashMap<>();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(AstroSpawners.getInstance(), () -> {
            this.blazeCooldown.clear();
        }, 10, 5 * 20 * 60);
        this.playerMoneyMap = new HashMap<>();
        this.cachedWeaponEnchants = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMobSpawn(SpawnerSpawnEvent event) {
        mobStackerUtils.stackMobs(event);
    }

    @EventHandler
    public void onStackedEntityDeath(StackedMobDeathEvent e) throws Exception {
        Entity entity = e.getEntity();
        if (e.getDamager() instanceof Player) {

            Player p = (Player) e.getDamager();

            if (!mobStackerUtils.isStackedMob(entity)) return;

            if (e.getEntity().getLastDamageCause() != null) {
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
            }

            if (AstroSpawners.getInstance().getWaitingForAfkGrindingVerification().contains(e.getDamager().getUniqueId())) {
                e.setCancelled(true);
                e.getDamager().sendMessage(CC.translate("&c&l(!)&c You cannot interact with blazes until you have solved the captcha, if you think there is an issue, relog and it will be resolved."));
                return;
            }
            double exp = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("baseXp");
            int remove = 1;

            if (((Player) e.getDamager()).getItemInHand() != null) {
                Map<CustomEnchantment, Integer> enchants;
                UUID uuid = e.getDamager().getUniqueId();
                if (cachedWeaponEnchants.containsKey(uuid)) {
                    enchants = cachedWeaponEnchants.get(e.getDamager().getUniqueId());
                } else {
                    enchants = EnchantmentAPI.getEnchantments(((Player) e.getDamager()).getItemInHand());
                    cachedWeaponEnchants.put(uuid, enchants);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroSpawners.getInstance(), () -> {
                        cachedWeaponEnchants.remove(uuid);
                    }, 60 * 20L);
                }

                if (enchants.containsKey(EnchantmentAPI.getEnchantment("Master Inquisitive"))) {
                    exp *= 1.15;
                } else if (enchants.containsKey(EnchantmentAPI.getEnchantment("Inquisitive"))) {
                    exp *= 1.05;

                }

                if (enchants.containsKey(EnchantmentAPI.getEnchantment("Kill Aura")) && Math.random() < 0.075) {
                    remove = ThreadLocalRandom.current().nextInt(2, 5 + 1);
                }

            }


            int amount = mobStackerUtils.getStackSize(entity);
            if (amount <= 1) {
                mobStackerUtils.removeStackedMob(entity);
                return;
            }
            amount -= remove;
            mobStackerUtils.setStackSize(entity, amount);
            if (remove > 1) {
                p.sendMessage(CC.translate("&6&l* KILL AURA [&7-" + remove + "x&6&l] *"));
            }


            //boolean isOnCosmicClient = CosmicPlayers.get().getCosmicPlayer(p).isOnCosmicClient();


            if (AstroArmorSetsAPI.getCurrentArmorSet(p) != null && AstroArmorSetsAPI.getCurrentArmorSet(p) == ArmorSetType.GRINDER) {
                exp *= 1.15;
            }

            while (remove != 0) {
                blazeLoot(p);
                remove--;
            }


            if (CosmicSkyInventoryPets.get().getPetManager().getPetBuffExpiration().containsKey(p.getUniqueId())) {
                exp *= 2.05;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.MEDIEVAL_SWORD_KING)) {
                exp *= 1.15;
            }


            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(p), OutpostType.COURTYARD)) {
                exp *= 1.15;
            }

            if (OutpostUtil.hasMarsOutpost(p)) {
                exp *= 1.15;
            }

            if (e.getDamager().hasPermission("astrocore.nebula")) {
                exp *= 2;
            }

            p.giveExp((int) exp);

            plugin.addPlayerToAfkGrinding(p);

            boolean showParticles = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getBoolean("showGrindParticles");

            if (showParticles) {
                de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), entity.getLocation(), 0, 1, 0, 0.4f, 12);
            }

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

        if (this.blazeCooldown.containsKey(e.getEntity().getUniqueId())) {
            if (System.currentTimeMillis() < this.blazeCooldown.get(e.getEntity().getUniqueId()) + (long) 83) {
                e.setCancelled(true);
                return;
            }

        }
        this.blazeCooldown.put(e.getEntity().getUniqueId(), System.currentTimeMillis());


        StackedMobDeathEvent stackedMobDeathEvent = new StackedMobDeathEvent(entity, e.getDamager());
        try {
            Bukkit.getPluginManager().callEvent(stackedMobDeathEvent);
        } catch (Exception ignored) {

        }
        entity.setHealth(entity.getMaxHealth());
        e.setCancelled(true);
    }

    public void blazeLoot(Player p) {
        double grinderKeyChance = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("grinderKeyChancePercent") * 0.01; //0.01
        double godlyKeyChance = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("godlyKeyChancePercent") * 0.01; //0.01
        double mobcoinChance = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("mobcoinChance") * 0.01;
        Map<CustomEnchantment, Integer> enchants;
        UUID uuid =p.getUniqueId();
        if (cachedWeaponEnchants.containsKey(uuid)) {
            enchants = cachedWeaponEnchants.get(p.getUniqueId());
        } else {
            enchants = EnchantmentAPI.getEnchantments((p.getItemInHand()));
            cachedWeaponEnchants.put(uuid, enchants);
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroSpawners.getInstance(), () -> {
                cachedWeaponEnchants.remove(uuid);
            }, 60 * 20L);
        }
        attemptGiveMoney(p);

        if (enchants.containsKey(EnchantmentAPI.getEnchantment("Coins"))) {
            mobcoinChance *= 1.4;
        }

        int mcAmount = AstroSpawners.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("mobcoinAmount");
        if (Math.random() < mobcoinChance) {
            MobcoinAPI.addMobcoins(p, mcAmount);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 1);
            p.sendMessage(CC.translate("&6&l+ &6⛃&e" + mcAmount));
        }
        if (AstroArmorSetsAPI.getCurrentArmorSet(p) != null && AstroArmorSetsAPI.getCurrentArmorSet(p) == ArmorSetType.GRINDER) {
            grinderKeyChance *= 1.2;
            godlyKeyChance *= 1.2;
        }



        if (enchants.containsKey(EnchantmentAPI.getEnchantment("Key Grind"))) {
            grinderKeyChance *= 1.4;
            godlyKeyChance *= 1.4;
        }



        double num = Math.random();

        if (godlyKeyChance >= num) {
            PlayerUtil.giveOrDropProtectedItem(p, godlyKey, 60);
        } else if (grinderKeyChance >= num) {
            PlayerUtil.giveOrDropProtectedItem(p, grinderKey, 60);
        }
    }

    public void attemptGiveMoney(Player player) {
        UUID uuid = player.getUniqueId();
        double blazeRodPrice = 22;
        if (this.playerMoneyMap.containsKey(uuid)) {
            int current = this.playerMoneyMap.get(uuid);
            this.playerMoneyMap.put(uuid, current + 1);
        } else {
            this.playerMoneyMap.put(uuid, 1);
        }
        if (this.playerMoneyMap.get(uuid) >= 10) {
            this.playerMoneyMap.put(uuid, 0);
            EconomyUtils.depositBalance(player, blazeRodPrice * 10);
            player.sendMessage(CC.translate("&a&l+ $" + NumberUtils.formatMoney(blazeRodPrice * 10)));
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

        }
    }


}
