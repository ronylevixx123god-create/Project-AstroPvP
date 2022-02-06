package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.earth2me.essentials.utils.DateUtil;
import me.astropvp.hotfixplugin.HotFixPlugin;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.lang.management.ManagementFactory;

public class CommandPingListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void pingCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        int ping = HotFixPlugin.getPing(player);
        String tps = HotFixPlugin.df.format(EssentialsUtils.getEssentials().getTimer().getAverageTPS());
        if (e.getMessage().equalsIgnoreCase("/ping") || e.getMessage().equalsIgnoreCase("/tps") || e.getMessage().equalsIgnoreCase("/mem") || e.getMessage().equalsIgnoreCase("/ping") || e.getMessage().equalsIgnoreCase("/lag")) {
            e.setMessage("/null");
            e.setCancelled(true);
            player.sendMessage("");
            player.sendMessage(CC.translate("&6&lAstro&f&lPvP &6&lPerformance"));
            player.sendMessage(CC.translate("&6&l* &eUptime: &f" + DateUtil.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime())));
            player.sendMessage(CC.translate("&6&l* &eServer \"TPS\": &a" + tps));
            player.sendMessage(CC.translate("&6&l* &ePing: &a" + ping + "ms"));
            player.sendMessage("");

        }

    }

    @EventHandler
    public void addmutated(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        if (e.getMessage().equalsIgnoreCase("/addmutated")) {
            e.setMessage("/null");
            e.setCancelled(true);
            NBTWrapper wrapper = new NBTWrapper(e.getPlayer().getItemInHand());
            NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
            cosmicData.setString("tintColor", "-153102204"); //-10237440
            player.sendMessage("done");
        }
    }
}
