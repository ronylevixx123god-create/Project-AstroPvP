package net.astropvp.astroevents.inventories;

import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.astroevents.AstroEvents;
import org.bukkit.entity.Player;

public class EventsUI {

    @Getter
    private final PaginatedGui eventsUI;

    public EventsUI(AstroEvents plugin, Player player) {
        this.eventsUI = new PaginatedGui(6, 28, "Events Menu");
    }
}
