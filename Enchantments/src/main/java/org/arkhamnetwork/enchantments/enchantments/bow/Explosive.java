package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;

public class Explosive extends CustomEnchantment
{
    protected static String explosiveEncahntmentMetadata;
    protected static String explosiveEncahntmentOwnerMetadata;
    
    public Explosive() {
        super("Explosive", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new ExplosiveListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, 1));
        ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE, playerHit.getEyeLocation(), 0.0f, 0.0f, 0.0f, 0.25f, 1);
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            final WitherSkull ws = (WitherSkull)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.WITHER_SKULL);
            ws.setIsIncendiary(false);
            ws.setYield(0.0f);
            e.getEntity().setPassenger((Entity)ws);
            e.getEntity().setMetadata(Explosive.explosiveEncahntmentMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            e.getEntity().setMetadata(Explosive.explosiveEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)((Player)player).getName()));
        }
    }
    
    static {
        Explosive.explosiveEncahntmentMetadata = "explosive_enchantment";
        Explosive.explosiveEncahntmentOwnerMetadata = "explosive_enchantment_owner";
    }
}
