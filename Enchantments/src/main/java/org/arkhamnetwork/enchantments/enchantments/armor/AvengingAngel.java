package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.listeners.CustomEnchantListener;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AvengingAngel extends CustomEnchantment {
    private Map<UUID, Long> cooldowns = (new MapMaker()).expireAfterWrite(20L, TimeUnit.SECONDS).makeMap();

    public AvengingAngel() {
        super("Avenging Angel", GeneralUtil.armor, 10);
        this.max = 4;
        this.base = 25.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents((Listener)new AvengingAngelListener(this), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int level, ItemStack item) {
        applyEquipEffect(player, level);
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata("avenging_angel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(level)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("avenging_angel", (Plugin)Enchantments.getInstance());
    }

    public void applyUnequipEffect(Player player, int enchantLevel, ItemStack item) {
        applyEquipEffect(player, enchantLevel);
    }

    class AvengingAngelListener extends CustomEnchantListener<AvengingAngel> {
        public AvengingAngelListener(AvengingAngel enchant) {
            super(enchant);
        }

        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            Faction faction = FactionUtils.getFaction(event.getEntity(), false);
            if (faction == null || !faction.isNormal())
                return;
            Player dead = event.getEntity();
            double distance = 10000.0D;
            Location location = event.getEntity().getLocation();
            for (Player pl : event.getEntity().getWorld().getPlayers()) {
                if (!pl.getUniqueId().equals(dead.getUniqueId()) &&
                        !AvengingAngel.this.cooldowns.containsKey(pl.getUniqueId()) && pl
                        .hasMetadata("avenging_angel")) {
                    double distanceFromPlayer = pl.getLocation().distanceSquared(location);
                    Faction fac;
                    if (distanceFromPlayer < distance && (fac = FactionUtils.getFaction(pl, false)) != null && faction.getRelationTo((RelationParticipator)fac).isAtLeast(Relation.ALLY)) {
                        int angelLevel = ((MetadataValue)pl.getMetadata("avenging_angel").get(0)).asInt();
                        double distanceNeeded = Math.min(50 + angelLevel * 12, 100);
                        if (distanceNeeded * distanceNeeded >= distanceFromPlayer) {
                            int duration = 6 + angelLevel;
                            AvengingAngel.this.cooldowns.put(pl.getUniqueId(), Long.valueOf(System.currentTimeMillis() + 10000L));
                            double healed = pl.getMaxHealth() - pl.getHealth();
                            GeneralUtil.healPlayer((LivingEntity)pl, healed);
                            pl.sendMessage(CC.YellowB + "* AVENGING ANGEL [" + CC.Gray + "+" + NumberUtils.formatMoney(healed) + " HP (" + dead.getName() + ")" + CC.YellowB + " *");
                            PlayerPotionEffects.trackPotionEffect(pl, new SpecificCustomEnchantment(this.customEnchant, angelLevel), new PotionEffect(PotionEffectType.ABSORPTION, 20 * duration, 5));
                            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
                                PlayerPotionEffects.untrackPotionEffect(pl, new SpecificCustomEnchantment(this.customEnchant, angelLevel));
                                pl.removePotionEffect(PotionEffectType.ABSORPTION);
                            },(20 * duration));
                            Bukkit.getLogger().info("[Enchantments] Healing " + pl.getName() + " to full HP due to " + dead.getName() + " dieing!");
                        }
                    }
                }
            }
        }
    }
}
