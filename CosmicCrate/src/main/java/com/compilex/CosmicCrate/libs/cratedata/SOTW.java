package com.compilex.CosmicCrate.libs.cratedata;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.libs.CosmicCrateData;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import io.netty.util.internal.ThreadLocalRandom;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SOTW extends CosmicCrateData {
    private ChatColor mainColor = ChatColor.GOLD;

    private ChatColor secondColor = ChatColor.GREEN;


    @Override
    public ItemStack getRandomMetaItem(Player p0) {
        double chance = Math.random();
        if (chance <= 0.05D) {
            new CustomItemBuilder("apollo_rank");
        }

        if (chance <= 0.5D) {
            CosmicCrate.getChests().get("PremadeOmni").createClaimableItem(false);
        }

        return ItemUtils.setAmount(CosmicSlotBotAPI.createSlotBotItem(), 8 + (new Random()).nextInt(16));
    }

    @Override
    public List<String> getDescriptionLore(String p0) {
        List<String> lore = new ArrayList<>();

        lore.add(CC.translate("&f&l&nADMIN ITEMS"));
        lore.add(CC.translate("&f* "));

        lore.add(CC.translate("&e&l&nCOSMETIC ITEMS"));
        lore.add(CC.translate("&e* "));


        lore.add(CC.translate("&6&l&nTREASURE ITEMS"));
        lore.add(CC.translate("&6* "));
        lore.add(CC.translate("&6* "));
        lore.add(CC.translate("&6* "));
        lore.add(CC.translate("&6* "));

        lore.add(CC.translate("&8&l&nBONUS ITEMS"));
        lore.add(CC.translate("&8* "));
        lore.add(CC.translate("&8* "));
        lore.add(CC.translate("&8* "));
        lore.add(CC.translate("&8* "));


        return lore;
    }

    @Override
    public List<ItemStack> getCrateLoot(Player p0) {
        List<ItemStack> items = Lists.newArrayList();
        items.add(ItemUtils.setAmount(, 16 + (new Random()).nextInt(16)));
        return items;
    }

    @Override
    public short getNonColorPane() {
        return (short) DyeColor.GREEN.getWoolData();
    }

    @Override
    public short getColorPane() {
        return (short)DyeColor.ORANGE.getWoolData();
    }

    @Override
    public List<Color> getConclusionFireworkColors() {
        return Lists.newArrayList(new Color[] { Color.ORANGE, Color.GREEN });
    }

    
}
