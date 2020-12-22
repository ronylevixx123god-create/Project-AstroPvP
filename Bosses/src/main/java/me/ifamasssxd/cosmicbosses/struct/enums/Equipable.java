/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.cosmicbosses.struct.enums;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Equipable {
    LivingEntity ent;

    public void setHelmet(ItemStack helmet) {
        this.ent.getEquipment().setHelmet(this.setUnbreakable(helmet));
        this.ent.getEquipment().setHelmetDropChance(0.0f);
    }

    public void setChestplate(ItemStack chest) {
        this.ent.getEquipment().setChestplate(this.setUnbreakable(chest));
        this.ent.getEquipment().setChestplateDropChance(0.0f);
    }

    public void setLeggings(ItemStack legs) {
        this.ent.getEquipment().setLeggings(this.setUnbreakable(legs));
        this.ent.getEquipment().setLeggingsDropChance(0.0f);
    }

    public void setBoots(ItemStack boots) {
        this.ent.getEquipment().setBoots(this.setUnbreakable(boots));
        this.ent.getEquipment().setBootsDropChance(0.0f);
    }

    public ItemStack setUnbreakable(ItemStack is) {
        ItemMeta im = is.getItemMeta();
        im.spigot().setUnbreakable(true);
        is.setItemMeta(im);
        return is;
    }

    private LivingEntity getEnt() {
        return this.ent;
    }

    public void setEnt(LivingEntity ent) {
        this.ent = ent;
    }
}

