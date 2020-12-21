/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  lilypad.client.connect.api.Connect
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import lilypad.client.connect.api.Connect;
import org.arkhamnetwork.Arkkit.patches.CommandSeasonalPass;
import org.arkhamnetwork.Arkkit.patches.SeasonalLilypadListener;
import org.arkhamnetwork.Arkkit.patches.SeasonalPassListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SeasonalPasses
extends ArkPlugin {
    private static Connect found;
    private SeasonalLilypadListener listener;

    public SeasonalPasses(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        found = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        super.enable();
        this.registerListener(new SeasonalPassListener());
        this.registerCommand(new CommandSeasonalPass());
        Connect found = this.getConnect();
        if (found != null) {
            this.listener = new SeasonalLilypadListener();
            found.registerEvents((Object)this.listener);
        }
    }

    public Connect getConnect() {
        if (found == null) {
            found = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        }
        return found;
    }

    @Override
    public void disable() {
        Connect connect;
        super.disable();
        this.unregisterCommands();
        this.unregisterListeners();
        if (this.listener != null && (connect = this.getConnect()) != null) {
            connect.unregisterEvents((Object)this.listener);
        }
    }

    public static ItemStack createSeasonPassReceipt(String whoClaimed) {
        return new ItemBuilder(Material.PAPER, CC.GrayB + "Intergalactic Receipt", new String[]{CC.Gray + "Proof of claiming 1 pass", CC.Gray + "to the Seasonal Galaxy", "", CC.GreenB + "CLAIMED BY " + CC.Green + whoClaimed}).build();
    }

    public static ItemStack createSeasonPass() {
        String bulletPoint = (CC.LightPurpleB + "* " + CC.White).intern();
        return new NBTWrapper(new ItemBuilder(Material.PAPER, CC.RedB + "Seasonal Galaxy Ticket", new String[]{CC.Gray + "Right-click to unlock access to", CC.LightPurpleB + "Nebula Map #3 " + CC.Gray + "(starts July 27th)", "", CC.LightPurpleB + "What is Nebula?", CC.Gray + "Nebula resides inside the", CC.Gray + "Seasonal Galaxy which means:", bulletPoint + "$2,500 PayPal Prize /f top", bulletPoint + "Raiding Points", bulletPoint + "Core Chunks", bulletPoint + "Pay to Play", bulletPoint + "2x XP Rates", bulletPoint + "No /fund", bulletPoint + "10% Dungeon Key Cost", bulletPoint + "/fly limited to claimed land", bulletPoint + "4 Week Maps", bulletPoint + "20k x 20k Worlds", bulletPoint + "Cosmic Galaxy Features"}).build()).setString("cosmicType", "entryTicket").build();
    }
}

