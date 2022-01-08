/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.files.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicArmored
extends HeroicCustomEnchantment {
    public HeroicArmored() {
        super("Paladin Armored", "Armored");
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        ItemStack i = null;
        if (attacker instanceof Player) {
            Player pAttacker = (Player)attacker;

            i = pAttacker.getItemInHand();
            if (Math.random() < (double)level * 0.003) {
                Blessed.blessPlayer((Player)player);
            }
        }


        double heroicArmoredNegation = CustomConfig.get().getDouble("heroicArmoredNegation");
        if(attacker instanceof Player)
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2021_BELT))
                heroicArmoredNegation+=0.03;
        if (i != null && i.getType().name().contains("_SWORD") && e.getDamage() * (1.0 - heroicArmoredNegation) > 1.0) {
            e.setDamage(e.getDamage() * (1.0 - 0.12));
            Location l = player.getLocation();
        }
    }
}

