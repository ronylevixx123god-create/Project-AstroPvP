// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.tasks;

import net.astropvp.utils.Utils;
import net.astropvp.SandBots;

public class AutoSaveTask implements Runnable
{
    private SandBots plugin;
    
    public AutoSaveTask(final SandBots plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        Utils.saveDataOfSandbots(this.plugin.getManager());
        Utils.logToServer("SandBots data saved successfully", true);
    }
}
