/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.ItemBuilder
 *  org.bukkit.Bukkit
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.help.gui;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HCFClaimingGUI
extends CosmicGUI {
    public HCFClaimingGUI(Player player) {
        super(player, "How to /f claim", 9);
    }

    public void redraw() {
        String bullet = CC.GreenB + " * " + CC.White;
        this.setItem(3, new ItemBuilder(Material.STICK, CC.GreenB + "How to Claim", "", bullet + "Use " + CC.GreenU + "/f claim" + CC.White + " to receive a claim stick", bullet + "Mark 2 corners of desired claim", bullet + "Claim must be at least " + CC.Green + "10x10", bullet + "Use " + CC.Green + "/f claim" + CC.White + " to claim selected area", "", CC.GreenB + "Claim Cost: $" + CC.Green + "5 per block", "", CC.GreenB + "TIP: " + CC.Gray + "Shift-Click the second position to auto claim.").build(), () -> Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) AstroCore.getInstance(), ((HCFClaimingGUI)this)::close, 1L));
        this.setItem(4, new ItemBuilder(Material.STAINED_GLASS).data((short)DyeColor.LIME.getWoolData()).name(CC.GreenB + "Claim Expansion").lore(CC.Gray + "** Expansion of your existing territory can", CC.Gray + "be achieved by lining up the", CC.Gray + "expanded claim corners with your", CC.Gray + "existing claim corners.", "", CC.Gray + "Faction claims cannot be irregular shapes", CC.Gray + "and can " + CC.GrayU + "only" + CC.Gray + " have " + CC.RedB + "4" + CC.Gray + " corners", "", CC.Green + "To view faction corners use " + CC.GreenU + "/f map" + CC.Green + "!").build(), null);
        this.setItem(5, new ItemBuilder(Material.BOOK, CC.GreenB + "How to Unclaim", "", CC.Gray + "You must use " + CC.Red + "/f unclaimall" + CC.Gray + " to unclaim all land", CC.Gray + "if you wish to move your faction base.").build(), null);
    }
}

