package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.*;
import com.earth2me.essentials.Essentials;
import com.massivecraft.factions.FPlayer;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.ifamasssxd.cosmicmasks.MaskType;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.mastery.MasteryFeignDeath;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Phoenix extends CustomEnchantment {
    public static Map<UUID, Integer> phoenixEquipped = new HashMap<>();
    protected static boolean cosmicOutposts;

    protected static boolean factionUpgrades;
    protected static HashMap<String, Long> last_phoenix = new HashMap<>();
    Essentials ess;

    public Phoenix() {
        super("Phoenix", GeneralUtil.boots, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 20.0D;
        cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
        factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents(new PhoenixListener(this), (Plugin) Enchantments.getInstance());
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) Enchantments.getEssentials(), new reducePhoenixCount(), 12000L, 12000L);
    }

    public static boolean hasPhoenixEquipped(Player player) {
        return phoenixEquipped.containsKey(player.getUniqueId());
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, double dmg) throws Exception {
        Integer level = phoenixEquipped.get(player.getUniqueId());
        return (level != null && procPhoenix(player, damager, level.intValue(), dmg));
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, int level, double dmg) throws Exception {
        if (player.getHealth() <= 0.0D)
            return false;

        if (player.getHealth() - dmg <= 0.0D) {

            if (player.hasMetadata("phoenixCd")) {
                player.sendMessage(CC.translate("&cPHOENIX - Still On Cooldown"));
                return false;
            }

            if (player.hasMetadata("witchered") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7Witcher Ability&c&l] *"));
                return false;
            }
            if (player.hasMetadata("noSoulProcs") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.RUINS_HELMET)) {
                player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7Leviathan Trophy&c&l] *"));
                return false;
            }

            if (MaskType.hasEquipped((Player) damager, "CRANKED") && Math.random() < 0.5) {
                ((Player) damager).playSound(damager.getLocation(), Sound.ANVIL_LAND, 2, 2);
                ((Player) damager).playSound(player.getLocation(), Sound.ANVIL_LAND, 2, 2);
                damager.sendMessage(CC.translate("&4&l* CRANKED MASK BLOCKED PHOENIX [&c" + player.getName() + "&4&l] *"));
                player.sendMessage(CC.translate("&4&l* PHOENIX BLOCKED [&c" + damager.getName() + " Cranked Mask&4&l] *"));
                return false;
            }

            if ((Player)damager != null && FactionUtils.getFPlayer((Player) damager).getPlayer() != null && HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(FactionUtils.getFPlayer(((Player) damager).getPlayer())), OutpostType.THE_GREAT_HALL) && Math.random() < 0.1) {
                player.sendMessage(CC.translate("&b&l** ENEMY FROZEN STRONGHOLD BONUS &7Blocked &cPhoenix &b&l**"));
                ((FPlayer) damager).sendMessage(CC.translate("&b&l** FROZEN STRONGHOLD BONUS &7Blocked &a" + damager.getName() + " &cPhoenix &b&l**"));
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10, 10);
                ((Player)damager).playSound(player.getLocation(), Sound.ANVIL_BREAK, 10, 10);
                return false;
            }




            if (Math.random() <= 0.4D && MaskType.hasEquipped((Player) damager, "DEATH_KNIGHT") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY) && !(FactionUtils.getFPlayer((Player) damager).getPlayer() != null && HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(FactionUtils.getFPlayer(((Player) damager).getPlayer())), OutpostType.THE_GREAT_HALL) && Math.random() < 0.1)) {
                player.sendMessage(CC.RedB + "* PHOENIX BLOCKED [" + CC.Gray + ((Player) damager).getName() + CC.RedB + "] *");
                ((Player) damager).sendMessage(CC.RedB + "* DEATH KNIGHT MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");

                Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + ((Player) damager).getName());
                return false;
            }
            if (MaskType.hasEquipped((Player) damager, "THANOS") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY) && !(FactionUtils.getFPlayer((Player) damager).getPlayer() != null && HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(FactionUtils.getFPlayer(((Player) damager).getPlayer())), OutpostType.THE_GREAT_HALL) && Math.random() < 0.1)) {
                player.sendMessage(CC.RedB + "* PHOENIX BLOCKED [" + CC.Gray + ((Player) damager).getName() + CC.RedB + "] *");
                ((Player) damager).sendMessage(CC.RedB + "* THANOS MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                return false;
            }
            int soulCost = getPhoenixProcSoulCost(player);
            if (KillTrackerAPI.hasSoulAmount(player, soulCost) && canUsePhoenix(player, level)) {
                KillTrackerAPI.removeSoulsFromGems(player, soulCost);
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                player.setMetadata("phoenixProcs", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Integer.valueOf(player.hasMetadata("phoenixProcs") ? (((MetadataValue) player.getMetadata("phoenixProcs").get(0)).asInt() + 1) : 1)));
                GeneralUtil.setPlayerHealth((LivingEntity) player, player.getMaxHealth());
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.UNDERLINE + "PHOENIX SOUL" + ChatColor.GOLD + ChatColor.BOLD + " ***");
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + soulCost + " Soul Gems");
                player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(player) + ChatColor.GRAY + " souls left.");
                player.sendMessage("");
                DamageIndicatorUtils.sendDamageIndicator((Player) player, CC.translate( "&c&l* PHOENIX *"), "PHOENIX");
                player.sendTitle(new Title(CC.translate("&c&l*** PHOENIX ***"), CC.translate("&7You have &n") + KillTrackerAPI.getAllSouls(player) + CC.translate("&7 Souls Left."), 10, 40, 10));
                if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY) && Math.random() < 0.25 && !(FactionUtils.getFPlayer((Player) damager).getPlayer() != null && HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(FactionUtils.getFPlayer(((Player) damager).getPlayer())), OutpostType.THE_GREAT_HALL) && Math.random() < 0.1)) {
                    MasteryFeignDeath.vanishPlayer(player, 3);
                }
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                for (Entity ent : player.getNearbyEntities(48.0D, 48.0D, 48.0D)) {
                    if (ent instanceof Player) {
                        ((Player) ent).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** PHOENIX SOUL (" + ChatColor.GRAY + player.getName() + ", -" + soulCost + " souls" + ChatColor.RED + ChatColor.BOLD + ") ***");
                        ((Player) ent).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                    }
                }
                enterPhoenixCooldown(player);
                Location loc = player.getLocation();
                de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.75F, 80);
                de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.75F, 80);

                return false;
            }
            if (!player.hasMetadata("outOfSoulsMessage") || ((MetadataValue) player.getMetadata("outOfSoulsMessage").get(0)).asLong() < System.currentTimeMillis()) {
                de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), player.getEyeLocation(), 0f, 0.5f, 0f, 0.4f, 20);

                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.4F);
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                player.setMetadata("outOfSoulsMessage", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis() + 15000L)));
            }
        }
        return false;
    }

    public static void enterPhoenixCooldown(Player player) {

        if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.THANKSGIVING_AMULET_AUTUMN)) {
            if (player.hasMetadata("autumn_amulet")) {
                if (player.getMetadata("autumn_amulet").get(0).asInt() == 0) {
                    player.sendMessage(CC.translate("&e&l* AUTUMN AMULET &7Ignored 1st &cPhoenix &7Cooldown &e&l*"));
                    player.setMetadata("autumn_amulet", new FixedMetadataValue(Arkkit.get(), 1));
                    return;
                }
            }
        }
        player.setMetadata("phoenixCd", new FixedMetadataValue(Enchantments.getInstance(), ""));
        last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                player.removeMetadata("phoenixCd", Enchantments.getInstance());
            }
        }, 300 *20);
    }

    public static boolean canUsePhoenix(Player pl, int enchantLevel) {
        return (!last_phoenix.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - last_phoenix.get(pl.getUniqueId().toString()) > ((4 - enchantLevel) * 60000));
    }

    public static int getPhoenixProcSoulCost(Player player) {
        int soulCost = 500;
        int procsSinceOutOfCombat = player.hasMetadata("phoenixProcs") ? ((MetadataValue) player.getMetadata("phoenixProcs").get(0)).asInt() : 0;
        while (procsSinceOutOfCombat-- > 0)
            soulCost *= 2;
        if (cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(player))
            soulCost /= 2;
        if (factionUpgrades)
            try {
                int heroic = UpgradeUtils.getUpgradeLevel(player, "HEROIC_SOUL_MASTERY");
                if (heroic > 0) {
                    soulCost *= (int) ((heroic == 1) ? 0.7D : ((heroic == 2) ? 0.55D : 1.0D));
                } else {
                    int perkLevel = FactionUpgradeAPI.getPerkLevel(player, FactionUpgrade.SOUL_MASTER);
                    if (perkLevel == 1)
                        soulCost *= 0;
                    if (perkLevel == 2)
                        soulCost *= 0;
                    if (perkLevel == 3)
                        soulCost *= 0;
                }
            } catch (Exception exception) {
            }
        soulCost = Math.min(soulCost, 8000);
        return soulCost;
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        phoenixEquipped.put(player.getUniqueId(), enchantLevel);
    }

    public void applyUnequipEffect(Player player, int level, ItemStack item) {
        phoenixEquipped.remove(player.getUniqueId());
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        applyUnequipEffect(player, enchantLevel, (ItemStack) null);
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getFinalDamage();
            try {
                if (procPhoenix((Player) player, attacker, level, dmg)) {
                    e.setDamage(0.0D);
                    e.setCancelled(true);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static long getPhoenixCooldown(Player player) {
        if(!player.hasMetadata("phoenixCd"))
            return 0;
        if(!last_phoenix.containsKey(player.getUniqueId().toString()))
            return 0;
        long cooldown = System.currentTimeMillis() - last_phoenix.get(player.getUniqueId().toString());
        return cooldown/1000;
    }
}
