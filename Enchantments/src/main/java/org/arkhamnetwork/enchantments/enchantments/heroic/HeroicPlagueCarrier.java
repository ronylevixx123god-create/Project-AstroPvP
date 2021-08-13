package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class HeroicPlagueCarrier extends HeroicCustomEnchantment {
    public HeroicPlagueCarrier() {
        super("Epidemic Carrier", "Plague Carrier", GeneralUtil.leggings, 10);
        this.max = 8;
        this.base = 25.0D;
        this.interval = 20.0D;
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player instanceof Player) {
            Player p = (Player) player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent(ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0D && Math.random() < 0.8D) {
                if (p.hasMetadata("HeroicPlagueCarrier") && System.currentTimeMillis() - ((MetadataValue) p.getMetadata("HeroicPlagueCarrier").get(0)).asLong() <= 120000L)
                    return;
                p.setMetadata("HeroicPlagueCarrier", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
                int radius = Math.round(level * 1.5F);
                List<LivingEntity> victims = new ArrayList<>();
                FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            Player pEnt = (Player) ent;
                            FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE))
                                continue;
                        }
                        victims.add((LivingEntity) ent);
                    }
                }
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayer(p, p.getLocation(), 0,1,0,1f,75);
                p.getWorld().playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0F, 0.75F);
                for (LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player) victim).sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.LIGHT_PURPLE + p.getName() + " has died, and was a Plague Carrier! RUN!");
                        ((Player) victim).getWorld().playSound(victim.getLocation(), Sound.CREEPER_HISS, 1.6F, 0.75F);
                    }
                }
                int plagueCarriers = radius / 2;
                while (plagueCarriers > 0) {
                    plagueCarriers--;
                    //spawnPlagueCreeper(p.getLocation().add(0.0D, 1.0D, 0.0D), (Player) player);
                }
                if (p.getHealth() > 0.0D)
                    p.damage(p.getHealth());
            }
        }
    }

    public void spawnPlagueCreeper(Location loc, Player player) {
        Creeper creeper = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
        creeper.setCustomNameVisible(true);
        creeper.setPowered(true);
        creeper.setCustomName(ChatColor.LIGHT_PURPLE + "Epidemic Carrier");
        creeper.setMetadata("creeperOwner", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), player.getName()));
        creeper.setMetadata("customCreeperType", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), "Stun"));
        creeper.setMetadata("noloot", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Boolean.valueOf(true)));
    }
}
