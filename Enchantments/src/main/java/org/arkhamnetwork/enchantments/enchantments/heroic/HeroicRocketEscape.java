package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import java.util.UUID;

import me.astropvp.AstroBackpacksAPI;
import me.astropvp.objects.BackpackType;
import me.cosmicpvp.CosmicFactionUtils.CosmicFactionUtils;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.RocketEscape;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class HeroicRocketEscape extends HeroicCustomEnchantment {
    public HeroicRocketEscape() {
        super("Guided Rocket Escape", "Rocket Escape", false);
        this.max = 3;
        setNaturalMaterials(GeneralUtil.boots);
        this.base = 15.0D;
    }

    private boolean canEscape(Player pl) {
        if (!RocketEscape.last_rocket_escape.containsKey(pl.getUniqueId().toString()))
            return true;
        return (System.currentTimeMillis() - ((Long)RocketEscape.last_rocket_escape.get(pl.getUniqueId().toString())).longValue() > 180000L);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity target, int level, EntityDamageEvent event) {
        if (player.hasMetadata("neutralized"))
            return;
        if (Math.random() < 0.3D && player instanceof Player) {
            double dmg = event.getFinalDamage();
            if (player.getWorld().getEnvironment() == World.Environment.THE_END)
                return;
            if (player.hasMetadata("inDungeonParkour"))
                return;
            if (player.getWorld().getName().equals("world_koth"))
                return;
            if (player.getHealth() - dmg <= 0.0D &&
                    canEscape((Player)player)) {
                Player pl = (Player)player;
                RocketEscape.last_rocket_escape.put(pl.getUniqueId().toString(), Long.valueOf(System.currentTimeMillis()));
                if (pl.hasMetadata("sabotage") &&
                        System.currentTimeMillis() - ((MetadataValue)pl.getMetadata("sabotage").get(0)).asLong() <= 1000L) {
                    int sabotageLevel = ((MetadataValue)pl.getMetadata("sabotageLevel").get(0)).asInt();
                    double negationChance = sabotageLevel * 0.1D;
                    if (Math.random() < negationChance) {
                        pl.sendMessage(CC.RedB + " ** " + CC.Gray + "Guided Rocket Escape:" + CC.RedB + " SABOTAGED **");
                        return;
                    }


                    if (((Player)player).getInventory().getChestplate() != null && AstroBackpacksAPI.itemHasSkin(((Player)player).getInventory().getChestplate()) && AstroBackpacksAPI.getBackpackByItem(((Player)player).getInventory().getChestplate()).equals(BackpackType.CURSED_WINGS)) {
                        pl.playSound(pl.getLocation(), Sound.ANVIL_BREAK , 5, 5);
                        pl.sendMessage("§6§l* CURSED WINGS BLOCKED §nROCKET ESCAPE§r §7[" + target.getKiller().getName() + "§7] §6§l*");
                        return;
                    }
                }
                event.setCancelled(true);
                event.setDamage(0.0D);
                pl.sendMessage("");
                pl.sendMessage(CC.GreenB + "(!) " + CC.Green + "Your Guided Rocket Escape boots have activated, flight temporarily enabled, recover while they last!");
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.EXPLODE, 1.0F, 0.54F);
                pl.setMetadata("no_fall_damage", (MetadataValue)new FixedMetadataValue((Plugin)CosmicFactionUtils.getPlugin(), Boolean.valueOf(true)));
                Vector velocity = new Vector(0, (pl.getWorld().getName().equals("world_duels") || pl.getWorld().getName().equals("world_duels2")) ? (2 + level) : (4 + level * 2), 0);
                Bukkit.getPluginManager().callEvent((Event)new PlayerVelocityEvent(pl, velocity));
                pl.setVelocity(velocity);
                PlayerPotionEffects.untrackPotionEffect(pl, PotionEffectType.SLOW);
                PlayerPotionEffects.untrackPotionEffect(pl, PotionEffectType.SLOW_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * (level + 2), level));
                UUID uuid = player.getUniqueId();
                RocketEscape.rocket_escapists.add(uuid);
                Location loc = pl.getLocation();
                ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 69);
                long flightDuration = level;
                pl.setMetadata("allow_flight", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis() + flightDuration * 1000L)));
                pl.setAllowFlight(true);
                pl.setFlying(true);
                pl.setFlySpeed(0.2F + level * 0.03F);
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
                    pl.setFlySpeed(0.1F);
                    pl.setFlying(false);
                    pl.setAllowFlight(false);
                },20L * flightDuration);
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
                    Player onlinePlayer = Bukkit.getPlayer(uuid);
                    if (onlinePlayer != null)
                        ParticleEffect.sendToLocation(ParticleEffect.CLOUD, onlinePlayer.getLocation(), (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, (float)Math.random() * 2.0F, 1.25F, 69);
                    RocketEscape.rocket_escapists.remove(uuid);
                },(20 * (level + 2) + 5));
            }
        }
    }
}
