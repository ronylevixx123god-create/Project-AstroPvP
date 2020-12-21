/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.reflect.StructureModifier
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  org.arkhamnetwork.enchantments.util.GeneralUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.List;
import net.minecraft.server.v1_7_R4.NBTBase;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class HideLore
extends ArkPlugin {
    private ProtocolManager protocolManager;
    private PacketListener packetListener;

    public HideLore(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Bukkit.getLogger().info("[Arkkit (HideLore)] ProtocolLib not found, disabling.");
            this.kill();
            return;
        }
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.packetListener = new PacketAdapter((Plugin)Arkkit.get(), ListenerPriority.NORMAL, new PacketType[]{PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS}){

            public void onPacketSending(PacketEvent event) {
                StructureModifier sm;
                PacketContainer packet;
                if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
                    packet = event.getPacket();
                    sm = packet.getItemModifier();
                    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                        return;
                    }
                    for (int i = 0; i < sm.size(); ++i) {
                        ItemMeta itemMeta;
                        boolean editedItem = false;
                        ItemStack item = (ItemStack)sm.getValues().get(i);
                        if (item != null && (itemMeta = item.getItemMeta()).hasLore()) {
                            List lore = itemMeta.getLore();
                            String toRemove = null;
                            for (String s : lore) {
                                if (!s.contains("IID")) continue;
                                toRemove = s;
                                break;
                            }
                            if (toRemove != null) {
                                lore.remove(toRemove);
                                itemMeta.setLore(lore);
                                item.setItemMeta(itemMeta);
                                editedItem = true;
                            }
                        }
                        if (!editedItem) continue;
                        event.getPacket().getItemModifier().write(i, (Object)item);
                    }
                }
                if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
                    packet = event.getPacket();
                    sm = packet.getItemArrayModifier();
                    for (int j = 0; j < sm.getValues().size(); ++j) {
                        ItemStack[] iArray = (ItemStack[])sm.getValues().get(j);
                        for (int x = 0; x < iArray.length; ++x) {
                            ItemMeta itemMeta;
                            ItemStack i = iArray[x];
                            if (i == null || !(itemMeta = i.getItemMeta()).hasLore()) continue;
                            List lore = itemMeta.getLore();
                            String toRemove = null;
                            for (String s : lore) {
                                if (!s.contains("IID")) continue;
                                toRemove = s;
                                break;
                            }
                            if (toRemove == null) continue;
                            lore.remove(toRemove);
                            itemMeta.setLore(lore);
                            i.setItemMeta(itemMeta);
                        }
                    }
                }
            }
        };
        this.protocolManager.addPacketListener(this.packetListener);
    }

    @Override
    public void disable() {
        if (this.protocolManager != null) {
            this.protocolManager.removePacketListener(this.packetListener);
        }
    }

    public static ItemStack removeAttributes(ItemStack i) {
        NBTTagCompound tag;
        if (i == null || !GeneralUtil.isArmor((ItemStack)i) && !GeneralUtil.isWeapon((ItemStack)i) && !GeneralUtil.isTool((ItemStack)i)) {
            return i;
        }
        net.minecraft.server.v1_7_R4.ItemStack nmsItem = CraftItemStack.asNMSCopy((ItemStack)i);
        if (!nmsItem.hasTag()) {
            tag = new NBTTagCompound();
            nmsItem.setTag(tag);
        } else {
            tag = nmsItem.getTag();
        }
        NBTTagList am = new NBTTagList();
        tag.set("AttributeModifiers", (NBTBase)am);
        nmsItem.setTag(tag);
        return CraftItemStack.asCraftMirror((net.minecraft.server.v1_7_R4.ItemStack)nmsItem);
    }
}

