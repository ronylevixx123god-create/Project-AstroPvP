package com.drtshock.playervaults.tasks;

import com.drtshock.playervaults.*;
import java.io.*;

public class Cleanup implements Runnable
{
    private final long diff;
    
    public Cleanup(final int n) {
        this.diff = n * 86400000;
    }
    
    @Override
    public void run() {
        final File vaultData = PlayerVaults.getInstance().getVaultData();
        if (!vaultData.exists()) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        for (final File file : vaultData.listFiles()) {
            if (currentTimeMillis - file.lastModified() > this.diff) {
                PlayerVaults.getInstance().getLogger().info("Deleting vault file (cleanup): " + file.getName());
                file.delete();
            }
        }
    }
}
