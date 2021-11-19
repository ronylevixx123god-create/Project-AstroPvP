// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.api;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.compilex.CosmicCrate.libs.RandomFireworks;
import com.compilex.CosmicCrate.managers.CosmicCrateManager;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CCAPI
{
    public static ItemStack getCosmicCrate(String crate) {
        ItemStack cc = null;
        CosmicCrateManager manager = CosmicCrate.get().getCosmicCrateManager();
        if (manager.getCosmicCrate(crate) != null) {
            final List<String> lore = manager.getCosmicCrate(crate).getDescriptionLore();
            cc = ItemUtils.buildItem(Material.ENDER_CHEST, (short)0, ChatColor.translateAlternateColorCodes('&', crate), (String[])lore.toArray(new String[0]));
        }
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            assert cc != null;
            ItemID.setItemID(cc, ItemID.generateNewItemID());
        }
        return cc;
    }
    
    public static CosmicCrateData getCosmicCrateVersion(final ItemStack i) {
        if (!i.hasItemMeta() || !i.getItemMeta().hasDisplayName() || i.getMaxStackSize() == 1) {
            return null;
        }
        final CosmicCrateData[] data = {null};
        CosmicCrate.get().getCosmicCrateManager().getCosmicCrates().forEach((string, crate) -> {
            if(string.equals(i.getItemMeta().getDisplayName())) {
                data[0] = crate;
            }
        });
        return data[0];
    }
    
    public static void claimCosmicCrate(final Player p, String crate) {
        final String uuid = p.getUniqueId().toString();
        CosmicCrateData data = CosmicCrate.get().getCosmicCrateManager().getCosmicCrate(crate);
        final List<CosmicCrateData> claimedCrates = CosmicCrate.claimedCosmicCrates.containsKey(uuid) ? CosmicCrate.claimedCosmicCrates.get(uuid) : new ArrayList<>();
        claimedCrates.add(data);
        CosmicCrate.claimedCosmicCrates.put(uuid, claimedCrates);
        p.getInventory().addItem(getCosmicCrate(crate));
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
        RandomFireworks.getManager().launchRandomFirework(p.getLocation());
    }
    
    public static boolean hasClaimedCosmicCrate(final Player p, final CosmicCrateData crate) {
        return CosmicCrate.claimedCosmicCrates.containsKey(p.getUniqueId().toString()) && CosmicCrate.claimedCosmicCrates.get(p.getUniqueId().toString()).contains(crate);
    }
    
    public static void launchFirework(final Location l, final Color c) {
        final Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        final FireworkMeta fwm = fw.getFireworkMeta();
        final FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
    }
    
    public static boolean isExplosiveSnowball(final ItemStack i) {
        return i != null && i.getType() == Material.SNOW_BALL && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Explosive Snowball");
    }
    
    public static List<ItemStack> getCosmicCrateLoot(final Player p, final CosmicCrateData crate) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        if (crate != null) {
            items = crate.getCrateLoot();
        }
        return items;
    }
    
    private static int rand(final int low, final int high) {
        return new Random().nextInt(high - low) + low;
    }
}
