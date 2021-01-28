package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.mastery.MasteryPoltergeist;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NatureWrath extends CustomEnchantment {
    int soulCost;
    Essentials ess;
    boolean cosmicOutposts;
    boolean factionUpgrades;

    public NatureWrath() {
        super("Nature Wrath", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 3.0;
        this.soulCost = 75;
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.cosmicOutposts = new File("plugins/CosmicOutposts.jar").exists();
        this.factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        Bukkit.getPluginManager().registerEvents((Listener) new NatureWrathListener(), (Plugin) Enchantments.getInstance());
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.001 * level;
        final boolean KOTH = player.getWorld().getName().equals("world_koth");
        if (random < chance) {
            final Player p = (Player) player;
            if (p.hasMetadata("soulTrap") && p.getMetadata("soulTrap").get(0).asLong() > System.currentTimeMillis()) {
                return;
            }
            int cost = KOTH ? (this.soulCost * 2) : this.soulCost;
            if (this.cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(p)) {
                cost /= 2;
            }
            if (this.factionUpgrades) {
                try {
                    final int heroicLevel = UpgradeUtils.getUpgradeLevel(p, "HEROIC_SOUL_MASTERY");
                    if (heroicLevel > 0) {
                        cost *= (int) ((heroicLevel == 1) ? 0.7 : ((heroicLevel == 2) ? 0.55 : 1.0));
                    } else {
                        final int perkLevel = FactionUpgradeAPI.getPerkLevel(p, FactionUpgrade.SOUL_MASTER);
                        if (perkLevel == 1) {
                            cost *= (int) 0.95;
                        }
                        if (perkLevel == 2) {
                            cost *= (int) 0.9;
                        }
                        if (perkLevel == 3) {
                            cost *= (int) 0.75;
                        }
                    }
                } catch (Exception ex) {
                }
            }
            if (KillTrackerAPI.hasSoulAmount(p, cost)) {
                KillTrackerAPI.removeSoulsFromGems(p, cost);
                p.sendMessage("");
                p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "** NATURE'S WRATH **");
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + cost + " Soul Gems");
                p.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.GRAY + " souls left.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.65f);
                p.sendMessage("");
                this.procNaturesWrath(p, attacker, level, e, 1.0);
            } else {
                de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0,1,0,0.4f,20);

                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
            }
        }
    }

    public int procNaturesWrath(final Player playerWhoProced, final LivingEntity affected, final int level, final EntityDamageEvent e, final double durationModifier) throws Exception {
        final int radius = 8 + level * 5;
        final List<String> affectedPlayers = new ArrayList<String>();
        final boolean KOTH = playerWhoProced.getWorld().getName().equals("world_koth");
        for (final Entity ent : playerWhoProced.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
            if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                continue;
            }
            if (ent.hasMetadata("spectator")) {
                continue;
            }
            if (ent instanceof EnderCrystal) {
                continue;
            }
            if (ent instanceof LivingEntity && !(ent instanceof Player) && !ent.hasMetadata("do_not_clear")) {
                ent.getWorld().strikeLightningEffect(ent.getLocation());
                de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), ent.getLocation(), 0f, 1f,0f, 0.6f, 10);
                de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), ent.getLocation(), 0f, 1f,0f, 0.6f, 30);
                ent.remove();
            } else {
                if (!(ent instanceof Player)) {
                    continue;
                }
                final Player pEnt = (Player) ent;
                if (GeneralUtil.isAtleastTruce(playerWhoProced, pEnt) || pEnt.hasMetadata("natureWrathTask") || pEnt.getGameMode() != GameMode.SURVIVAL || this.ess.getUser(pEnt).isGodModeEnabled()) {
                    continue;
                }
                final CustomEnchantProcOnEntityEvent event = new CustomEnchantProcOnEntityEvent(playerWhoProced, pEnt, e, this, level);
                boolean freeze = true;
                if (!event.callEvent()) {
                    if (event.getEvent() == null) {
                        continue;
                    }
                    freeze = false;
                }
                affectedPlayers.add(pEnt.getName());
                pEnt.getWorld().strikeLightningEffect(pEnt.getLocation());
                final double dodge = MasteryPoltergeist.getChanceToProc(pEnt);
                if (dodge > 0.0 && freeze) {
                    freeze = false;
                    pEnt.sendMessage(CC.DarkRedB + "* POLTERGEIST [" + CC.Gray + "Immune: Nature's Wrath" + CC.DarkRedB + "] *");
                }


                if (AstroItemSkinsAPI.hasSkinEquipped(pEnt, SkinType.CUPID_AMULET)) {
                    freeze = false;
                    pEnt.sendMessage(CC.translate("&c&l* CUPID AMULET BLOCKED &7[Natures Wrath] &c&l*"));
                }
                if (freeze) {
                    pEnt.setWalkSpeed(0.0f);
                    pEnt.removePotionEffect(PotionEffectType.JUMP);
                    pEnt.removePotionEffect(PotionEffectType.SLOW);
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (7 + level) * 20, 128));
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (7 + level) * 20, 128));
                    pEnt.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (7 + level) * 20, 2));
                }
                pEnt.playSound(pEnt.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0f, 2.0f);
                final NatureWrathTask nwt = new NatureWrathTask(pEnt, level);
                nwt.runTaskTimer((Plugin) Enchantments.getInstance(), 20L, 20L);
                pEnt.setMetadata("lastNatureWrathEffect", new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                pEnt.setMetadata("natureWrathTask", new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) nwt));
            }
        }
        final String[] players = affectedPlayers.toArray(new String[affectedPlayers.size()]);
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                for (final String s : players) {
                    if (Bukkit.getPlayer(s) != null) {
                        final Player p = Bukkit.getPlayer(s);
                        if (p.hasMetadata("natureWrathTask")) {
                            final NatureWrathTask nwt = (NatureWrathTask) p.getMetadata("natureWrathTask").get(0).value();
                            nwt.cancel();
                            p.setWalkSpeed(0.2f);
                            p.removeMetadata("natureWrathTask", (Plugin) Enchantments.getInstance());
                        }
                    }
                }
            }
        }, (long) ((KOTH ? ((7 + level) * 5L) : ((7 + level) * 20L)) * durationModifier));
        return affectedPlayers.size();
    }
}
