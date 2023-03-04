package me.astropvp.proxypach.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.proxypach.ProxyPatch;
import me.astropvp.proxypach.tasks.ProxyPatchTask;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;

@Command("proxypatch")
public class ProxyPatchCommand extends CommandBase {

    private ProxyPatch plugin;
    public ProxyPatchCommand(ProxyPatch plugin) {
        this.plugin = plugin;
    }

    @SubCommand("end")
    @Alias({"finish", "reset"})
    @Permission("astro.admin")
    public void endCommand(final CommandSender commandSender) {
        this.plugin.getProxyPatchTask().end();
    }

    @SubCommand("start")
    @Permission("astro.admin")
    public void startCommand(final CommandSender commandSender) {
        this.plugin.setProxyPatchTask(new ProxyPatchTask());
        this.plugin.getProxyPatchTask().runTaskTimer(this.plugin, 60L, 20L);
    }

    @SubCommand("restart")
    @Permission("astro.admin")
    public void restartCommand(final CommandSender commandSender) {
        this.plugin.getProxyPatchTask().end();
        this.plugin.setProxyPatchTask(new ProxyPatchTask());
        this.plugin.getProxyPatchTask().runTaskTimer(this.plugin, 60L, 20L);
    }

    @SubCommand("stop")
    @Alias("cancel")
    @Permission("astro.admin")
    public void stopCommand(final CommandSender commandSender) {
        this.plugin.getProxyPatchTask().cancel();
    }

    @SubCommand("reload")
    @Permission("astro.admin")
    public void reload(CommandSender sender) {
        ProxyPatch.getInstance().saveConfig();
        sender.sendMessage(CC.Green + "Reload ProxyPatch Config...");
    }

    @SubCommand("setmax")
    @Permission("astro.admin")
    public void setMax(CommandSender sender, Integer amount) {
        ProxyPatch.getInstance().getConfig().set("max-accounts", amount);
        ProxyPatch.getInstance().saveConfig();
        sender.sendMessage(CC.Green + "Set the max amount of accounts to " + CC.White + amount);
    }
}
