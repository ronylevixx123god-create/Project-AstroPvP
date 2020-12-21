/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.worldcretornica.plotme_core.PlotMapInfo
 *  com.worldcretornica.plotme_core.PlotMeCoreManager
 *  com.worldcretornica.plotme_core.api.IPlayer
 *  com.worldcretornica.plotme_core.api.IServerBridge
 *  com.worldcretornica.plotme_core.api.IWorld
 *  com.worldcretornica.plotme_core.api.event.InternalPlotCreateEvent
 *  com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.worldcretornica.plotme_core.PlotMapInfo;
import com.worldcretornica.plotme_core.PlotMeCoreManager;
import com.worldcretornica.plotme_core.api.IPlayer;
import com.worldcretornica.plotme_core.api.IServerBridge;
import com.worldcretornica.plotme_core.api.IWorld;
import com.worldcretornica.plotme_core.api.event.InternalPlotCreateEvent;
import com.worldcretornica.plotme_core.bukkit.PlotMe_CorePlugin;
import java.util.HashSet;
import net.milkbowl.vault.economy.EconomyResponse;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class PlotMeOptimizationListener
implements Listener {
    final PlotMe_CorePlugin plugin = (PlotMe_CorePlugin)Bukkit.getPluginManager().getPlugin("PlotMe");
    private HashSet<String> claimedPlots = new HashSet();

    PlotMeOptimizationListener() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (!cmd.equals("/plotme auto") && !cmd.equals("/plot auto")) {
            if (!cmd.equals("/p auto")) return;
        }
        e.setCancelled(true);
        e.setMessage("/null");
        final Player player = e.getPlayer();
        final IPlayer iplayer = this.plugin.wrapPlayer(player);
        final PlotMeCoreManager manager = PlotMeCoreManager.getInstance();
        final IServerBridge serverBridge = this.plugin.getAPI().getServerBridge();
        if (player.hasMetadata("plotme_auto") && System.currentTimeMillis() < ((MetadataValue)player.getMetadata("plotme_auto").get(0)).asLong()) {
            player.sendMessage((Object)ChatColor.RED + "Please wait a minute before executing this command again.");
            return;
        }
        player.setMetadata("plotme_auto", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + 60000L)));
        if (!player.hasPermission("PlotMe.use.auto")) {
            player.sendMessage("\u00a7c" + this.C("MsgPermissionDenied"));
            return;
        }
        if (!player.getWorld().getName().equals("plotworld")) {
            player.sendMessage("\u00a7c" + this.C("MsgNotPlotWorld"));
            return;
        }
        World world = Bukkit.getWorld((String)manager.getFirstWorld().getName());
        final IWorld iworld = manager.getFirstWorld();
        if (serverBridge.getConfig().getBoolean("allowWorldTeleport")) {
            if (!manager.isPlotWorld(iworld)) {
                player.sendMessage("\u00a7c" + (Object)iworld + " " + this.C("MsgWorldNotPlot"));
                return;
            }
        } else {
            world = player.getWorld();
        }
        final PlotMapInfo pmi = manager.getMap(iworld);
        short playerLimit = this.getPlotLimit(player);
        short plotsOwned = manager.getNbOwnedPlot(player.getUniqueId(), world.getName().toLowerCase());
        if (playerLimit != -1 && plotsOwned >= playerLimit && !player.hasPermission("PlotMe.admin")) {
            player.sendMessage("\u00a7c" + this.C("MsgAlreadyReachedMaxPlots") + " (" + plotsOwned + "/" + playerLimit + "). " + this.C("WordUse") + " \ufffdc/plotme home\ufffdr " + this.C("MsgToGetToIt"));
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){
            int limit;
            int x;
            int z;
            int dx;
            int dz;
            int t;
            int maxPlots;
            {
                this.limit = pmi.getPlotAutoLimit();
                this.x = 0;
                this.z = 0;
                this.dx = 0;
                this.dz = -1;
                this.t = this.limit;
                this.maxPlots = this.t * this.t;
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run() {
                player.sendMessage((Object)ChatColor.YELLOW + "Searching for empty plot...");
                player.sendMessage((Object)ChatColor.GRAY + "This process may take a few moments, please wait.");
                int i = 0;
                while (true) {
                    String id;
                    if (i >= this.maxPlots) {
                        player.sendMessage(PlotMeOptimizationListener.this.C("MsgNoPlotFound"));
                        return;
                    }
                    if (-this.limit / 2 <= this.x && this.x <= this.limit / 2 && -this.limit / 2 <= this.z && this.z <= this.limit / 2 && PlotMeOptimizationListener.this.isPlotAvailable(id = "" + this.x + ";" + this.z, pmi)) {
                        InternalPlotCreateEvent event;
                        double price = 0.0;
                        if (manager.isEconomyEnabled(pmi)) {
                            price = pmi.getClaimPrice();
                            double balance = serverBridge.getBalance(iplayer);
                            if (!(balance >= price)) {
                                player.sendMessage("\u00a7c" + PlotMeOptimizationListener.this.C("MsgNotEnoughAuto") + " " + PlotMeOptimizationListener.this.C("WordMissing") + " \u00a7r" + PlotMeOptimizationListener.this.plugin.getAPI().getUtil().moneyFormat(price - balance, false));
                                return;
                            }
                            event = serverBridge.getEventFactory().callPlotCreatedEvent(PlotMeOptimizationListener.this.plugin.getAPI(), iworld, id, iplayer);
                            if (event.isCancelled()) {
                                return;
                            }
                            EconomyResponse er = serverBridge.withdrawPlayer(iplayer, price);
                            if (!er.transactionSuccess()) {
                                player.sendMessage("\u00a7c" + er.errorMessage);
                                serverBridge.getLogger().warning(er.errorMessage);
                                return;
                            }
                        } else {
                            event = serverBridge.getEventFactory().callPlotCreatedEvent(PlotMeOptimizationListener.this.plugin.getAPI(), iworld, id, iplayer);
                        }
                        if (!event.isCancelled()) {
                            final double f_price = price;
                            Bukkit.getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                                @Override
                                public void run() {
                                    manager.createPlot(iworld, id, player.getName(), player.getUniqueId(), pmi);
                                    iplayer.setLocation(manager.getPlotHome(iworld, id));
                                    player.sendMessage(PlotMeOptimizationListener.this.C("MsgThisPlotYours") + " " + PlotMeOptimizationListener.this.C("WordUse") + " \u00a7c/plotme home\u00a7r " + PlotMeOptimizationListener.this.C("MsgToGetToIt"));
                                    if (PlotMeOptimizationListener.this.isAdvancedLogging()) {
                                        if (f_price == 0.0) {
                                            serverBridge.getLogger().info(player.getName() + " " + PlotMeOptimizationListener.this.C("MsgClaimedPlot") + " " + id);
                                        } else {
                                            serverBridge.getLogger().info(player.getName() + " " + PlotMeOptimizationListener.this.C("MsgClaimedPlot") + " " + id + " " + PlotMeOptimizationListener.this.C("WordFor") + " " + f_price);
                                        }
                                    }
                                }
                            });
                            return;
                        }
                    }
                    if (this.x == this.z || this.x < 0 && this.x == -this.z || this.x > 0 && this.x == 1 - this.z) {
                        this.t = this.dx;
                        this.dx = -this.dz;
                        this.dz = this.t;
                    }
                    this.x += this.dx;
                    this.z += this.dz;
                    ++i;
                }
            }
        });
    }

    short getPlotLimit(Player player) {
        if (player.hasPermission("plotme.limit.*")) {
            return -1;
        }
        int max = -2;
        for (int ctr = 0; ctr < 255; ctr = (int)((short)(ctr + 1))) {
            if (!player.hasPermission("plotme.limit." + ctr)) continue;
            max = ctr;
        }
        if (max == -2) {
            if (player.hasPermission("plotme.admin")) {
                return -1;
            }
            if (player.hasPermission("plotme.use")) {
                return 1;
            }
            return 0;
        }
        return (short)max;
    }

    protected boolean isAdvancedLogging() {
        return this.plugin.getAPI().getServerBridge().getConfig().getBoolean("AdvancedLogging");
    }

    protected String C(String caption) {
        return this.plugin.getAPI().getUtil().C(caption);
    }

    protected boolean isPlotAvailable(String id, PlotMapInfo pmi) {
        boolean claimed;
        if (this.claimedPlots.contains(id)) {
            return false;
        }
        boolean bl = claimed = !PlotMeCoreManager.getInstance().isPlotAvailable(id, pmi);
        if (claimed) {
            this.claimedPlots.add(id);
            return false;
        }
        return true;
    }
}

