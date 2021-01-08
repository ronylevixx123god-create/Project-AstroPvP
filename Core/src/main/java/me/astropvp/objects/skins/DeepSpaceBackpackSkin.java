/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 */
package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeepSpaceBackpackSkin
extends Backpack {
    @Override
    public BackpackType getType() {
        return BackpackType.DEEP_SPACE_BACKPACK;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "+3% Outgoing Damage", CC.Red + "Immune to Soul Trap");
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent e) {
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.03);
    }
}

