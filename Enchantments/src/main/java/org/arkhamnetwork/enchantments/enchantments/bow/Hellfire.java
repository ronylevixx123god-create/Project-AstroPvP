package org.arkhamnetwork.enchantments.enchantments.bow;

import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Hellfire extends CustomEnchantment {
    protected static String hellfireEncahntmentMetadata;
    protected static String hellfireEncahntmentOwnerMetadata;
    protected static Essentials ess3;

    static {
        Hellfire.hellfireEncahntmentMetadata = "hellfire_enchantment";
        Hellfire.hellfireEncahntmentOwnerMetadata = "hellfire_enchantment_owner";
    }

    public Hellfire() {
        super("Hellfire", new Material[]{Material.BOW}, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Hellfire.ess3 = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents((Listener) new FireballListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            final Player p1 = (Player) player;
            final Player p2 = (Player) playerHit;
            if (!p1.canSee(p2) || p1.getGameMode() != GameMode.SURVIVAL) {
                return;
            }
        }
        playerHit.setFireTicks(level * 40);
        ParticleEffect.sendToLocation(ParticleEffect.FLAME, playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.15f, 30);
        ParticleEffect.sendToLocation(ParticleEffect.LAVA, playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 20);
    }

    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            ((Arrow) e.getEntity()).setFireTicks(Integer.MAX_VALUE);
            e.getEntity().setMetadata(Hellfire.hellfireEncahntmentMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            e.getEntity().setMetadata(Hellfire.hellfireEncahntmentOwnerMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) ((Player) player).getName()));
        }
    }
}
