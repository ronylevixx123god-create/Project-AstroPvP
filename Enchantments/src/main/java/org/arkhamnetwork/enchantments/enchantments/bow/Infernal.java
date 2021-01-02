package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
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

public class Infernal extends CustomEnchantment {
    protected static String infernalEncahntmentMetadata;
    protected static String infernalEncahntmentOwnerMetadata;

    static {
        Infernal.infernalEncahntmentMetadata = "infernal_enchantment";
        Infernal.infernalEncahntmentOwnerMetadata = "infernal_enchantment_owner";
    }

    public Infernal() {
        super("Infernal", new Material[]{Material.BOW}, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new InfernalListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.setFireTicks(level * 20);
        ParticleEffect.sendToLocation(ParticleEffect.FLAME, playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.25f, 10);
    }

    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            ((Arrow) e.getEntity()).setFireTicks(level * 60);
            e.getEntity().setMetadata(Infernal.infernalEncahntmentMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            e.getEntity().setMetadata(Infernal.infernalEncahntmentOwnerMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) ((Player) player).getName()));
        }
    }
}
