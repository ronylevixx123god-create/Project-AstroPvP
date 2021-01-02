package org.arkhamnetwork.enchantments.enchantments.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class Protection extends CustomEnchantment {
    private static HashMap<Integer, BukkitTask> protectionTasks;
    private Multimap<Integer, Player> protectionPlayers;

    public Protection() {
        super("Protection", GeneralUtil.armor, 2);
        this.protectionPlayers = HashMultimap.create();
        this.max = 5;
        this.base = 10.0;
        this.interval = 5.0;
        /*for (int i = 1; i <= this.max; ++i) {
            final int level = i;
            final BukkitTask existing = Protection.protectionTasks.get(i);
            if (existing != null) {
                existing.cancel();
            }
            final int radius = level * 2;
            final BukkitTask task = new BukkitRunnable() {
                public void run() {
                    final Collection<Player> leveledPlayers = (Collection<Player>)Protection.this.protectionPlayers.get(level);
                    if (leveledPlayers == null || leveledPlayers.isEmpty()) {
                        return;
                    }
                    leveledPlayers.forEach(player -> {
                        int val$level = level;
                        int val$radius = radius;
                        if (player.isValid() && !player.isDead() && !player.hasMetadata("spectator") && !player.hasMetadata("in_duel") && !StaffPlus.get().freeze.isActive(player.getName())) {
                            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                            Player pl = player;
                            int amountToHeal = ((val$level > 4) ? 2 : 1);
                            Iterator<Entity> iterator = player.getNearbyEntities((double)val$radius, (double)val$radius, (double)val$radius).iterator();
                            while (iterator.hasNext()) {
                                Entity ent = iterator.next();
                                if (ent instanceof Player) {
                                    Player p = (Player)ent;
                                    FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                                    if (fp != null && fp.getFaction() != null && fp.getFaction().getRelationTo((RelationParticipator)fplayer.getFaction()).isAtLeast(Relation.ALLY)) {
                                        if (p.getHealth() + amountToHeal < p.getMaxHealth() && p.getHealth() > 0.0 && !p.isDead()) {
                                            p.setHealth(p.getHealth() + amountToHeal);
                                            ParticleEffect.playEffect(ParticleEffect.RED_DUST, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 20);
                                        }
                                        if (val$level >= 1 && Math.random() <= 0.0015) {
                                            PotionEffect potionEffect = new PotionEffect(PotionEffectType.REGENERATION, val$level * 20, (int)((val$level > 2) ? 1 : 0));
                                            ((Player)pl).addPotionEffect(potionEffect);
                                            ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 10);
                                        }
                                        if (val$level >= 2 && Math.random() <= 0.001) {
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, val$level * 20 * 2, val$level + 1));
                                            ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 20);
                                        }
                                        if (val$level >= 3 && Math.random() <= 0.0085) {
                                            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, val$level * 20 * 2, val$level + 1));
                                            ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 30);
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
                        }
                    });
                }
            }.runTaskTimer((Plugin)Enchantments.getInstance(), (long)(40 * (5 / level)), (long)(40 * (5 / level)));
            Protection.protectionTasks.put(i, task);
        }
    }
    
    public void applyEquipEffect(final Player player, final int level) {
        player.setMetadata(this.name() + "_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
        this.protectionPlayers.put(level, player);
    }
    
    public void applyUnequipEffect(final Player player, final int level) {
        player.removeMetadata(this.name() + "_enchant", (Plugin)Enchantments.getInstance());
        this.protectionPlayers.remove((Object)level, (Object)player);
    }
    
    static {
        Protection.protectionTasks = new HashMap<Integer, BukkitTask>();
    }*/
    }
}
