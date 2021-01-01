package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import com.earth2me.essentials.*;
import com.google.common.collect.*;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.*;
import org.zencode.shortninja.staffplus.*;
import com.massivecraft.factions.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.potion.*;
import java.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class Destruction extends CustomEnchantment
{
    Essentials ess3;
    private static HashMap<Integer, BukkitTask> destructionTasks;
    private Multimap<Integer, Player> destructionPlayers;

    public Destruction() {
        super("Destruction", GeneralUtil.helmets, 2);
        this.destructionPlayers = HashMultimap.create();
        this.max = 5;
        this.base = 10.0;
        this.interval = 5.0;
        this.ess3 = (Bukkit.getPluginManager().isPluginEnabled("Essentials") ? ((Essentials)Bukkit.getPluginManager().getPlugin("Essentials")) : null);
        for (int i = 1; i <= this.max; ++i) {
            final int level = i;
            final BukkitTask existing = Destruction.destructionTasks.get(i);
            if (existing != null) {
                existing.cancel();
            }
            final BukkitTask task = new BukkitRunnable() {
                public void run() {
                    final Collection<Player> leveledPlayers = (Collection<Player>)Destruction.this.destructionPlayers.get(level);
                    if (leveledPlayers == null || leveledPlayers.isEmpty()) {
                        return;
                    }
                    final int radius = level;

                    leveledPlayers.forEach(player -> {
                        int val$level = level;
                        Player p = player;
                        if (player.isValid() && !player.isDead() && !player.hasMetadata("spectator") && !StaffPlus.get().freeze.isActive(player.getName()) && !Destruction.this.ess3.getUser(player).isGodModeEnabled()) {
                            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                            int n = 4;
                            Iterator<Entity> iterator = player.getNearbyEntities((double)n, (double)n, (double)n).iterator();
                            while (iterator.hasNext()) {
                                Entity ent = iterator.next();
                                if (ent instanceof Player) {
                                   Player pl = (Player)ent;
                                    if (p.getGameMode() == GameMode.SURVIVAL) {
                                        if (p.hasMetadata("NPC")) {
                                            continue;
                                        }
                                        else if (!p.hasMetadata("spectator") && !StaffPlus.get().freeze.isActive(p.getName()) && !Destruction.this.ess3.getUser(p).isVanished()) {
                                            FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                                            if (fp.getFaction().getRelationTo((RelationParticipator)fplayer.getFaction()).isAtMost(Relation.NEUTRAL) && !WorldGuardUtils.isPvPDisabled(p.getLocation())) {
                                                if (p.getHealth() > 0.0 && !p.isDead()) {
                                                    p.damage((val$level == 5) ? 2.0 : 1.0);
                                                    ParticleEffect.playEffect(ParticleEffect.LAVA, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 20);
                                                }
                                                if (val$level >= 1 && Math.random() <= 0.15) {
                                                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, val$level * 20, (int)((val$level > 2) ? 1 : 0));
                                                    ((Player)pl).addPotionEffect(potionEffect);
                                                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 10);
                                                }
                                                if (val$level >= 2 && Math.random() <= 0.1) {
                                                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, val$level * 20 * 2, val$level + 1));
                                                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 20);
                                                }
                                                if (val$level >= 3 && Math.random() <= 0.085) {
                                                    p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, val$level * 20 * 2, 1));
                                                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 30);
                                                }
                                                else {
                                                    continue;
                                                }
                                            }
                                            else {
                                                continue;
                                            }
                                        }
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                        }
                    });
                }
            }.runTaskTimer((Plugin) Enchantments.getInstance(), 60L * (5 / level), 60L * (5 / level));
            Destruction.destructionTasks.put(i, task);
        }
    }

    public void applyEquipEffect(final Player player, final int level) {
        player.setMetadata(this.name() + "_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
        this.destructionPlayers.put(level, player);
    }

    public void applyUnequipEffect(final Player player, final int level) {
        player.removeMetadata(this.name() + "_enchant", (Plugin)Enchantments.getInstance());
        this.destructionPlayers.remove((Object)level, (Object)player);
    }

    static {
        Destruction.destructionTasks = new HashMap<Integer, BukkitTask>();
    }
}
