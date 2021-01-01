package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;

final class NatureWrathTask extends BukkitRunnable
{
    Player victim;
    public int enchantLevel;
    
    public NatureWrathTask(final Player p, final int level) {
        this.victim = p;
        this.enchantLevel = level;
    }
    
    public void run() {
        if (this.victim != null && !this.victim.isDead()) {
            this.victim.getWorld().strikeLightningEffect(this.victim.getLocation());
            this.victim.damage((double)this.enchantLevel);
            this.victim.playSound(this.victim.getLocation(), Sound.GHAST_SCREAM2, 2.0f, 2.0f);
            this.victim.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + "** NATURE'S WRATH **");
            ParticleEffect.playEffect(ParticleEffect.SPELL, this.victim.getLocation().add(0.0, 1.0, 0.0), 0.4f, 35);
        }
    }
}
