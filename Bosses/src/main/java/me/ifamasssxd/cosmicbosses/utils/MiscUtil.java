/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  net.minecraft.server.v1_7_R4.Enchantment
 */
package me.ifamasssxd.cosmicbosses.utils;

import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MiscUtil {
    public static void launchFirework(Location l, ItemRarity ir) {
        Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Color c = null;
        if (ir == ItemRarity.SIMPLE) {
            c = Color.GRAY;
        }
        if (ir == ItemRarity.UNIQUE) {
            c = Color.GREEN;
        }
        if (ir == ItemRarity.ELITE) {
            c = Color.AQUA;
        }
        if (ir == ItemRarity.ULTIMATE) {
            c = Color.YELLOW;
        }
        if (ir == ItemRarity.LEGENDARY) {
            c = Color.BLACK;
        }
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.STAR).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(2);
        fw.setFireworkMeta(fwm);
    }

    public static ItemStack getBossEgg(Boss b) {
        ItemStack is = new ItemStack(Material.getMaterial(383), 1);
        ItemRarity ir = CosmicSpaceChestsAPI.getItemRarity((int)b.rarity.i);
        ChatColor cc = CosmicSpaceChestsAPI.getColor((ItemRarity)ir);
        String et_name = b.custom_name.split(" ")[b.custom_name.split(" ").length - 1];
        is.setDurability(MiscUtil.getMobID(et_name));
        ItemMeta im = is.getItemMeta();
        im.setDisplayName((Object)((Object)cc) + "Spawn " + b.custom_name);
        im.setLore(new ArrayList<String>(Arrays.asList(ChatColor.GRAY.toString() + (Object)((Object)ChatColor.ITALIC) + "Be careful where you put this.")));
        is.setItemMeta(im);
        return is;
    }

    public static short getMobID(String mob_name) {
        EntityType et;
        short id = 0;
        if (mob_name.equalsIgnoreCase("irongolem")) {
            id = 99;
        }
        if (mob_name.equalsIgnoreCase("wither")) {
            id = 58;
        }
        if (mob_name.equalsIgnoreCase("king")) {
            id = 96;
        }
        if (mob_name.equalsIgnoreCase("goblin")) {
            id = 62;
        }
        if (id == 0 && (et = EntityType.fromName(mob_name)) != null) {
            id = et.getTypeId();
        }
        return id;
    }

    public static ItemStack getPlayerHead(String playerName) {
        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, 3);
        SkullMeta sm = (SkullMeta)i.getItemMeta();
        sm.setOwner(playerName);
        i.setItemMeta(sm);
        return i;
    }

    public static Location getRandomLocationNearby(Location l, int max_dist, int y_boost) {
        int min_distance = 7;
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max_dist - min_distance) + min_distance), 0.0, (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max_dist - min_distance) + min_distance));
        rand_loc.add(0.5, y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }

    public static float getEnchantDamageModifier(Player pl) {
        ItemStack weapon = pl.getItemInHand();
        float damage_mod = 0.0f;
        if (weapon != null) {
            if (weapon.getType() != Material.BOW && weapon.getEnchantments().containsKey((Object) Enchantment.DAMAGE_ALL)) {
                damage_mod = (float)weapon.getEnchantments().get((Object)Enchantment.DAMAGE_ALL).intValue() * 0.625f;
            }
            if (weapon.getType() == Material.BOW && weapon.getEnchantments().containsKey((Object)Enchantment.ARROW_DAMAGE)) {
                damage_mod = 0.25f * ((float)weapon.getEnchantments().get((Object)Enchantment.ARROW_DAMAGE).intValue() + 1.0f);
            }
        }
        return damage_mod;
    }
}

