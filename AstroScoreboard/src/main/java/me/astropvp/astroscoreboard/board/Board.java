package me.astropvp.astroscoreboard.board;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.board.BoardTimer;
import com.massivecraft.factions.integration.Econ;
import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Board {
    private final BoardAdapter adapter;

    private final Player player;

    public BoardAdapter getAdapter() {
        return this.adapter;
    }

    public Player getPlayer() {
        return this.player;
    }

    private List<BoardEntry> entries = new ArrayList<>();

    public List<BoardEntry> getEntries() {
        return this.entries;
    }

    private Set<BoardTimer> timers = new HashSet<>();

    private Set<String> keys = new HashSet<>();

    private Scoreboard scoreboard;

    private Objective objective;

    public Set<String> getKeys() {
        return this.keys;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public Board(Player player, BoardAdapter adapter) {
        this.adapter = adapter;
        this.player = player;
        init();
    }

    private void init() {
        if (!this.player.getScoreboard().equals(AstroScoreboardPlugin.getInstance().getServer().getScoreboardManager().getMainScoreboard())) {
            this.scoreboard = this.player.getScoreboard();
        } else {
            this.scoreboard = AstroScoreboardPlugin.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        }
        this.objective = this.scoreboard.registerNewObjective("Default", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(this.adapter.getTitle(this.player));

        Objective h = scoreboard.registerNewObjective("showhealth", Criterias.HEALTH);
        h.setDisplaySlot(DisplaySlot.BELOW_NAME);
        h.setDisplayName(ChatColor.RED + "❤");
    }

    public String getNewKey(BoardEntry entry) {
        for (ChatColor color : ChatColor.values()) {
            String colorText = color + "" + ChatColor.WHITE;
            if (entry.getText().length() > 16) {
                String sub = entry.getText().substring(0, 16);
                colorText = colorText + ChatColor.getLastColors(sub);
            }
            if (!this.keys.contains(colorText)) {
                this.keys.add(colorText);
                return colorText;
            }
        }
        throw new IndexOutOfBoundsException("No more keys available!");
    }

    public List<String> getBoardEntriesFormatted() {
        List<String> toReturn = new ArrayList<>();
        for (BoardEntry entry : this.entries)
            toReturn.add(entry.getText());
        return toReturn;
    }

    public BoardEntry getByPosition(int position) {
        for (int i = 0; i < this.entries.size(); i++) {
            if (i == position)
                return this.entries.get(i);
        }
        return null;
    }

    public BoardTimer getCooldown(String id) {
        for (BoardTimer cooldown : getTimers()) {
            if (cooldown.getId().equals(id))
                return cooldown;
        }
        return null;
    }

    public Set<BoardTimer> getTimers() {
        this.timers.removeIf(cooldown -> (System.currentTimeMillis() >= cooldown.getEnd()));
        return this.timers;
    }
}
