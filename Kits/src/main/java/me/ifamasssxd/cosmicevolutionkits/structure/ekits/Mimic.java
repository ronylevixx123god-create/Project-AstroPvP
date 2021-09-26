/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.enchantments.util.TieredEnchantments
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicevolutionkits.structure.ekits;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mimic
extends EvolutionKit {
    private ChatColor mainColor = ChatColor.YELLOW;
    private List<Material> validDisplayMaterials = Lists.newArrayList(Material.BLAZE_POWDER, Material.POTION, Material.SKULL_ITEM, Material.GOLD_SWORD, Material.DIAMOND_AXE, Material.BONE);

    @Override
    public List<ItemStack> getItems(Player player, int level) {
        List<ItemStack> items = this.getItems(this.getRiggedRandomEvolutionKit(), player, level);
        if (Math.random() <= (double)level * 0.01) {
            items.add(TieredEnchantments.getMysteryEnchantmentBook((int)6));
        }
        return items;
    }

    public List<ItemStack> getItems(EvolutionKits kitToMimic, Player player, int level) {
        return kitToMimic.getKit().getItems(player, level);
    }

    public List<ItemStack> getRandomNotRigged(Player player, int level) {
        return this.getItems(this.getRandomEvolutionKit(), player, level);
    }

    public EvolutionKits getRiggedRandomEvolutionKit() {
        ArrayList kits = Lists.newArrayList();
        if (Math.random() <= 0.7) {
            kits.add(EvolutionKits.PHOENIX);
            kits.add(EvolutionKits.TROLL);
            kits.add(EvolutionKits.OGRE);
        } else {
            kits.add(EvolutionKits.SLAYER);
            kits.add(EvolutionKits.JUDGEMENT);
            kits.add(EvolutionKits.ALCHEMIST);
        }
        return (EvolutionKits)((Object)kits.get(this.random.nextInt(kits.size())));
    }

    public EvolutionKits getRandomEvolutionKit() {
        ArrayList kits = Lists.newArrayList(EvolutionKits.values());
        kits.remove((Object)EvolutionKits.MIMIC);
        return (EvolutionKits)((Object)kits.get(this.random.nextInt(kits.size())));
    }

    @Override
    public String getItemName() {
        return CC.GoldB + CC.Magic + "?" + (Object)this.mainColor + CC.Bold + " Mimic " + CC.GoldB + CC.Magic + "?" + (Object)this.mainColor + CC.Bold;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList("", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "Random Evolution Kit Items", (Object)this.mainColor + ChatColor.BOLD.toString() + "* " + (Object)this.mainColor + (Object)ChatColor.BOLD + "A chance of...", (Object)this.mainColor + ChatColor.BOLD.toString() + "   - " + (Object)this.mainColor + "Random Soul Enchantment Book", "", CC.Gray + "Receive items from a random /vkit", CC.Gray + "equivalent to this Kit Level!", (Object)ChatColor.GOLD + "* Summer 2018");
    }

    @Override
    public Material getDisplayMaterial() {
        return this.validDisplayMaterials.get(this.random.nextInt(this.validDisplayMaterials.size()));
    }

    @Override
    public int getInventorySlot() {
        return 8;
    }
}

