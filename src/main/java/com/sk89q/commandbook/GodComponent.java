/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.commandbook;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.InfoComponent;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import com.zachsthings.libcomponents.config.ConfigurationBase;
import com.zachsthings.libcomponents.config.Setting;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

@ComponentInformation(friendlyName="God", desc="God mode support")
public class GodComponent
extends BukkitComponent
implements Listener {
    public static final String METADATA_KEY = "god";
    private LocalConfiguration config;

    @Override
    public void enable() {
        this.config = this.configure(new LocalConfiguration());
        this.registerCommands(Commands.class);
        for (Player player : CommandBook.server().getOnlinePlayers()) {
            this.checkAutoEnable(player);
        }
        CommandBook.registerEvents(this);
    }

    @Override
    public void reload() {
        super.reload();
        this.config = this.configure(this.config);
        for (Player player : CommandBook.server().getOnlinePlayers()) {
            this.checkAutoEnable(player);
        }
    }

    public void enableGodMode(Player player) {
        if (!this.hasGodMode(player)) {
            player.setMetadata(METADATA_KEY, new FixedMetadataValue((Plugin)CommandBook.inst(), true));
        }
    }

    public void disableGodMode(Player player) {
        player.removeMetadata(METADATA_KEY, CommandBook.inst());
    }

    public boolean hasGodMode(Player player) {
        List<MetadataValue> values = player.getMetadata(METADATA_KEY);
        switch (values.size()) {
            case 0: {
                return false;
            }
            case 1: {
                return values.get(0).asBoolean();
            }
        }
        for (MetadataValue val : values) {
            if (!val.asBoolean()) continue;
            return true;
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.checkAutoEnable(event.getPlayer());
    }

    private boolean checkAutoEnable(Player player) {
        if (this.config.autoEnable && (CommandBook.inst().getPermissionsResolver().inGroup((OfflinePlayer)player, "cb-invincible") || CommandBook.inst().hasPermission(player, "commandbook.god.auto-invincible"))) {
            this.enableGodMode(player);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onCombust(EntityCombustEvent event) {
        Player player;
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player && this.hasGodMode(player = (Player)event.getEntity())) {
            event.setCancelled(true);
            player.setFireTicks(0);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Player player;
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player && this.hasGodMode(player = (Player)event.getEntity())) {
            event.setCancelled(true);
            player.setFireTicks(0);
        }
    }

    @EventHandler
    public void playerChangedWorld(PlayerChangedWorldEvent event) {
        if (!CommandBook.inst().hasPermission(event.getPlayer(), "commandbook.god")) {
            this.disableGodMode(event.getPlayer());
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void foodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (event.getFoodLevel() < player.getFoodLevel() && this.hasGodMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerWhois(InfoComponent.PlayerWhoisEvent event) {
        if (event.getPlayer() instanceof Player && CommandBook.inst().hasPermission(event.getSource(), "commandbook.god.check")) {
            event.addWhoisInformation(null, "Player " + (this.hasGodMode((Player)event.getPlayer()) ? "has" : "does not have") + " god mode");
        }
    }

    public class Commands {
        @Command(aliases={"god"}, usage="[player]", desc="Enable godmode on a player", flags="s", max=1)
        public void god(CommandContext args, CommandSender sender) throws CommandException {
            Iterable<Player> targets = null;
            boolean included = false;
            if (args.argsLength() == 0) {
                targets = PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender));
            } else if (args.argsLength() == 1) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
            }
            for (Player player : targets) {
                if (player == sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.god");
                    continue;
                }
                CommandBook.inst().checkPermission(sender, "commandbook.god.other");
                break;
            }
            for (Player player : targets) {
                if (GodComponent.this.hasGodMode(player)) {
                    if (player == sender) {
                        player.sendMessage((Object)((Object)ChatColor.RED) + "You already have god mode!");
                        included = true;
                        continue;
                    }
                    sender.sendMessage((Object)((Object)ChatColor.RED) + player.getName() + " already has god mode!");
                    continue;
                }
                GodComponent.this.enableGodMode(player);
                player.setFireTicks(0);
                if (player.equals(sender)) {
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God mode enabled! Use /ungod to disable.");
                    included = true;
                    continue;
                }
                if (args.hasFlag('s')) continue;
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God enabled by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + ".");
            }
            if (!included) {
                sender.sendMessage(ChatColor.YELLOW.toString() + "Players now have god mode.");
            }
        }

        @Command(aliases={"ungod"}, usage="[player]", desc="Disable godmode on a player", flags="s", max=1)
        public void ungod(CommandContext args, CommandSender sender) throws CommandException {
            Iterable<Player> targets = null;
            boolean included = false;
            if (args.argsLength() == 0) {
                targets = PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender));
            } else if (args.argsLength() == 1) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
            }
            for (Player player : targets) {
                if (player == sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.god");
                    continue;
                }
                CommandBook.inst().checkPermission(sender, "commandbook.god.other");
                break;
            }
            for (Player player : targets) {
                if (!GodComponent.this.hasGodMode(player)) {
                    if (player == sender) {
                        player.sendMessage((Object)((Object)ChatColor.RED) + "You do not have god mode enabled!");
                        included = true;
                        continue;
                    }
                    sender.sendMessage((Object)((Object)ChatColor.RED) + player.getName() + " did not have god mode enabled!");
                    continue;
                }
                GodComponent.this.disableGodMode(player);
                if (player.equals(sender)) {
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God mode disabled!");
                    included = true;
                    continue;
                }
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God disabled by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW) + ".");
            }
            if (!included) {
                sender.sendMessage(ChatColor.YELLOW.toString() + "Players no longer have god mode.");
            }
        }
    }

    private static class LocalConfiguration
    extends ConfigurationBase {
        @Setting(value="auto-enable")
        public boolean autoEnable = false;

        private LocalConfiguration() {
        }
    }
}

