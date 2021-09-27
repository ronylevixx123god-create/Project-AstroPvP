// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.animations;

import net.astropvp.SandBots;
import net.astropvp.managers.SandBotManager;
import org.bukkit.util.EulerAngle;
import org.bukkit.scheduler.BukkitRunnable;

public class Animation extends BukkitRunnable
{
    private Integer flag;
    private SandBotManager bot;
    private SandBots plugin;
    
    public Animation(final SandBotManager bot, final SandBots plugin) {
        this.flag = 0;
        this.bot = bot;
        this.plugin = plugin;
    }
    
    public void run() {
        if (this.bot.getSandbotModel() == null || this.bot.getSandbotModel().isDead() || !this.bot.getSandbotModel().isValid()) {
            this.cancel();
        }
        if (!this.bot.isAnimate()) {
            return;
        }
        final EulerAngle old = this.bot.getSandbotModel().getRightArmPose();
        if (Math.abs(old.getX()) >= 1.0 && this.flag == 0) {
            final EulerAngle newe = old.add(0.05000000074505806, 0.05000000074505806, 0.019999999552965164);
            this.bot.getSandbotModel().setRightArmPose(newe);
        }
        else {
            this.flag = 1;
            if (Math.abs(old.getX()) < 2.5 && this.flag == 1) {
                final EulerAngle newe = old.add(-0.05000000074505806, -0.05000000074505806, -0.019999999552965164);
                this.bot.getSandbotModel().setRightArmPose(newe);
            }
            else {
                this.flag = 0;
            }
        }
    }
}
