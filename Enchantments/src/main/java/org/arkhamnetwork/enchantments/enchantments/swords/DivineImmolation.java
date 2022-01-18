/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.rit.sucy.CustomEnchantment
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.net.astropvp.armorsets.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.LunaSet;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DivineImmolation
        extends CustomEnchantment {
    public DivineImmolation() {
        super("Divine Immolation", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener) new DivineImmolationListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        Player p = (Player) player;
        double radius = 1.0 * (double) level;
        double damage = 1.5;

        if (!(playerHit instanceof Player)) return;
        if (KillTrackerAPI.hasGodMode((Player) p) && KillTrackerAPI.getAllSouls((Player) p) > 0) {
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - ((MetadataValue) p.getMetadata("lastSoulRemoveEvent").get(0)).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems((Player) p, (int) 20);
            }
            List<Entity> ents = playerHit.getNearbyEntities(radius, radius, radius);
            ents.add(playerHit);
            for (Entity ent : ents) {
                if (!(ent instanceof LivingEntity)) continue;
                Player pEnt = null;
                if (ent instanceof Player) {
                    pEnt = (Player) ent;
                }
                if (pEnt != null && p.getName().equals(pEnt.getName()) || pEnt != null && GeneralUtil.isAtleastTruce(p, pEnt) || pEnt != null && pEnt.getGameMode() != GameMode.SURVIVAL || pEnt != null && !p.canSee(pEnt) || WorldGuardUtils.isPvPDisabled((Location) ent.getLocation()) || ent.hasMetadata("spectator"))
                    continue;
                LivingEntity le = (LivingEntity) ent;

                if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.VALENTINES_2021_HELMET) && Math.random() < 0.5) {
                    le.sendMessage(CC.translate("&e&l* SOUL HARVESTER &7[Blocked Divine Immolation&e&l] *"));
                    continue;
                }

                if (LunaSet.isLunarStriked((Player) playerHit)) {
                    continue;
                }

                if (CosmicArmorSetsAPI.hasCrystalApplied((Player) player, ArmorSet.LUNA)) {
                    damage *= 1.1;
                }
                if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                    damage += 1.3;
                }

                le.damage(damage);

                le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3*20, 1));
                if (le instanceof Player) {
                    ((Player) le).playSound(le.getLocation(), Sound.FIREWORK_BLAST, 1.0f, 0.3f);
                    ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.8f, 0.5f);
                }
                ent.setMetadata("divineFire", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                ent.setMetadata("divineFireLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            }
        }
    }
}

