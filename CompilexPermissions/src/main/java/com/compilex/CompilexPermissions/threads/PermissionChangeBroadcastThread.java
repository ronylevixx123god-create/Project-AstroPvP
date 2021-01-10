/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.vaqxine.NetworkManager.threads.SocketSenderThread
 *  org.bukkit.Bukkit
 */
package com.compilex.CompilexPermissions.threads;

import org.bukkit.Bukkit;

import java.util.HashSet;

public class PermissionChangeBroadcastThread
extends Thread {
    public HashSet<String> uuid_to_reload = new HashSet();
    private final String socket_pw = "6C84E9215294D6026037B29BB3F167B";
    private boolean running = false;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000L);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            if (!this.running || this.uuid_to_reload.size() <= 0) continue;
            String usr_string = "";
            for (String usr : this.uuid_to_reload) {
                usr_string = usr_string + usr + ",";
            }
            usr_string = usr_string.substring(0, usr_string.length() - 1);
            String packet = "ARK-6C84E9215294D6026037B29BB3F167B-reloadpermissions{" + usr_string + "}";
            Bukkit.getLogger().info("Sending packet " + packet + " to *!");
            this.uuid_to_reload.clear();
            this.running = false;
        }
    }

    public void syncPendingPermissions() {
        this.running = true;
    }
}

