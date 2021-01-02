package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Venom extends CustomEnchantment {
    protected static String venomEncahntmentMetadata;
    protected static String venomEncahntmentOwnerMetadata;

    static {
        Venom.venomEncahntmentMetadata = "venom_enchantment";
        Venom.venomEncahntmentOwnerMetadata = "venom_enchantment_owner";
    }

    public Venom() {
        super("Venom", new Material[]{Material.BOW}, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new VenomListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
    }

    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            final Player pl = (Player) player;
            if (!EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Explosive")) {
                final ExperienceOrb mark = (ExperienceOrb) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
                mark.setExperience(0);
                e.getEntity().setPassenger((Entity) mark);
            }
            e.getEntity().setMetadata(Venom.venomEncahntmentMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            e.getEntity().setMetadata(Venom.venomEncahntmentOwnerMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) ((Player) player).getName()));
        }
    }
}
