/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses;

import com.cosmicpvp.CosmicBossEggs.listeners.BossListener;
import me.ifamasssxd.cosmicbosses.boss.type.Bosses;
import me.ifamasssxd.cosmicbosses.boss.type.SlimeBoss;
import me.ifamasssxd.cosmicbosses.listeners.BossEggListener;
import me.ifamasssxd.cosmicbosses.utils.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class CustomBossEggs
extends JavaPlugin {
    Logger log = new Logger();
    public static CustomBossEggs cbe;

    @Override
    public void onEnable() {
        cbe = this;
        this.getServer().getPluginManager().registerEvents(new BossListener(), this);
        this.getServer().getPluginManager().registerEvents(new BossEggListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static CustomBossEggs getPlugin() {
        return cbe;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cbe")) {
            if (sender instanceof Player && !((Player)sender).isOp()) {
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("egg")) {
                ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.SLIME.b));
                ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.IRONGOLEM.b));
                ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.BLAZE.b));
            }
            Player pl = (Player)sender;
            new SlimeBoss().spawnBoss(pl.getTargetBlock((HashSet<Byte>)null, 16).getLocation());
        }
        return true;
    }
}

