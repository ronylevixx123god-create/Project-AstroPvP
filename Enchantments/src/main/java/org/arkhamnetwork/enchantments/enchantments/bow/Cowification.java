package org.arkhamnetwork.enchantments.enchantments.bow;

import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.entity.*;
import com.rit.sucy.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;

public class Cowification extends CustomEnchantment
{
    protected static String cowificationEncahntmentMetadata;
    protected static String cowificationEncahntmentOwnerMetadata;
    
    public Cowification() {
        super("Cowification", new Material[] { Material.BOW }, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new CowificationListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() < 0.1 * level) {
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * level, 1));
            if (playerHit instanceof Player) {
                ((Player)playerHit).playSound(playerHit.getLocation(), Sound.COW_IDLE, 1.0f, 0.75f);
            }
        }
        ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE, playerHit.getEyeLocation(), 0.0f, 0.0f, 0.0f, 0.25f, 1);
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            if (!EnchantmentAPI.itemHasEnchantment(((Player)player).getItemInHand(), "Explosive")) {
                final Cow mark = (Cow)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 3.0, 0.0), EntityType.COW);
                mark.setMetadata("cowification", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
                mark.setCanPickupItems(false);
                mark.setNoDamageTicks(200);
                e.getEntity().setPassenger((Entity)mark);
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (mark != null && mark.isValid() && !mark.isDead()) {
                            mark.remove();
                        }
                    }
                }, 200L);
            }
            e.getEntity().setMetadata(Cowification.cowificationEncahntmentMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            e.getEntity().setMetadata(Cowification.cowificationEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)((Player)player).getName()));
        }
    }
    
    static {
        Cowification.cowificationEncahntmentMetadata = "cowification_enchantment";
        Cowification.cowificationEncahntmentOwnerMetadata = "cowification_enchantment_owner";
    }
}
