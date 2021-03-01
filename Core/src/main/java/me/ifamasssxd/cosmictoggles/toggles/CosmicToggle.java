/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  me.cosmicpvp.CosmicFactionUtils.cmds.CommandToggleSmoke
 *  me.ifamasssxd.betterpay.BetterPay
 *  me.ifamasssxd.cosmiccoinflip.CosmicCoinFlip
 *  me.ifamasssxd.cosmicduel.CosmicDuel
 *  me.ifamasssxd.cosmicjackpot.CosmicJackpot
 *  me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest
 *  me.ifamasssxd.itemfilter.Filter
 *  me.ifamasssxd.itemfilter.ItemFilter
 *  me.recolance.auction.Auction
 *  org.arkhamnetwork.Arkkit.patches.ClearInventoryConfirm
 *  org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter
 *  org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmictoggles.toggles;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.EssentialsToggleCommand;
import com.massivecraft.factions.FPlayer;
import me.astropvp.astroparticles.utils.ParticleUtils;
import me.astropvp.damageindicators.DamageIndicators;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.itemfilter.Filter;
import me.recolance.auction.Auction;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.GrindingMessagesUtil;
import org.arkhamnetwork.Arkkit.patches.ClearInventoryConfirm;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum CosmicToggle {
    GLOBAL_CHAT("Global Chat", "/f global"),
    FILTER("Chat Filter", "/togglefilter"),
    MSG("Private Messages", "/msgtoggle"),
    F_TP("Teleportation Requests", "/f tptoggle"),
    SCOREBOARD("Faction Scoreboard", "/togglesb"),
    SMOKE("Fly Particles", "/togglesmoke"),
    PARTICLE("Toggle Particles", "/particles"),
    ITEM_FILTER("Item Filter", "/filter toggle"),
    DEATH_MESSAGES("Death Messages", "/toggledeathmessage"),
    COINFLIP("Coin Flip Notifications", "/coinflip stfu"),
    JACKPOT("Jackpot Notifications", "/jackpot stfu"),
    AUCTION("Auction Bid Alerts", "/ah stfu"),
    DUELS("Duel Requests", "/duel toggle"),
    RELATION_REQUESTS("Relation Requests", "/f tr"),
    TOGGLE_DAMAGEINDICATORS("Damage Indicators", "/di"),
    GRINDING_NOTIFICATIONS("Grinding Notifications", "/grinding stfu"),
    CLEAR_INVENTORY_CONFIRMATION("/clearinventory Confirmation", "/toggleciconfirm");

    private final String displayName;
    private final String command;

    public boolean getToggle(Player player, FPlayer fplayer) {
        if (this == GLOBAL_CHAT || this == F_TP) {
            return this == GLOBAL_CHAT ? !fplayer.hasGlobalChatHidden() : !fplayer.onlyAllowTpFromFactionMembers();
        }
        if (this == FILTER) {
            return !ChatFilter.disabledFilteredPlayers.contains(player.getUniqueId());
        }

        if (this == PARTICLE) {
            return ParticleUtils.isPlayerIndicating(player);
        }
        if (this == SCOREBOARD) {
            return !AstroCore.getInstance().getToggleManager().getScoreboardToggles().contains(player.getUniqueId());
        }
        if (this == ITEM_FILTER) {
            Filter filter = AstroCore.getInstance().getFilterManager().getItemFilters().get(player.getUniqueId());
            return filter != null && filter.isEnabled();
        }
        if (this == DEATH_MESSAGES) {
            return !player.hasMetadata("deathMessageToggle");
        }
        if (this == COINFLIP) {
            return !AstroCore.getInstance().getCoinFlipManager().getToggledNotifications().contains(player.getUniqueId());
        }
        if (this == JACKPOT) {
            return !AstroCore.getInstance().getJackpotManager().getToggledNofications().contains(player.getUniqueId());
        }
        if (this == AUCTION) {
            return !Auction.getPlugin().getToggles().isBidMessageToggled(player);
        }
        if (this == DUELS) {
            try {
                return !CosmicDuel.get().getDuelToggles().hasDuelsToggled(player);
            }
            catch (Exception e) {
                Bukkit.getLogger().info("Unable to pull toggle for " + player.getName() + "!");
                return false;
            }
        }
        if (this == RELATION_REQUESTS) {
            return fplayer.hasFaction() && fplayer.getFaction().announceRelationshipRequests;
        }
        if (this == TOGGLE_DAMAGEINDICATORS) {
            return DamageIndicatorUtils.isPlayerIndicating(player);
        }

        if (this == GRINDING_NOTIFICATIONS) {
            return GrindingMessagesUtil.isPlayerIndicating(player);
        }
        if (this == CLEAR_INVENTORY_CONFIRMATION) {
            try {
                return !((ClearInventoryConfirm)ArkPlugins.CLEAR_INVENTORY_CONFIRM.getArkPlugin()).getClearInventoryToggled().contains(player.getUniqueId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return false;
    }

    CosmicToggle(String displayName, String command) {
        this.displayName = displayName;
        this.command = command;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getCommand() {
        return this.command;
    }
}

