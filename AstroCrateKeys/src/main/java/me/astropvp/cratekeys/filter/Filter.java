package me.astropvp.cratekeys.filter;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
public abstract class Filter<T> {
    private transient String name;

    private HashMap<T, Boolean> filterMap;

    public Filter(String name) {
        this.name = name;
        this.filterMap = new HashMap<>();
    }

    public boolean isFiltered(T t) {
        return this.filterMap.containsKey(t) && !this.filterMap.get(t);
    }

    public void setFiltered(T t, boolean b) {
        this.filterMap.put(t, b);
    }

}
