package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;

public class Infernal extends CustomEnchantment
{
    protected static String infernalEncahntmentMetadata;
    protected static String infernalEncahntmentOwnerMetadata;
    
    public Infernal() {
        super("Infernal", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new InfernalListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.setFireTicks(level * 20);
        ParticleEffect.sendToLocation(ParticleEffect.FLAME, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25f, 10);
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            ((Arrow)e.getEntity()).setFireTicks(level * 60);
            e.getEntity().setMetadata(Infernal.infernalEncahntmentMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            e.getEntity().setMetadata(Infernal.infernalEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)((Player)player).getName()));
        }
    }
    
    static {
        Infernal.infernalEncahntmentMetadata = "infernal_enchantment";
        Infernal.infernalEncahntmentOwnerMetadata = "infernal_enchantment_owner";
    }
}
