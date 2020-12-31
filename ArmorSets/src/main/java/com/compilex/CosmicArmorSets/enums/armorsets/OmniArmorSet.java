package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_7_R4.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class OmniArmorSet extends ArmorSetInfo {
    public ItemStack getHelmet(boolean applyTransmog) {
        ItemStack item = ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&1&lOmni Helmet", "", "&7Through the veil it's all so clear: &1purposeful.", "&7Your path. announcing itself, appearas within the mist", "&7Your breath hitches, held your next step, &1precise, infallable", "", "&7A flash, so fast, so clean, you exhale.", "&7The &1smell of death &7surrounds you.", "&7His life drains, once, splashing over your boots", "&7to the earth below.", "",
                "&7Your smirk, the thrist quenched....... for now.......", "&4&oWho's Next?!", "", "&3&lOMNI SET BONUS", "&3&l* &3Acts as ANY Armor Set type/piece.");
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setInt("omniArmorSet", Integer.valueOf(1));
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        NBTTagCompoundBuilder display = wrapper.hasKey("display") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
        display.setInt("color", Integer.valueOf(16711680));
        wrapper.setNBTBase("display", display.get());
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(wrapper.build(), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"),
                EnchantmentAPI.getEnchantment("Drunk"),
                EnchantmentAPI.getEnchantment("Enlighted"),
                EnchantmentAPI.getEnchantment("Implants"),
                EnchantmentAPI.getEnchantment("Tank"),
                EnchantmentAPI.getEnchantment("Armored"),
                EnchantmentAPI.getEnchantment("Angelic"))));
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack item = ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&1&lOmni Chestplate", "", "&3&lOMNI SET BONUS", "&3&l* &3Acts as ANY Armor Set type/piece.");
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setInt("omniArmorSet", Integer.valueOf(1));
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        NBTTagCompoundBuilder display = wrapper.hasKey("display") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
        display.setInt("color", Integer.valueOf(16711680));
        wrapper.setNBTBase("display", display.get());
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(wrapper.build(), Arrays.asList(EnchantmentAPI.getEnchantment("Overload"),
                EnchantmentAPI.getEnchantment("Blood Lust"),
                EnchantmentAPI.getEnchantment("Enlighted"),
                EnchantmentAPI.getEnchantment("Armored"),
                EnchantmentAPI.getEnchantment("Tank"),
                EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Angelic"))));
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack item = ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&1&lOmni Leggings", "", "&3&lOMNI SET BONUS", "&3&l* &3Acts as ANY Armor Set type/piece.");
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setInt("omniArmorSet", Integer.valueOf(1));
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        NBTTagCompoundBuilder display = wrapper.hasKey("display") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
        display.setInt("color", Integer.valueOf(16711680));
        wrapper.setNBTBase("display", display.get());
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(wrapper.build(), Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"),
                EnchantmentAPI.getEnchantment("Clarity"),
                EnchantmentAPI.getEnchantment("Enlighted"),
                EnchantmentAPI.getEnchantment("Obsidianshield"),
                EnchantmentAPI.getEnchantment("Tank"),
                EnchantmentAPI.getEnchantment("Armored"),
                EnchantmentAPI.getEnchantment("Angelic"),
                EnchantmentAPI.getEnchantment("Cactus"))));
    }

    public ItemStack getBoots(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&1&lOmni Boots", "", "&3&lOMNI SET BONUS", "&3&l*&3 This armor works with ANY armor set."), Arrays.asList(EnchantmentAPI.getEnchantment("Gears"),
                EnchantmentAPI.getEnchantment("Enlighted"),
                EnchantmentAPI.getEnchantment("Deathbringer"),
                EnchantmentAPI.getEnchantment("Rocket Escape"),
                EnchantmentAPI.getEnchantment("Springs"),
                EnchantmentAPI.getEnchantment("Armored"),
                EnchantmentAPI.getEnchantment("Tank"))));
    }

    public List<String> getEquipDescription() {
        return Lists.newArrayList(ChatColor.RED + "" + ChatColor.BOLD + "PHANTOM SET BONUS: " + ChatColor.RED + "+25% DMG");
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
             if (isBlockedOutpostWorld((Entity)damaged)) return false;
             event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8D);
             return true;
          }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        return true;
    }

    public boolean onPlayerDamagedBonus(Player damaged, int applied, EntityDamageByEntityEvent event) {
        return true;
    }

    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        return true;
    }
}
