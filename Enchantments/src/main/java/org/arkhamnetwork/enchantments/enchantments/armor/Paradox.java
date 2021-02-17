package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class Paradox extends CustomEnchantment {
    int soulCost;
    Essentials ess;
    private boolean cosmicOutposts;
    private boolean factionUpgrades;

    public Paradox() {
        super("Paradox", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 3.0;
        this.soulCost = 5;
        this.cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
        this.factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
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
        final double chance = 0.1 * level;
        final int radius = 8 + level * 4;
        final List<Player> affectedPlayers = new ArrayList<Player>();
        if (random < chance) {
            final Player p = (Player) player;
            final double amountToHeal = e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (level / 10);
            int cost = this.soulCost;
            if (this.cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(p)) {
                cost /= 2;
            }
            if (this.factionUpgrades) {
                try {
                    final int heroic = UpgradeUtils.getUpgradeLevel(p, "HEROIC_SOUL_MASTERY");
                    if (heroic > 0) {
                        cost *= (int) ((heroic == 1) ? 0.7 : ((heroic == 2) ? 0.55 : 1.0));
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
                int highImpactPlayers = 0;
                for (final Entity ent : p.getNearbyEntities((double) radius, (double) (radius + 128), (double) radius)) {
                    if (ent.hasMetadata("spectator")) {
                        continue;
                    }
                    if (!(ent instanceof Player)) {
                        continue;
                    }
                    final Player pEnt = (Player) ent;
                    if (!GeneralUtil.isAlly(p, pEnt) || pEnt.getGameMode() != GameMode.SURVIVAL) {
                        continue;
                    }
                    affectedPlayers.add(pEnt);
                    if (pEnt.getHealth() + amountToHeal > pEnt.getMaxHealth()) {
                        continue;
                    }
                    ++highImpactPlayers;
                }
                if (highImpactPlayers > 0) {
                    KillTrackerAPI.removeSoulsFromGems(p, cost);
                    for (final Player pEnt2 : affectedPlayers) {
                        try {
                            de.inventivegames.particle.ParticleEffect.HAPPY_VILLAGER.sendToPlayers(Bukkit.getOnlinePlayers(), pEnt2.getLocation(), 0,0,0,0.64f,20);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        GeneralUtil.healPlayer((LivingEntity) pEnt2, amountToHeal);
                        pEnt2.playSound(pEnt2.getLocation(), Sound.EAT, 1.0f, 2.0f);
                        pEnt2.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "** PARADOX [" + p.getName() + "] (+" + amountToHeal + "HP) **");
                    }
                    p.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + "** PARADOX [" + affectedPlayers.size() * amountToHeal + " -> HP]  **");
                    if (KillTrackerAPI.getAllSouls(p) % 100 == 0 || KillTrackerAPI.getAllSouls(p) < 10) {
                        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + cost + " Soul Gems");
                        p.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.GRAY + " souls left.");
                    }
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.65f);
                }
            } else {
                try {
                    de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getEyeLocation(), 0f, 0.5f, 0f, 0.3f, 30);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
            }
        }
    }
}
