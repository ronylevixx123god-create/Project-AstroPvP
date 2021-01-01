package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.ItemSkinUtils;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import me.ifamasssxd.killtracker.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import org.bukkit.metadata.*;

public class Teleblock extends CustomEnchantment
{
    public Teleblock() {
        super("Teleblock", new Material[] { Material.BOW }, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 0.0;
        Bukkit.getPluginManager().registerEvents((Listener)new TeleblockListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        final Player p = (Player)player;
        final int soulCost = level * 6;
        Player pp = (Player) e.getEntity();
        if (AstroItemSkinsAPI.hasSkinEquipped(pp, SkinType.VIRUS_HELMET_N95)) {
            pp.sendMessage("§a§l* ANTI CORONA BLOCKED §7[Teleblock] §a§l*");
            return;
        }
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) >= soulCost) {
            KillTrackerAPI.removeSoulsFromGems(p, soulCost);
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 65);
            p.playSound(p.getLocation(), Sound.EAT, 0.4f, 0.2f);
            if (KillTrackerAPI.getAllSouls(p) % 100 == 0) {
                p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "** SOULS: " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " **");
            }
            e.getEntity().setMetadata("teleblockEnchantment", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
        }
    }
}
