package me.astropvp.utils;

import me.astropvp.objects.BackpackType;

public class BackpackUtils {
    public static BackpackType getBackpackTypeFromID(String id) {
        for(BackpackType type : BackpackType.values()) {
            if(type.getId().equalsIgnoreCase(id))
                return type;
        }
        return null;
    }
}
