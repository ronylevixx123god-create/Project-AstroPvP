/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.entity.Player
 */
package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Player;

public class RainbowBackpackSkin
extends Backpack {
    @Override
    public BackpackType getType() {
        return BackpackType.RAINBOW_BACKPACK;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "+1 Max Heart");
    }

    @Override
    public void onEquip(Player player) {
        double health = player.getHealth();
        player.setMaxHealth(health + 2.0);
        player.setHealth(health);
    }

    @Override
    public void onUnequip(Player player) {
        double health = player.getMaxHealth();
        player.setMaxHealth(20.0);
    }
}

