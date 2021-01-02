package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.SneakyThrows;
import me.ifamasssxd.cosmicmasks.MaskType;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.mastery.MasteryFeignDeath;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.ItemSkinUtils;
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

public class Phoenix extends CustomEnchantment {
    Essentials ess;

    protected static boolean cosmicOutposts;

    protected static boolean factionUpgrades;

    public Phoenix() {
        super("Phoenix", GeneralUtil.boots, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 20.0D;
        cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
        factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents(new PhoenixListener(this), (Plugin)Enchantments.getInstance());
        Bukkit.getScheduler().runTaskTimer((Plugin)Enchantments.getEssentials(), new reducePhoenixCount(), 12000L, 12000L);
    }

    public static boolean hasPhoenixEquipped(Player player) {
        return phoenixEquipped.containsKey(player.getUniqueId());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        phoenixEquipped.put(player.getUniqueId(), enchantLevel);
    }

    public void applyUnequipEffect(Player player, int level, ItemStack item) {
        phoenixEquipped.remove(player.getUniqueId());
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        applyUnequipEffect(player, enchantLevel, (ItemStack)null);
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue((Player)player));
            if (procPhoenix((Player)player, attacker, level, dmg)) {
                e.setDamage(0.0D);
                e.setCancelled(true);
            }
        }
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, double dmg) throws NoSuchFieldException, IllegalAccessException {
        Integer level = phoenixEquipped.get(player.getUniqueId());
        return (level != null && procPhoenix(player, damager, level.intValue(), dmg));
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, int level, double dmg) throws NoSuchFieldException, IllegalAccessException {
        if (player.getHealth() <= 0.0D)
            return false;
        if (player.hasMetadata("witchered"))
            return false;
        if (player.hasMetadata("noSoulProcs"))
            return false;
        if (player.getHealth() - dmg <= 0.0D) {
            if (damager instanceof Player && Math.random() <= 0.5D && MaskType.hasEquipped((Player) damager, "DEATH_KNIGHT") && !ItemSkinUtils.hasItemSkin(player, "CORRUPT_BELT_UTILITY")) {
                player.sendMessage(CC.RedB + "* PHOENIX BLOCKED [" + CC.Gray + ((Player)damager).getName() + CC.RedB + "] *");
                ((Player)damager).sendMessage(CC.RedB + "* DEATH KNIGHT MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + ((Player)damager).getName());
                return false;
            }
            if (damager instanceof Player && MaskType.hasEquipped((Player) damager, "THANOS") && !ItemSkinUtils.hasItemSkin(player, "CORRUPT_BELT_UTILITY")) {
                player.sendMessage(CC.RedB + "* PHOENIX BLOCKED [" + CC.Gray + ((Player)damager).getName() + CC.RedB + "] *");
                ((Player)damager).sendMessage(CC.RedB + "* THANOS MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                return false;
            }
            int soulCost = getPhoenixProcSoulCost(player);
            if (KillTrackerAPI.hasSoulAmount(player, soulCost) && canUsePhoenix(player, level)) {
                KillTrackerAPI.removeSoulsFromGems(player, soulCost);
                last_phoenix.put(player.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                player.setMetadata("phoenixProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(player.hasMetadata("phoenixProcs") ? (((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() + 1) : 1)));
                GeneralUtil.setPlayerHealth((LivingEntity)player, player.getMaxHealth());
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.UNDERLINE + "PHOENIX SOUL" + ChatColor.GOLD + ChatColor.BOLD + " ***");
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + soulCost + " Soul Gems");
                player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(player) + ChatColor.GRAY + " souls left.");
                player.sendMessage("");
                if (ItemSkinUtils.hasItemSkin(player, "CORRUPT_BELT_UTILITY") && Math.random() < 0.25) {
                    MasteryFeignDeath.vanishPlayer(player, 3);
                }
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                for (Entity ent : player.getNearbyEntities(48.0D, 48.0D, 48.0D)) {
                    if (ent instanceof Player) {
                        ((Player)ent).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** PHOENIX SOUL (" + ChatColor.GRAY + player.getName() + ", -" + soulCost + " souls" + ChatColor.RED + ChatColor.BOLD + ") ***");
                        ((Player)ent).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.25F);
                    }
                }
                Location loc = player.getLocation();
                ParticleEffect.sendToLocation(ParticleEffect.FLAME, loc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.75F, 80);
                ParticleEffect.sendToLocation(ParticleEffect.LAVA, loc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.7F, 20);
                return true;
            }
            if (!player.hasMetadata("outOfSoulsMessage") || ((MetadataValue)player.getMetadata("outOfSoulsMessage").get(0)).asLong() < System.currentTimeMillis()) {
                ParticleEffect.playEffect(ParticleEffect.LAVA, player.getEyeLocation(), 0.4F, 20);
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7F, 0.4F);
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                player.setMetadata("outOfSoulsMessage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis() + 15000L)));
            }
        }
        return false;
    }

    public static boolean canUsePhoenix(Player pl, int enchantLevel) {
        return (!last_phoenix.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - ((Long)last_phoenix.get(pl.getUniqueId().toString())).longValue() > ((4 - enchantLevel) * 60000));
    }

    public static int getPhoenixProcSoulCost(Player player) {
        int soulCost = 500;
        int procsSinceOutOfCombat = player.hasMetadata("phoenixProcs") ? ((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() : 0;
        while (procsSinceOutOfCombat-- > 0)
            soulCost *= 2;
        if (cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(player))
            soulCost /= 2;
        if (factionUpgrades)
            try {
                int heroic = UpgradeUtils.getUpgradeLevel(player, "HEROIC_SOUL_MASTERY");
                if (heroic > 0) {
                    soulCost *= (int)((heroic == 1) ? 0.7D : ((heroic == 2) ? 0.55D : 1.0D));
                } else {
                    int perkLevel = FactionUpgradeAPI.getPerkLevel(player, FactionUpgrade.SOUL_MASTER);
                    if (perkLevel == 1)
                        soulCost *= 0;
                    if (perkLevel == 2)
                        soulCost *= 0;
                    if (perkLevel == 3)
                        soulCost *= 0;
                }
            } catch (Exception exception) {}
        soulCost = Math.min(soulCost, 8000);
        return soulCost;
    }

    protected static HashMap<String, Long> last_phoenix = new HashMap<>();

    public static Map<UUID, Integer> phoenixEquipped = new HashMap<>();
}
