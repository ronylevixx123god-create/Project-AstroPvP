package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.P;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class RocketEscape extends CustomEnchantment {
    public static HashMap<String, Long> last_rocket_escape;
    public static HashSet<UUID> rocket_escapists;

    static {
        RocketEscape.last_rocket_escape = new HashMap<String, Long>();
        RocketEscape.rocket_escapists = new HashSet<UUID>();
    }

    public RocketEscape() {
        super("Rocket Escape", GeneralUtil.boots, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 15.0;
    }

    public static boolean isEscaping(final Player pl) {
        return RocketEscape.rocket_escapists.contains(pl.getUniqueId());
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getFinalDamage();
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float) ArmorUtil.getArmorValue((Player) player));
            if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
                return;
            }
            if (player.hasMetadata("inDungeonParkour")) {
                return;
            }
            if (player.getWorld().getName().equals("world_koth")) {
                return;
            }
            if (player.getHealth() - dmg <= 0.0 && this.canEscape((Player) player)) {
                final Player pl = (Player) player;
                RocketEscape.last_rocket_escape.put(pl.getUniqueId().toString(), System.currentTimeMillis());
                if (pl.hasMetadata("sabotage") && System.currentTimeMillis() - pl.getMetadata("sabotage").get(0).asLong() <= 1000L) {
                    final int sabotageLevel = pl.getMetadata("sabotageLevel").get(0).asInt();
                    final double negationChance = sabotageLevel * 0.1;
                    if (Math.random() < negationChance) {
                        pl.sendMessage(CC.RedB + " ** " + CC.Gray + "Rocket Escape:" + CC.RedB + " SABOTAGED **");
                        return;
                    }

                }
                if (AstroItemSkinsAPI.hasSkinEquipped(pl, SkinType.HALLOWEEN_BACKPACK) && (!GoldenAdmirer.isAdmirerSilenced(pl))) {
                    pl.playSound(pl.getLocation(), Sound.ANVIL_BREAK, 5, 5);
                    pl.sendMessage(CC.translate("&6&l* CURSED WINGS BLOCKED &nROCKET ESCAPE&r &7[") + attacker.getKiller().getName() + CC.translate("&7] &6&l*"));
                    return;
                }

                if (AstroItemSkinsAPI.hasSkinEquipped(pl, SkinType.CHRISTMAS_HELMET_STAR)) {
                    pl.playSound(pl.getLocation(), Sound.ANVIL_BREAK, 5, 5);
                    pl.sendMessage(CC.translate("&a&l* NORTH STAR BLOCKED &nROCKET ESCAPE&r &7[") + attacker.getKiller().getName() + CC.translate("&7] &a&l*"));
                    return;
                }
                e.setCancelled(true);
                e.setDamage(0.0);
                pl.sendMessage("");
                pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "Your Rocket Escape boots have activated, recover while they last!");
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.EXPLODE, 1.0f, 0.54f);
                pl.setMetadata("no_fall_damage", new FixedMetadataValue(P.getP(), true));
                pl.setVelocity(new Vector(0, (pl.getWorld().getName().equals("world_duels") || pl.getWorld().getName().equals("world_duels2")) ? (2 + level) : (4 + level * 2), 0));
                PlayerPotionEffects.untrackPotionEffect(pl, PotionEffectType.SLOW);
                PlayerPotionEffects.untrackPotionEffect(pl, PotionEffectType.SLOW_DIGGING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * (level + 2), level));
                final UUID uuid = player.getUniqueId();
                RocketEscape.rocket_escapists.add(uuid);
                final Location loc = pl.getLocation();
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, 1.25f, 69);

                Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        if (Bukkit.getPlayer(String.valueOf(uuid)) != null) {
                            de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), Bukkit.getPlayer(String.valueOf(uuid)).getLocation(), (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, 1.25f, 69);
                        }
                        RocketEscape.rocket_escapists.remove(uuid);
                    }
                }, (long) (30 * (level + 2) + 5));
            }
        }
    }

    private boolean canEscape(final Player pl) {
        return !RocketEscape.last_rocket_escape.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - RocketEscape.last_rocket_escape.get(pl.getUniqueId().toString()) > 30000L;
    }
}
