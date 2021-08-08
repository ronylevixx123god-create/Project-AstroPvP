package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Teleblock extends CustomEnchantment {
    public Teleblock() {
        super("Teleblock", new Material[]{Material.BOW}, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 0.0;
        Bukkit.getPluginManager().registerEvents((Listener) new TeleblockListener(), (Plugin) Enchantments.getInstance());
    }

    @SneakyThrows
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        final Player p = (Player) player;
        final int soulCost = level * 6;
        Player pp = (Player) e.getEntity();
        if (AstroItemSkinsAPI.hasSkinEquipped(pp, SkinType.VIRUS_HELMET_N95)) {
            pp.sendMessage(CC.translate("&a&l* ANTI CORONA BLOCKED &7[Teleblock] &a&l*"));
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(pp, SkinType.CHRISTMAS_BOOTS_SLIPPERS)) {
            pp.sendMessage(CC.translate("&c&l* SNOWFLAKE FLIPPERS &7Blocked Teleblock &c&l*"));
            return;
        }

        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) >= soulCost) {
            KillTrackerAPI.removeSoulsFromGems(p, soulCost);
            ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(),p.getLocation(), 0,1,0,0.55f,60);
            p.playSound(p.getLocation(), Sound.EAT, 0.4f, 0.2f);
            if (KillTrackerAPI.getAllSouls(p) % 100 == 0) {
                p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "** SOULS: " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " **");
            }
            e.getEntity().setMetadata("teleblockEnchantment", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
        }
    }
}
