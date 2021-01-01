package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.bukkit.metadata.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.*;

final class GuardiansListener implements Listener
{
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().hasMetadata("guardianSummoner")) {
            final Player p = (Player)e.getEntity();
            final String summoner = e.getDamager().getMetadata("guardianSummoner").get(0).asString();
            final Player pSummoner = Bukkit.getPlayer(summoner);
            if (pSummoner != null && (summoner.equalsIgnoreCase(p.getName()) || GeneralUtil.isAtleastTruce(p, pSummoner))) {
                e.setCancelled(true);
                e.setDamage(0.0);
                return;
            }
        }
        if (e.getEntity().hasMetadata("guardianSummoner") && e instanceof EntityDamageByEntityEvent) {
            Entity damager = e.getDamager();
            if (damager instanceof Projectile) {
                damager = (Entity)((Projectile)damager).getShooter();
            }
            if (damager instanceof Player) {
                final Player p2 = (Player)damager;
                final String summoner2 = e.getEntity().getMetadata("guardianSummoner").get(0).asString();
                final Player pSummoner2 = Bukkit.getPlayer(summoner2);
                if (pSummoner2 != null && (summoner2.equalsIgnoreCase(p2.getName()) || GeneralUtil.isAlly(p2, pSummoner2))) {
                    e.setCancelled(true);
                    e.setDamage(0.0);
                    return;
                }
                if (pSummoner2 != null && pSummoner2.hasMetadata("bloodLink")) {
                    final int bloodCurseLevel = pSummoner2.getMetadata("bloodLink").get(0).asInt();
                    if (Math.random() < bloodCurseLevel * 0.05 && pSummoner2.getHealth() < pSummoner2.getMaxHealth() && !pSummoner2.isDead()) {
                        GeneralUtil.healPlayer((LivingEntity)pSummoner2, (bloodCurseLevel == 5) ? 2 : 1);
                        ParticleEffect.playEffect(ParticleEffect.HEART, pSummoner2.getEyeLocation(), 0.2f, 1);
                        ParticleEffect.playEffect(ParticleEffect.HEART, e.getEntity().getLocation().add(0.0, 3.0, 0.0).getBlock().getLocation(), 0.2f, 1);
                    }
                }
                ((IronGolem)e.getEntity()).setTarget((LivingEntity)p2);
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("guardianSummoner")) {
            e.getDrops().clear();
            e.getEntity().remove();
        }
        if (e.getEntity().hasMetadata("targetTaskID")) {
            Bukkit.getScheduler().cancelTask(e.getEntity().getMetadata("targetTaskID").get(0).asInt());
        }
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityTarget(final EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("guardianSummoner")) {
            final Player p = (Player)e.getTarget();
            if (e.getEntity().getMetadata("guardianSummoner").get(0).asString().equalsIgnoreCase(p.getName())) {
                e.setCancelled(true);
                return;
            }
            final String summonerName = e.getEntity().getMetadata("guardianSummoner").get(0).asString();
            final Player summonerPlayer = Bukkit.getPlayer(summonerName);
            if (summonerPlayer != null && this.isAlly(p, summonerPlayer)) {
                e.setCancelled(true);
            }
            if (p.hasMetadata("spectator")) {
                e.setCancelled(true);
            }
        }
        if (e.getTarget() != null && e.getTarget().hasMetadata("combatNPC_PlayerName")) {
            e.setCancelled(true);
            e.setTarget((Entity)null);
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
