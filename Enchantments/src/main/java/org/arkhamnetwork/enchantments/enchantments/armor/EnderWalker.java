package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTracker;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EnderWalker extends CustomEnchantment {
    public EnderWalker() {
        super("Ender Walker", GeneralUtil.boots, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 10.0;
    }

    public static void updateEntity(final Entity entity, final List<Player> observers) {
        final World world = entity.getWorld();
        final WorldServer worldServer = ((CraftWorld) world).getHandle();
        final EntityTracker tracker = worldServer.tracker;
        final EntityTrackerEntry entry = tracker.trackedEntities.get(entity.getEntityId());
        final List<EntityPlayer> nmsPlayers = getNmsPlayers(observers);
        if (entry != null) {
            entry.trackedPlayers.removeAll(nmsPlayers);
            entry.scanPlayers((List) nmsPlayers);
        }
    }

    private static List<EntityPlayer> getNmsPlayers(final List<Player> players) {
        final List<EntityPlayer> nsmPlayers = new ArrayList<EntityPlayer>();
        for (final Player bukkitPlayer : players) {
            final CraftPlayer craftPlayer = (CraftPlayer) bukkitPlayer;
            nsmPlayers.add(craftPlayer.getHandle());
        }
        return nsmPlayers;
    }

    public void applyEquipEffect(final Player player, final int level) {
        player.playSound(player.getLocation(), Sound.WITHER_HURT, 1.0f, 0.6f);
    }

    public void applyUnequipEffect(final Player player, final int level) {
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON) {
            e.setCancelled(true);
            final boolean heal = Math.random() <= level * 0.15;
            if (heal && GeneralUtil.healPlayer(player, e.getDamage(EntityDamageEvent.DamageModifier.BASE))) {
                de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayer((Player) player, player.getLocation(), 0,1,0,0.75f,35);
            }
            e.setDamage(0.0);
            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @Override
                public void run() {
                    if (!player.hasPotionEffect(PotionEffectType.BLINDNESS) && !player.hasMetadata("clarityEnchant")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                    }
                }
            }, 20L);
            de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayer((Player) player, player.getLocation(), 0,1,0,0.75f,70);
        }
    }

    private List<Player> getNearbyPlayers(final List<Entity> ents) {
        final List<Player> players = new ArrayList<Player>();
        for (final Entity e : ents) {
            if (e instanceof Player) {
                players.add((Player) e);
            }
        }
        return players;
    }
}
