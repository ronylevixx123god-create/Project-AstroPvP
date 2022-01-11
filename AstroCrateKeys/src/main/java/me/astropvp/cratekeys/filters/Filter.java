package me.astropvp.cratekeys.filters;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter @Setter
public abstract class Filter<T> {
    private transient String name;

    private HashMap<T, Boolean> filterMap;

    public Filter(String name) {
        this.name = name;
        this.filterMap = new HashMap<>();
    }

    public Filter(String name, HashMap<T, Boolean> filterMap) {
        this.name = name;
        this.filterMap = filterMap;
    }

    public boolean isFiltered(T t) {
        return this.filterMap.containsKey(t) && this.filterMap.get(t);
    }

    public void setFiltered(T t, boolean b) {
        this.filterMap.put(t, b);
    }

}
