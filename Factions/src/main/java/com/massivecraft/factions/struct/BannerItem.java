/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.DyeColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.massivecraft.factions.struct;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import java.util.List;

import com.massivecraft.factions.P;
import com.massivecraft.factions.manager.BannerManager;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BannerItem extends CosmicItem {
    public BannerItem() {
        super("FACTION_BANNER");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        if (objects.length != 4) {
            return null;
        }
        Faction faction = (Faction)(objects[0] instanceof Faction ? objects[0] : Factions.i.get(objects[0].toString()));
        if (faction == null) {
            return null;
        }
        Player pla = objects[1] instanceof Player ? (Player) objects[1] : player;
        boolean white = objects[2] instanceof Boolean && (Boolean)objects[2] != false;
        String freeReason = objects[3] instanceof String ? (String)objects[3] : null;
        NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder().setString("fId", faction.getId()).setString("fTag", faction.getTag());
        if (freeReason != null) {
            builder.setBoolean("noSoulCost", Boolean.valueOf(true));
        }
        ItemStack retr = new NBTWrapper(new ItemBuilder(BannerManager.bannerMaterial).name(CC.DarkPurpleB + "Faction Banner").lore("", CC.DarkPurpleB + "COST: " + CC.White + (freeReason != null ? CC.GreenB + "FREE (" + CC.Gray + freeReason + CC.GreenB + ")" : P.getP().getConfig().getInt("soulCost") + " souls"), CC.DarkPurpleB + "DURATION: " + CC.White + P.getP().getConfig().getInt("bannerCooldown") + "s", "", CC.Gray + "Place down to create an", CC.Gray + "inter-dimensional rift", CC.Gray + "that allows faction members", CC.Gray + "to travel to your location", CC.Gray + "by using the /f assist command.").build()).setString("cosmicType", "FACTION_BANNER").setNBTBase("conaxData", (NBTBase)builder.get()).build();
        if (white) {
            BannerMeta meta = (BannerMeta)retr.getItemMeta();
            meta.setBaseColor(DyeColor.WHITE);
            meta.setPatterns((List)Lists.newArrayList());
            retr.setItemMeta((ItemMeta)meta);
            retr.setDurability((short)meta.getBaseColor().getDyeData());
        }
        return retr;
    }
}

