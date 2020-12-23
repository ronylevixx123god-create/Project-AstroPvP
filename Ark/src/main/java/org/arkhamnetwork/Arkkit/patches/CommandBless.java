/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.google.common.collect.MapMaker
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.MapMaker;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;

final class CommandBless
extends AbstractCommand {
    //private Map<UUID, Long> blessCooldown = new MapMaker().expireAfterWrite(5L, TimeUnit.MINUTES).makeMap();
    private Cache<UUID, Long> blessCooldown = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build();
    public CommandBless(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("cosmicpvp.heroic")) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You are not a high enough rank to use that command!");
            sender.sendMessage(CC.Gray + "Unlock a Heroic rank at " + CC.GrayU + "buy.cosmicpvp.com" + CC.Gray + "!");
            return true;
        }
        Player pl = (Player)sender;
        Long value = this.blessCooldown.asMap().get(pl.getUniqueId());
        if (value != null && value > System.currentTimeMillis() && !pl.isOp()) {
            pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You must wait " + TimeUtils.formatFutureTime((long)value) + " before using /bless again!");
            return true;
        }
        if (pl.hasMetadata("lastNatureWrathEffect")) {
            long lastAffected = ((MetadataValue)pl.getMetadata("lastNatureWrathEffect").get(0)).asLong();
            long timeSinceAffected = System.currentTimeMillis() - lastAffected;
            if (timeSinceAffected < TimeUnit.SECONDS.toMillis(5L)) {
                pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You must wait at least 5 seconds after being affected by " + CC.RedB + "Natures Wrath " + CC.Red + " before using /bless.");
                return true;
            }
        }
        this.blessCooldown.put(pl.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5L));
        PlayerPotionEffects.untrackPotionEffect((Player)pl, (PotionEffectType)PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect((Player)pl, (PotionEffectType)PotionEffectType.SLOW_DIGGING);
        pl.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "BLESSED!");
        return true;
    }
}

