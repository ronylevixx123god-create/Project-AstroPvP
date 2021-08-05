package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelfDestruct extends CustomEnchantment {
    public SelfDestruct() {
        super("Self Destruct", GeneralUtil.armor, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 10.0;
        Enchantments.getInstance().getServer().getPluginManager().registerEvents((Listener) new SelfDestructListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player) player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float) ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {

                if (p.hasMetadata("SelfDestructing") && System.currentTimeMillis() - p.getMetadata("SelfDestructing").get(0).asLong() <= 10000L) {
                    return;
                }

                if (WeaponBonusUtils.getWeaponBonusByItem(((Player)attacker).getItemInHand()) == WeaponBonusType.CHAMPION && Math.random() < 0.5) {
                    attacker.sendMessage(CC.translate("&c&l** BLOCKED " + player.getName() + " &c&lSELF DESTRUCT &7(Champion Weapon Bonus) &c&l**"));
                    player.sendMessage(CC.translate("&c&l** SELF DESTRUCT BLOCKED &7(" + attacker.getName() + " Champion Weapon Bonus) &c&l**"));
                    return;
                }
                p.setMetadata("SelfDestructing", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                final int radius = (int) (level * 2.5f);
                final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
                final List<LivingEntity> victims = new ArrayList<LivingEntity>();
                for (final Entity ent : player.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            final Player pEnt = (Player) ent;
                            final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.ALLY)) {
                                continue;
                            }
                        }
                        victims.add((LivingEntity) ent);
                    }
                }
                p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 2.0f, 0.75f);
                for (final LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player) victim).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + "'s Self-Destruct was activated, RUN!");
                    }
                }
                int tntAmount = radius;
                final int fuseTicks = 120 - level * 20;
                while (tntAmount > 0) {
                    --tntAmount;
                }
            }
        }
    }


    private Location getNearbyLocation(final Location l, final int radius, final int y_boost) {
        final Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)), 0.0, (double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)));
        rand_loc.add(0.5, (double) y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }
}
