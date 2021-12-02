package net.astropvp.astroevents.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.astropvp.astroevents.AstroEvents;
import net.astropvp.astroevents.inventories.EventsUI;
import org.bukkit.entity.Player;

@CommandAlias("event|events")
public class EventsCommand extends BaseCommand {

    private final AstroEvents plugin;

    public EventsCommand(AstroEvents plugin) {
        this.plugin = plugin;
    }

    @Default
    public void baseCommand(Player sender) {
        EventsUI eventsUI = new EventsUI(plugin, sender);
        eventsUI.getEventsUI().open(sender);
    }

}
