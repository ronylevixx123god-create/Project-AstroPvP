/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ChatSerializer
 *  net.minecraft.server.v1_7_R4.IChatBaseComponent
 *  net.minecraft.server.v1_7_R4.Packet
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.spigotmc.ProtocolInjector$PacketTabHeader
 */
package org.arkhamnetwork.Arkkit.patches;

import com.comphenix.protocol.injector.netty.ProtocolInjector;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.TabListListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.ProtocolInjector;

public class TabList
extends ArkPlugin {
    private static String header;
    private static String footer;

    public TabList(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        header = ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.tab_list.header"));
        footer = ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.tab_list.footer"));
        this.registerListener(new TabListListener());
        for (Player p : Bukkit.getOnlinePlayers()) {
            TabList.sendTabList(p);
        }
    }

    @Override
    public void disable() {
    }

    public static void sendTabList(final Player player) {
        new BukkitRunnable(){

            public void run() {
                CraftPlayer cp = (CraftPlayer)player;
                if (cp.getHandle().playerConnection.networkManager.getVersion() != 47) {
                    return;
                }
                IChatBaseComponent hic = IChatBaseComponent.ChatSerializer.a((String)header);
                IChatBaseComponent fic = IChatBaseComponent.ChatSerializer.a((String)footer);
                ProtocolInjector.PacketTabHeader packet = new ProtocolInjector.PacketTabHeader(hic, fic);
                cp.getHandle().playerConnection.sendPacket((Packet)packet);
            }
        }.runTaskAsynchronously((Plugin)Arkkit.get());
    }
}

