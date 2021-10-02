package me.astropvp.astrospawners.commands;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.pets.InventoryBansheePet;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandMobCoinShop implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Inventory inventory = Bukkit.createInventory(null, 18, String.valueOf(CC.DarkGray) + "Mob Coin Shop");
        Map<String, MysteryChest> chests = CosmicMysteryChest.get().getChestManager().getMysteryChestMap();
        LinkedHashMap<ItemStack, Integer> values = new LinkedHashMap<>();

        String rank = PermissionsAPI.getPermissionUser(((Player) sender).getPlayer().getUniqueId()).getGroup();

        if (rank.equals("apollo")) {
            if (chests.containsKey("OmniCrystal"))
                values.put(((MysteryChest)chests.get("OmniCrystal")).createClaimableItem(false), Integer.valueOf(200));
            if (chests.containsKey("QuadCrystal"))
                values.put(((MysteryChest)chests.get("QuadCrystal")).createClaimableItem(false), Integer.valueOf(200));
            if (chests.containsKey("HeroicPet"))
                values.put(((MysteryChest)chests.get("HeroicPet")).createClaimableItem(false), Integer.valueOf(200));
            if (chests.containsKey("AdminWeapon"))
                values.put(((MysteryChest)chests.get("AdminWeapon")).createClaimableItem(false), Integer.valueOf(320));
            if (chests.containsKey("Slotbot"))
                values.put(((MysteryChest)chests.get("Slotbot")).createClaimableItem(false), Integer.valueOf(800));
            if (chests.containsKey("SkinBundle"))
                values.put(((MysteryChest)chests.get("SkinBundle")).createClaimableItem(false), Integer.valueOf(160));
            if (chests.containsKey("ArmorCache"))
                values.put(((MysteryChest)chests.get("ArmorCache")).createClaimableItem(false), Integer.valueOf(200));
            if (chests.containsKey("HeroicProtVI"))
                values.put(((MysteryChest)chests.get("HeroicProtVI")).createClaimableItem(false), Integer.valueOf(320));
            if (chests.containsKey("Backpack"))
                values.put(((MysteryChest)chests.get("Backpack")).createClaimableItem(false), Integer.valueOf(480));
            if (chests.containsKey("GrinderCache"))
                values.put(((MysteryChest)chests.get("GrinderCache")).createClaimableItem(false), Integer.valueOf(400));
            if (chests.containsKey("CustomBlock"))
                values.put(((MysteryChest)chests.get("CustomBlock")).createClaimableItem(false), Integer.valueOf(800));
            if (chests.containsKey("PremadeOmni"))
                values.put(((MysteryChest)chests.get("PremadeOmni")).createClaimableItem(false), Integer.valueOf(2000));
        } else {
            if (chests.containsKey("OmniCrystal"))
                values.put(((MysteryChest)chests.get("OmniCrystal")).createClaimableItem(false), Integer.valueOf(250));
            if (chests.containsKey("QuadCrystal"))
                values.put(((MysteryChest)chests.get("QuadCrystal")).createClaimableItem(false), Integer.valueOf(250));
            if (chests.containsKey("HeroicPet"))
                values.put(((MysteryChest)chests.get("HeroicPet")).createClaimableItem(false), Integer.valueOf(250));
            if (chests.containsKey("AdminWeapon"))
                values.put(((MysteryChest)chests.get("AdminWeapon")).createClaimableItem(false), Integer.valueOf(400));
            if (chests.containsKey("Slotbot"))
                values.put(((MysteryChest)chests.get("Slotbot")).createClaimableItem(false), Integer.valueOf(1000));
            if (chests.containsKey("SkinBundle"))
                values.put(((MysteryChest)chests.get("SkinBundle")).createClaimableItem(false), Integer.valueOf(200));
            if (chests.containsKey("ArmorCache"))
                values.put(((MysteryChest)chests.get("ArmorCache")).createClaimableItem(false), Integer.valueOf(250));
            if (chests.containsKey("HeroicProtVI"))
                values.put(((MysteryChest)chests.get("HeroicProtVI")).createClaimableItem(false), Integer.valueOf(400));
            if (chests.containsKey("Backpack"))
                values.put(((MysteryChest)chests.get("Backpack")).createClaimableItem(false), Integer.valueOf(600));
            if (chests.containsKey("GrinderCache"))
                values.put(((MysteryChest)chests.get("GrinderCache")).createClaimableItem(false), Integer.valueOf(500));
            if (chests.containsKey("CustomBlock"))
                values.put(((MysteryChest)chests.get("CustomBlock")).createClaimableItem(false), Integer.valueOf(1000));
            if (chests.containsKey("PremadeOmni"))
                values.put(((MysteryChest)chests.get("PremadeOmni")).createClaimableItem(false), Integer.valueOf(2500));
        }



        //LOOT TABLE

        //


        int x = 0;
        for (Map.Entry<ItemStack, Integer> entry : values.entrySet()) {
            ItemStack item = entry.getKey();
            ItemMeta meta = item.getItemMeta();
            int price = ((Integer)entry.getValue()).intValue();
            List<String> lore = meta.hasLore() ? meta.getLore() : Lists.newArrayList();
            lore.add("");
            if (rank.equals("apollo") || rank.equals("helper") || rank.equals("mod") || rank.equals("banteam") || rank.equals("admin")) {
                lore.add(String.valueOf(CC.AquaB) + "* PRICE (Apollo Bonus): " + CC.White + NumberUtils.formatMoney(price) + " Mob Coins");
            } else {
                lore.add(String.valueOf(CC.GreenB) + "* PRICE: " + CC.White + NumberUtils.formatMoney(price) + " Mob Coins");

            }
            lore.add("");
            meta.setLore(lore);
            item.setItemMeta(meta);
            NBTWrapper wrapper = new NBTWrapper(item);
            NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder();
            builder.setInt("cost", Integer.valueOf(price));
            item = wrapper.setNBTBase("shopData", (NBTBase)builder.get()).build();
            inventory.setItem(x, item);
            x++;
        }

        ((Player)sender).openInventory(inventory);
        return false;
    }

    public static ItemStack setAmount(ItemStack item, int amount) {
        item.setAmount(amount);
        return item;
    }
}
