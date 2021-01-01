package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import com.earth2me.essentials.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;

public class Hellfire extends CustomEnchantment
{
    protected static String hellfireEncahntmentMetadata;
    protected static String hellfireEncahntmentOwnerMetadata;
    protected static Essentials ess3;
    
    public Hellfire() {
        super("Hellfire", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Hellfire.ess3 = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents((Listener)new FireballListener(), (Plugin) Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            final Player p1 = (Player)player;
            final Player p2 = (Player)playerHit;
            if (!p1.canSee(p2) || p1.getGameMode() != GameMode.SURVIVAL) {
                return;
            }
        }
        playerHit.setFireTicks(level * 40);
        ParticleEffect.sendToLocation(ParticleEffect.FLAME, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.15f, 30);
        ParticleEffect.sendToLocation(ParticleEffect.LAVA, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 20);
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow && player instanceof Player) {
            ((Arrow)e.getEntity()).setFireTicks(Integer.MAX_VALUE);
            e.getEntity().setMetadata(Hellfire.hellfireEncahntmentMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            e.getEntity().setMetadata(Hellfire.hellfireEncahntmentOwnerMetadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)((Player)player).getName()));
        }
    }
    
    static {
        Hellfire.hellfireEncahntmentMetadata = "hellfire_enchantment";
        Hellfire.hellfireEncahntmentOwnerMetadata = "hellfire_enchantment_owner";
    }
}
