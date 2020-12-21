/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.domains;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.Domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultDomain
implements Domain {
    private final Set<String> groups = new LinkedHashSet<String>();
    private final Set<String> players = new HashSet<String>();

    public void addPlayer(String name) {
        this.players.add(name.toLowerCase());
    }

    public void addPlayer(LocalPlayer player) {
        this.players.add(player.getName().toLowerCase());
    }

    public void removePlayer(String name) {
        this.players.remove(name.toLowerCase());
    }

    public void removePlayer(LocalPlayer player) {
        this.players.remove(player.getName().toLowerCase());
    }

    public void addGroup(String name) {
        this.groups.add(name.toLowerCase());
    }

    public void removeGroup(String name) {
        this.groups.remove(name.toLowerCase());
    }

    public Set<String> getGroups() {
        return this.groups;
    }

    public Set<String> getPlayers() {
        return this.players;
    }

    @Override
    public boolean contains(LocalPlayer player) {
        if (this.contains(player.getName())) {
            return true;
        }
        for (String group : this.groups) {
            if (!player.hasGroup(group)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(String playerName) {
        return this.players.contains(playerName.toLowerCase());
    }

    public int size() {
        return this.groups.size() + this.players.size();
    }

    public String toPlayersString() {
        StringBuilder str = new StringBuilder();
        ArrayList<String> output = new ArrayList<String>(this.players);
        Collections.sort(output, String.CASE_INSENSITIVE_ORDER);
        Iterator it = output.iterator();
        while (it.hasNext()) {
            str.append((String)it.next());
            if (!it.hasNext()) continue;
            str.append(", ");
        }
        return str.toString();
    }

    public String toGroupsString() {
        StringBuilder str = new StringBuilder();
        Iterator<String> it = this.groups.iterator();
        while (it.hasNext()) {
            str.append("*");
            str.append(it.next());
            if (!it.hasNext()) continue;
            str.append(", ");
        }
        return str.toString();
    }

    public String toUserFriendlyString() {
        StringBuilder str = new StringBuilder();
        if (this.players.size() > 0) {
            str.append(this.toPlayersString());
        }
        if (this.groups.size() > 0) {
            if (str.length() > 0) {
                str.append("; ");
            }
            str.append(this.toGroupsString());
        }
        return str.toString();
    }

    public void removeAll() {
        this.groups.clear();
        this.players.clear();
    }
}

