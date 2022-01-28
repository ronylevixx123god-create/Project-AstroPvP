package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.cmds.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import org.bukkit.metadata.*;
import java.util.*;

public class DivineImmolation extends CustomEnchantment
{
    public DivineImmolation() {
        super("Divine Immolation", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents(new DivineImmolationListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (playerHit.hasMetadata("stacked")) {
            return;
        }
        final Player p = (Player)player;
        double radius = 1.0 * level;
        double damage = Math.floor((level));

        if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.CHRISTMAS_SWORD_ORNAMENTAL)) {
            radius += 2.0;
        }
        if (player.hasMetadata("leviathanTrophySilence") && player.getMetadata("leviathanTrophySilence").get(0).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.DOOMSDAY_SWORD)) {
            damage *= 1.25;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.THANKSGIVING_SWORD_HARVESTER)) {
            damage *= 1.3;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.SUMMER_2021_AMULET)) {
            damage *= 1.25;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.SUMMER_2021_SWORD)) {
            damage *= 1.15;
        }

        if (player.hasMetadata("soulEnchantMultiplier")) {
            damage *= player.getMetadata("soulEnchantMultiplier").get(0).asDouble();
        }
        if (!GeneralUtil.isSword(p.getItemInHand())) {
            return;
        }
        if (player.hasMetadata("witcher_negate") && player.getMetadata("witcher_negate").get(0).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - p.getMetadata("lastItemHeldChange").get(0).asLong() <= 5L) {
            return;
        }

        if (Math.random() < 0.1 && AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.SUMMER_2021_AMULET)) {
            damage = Math.floor(level * 2);
        }
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0) {
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - p.getMetadata("lastSoulRemoveEvent").get(0).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems(p, 20);
            }
            final List<Entity> ents = playerHit.getNearbyEntities(radius, radius, radius);
            ents.add(playerHit);
            for (final Entity ent : ents) {
                if (ent instanceof LivingEntity) {
                    Player pEnt = null;
                    if (ent instanceof Player) {
                        pEnt = (Player)ent;
                    }
                    if (pEnt != null && p.getName().equals(pEnt.getName())) {
                        continue;
                    }
                    if (pEnt != null && GeneralUtil.isAtleastTruce(p, pEnt)) {
                        continue;
                    }
                    if (pEnt != null && pEnt.getGameMode() != GameMode.SURVIVAL) {
                        continue;
                    }
                    if (pEnt != null && !p.canSee(pEnt)) {
                        continue;
                    }
                    if (pEnt != null && pEnt.hasMetadata("NPC")) {
                        continue;
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                        continue;
                    }
                    if (ent.hasMetadata("spectator")) {
                        continue;
                    }
                    final LivingEntity le = (LivingEntity)ent;
                    final Player attacker = (Player)player;
                    AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(damage, e, "&c&lDivine Immolation");
                    if (le instanceof Player) {
                        ((Player)le).playSound(le.getLocation(), Sound.FIREWORK_BLAST, 1.0f, 0.3f);
                        ((Player)le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.8f, 0.5f);
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3*20, 1));
                    }
                    double multiplier = 1.0;
                    if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.DOOMSDAY_SWORD) || (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.THANKSGIVING_SWORD_HARVESTER) && le instanceof Player && !AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.VALENTINES_2021_HELMET)) || player.hasMetadata("soulEnchantMultiplier")) {
                        multiplier += 0.25;
                    }

                    ent.setMetadata("divineFire", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
                    ent.setMetadata("divineFireLevel", new FixedMetadataValue((Plugin)Enchantments.getInstance(), multiplier * level));
                }
            }
        }
    }
}
