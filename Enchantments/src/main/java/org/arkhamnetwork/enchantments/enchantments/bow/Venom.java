package org.arkhamnetwork.enchantments.enchantments.bow;

import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.potion.*;
import org.bukkit.event.entity.*;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;

public class Venom extends CustomEnchantment
{
    protected static String venomEncahntmentMetadata;
    protected static String venomEncahntmentOwnerMetadata;
    
    public Venom() {
        super("Venom", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new VenomListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            final Player pl = (Player)player;
            if (!EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Explosive")) {
                final ExperienceOrb mark = (ExperienceOrb)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
                mark.setExperience(0);
                e.getEntity().setPassenger((Entity)mark);
            }
            e.getEntity().setMetadata(Venom.venomEncahntmentMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            e.getEntity().setMetadata(Venom.venomEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)((Player)player).getName()));
        }
    }
    
    static {
        Venom.venomEncahntmentMetadata = "venom_enchantment";
        Venom.venomEncahntmentOwnerMetadata = "venom_enchantment_owner";
    }
}
