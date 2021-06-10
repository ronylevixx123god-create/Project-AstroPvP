package com.drtshock.playervaults.vaultmanagement;

import java.util.*;
import org.bukkit.*;

public class VaultViewInfo
{
    UUID uuid;
    final String holder;
    final int number;
    
    @Deprecated
    public VaultViewInfo(final String holder, final int number) {
        this.holder = holder;
        this.number = number;
    }
    
    public VaultViewInfo(final UUID uuid, final int number) {
        this.uuid = uuid;
        this.number = number;
        this.holder = Bukkit.getOfflinePlayer(uuid).getName();
    }
    
    @Deprecated
    public String getHolder() {
        return this.holder;
    }
    
    public UUID getHolderUUID() {
        return this.uuid;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    @Override
    public String toString() {
        return this.uuid + " " + this.number;
    }
}
