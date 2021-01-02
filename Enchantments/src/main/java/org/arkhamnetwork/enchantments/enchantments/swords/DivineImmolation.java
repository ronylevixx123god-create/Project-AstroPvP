package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.CustomEnchantment;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DivineImmolation extends CustomEnchantment {
    public DivineImmolation() {
        super("Divine Immolation", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 10.0D;
        this.interval = 10.0D;
        Bukkit.getPluginManager().registerEvents(new DivineImmolationListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled)
            return;
        Player p = (Player) player;
        double radius = 1.0D * level;
        double damage = Math.floor((level * 2.0));
        if (!GeneralUtil.isSword(p.getItemInHand()))
            return;
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue) p.getMetadata("lastItemHeldChange").get(0)).asLong() <= 5L)
            return;
        if (p.hasMetadata("soulTrap") && ((MetadataValue) p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis())
            return;
        if (p.hasMetadata("noSoulProcs"))
            return;
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0) {
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - ((MetadataValue) p.getMetadata("lastSoulRemoveEvent").get(0)).asLong() > 1000L)
                KillTrackerAPI.removeSoulsFromGems(p, 5);
            List<Entity> ents = playerHit.getNearbyEntities(radius, radius, radius);
            ents.add(playerHit);
            for (Entity ent : ents) {
                if (ent instanceof LivingEntity) {
                    Player pEnt = null;
                    if (ent instanceof Player)
                        pEnt = (Player) ent;
                    if (pEnt != null && p.getName().equals(pEnt.getName()))
                        continue;
                    if (pEnt != null && GeneralUtil.isAtleastTruce(p, pEnt))
                        continue;
                    if (pEnt != null && pEnt.getGameMode() != GameMode.SURVIVAL)
                        continue;
                    if (pEnt != null && !p.canSee(pEnt))
                        continue;
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation()))
                        continue;
                    if (ent.hasMetadata("spectator"))
                        continue;
                    LivingEntity le = (LivingEntity) ent;
                    le.damage(damage);
                    ParticleEffect.playEffect(ParticleEffect.FLAME, le.getEyeLocation(), 0.15F, 30);
                    ParticleEffect.playEffect(ParticleEffect.LAVA, le.getEyeLocation(), 0.5F, 20);
                    le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (damage * 10.0D), 1));
                    if (le instanceof Player) {
                        ((Player) le).playSound(le.getLocation(), Sound.FIREWORK_BLAST, 1.0F, 0.3F);
                        ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.8F, 0.5F);
                    }
                    ent.setMetadata("divineFire", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
                    ent.setMetadata("divineFireLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Integer.valueOf(level)));
                }
            }
        }
    }
}
