package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.util.*;
import org.arkhamnetwork.enchantments.listeners.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.Arkkit.utils.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.util.Vector;

public class HeroicTeleportation extends HeroicCustomEnchantment
{
    public HeroicTeleportation() {
        super("Bidirectional Teleportation", "Teleportation", false);
        this.max = 5;
        this.setNaturalMaterials(new Material[] { Material.BOW });
        Bukkit.getPluginManager().registerEvents((Listener)new HeroicTeleportListener(this), (Plugin)Enchantments.getInstance());
    }
    
    public void applyProjectileEffect(final LivingEntity user, final int enchantLevel, final ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof Arrow && !event.isCancelled()) {
            event.getEntity().setMetadata("bi_teleport_arrow", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
        }
    }
    
    public void grapplePlayer(final Player target, final Player damager, final int level) {
        if (!target.getWorld().equals(damager.getWorld())) {
            Bukkit.getLogger().info("unable to grapple to " + target.getName() + " because " + damager.getName() + " was in another world!");
            return;
        }
        final Vector velocity = target.getLocation().toVector().subtract(damager.getLocation().toVector());
        if (velocity.length() != 0.0) {
            velocity.normalize();
        }
        final double distance = target.getLocation().distanceSquared(damager.getLocation());
        double maxDistance = level * 10;
        maxDistance *= maxDistance;
        if (distance > maxDistance) {
            if (level == this.getMaxLevel()) {
                damager.sendMessage(CC.RedB + "(!) " + CC.Red + "Your Teleportation target is too far away to pull with Bidirectional Teleportation!");
            }
            else {
                damager.sendMessage(CC.RedB + "(!) " + CC.Red + "Your Teleportation target is too far away to pull with this Bidirectional Teleportation level!");
            }
            return;
        }
        final double toMultiply = Math.min(Math.max(1.0, distance / 50.0), 6.0 + level * 0.5);
        final double y = velocity.getY();
        velocity.multiply(-toMultiply);
        velocity.setY(y * (-toMultiply / 1.75));
        if (velocity.getY() < 0.01 && velocity.getY() > -0.01) {
            velocity.multiply(new Vector(1.0, 7.5, 1.0));
        }
        target.setVelocity(velocity);
        target.playSound(target.getLocation(), Sound.ZOMBIE_METAL, 10.0f, 1.1f);
        target.playSound(target.getLocation(), Sound.WITHER_SHOOT, 10.0f, 1.5f);
        target.sendMessage(CC.RedB + "* BIDIRECTIONAL TELEPORT [towards: " + CC.Gray + damager.getName() + CC.RedB + "] *");
        damager.playSound(target.getLocation(), Sound.ZOMBIE_METAL, 10.0f, 1.1f);
        damager.playSound(target.getLocation(), Sound.WITHER_SHOOT, 10.0f, 1.5f);
        damager.sendMessage(CC.LightPurpleB + "* BIDIRECTIONAL TELEPORT [pulling: " + CC.Gray + target.getName() + CC.LightPurpleB + "] *");
    }
    
    public void trapTarget(final Player player, final Player damager, final int level) {
        if (player.getWalkSpeed() < 0.2f) {
            return;
        }
        player.setWalkSpeed(0.0f);
        final int duration = (int)(1.0 + level * 0.125);
        player.sendMessage(CC.RedB + "* BIDIRECTIONAL TRAPPED [by: " + CC.Gray + damager.getName() + " (" + NumberUtils.formatSeconds((double)duration) + "s)]" + CC.RedB + " *");
        Enchantments.getInstance().getServer().getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> player.setWalkSpeed(0.2f), (long)(duration * 20));
    }
    
    class HeroicTeleportListener extends CustomEnchantListener<HeroicTeleportation>
    {
        public HeroicTeleportListener(final HeroicTeleportation enchant) {
            super(enchant);
        }
        
        @EventHandler
        public void onPlayerDamageByArrow(final EntityDamageByEntityEvent event) {
            if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player && ((Arrow)event.getDamager()).getShooter() instanceof Player) {
                final List<MetadataValue> meta = (List<MetadataValue>)event.getDamager().getMetadata("bi_teleport_arrow");
                if (meta == null || meta.isEmpty()) {
                    return;
                }
                final int level = meta.get(0).asInt();
                final double chanceToProc = 0.066 * level;
                final Player shooter = (Player)((Arrow)event.getDamager()).getShooter();
                final Player playerHit = (Player)event.getEntity();
                if (!shooter.canSee(playerHit) || WorldGuardUtils.isPvPDisabled(playerHit.getLocation()) || WorldGuardUtils.isPvPDisabled(shooter.getLocation())) {
                    return;
                }
                final Relation relation = FactionUtils.getRelationTo((RelationParticipator)FactionUtils.getFPlayer(shooter), (RelationParticipator)FactionUtils.getFPlayer((Player)event.getEntity()));
                if (relation == Relation.ENEMY || relation == Relation.NEUTRAL) {
                    if (MaskType.hasEquipped(playerHit, "GLITCH")) {
                        return;
                    }
                    if (Math.random() <= chanceToProc) {
                        ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 35);
                        ParticleEffect.sendToLocation(ParticleEffect.FLAME, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.1f, 10);
                        ((HeroicTeleportation)this.customEnchant).grapplePlayer(playerHit, shooter, level);
                        event.setCancelled(true);
                        event.setDamage(0.0);
                        return;
                    }
                    ((HeroicTeleportation)this.customEnchant).trapTarget(playerHit, shooter, level);
                }
                else if (relation == Relation.ALLY || relation == Relation.MEMBER) {
                    if (playerHit.getWorld().getName().equals("cosmic-station-1")) {
                        return;
                    }
                    event.setCancelled(true);
                    event.setDamage(0.0);
                    if (event.getDamager() instanceof Projectile) {
                        event.getDamager().remove();
                    }
                    final Location tp = playerHit.getLocation();
                    tp.setPitch(shooter.getLocation().getPitch());
                    tp.setYaw(shooter.getLocation().getYaw());
                    if (!shooter.getWorld().getName().equals(playerHit.getWorld().getName()) || tp.distanceSquared(shooter.getLocation()) > Math.pow(30.0, 2.0)) {
                        shooter.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your ally is too far away to teleport to with this level of Bidirectional Teleportation.");
                        return;
                    }
                    if ((playerHit.getWorld().getEnvironment() == World.Environment.THE_END || playerHit.getWorld().getName().equals("world_koth")) && !WorldGuardUtils.isPvPDisabled(shooter.getLocation()) && WorldGuardUtils.isPvPDisabled(tp)) {
                        shooter.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot teleport from PvP-enabled to PvP-disabled with Bidirectional Teleportation in The End or KOTH.");
                        return;
                    }
                    shooter.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)tp));
                    shooter.teleport(tp, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    shooter.removeMetadata("ignore_combat_tag", (Plugin)Enchantments.getInstance());
                    shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 0.75f, 0.341f);
                    final Location loc = playerHit.getLocation().add(0.0, 0.5, 0.0);
                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 35);
                    ParticleEffect.sendToLocation(ParticleEffect.FLAME, loc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.1f, 10);
                }
            }
        }
    }
}
