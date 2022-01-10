package me.astropvp.cratekeys.filter;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class FilterPlayer {
    private HashMap<String, Filter<?>> filters;

    private UUID uuid;

    public FilterPlayer(UUID uuid) {
        this.uuid = uuid;
        this.filters = new HashMap<>();
    }

    public boolean hasFilter(String name) {
        return this.filters.containsKey(name);
    }

    public Filter<?> getFilter(String name) {
        return this.filters.get(name);
    }

    public void registerFilter(Filter<?> filter) {
        this.filters.put(filter.getName(), filter);
    }

    public void loadData() {
        
    }

    public void saveData() {

    }
}
