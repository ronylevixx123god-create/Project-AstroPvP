package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Cowification extends CustomEnchantment {
    protected static String cowificationEncahntmentMetadata;
    protected static String cowificationEncahntmentOwnerMetadata;

    static {
        Cowification.cowificationEncahntmentMetadata = "cowification_enchantment";
        Cowification.cowificationEncahntmentOwnerMetadata = "cowification_enchantment_owner";
    }

    public Cowification() {
        super("Cowification", new Material[]{Material.BOW}, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new CowificationListener(), (Plugin) Enchantments.getInstance());
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() < 0.1 * level) {
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * level, 1));
            if (playerHit instanceof Player) {
                ((Player) playerHit).playSound(playerHit.getLocation(), Sound.COW_IDLE, 1.0f, 0.75f);
            }
        }
        de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getEyeLocation(), 0.0f, 0.0f, 0.0f, 0.25f, 1);

    }

    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            if (!EnchantmentAPI.itemHasEnchantment(((Player) player).getItemInHand(), "Explosive")) {
                final Cow mark = (Cow) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 3.0, 0.0), EntityType.COW);
                mark.setMetadata("cowification", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                mark.setCanPickupItems(false);
                mark.setNoDamageTicks(200);
                e.getEntity().setPassenger((Entity) mark);
                Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                    @Override
                    public void run() {
                        if (mark != null && mark.isValid() && !mark.isDead()) {
                            mark.remove();
                        }
                    }
                }, 200L);
            }
            e.getEntity().setMetadata(Cowification.cowificationEncahntmentMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            e.getEntity().setMetadata(Cowification.cowificationEncahntmentOwnerMetadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) ((Player) player).getName()));
        }
    }
}
