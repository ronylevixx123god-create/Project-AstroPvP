/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.CosmicCustomTNT.enums;

import java.util.Random;
import org.arkhamnetwork.CosmicCustomTNT.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TNTTypes {
    Huge((Object)ChatColor.AQUA + "Gigantic TNT", new String[]{ChatColor.AQUA.toString() + ChatColor.ITALIC.toString() + "Explodes in a much larger radius.", "", (Object)ChatColor.GRAY + "Spawns 2 extra explosions at", (Object)ChatColor.GRAY + "y+3 and y-3 of TNT, and another", (Object)ChatColor.GRAY + "2 explosions at either x+3, z+3 or", (Object)ChatColor.GRAY + "x-3 and z-3."}),
    Chemical((Object)ChatColor.GOLD + "Chemical TNT", new String[]{ChatColor.GOLD.toString() + ChatColor.ITALIC.toString() + "Creates a poisonous cloud of debuffs.", "", (Object)ChatColor.GRAY + "Gives a combination of poison,", (Object)ChatColor.GRAY + "confusion, blindness, and slowness", (Object)ChatColor.GRAY + "to all players within 8x8 of explosion."}),
    Lethal((Object)ChatColor.DARK_RED + "Lethal TNT", new String[]{ChatColor.DARK_RED.toString() + ChatColor.ITALIC.toString() + "A more dense, dangerous explosion.", "", (Object)ChatColor.GRAY + "Creates 4x extra TNT explosions at", (Object)ChatColor.GRAY + "same location as the original."}),
    Incindiary((Object)ChatColor.RED + "Incindiary TNT", new String[]{ChatColor.RED.toString() + ChatColor.ITALIC.toString() + "A firey, burning explosion.", "", (Object)ChatColor.GRAY + "Sets players within 8x8 on", (Object)ChatColor.GRAY + "fire for 8 seconds."}),
    Lucky((Object)ChatColor.YELLOW + "Lucky TNT", new String[]{ChatColor.YELLOW.toString() + ChatColor.ITALIC.toString() + "Increases chances of spawner drops.", "", (Object)ChatColor.GRAY + "200% the normal chance of a", (Object)ChatColor.GRAY + "spawner dropping from explosion."}),
    Tactical((Object)ChatColor.BLUE + "Tactical TNT", new String[]{ChatColor.BLUE.toString() + ChatColor.ITALIC.toString() + "Explosion only affects mob spawners.", "", (Object)ChatColor.GRAY + "No blocks will be affected by", (Object)ChatColor.GRAY + "this explosion except for", (Object)ChatColor.GRAY + "Monster Spawners."}),
    Mimic((Object)ChatColor.GREEN + "Mimic TNT", new String[]{ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + "Repeats the same explosion many times.", "", (Object)ChatColor.GRAY + "Spawns a TNT explosion at", (Object)ChatColor.GRAY + "same location every 5s", (Object)ChatColor.GRAY + "for 15s total, or 3 explosions."}),
    Hex((Object)ChatColor.LIGHT_PURPLE + "Hex TNT", new String[]{ChatColor.LIGHT_PURPLE.toString() + ChatColor.ITALIC.toString() + "Weakens block durability.", "", (Object)ChatColor.GRAY + "All blocks within the explosion", (Object)ChatColor.GRAY + "radius take 2x damage for", (Object)ChatColor.GRAY + "30s after the initial explosion."}),
    Silent((Object)ChatColor.DARK_GRAY + "Silent TNT", new String[]{ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC.toString() + "Quiet, makes much less noise.", "", (Object)ChatColor.GRAY + "Explosion can only be heard", (Object)ChatColor.GRAY + "by players within 16x16 blocks."});

    String name;
    String[] lore;

    private TNTTypes(String name, String[] lore) {
        this.name = name;
        this.lore = lore;
    }

    public String getItemName() {
        return this.name;
    }

    public static ItemStack getTNTItem(TNTTypes tt) {
        return ItemUtils.buildItem(Material.TNT, (short)0, tt.name, tt.lore);
    }

    public static ItemStack getRandomCustomTNT(int amount) {
        TNTTypes tt = TNTTypes.values()[new Random().nextInt(TNTTypes.values().length)];
        if (tt == Chemical) {
            tt = Mimic;
        }
        if (tt == Incindiary) {
            tt = Hex;
        }
        ItemStack i = TNTTypes.getTNTItem(tt);
        i.setAmount(amount);
        return i;
    }
}

