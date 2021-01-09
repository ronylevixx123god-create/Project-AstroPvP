/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmichcf.menus;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import me.ifamasssxd.cosmichcf.struct.Outpost;
import me.ifamasssxd.cosmichcf.struct.OutpostState;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class OutpostMenu
extends CosmicGUI {
    private static SimpleDateFormat format = new SimpleDateFormat("haa");

    public OutpostMenu(Player player) {
        super(player, CosmicKOTHPlugin.get_().getBaseName() + "s", 9);
    }

    public void redraw() {
        int index = 3;
        Calendar current = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("CST"));
        current.setTimeZone(TimeZone.getTimeZone("CST"));
        for (Outpost outpost : CosmicKOTHPlugin.get_().getOutpostManager().getOutposts()) {
            OutpostType type = outpost.getOutpostType();
            if (type == null) continue;
            String coordString = "Unknown";
            if (outpost.getTeleportLocation() != null) {
                Location l = outpost.getTeleportLocation();
                String color = outpost.getNameColor();
                String colorB = outpost.getNameColor() + CC.Bold;
                coordString = "  " + color + l.getBlockX() + colorB + "x " + color + l.getBlockY() + colorB + "y " + color + l.getBlockZ() + colorB + "z" + (this.player.getWorld().equals((Object)outpost.getTeleportLocation().getWorld()) ? color + " (" + colorB + outpost.getDistanceDecimalFormat().format(outpost.getTeleportLocation().distance(this.player.getLocation())) + "m" + color + " away)" : "");
            }
            ArrayList<String> lore = Lists.newArrayList("", coordString, "", CC.GoldB + "Status", outpost.getOutpostState().getName() + (outpost.getOutpostState() == OutpostState.CONTROLLED ? CC.Gray + " (" + CC.Green + outpost.getLastFactionToCapTag() + CC.Gray + ")" : "") + CC.Gray + " " + outpost.getPrettyFormattedCapturePercent(), "", CC.GoldB + "Rewards When Captured");
            type.getRewardStrings().forEach(re -> lore.add(" " + re));
            lore.add("");
            lore.add(CC.Gray + "Control the " + CosmicKOTHPlugin.get_().getBaseName() + " to gain access to");
            lore.add(CC.Gray + "these rewards for you and your faction!");
            this.setItem(index, new ItemBuilder(type.getGuiMaterial().getItemType(), outpost.getOutpostName() + " " + CosmicKOTHPlugin.get_().getBaseName(), new String[0]).lore((List)lore).data((short)type.getGuiMaterial().getData()).build(), () -> Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicKOTHPlugin.get_(), () -> {
                this.player.closeInventory();
                this.player.performCommand(CosmicKOTHPlugin.get_().getBaseName().toLowerCase());
            }));
            index += 2;
        }
    }

    static {
        format.setTimeZone(TimeZone.getTimeZone("CST"));
    }
}

