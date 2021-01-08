/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 */
package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import java.util.List;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PortablePicnicSkin
extends Backpack {
    public static List<Player> picnicPlayers = Lists.newArrayList();

    @Override
    public BackpackType getType() {
        return BackpackType.PORTABLE_PICNIC;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "No Food Loss", CC.Red + "-4% Incoming Damage", CC.Red + "+1 Food to nearby allies within", CC.Red + "10 blocks every 10s");
    }

    @Override
    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment((String)"Implants").applyEquipEffect(player, 3);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment((String)"Implants").applyUnequipEffect(player, 3);
    }

    @Override
    public void onDamaged(Player damaged, Entity damager, EntityDamageByEntityEvent e) {
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.96);
    }
}

