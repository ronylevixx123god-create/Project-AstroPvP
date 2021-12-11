package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GoblinArmorSet extends ArmorSetInfo implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        LinkedList<CustomEnchantment> enchants = new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Spirits"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Smoke Bomb"), EnchantmentAPI.getEnchantment("Hardened")));
        if (Math.random() <= 0.1) {
            enchants.add(this.getEnchantment("Phoenix"));
        } else {
            enchants.add(this.getEnchantment("Marksman"));
        }
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&e&lGoblin Helmet", "&e&lGOBLIN SET BONUS", "&e&l* &eDeal 25% More Damage All Enemies", "&e&l* &eReceive 15% Less Damage", "&e&l* &e35% chance to negate Rogue Enchantment", "&e&l* &e25% chance to disable Enemies Necromancer Mask for 20s", "&e&l* &e35% chance to disable enemy Golden Admirer", "&e&l* &eImmune to skrrt pet", "&e&l* &eSuper Charged Goblin Ability"), enchants));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&e&lGoblin Chestplate", "&e&lGOBLIN SET BONUS", "&e&l* &eDeal 25% More Damage All Enemies", "&e&l* &eReceive 15% Less Damage", "&e&l* &e35% chance to negate Rogue Enchantment", "&e&l* &e25% chance to disable Enemies Necromancer Mask for 20s", "&e&l* &e35% chance to disable enemy Golden Admirer", "&e&l* &eImmune to skrrt pet", "&e&l* &eSuper Charged Goblin Ability"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Diminish"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Overload"), EnchantmentAPI.getEnchantment("Leadership"), EnchantmentAPI.getEnchantment("Blood Link"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Repair Guard"))));
        if (Math.random() <= 0.1) {
            this.addEnchantment("Enlighted", this.getMaxEnchantLevel("Enlighted"), item);
            this.addEnchantment("Divine Enlighted", ItemUtils.getRandomArmorSetEnchantLevel("Divine Enlighted"), item);
        } else {
            this.addEnchantment("Enlighted", this.getMaxEnchantLevel("Enlighted"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&e&lGoblin Leggings","&e&lGOBLIN SET BONUS", "&e&l* &eDeal 25% More Damage All Enemies", "&e&l* &eReceive 15% Less Damage", "&e&l* &e35% chance to negate Rogue Enchantment", "&e&l* &e25% chance to disable Enemies Necromancer Mask for 20s", "&e&l* &e35% chance to disable enemy Golden Admirer", "&e&l* &eImmune to skrrt pet", "&e&l* &eSuper Charged Goblin Ability"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Cactus"))));
        if (Math.random() <= 0.1) {
            this.addEnchantment("Nature Wrath", ItemUtils.getRandomArmorSetEnchantLevel("Nature Wrath"), item);
        } else {
            this.addEnchantment("Protection", ItemUtils.getRandomArmorSetEnchantLevel("Protection"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&e&lGoblin Boots", "&e&lGOBLIN SET BONUS", "&e&l* &eDeal 25% More Damage All Enemies", "&e&l* &eReceive 15% Less Damage", "&e&l* &e35% chance to negate Rogue Enchantment", "&e&l* &e25% chance to disable Enemies Necromancer Mask for 20s", "&e&l* &e35% chance to disable enemy Golden Admirer", "&e&l* &eImmune to skrrt pet", "&e&l* &eSuper Charged Goblin Ability"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"))));
        item = this.addEnchantment("Springs", 2, item);
        if (Math.random() <= 0.1) {
            item = this.addEnchantment("Deathbringer", this.getMaxEnchantLevel("Deathbringer"), item);
            item = this.addEnchantment("Planetary Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Planetary Deathbringer"), item);
        } else {
            item = this.addEnchantment("Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Deathbringer"), item);
            item = this.addEnchantment("Ghost", ItemUtils.getRandomArmorSetEnchantLevel("Ghost"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList("&e&lGOBLIN SET BONUS", CC.translate("&e&l* &eDeal 25% More Damage All Enemies"), CC.translate("&e&l* &eReceive 15% Less Damage"), CC.translate("&e&l* &e35% chance to negate Rogue Enchantment"), CC.translate("&e&l* &e25% chance to disable Enemies Necromancer Mask for 20s"), CC.translate("&e&l* &e35% chance to disable enemy Golden Admirer"), CC.translate("&e&l* &eImmune to skrrt pet"), CC.translate("&e&l* &eSuper Charged Goblin Ability"));
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.85);
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        this.modifyDamage(damaged, event, -4, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        this.modifyDamage(damager, event, 8, multiplier);
        if(event.getEntity() instanceof Player && !event.getEntity().hasMetadata("necro_blocked") && Math.random() < (5 * multiplier)) {
            damager.sendMessage(CC.translate("&6&l*&e&l*&6&l* &e&lGOBLIN ARMOR SET &7(Blocked " + event.getEntity().getName() + "'s Rogue) &6&l*&e&l*&6&l*"));
            event.getEntity().sendMessage(CC.translate("&2&l** NECROMANCER MASK BLOCKED &7(" + damager.getName() + "'s Goblin Armor Set) &2&l**"));
            event.getEntity().setMetadata("necro_blocked", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis()));
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CosmicArmorSetsPlugin.get_(), () -> event.getEntity().removeMetadata("necro_blocked", CosmicArmorSetsPlugin.get_()), 400L);
        }
        return false;
    }

    @Override
    public void onEquip(Player player) {
    }

    @Override
    public void onUnequip(Player player) {
    }


    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.25);
        return true;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        ItemStack item = new NBTWrapper(new ItemBuilder(Material.DIAMOND_AXE, CC.YellowB + "Goblin Axe", "", CC.YellowB + "GOBLIN WEAPON BONUS", CC.Yellow + "Deal +25% damage to all enemies at desert", CC.Yellow + "35% chance to negate stitches pet", CC.Yellow + "Ability to see feign death players in warzone", CC.Gray + "(" + CC.GrayI + "Requires all 4 goblin items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "GOBLIN_WEAPON").build();
        this.addEnchantment("Block", this.getMaxEnchantLevel("Block"), item);
        this.addEnchantment("Reflective Block", this.getMaxEnchantLevel("Reflective Block"), item);
        return item;
    }
}
