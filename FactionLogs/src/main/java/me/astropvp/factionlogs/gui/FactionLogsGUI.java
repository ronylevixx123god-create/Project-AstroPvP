package me.astropvp.factionlogs.gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import me.astropvp.factionlogs.FactionLogs;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.struct.impl.*;
import me.astropvp.factionlogs.utils.EntityUtils;
import me.astropvp.factionlogs.utils.FactionsLogsAPI;
import me.astropvp.factionlogs.utils.TimeDateUtils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class FactionLogsGUI {
    private PaginatedGui fLogGui;

    public FactionLogsGUI(Player player, Faction faction) {
        this.fLogGui = new PaginatedGui(3, CC.translate(CC.DarkGray + faction.getTag() + " Faction Logs"));


        for (LogType logType : LogType.values()) {
            ItemBuilder item = new ItemBuilder(logType.getGuiMaterial(), CC.translate(logType.getGuiColorCode() + logType.getDisplayName()))
                    .addLoreLine(CC.translate("&7Below you can find the latest logs:"))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&f&l") + logType.getDisplayName());
            String prefix = logType.getGuiColorCode() + " * &e";

            for (FactionLog log : FactionsLogsAPI.getFactionLogs(faction, logType)) {
                switch (logType) {
                    case CLAIM:
                        LandClaim landClaim = (LandClaim) log;
                        item.addLoreLine(CC.translate(prefix + landClaim.getLandClaimEvent().getfPlayer().getName() + " &7claimed land at &d" + landClaim.getLandClaimEvent().getLocation() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case UNCLAIM:
                        LandUnclaim landUnClaim = (LandUnclaim) log;
                        item.addLoreLine(CC.translate(prefix + landUnClaim.getLandUnclaimEvent().getfPlayer().getName() + " &7unclaimed land at &d" + landUnClaim.getLandUnclaimEvent().getLocation() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case UNCLAIMALL:
                        LandUnclaimAll landUnclaimAll = (LandUnclaimAll) log;
                        item.addLoreLine(CC.translate(prefix + landUnclaimAll.getLandUnclaimAllEvent().getfPlayer().getName() + " &7unclaimed ALL land. " + "&8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case PLAYER_JOIN:
                        PlayerJoin playerJoin = (PlayerJoin) log;
                        item.addLoreLine(CC.translate(prefix + playerJoin.getFPlayerJoinEvent().getfPlayer().getName() + " &7join the faction due to &e" + playerJoin.getFPlayerJoinEvent().getReason().name().toLowerCase() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case PLAYER_LEAVE:
                        PlayerLeave playerLeave = (PlayerLeave) log;
                        item.addLoreLine(CC.translate(prefix + playerLeave.getFPlayerLeaveEvent().getfPlayer().getName() + " &7left the faction due to &e" + playerLeave.getFPlayerLeaveEvent().getReason().name().toLowerCase() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case CHUNKBUST:
                        ChunkBust chunkBust = (ChunkBust) log;
                        item.addLoreLine(CC.translate(prefix + Bukkit.getOfflinePlayer(chunkBust.getUuid()).getName() + " &7placed a chunkbuster at &d" + chunkBust.getLocation() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case SPAWNER_BREAK:
                        SpawnerBreak spawnerBreak = (SpawnerBreak) log;
                        item.addLoreLine(CC.translate(prefix + Bukkit.getOfflinePlayer(spawnerBreak.getUuid()).getName() + " &7broke &ex" + spawnerBreak.getAmount() + " " + EntityUtils.getEntityName(spawnerBreak.getEntityType()) + "&7 at &d" + spawnerBreak.getLocation() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case SPAWNER_PLACE:
                        SpawnerPlace spawnerPlace = (SpawnerPlace) log;
                        item.addLoreLine(CC.translate(prefix + Bukkit.getOfflinePlayer(spawnerPlace.getUuid()).getName() + " &7placed &ex" + spawnerPlace.getAmount() + " " + EntityUtils.getEntityName(spawnerPlace.getEntityType()) + "&7 at &d" + spawnerPlace.getLocation() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case RELATION_EDITS:
                        RelationEdits relationEdits = (RelationEdits) log;
                        item.addLoreLine(CC.translate(prefix + relationEdits.getFactionRelationEvent().getFaction().getTag() + " &7changed relation: &d" + relationEdits.getFactionRelationEvent().getOldRelation().nicename + " &7-> &d" + relationEdits.getFactionRelationEvent().getRelation().nicename + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                    case FACTION_RENAME:
                        FactionRename factionRename = (FactionRename) log;
                        item.addLoreLine(CC.translate(prefix + factionRename.getFactionRenameEvent().getfPlayer().getName() + " &7renamed the faction tag &d" + factionRename.getFactionRenameEvent().getOldFactionTag() + " &7-> &d" + factionRename.getFactionRenameEvent().getFactionTag() + "&7. &8(&7" + TimeDateUtils.splitToComponentTimes((System.currentTimeMillis() - log.getTime()) / 1000) + "ago&8)"));
                        break;
                }
            }
            item.addLoreLine("");
            item.addLoreLine(CC.translate("&7&nClick&7 to view all the " + logType.getDisplayName()));
            this.fLogGui.addItem(new GuiItem(item.build(), event -> {
                //Make a pastebin link and send it to plaer
            }));

        }

        this.fLogGui.setDefaultClickAction(event -> event.setCancelled(true));

    }

}
