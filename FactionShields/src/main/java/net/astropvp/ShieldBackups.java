// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp;

import net.astropvp.config.ShieldConfig;

public class ShieldBackups extends Thread
{
    private static final int BACKUP_INTERVAL;
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(ShieldBackups.BACKUP_INTERVAL);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            FactionShieldsPlugin.getInstance().getShieldHandler().backup();
        }
    }
    
    static {
        BACKUP_INTERVAL = 60000 * ShieldConfig.SHIELD_BACKUP_INTERVAL;
    }
}
