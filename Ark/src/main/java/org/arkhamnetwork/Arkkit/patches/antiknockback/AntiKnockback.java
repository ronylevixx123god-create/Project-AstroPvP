/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Vector
 */
package org.arkhamnetwork.Arkkit.patches.antiknockback;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.antiknockback.PlayerNoKnockbackEvent;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AntiKnockback
extends ArkPlugin {
    HashMap<UUID, BukkitRunnable> runnables = new HashMap();
    private PacketListener packetListener;
    public HashMap<UUID, List<Double>> yChanges = new HashMap();

    public AntiKnockback(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.packetListener = new PacketAdapter((Plugin)Arkkit.get(), new PacketType[]{PacketType.Play.Server.ENTITY_VELOCITY}){

            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.ENTITY_VELOCITY) {
                    int z;
                    int y;
                    int entityId = (Integer)event.getPacket().getIntegers().readSafely(0);
                    if (entityId != event.getPlayer().getEntityId()) {
                        return;
                    }
                    if (event.getPlayer().hasPermission("antiknockback.bypass")) {
                        return;
                    }
                    final Player player = event.getPlayer();
                    if (AntiKnockback.this.runnables.containsKey(player.getUniqueId())) {
                        AntiKnockback.this.runnables.get(player.getUniqueId()).cancel();
                    }
                    final Location current = event.getPlayer().getLocation().clone();
                    int x = (Integer)event.getPacket().getIntegers().readSafely(1);
                    final Vector velocity = new Vector(x, y = ((Integer)event.getPacket().getIntegers().readSafely(2)).intValue(), z = ((Integer)event.getPacket().getIntegers().readSafely(3)).intValue());
                    if (velocity.getY() <= 500.0 || !event.getPlayer().isOnGround()) {
                        return;
                    }
                    if (current.clone().add(0.0, 2.0, 0.0).getBlock().getType() != Material.AIR) {
                        return;
                    }
                    BukkitRunnable run = new BukkitRunnable(){
                        double highestY = 0.0;
                        int run = 0;

                        public void run() {
                            if (this.run < 9 && player.isOnline()) {
                                ++this.run;
                                if (player.getLocation().getY() > this.highestY) {
                                    this.highestY = player.getLocation().getY();
                                }
                            } else {
                                ArrayList ys;
                                this.cancel();
                                List<Object> list = ys = AntiKnockback.this.yChanges.containsKey(player.getUniqueId()) ? AntiKnockback.this.yChanges.get(player.getUniqueId()) : Lists.newArrayList();
                                if (!AntiKnockback.this.yChanges.containsKey(player.getUniqueId())) {
                                    AntiKnockback.this.yChanges.put(player.getUniqueId(), ys);
                                }
                                double difference = this.highestY - current.getY();
                                ys.add(difference);
                                if (difference <= 0.2) {
                                    Bukkit.getPluginManager().callEvent((Event)new PlayerNoKnockbackEvent(player, velocity, current, this.highestY, difference, ys));
                                }
                            }
                        }
                    };
                    run.runTaskTimer((Plugin)Arkkit.get(), 1L, 1L);
                    AntiKnockback.this.runnables.put(player.getUniqueId(), run);
                }
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener(this.packetListener);
    }

    @Override
    public void disable() {
        try {
            if (this.packetListener != null) {
                ProtocolLibrary.getProtocolManager().removePacketListener(this.packetListener);
            }
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}

