/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.ConfirmGUI
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.zencode.shortninja.staffplus.StaffPlus
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.gui.ConfirmGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import java.util.List;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

final class CommandSuicide
extends AbstractCommand {
    public CommandSuicide(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.isDead() || p.getHealth() <= 0.0) {
                p.sendMessage((Object)ChatColor.RED + "You are already dead!");
                return true;
            }
            if (p.hasMetadata("lastSuicideTick") && ((MetadataValue)p.getMetadata("lastSuicideTick").get(0)).asLong() > (long)(MinecraftServer.currentTick - 20)) {
                p.sendMessage((Object)ChatColor.RED + "You cannot kill yourself that often!");
                return true;
            }
            if (args.length > 0) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "/suicide: " + (Object)ChatColor.RED + "Instantly kills your player, dropping all items and sending you to spawn.");
                return true;
            }
            if (!p.hasMetadata("suicideCommandConfirm")) {
                new ConfirmGUI(p, "Confirm /suicide", 9, new ItemBuilder(Material.STAINED_GLASS_PANE, CC.GreenB + "Confirm /suicide?", new String[]{CC.Gray + "Are you sure you want to " + CC.Red + "KILL YOURSELF" + CC.Gray + "?", "", CC.Gray + "You will lose " + CC.RedU + "ALL" + CC.Gray + " of your items on death!"}).data((short)DyeColor.LIME.getWoolData()).build(), new ItemBuilder(Material.STAINED_GLASS_PANE, CC.RedB + "Cancel Suicide", new String[]{CC.Gray + "Click to cancel this action."}).data((short)DyeColor.RED.getWoolData()).build(), new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data((short)DyeColor.GRAY.getWoolData()).build(), () -> {
                    p.setMetadata("suicideCommandConfirm", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                    p.chat("/suicide");
                }, () -> {
                    p.sendMessage(CC.RedB + "CANCELLED - " + CC.Red + "/suicide");
                    p.closeInventory();
                }).openGUI((JavaPlugin)Arkkit.get());
                return true;
            }
            p.setMetadata("lastSuicideTick", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)MinecraftServer.currentTick));
            p.setHealth(0.0);
            p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "/suicide: " + (Object)ChatColor.RED + "You have killed yourself!");
            p.removeMetadata("suicideCommandConfirm", (Plugin)Arkkit.get());
        }
        return true;
    }
}

