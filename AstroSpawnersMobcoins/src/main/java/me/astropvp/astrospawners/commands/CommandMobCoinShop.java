package me.astropvp.astrospawners.commands;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CosmicArmorSets.items.OmniCrystal;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.data.CrateData;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.pets.InventoryBansheePet;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import net.astropvp.core.items.RevokeScratchCard;
import net.astropvp.core.items.RogueScratchCard;
import net.astropvp.core.items.SlotbotScratchCard;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
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
        Inventory inventory = Bukkit.createInventory(null, 36, String.valueOf(CC.DarkGray) + "Mob Coin Shop");
        Map<String, MysteryChest> chests = CosmicMysteryChest.get().getChestManager().getMysteryChestMap();
        LinkedHashMap<ItemStack, Integer> values = new LinkedHashMap<>();
        PlayerDataManager manager = AstroSpawners.getInstance().getManager();
        PlayerData data = manager.getPlayerData((Player) sender);
        double balance = ((data == null) ? 0 : data.getCoins());


        if (sender.hasPermission("astrocore.apollo")) {
            if (chests.containsKey("PremadeMeta"))
                values.put(((MysteryChest)chests.get("PremadeMeta")).createClaimableItem(false), Integer.valueOf(4000));
            if (chests.containsKey("NewGenSkin"))
                values.put(((MysteryChest)chests.get("NewGenSkin")).createClaimableItem(false), Integer.valueOf(1200));
            if (chests.containsKey("Backpack"))
                values.put(((MysteryChest)chests.get("Backpack")).createClaimableItem(false), Integer.valueOf(800));
            if (chests.containsKey("AstroNote"))
                values.put(((MysteryChest)chests.get("AstroNote")).createClaimableItem(false), Integer.valueOf(1600));
            //Normal Items
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("rogue_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(2400));
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("slotbot_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(2400));
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("revoke_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(2400));
            values.put(CrateKeyItem.getItem(CrateType.APOLLO), Integer.valueOf(4800));
            values.put(CosmicSlotBotAPI.createSlotBotItem(), Integer.valueOf(6400));
            values.put(CrateKeyItem.getItem(CrateType.valueOf("WINTER")), Integer.valueOf(20000));
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("holy_water").createItem(((Player) sender).getPlayer(), 35), Integer.valueOf(1600));





        } else {
            if (chests.containsKey("PremadeMeta"))
                values.put(((MysteryChest)chests.get("PremadeMeta")).createClaimableItem(false), Integer.valueOf(5000));
            if (chests.containsKey("NewGenSkin"))
                values.put(((MysteryChest)chests.get("NewGenSkin")).createClaimableItem(false), Integer.valueOf(1500));
            if (chests.containsKey("Backpack"))
                values.put(((MysteryChest)chests.get("Backpack")).createClaimableItem(false), Integer.valueOf(1000));
            if (chests.containsKey("AstroNote"))
                values.put(((MysteryChest)chests.get("AstroNote")).createClaimableItem(false), Integer.valueOf(2000));

            //Normal items
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("rogue_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(3000));
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("slotbot_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(3000));
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("revoke_scratchcard").createItem(((Player) sender).getPlayer()), Integer.valueOf(3000));
            values.put(CrateKeyItem.getItem(CrateType.APOLLO), Integer.valueOf(6000));
            values.put(CosmicSlotBotAPI.createSlotBotItem(), Integer.valueOf(8000));
            values.put(CrateKeyItem.getItem(CrateType.valueOf("WINTER")), 25000);
            values.put(CosmicSkyCustomItems.get().getItemManager().getCustomItem("holy_water").createItem(((Player) sender).getPlayer(), 35), Integer.valueOf(2000));






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
            if (sender.hasPermission("astrocore.apollo")) {
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



        ItemStack coinsItem = new ItemBuilder(Material.DOUBLE_PLANT).name(CC.translate("&e&lMobcoins Shop")).addLoreLine(CC.translate("&7Welcome to the mobcoins shop."))
                .addLoreLine(CC.translate("&7Here you can spend all your mobcoins.")).addLoreLine("")
                .addLoreLine(CC.translate(CC.GreenB) + "* TOTAL: " + CC.White + NumberUtils.formatMoney(balance) + " Mob Coins").build();

        inventory.setItem(31, coinsItem);
        for (int i = 27; i<inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, getFillerItem());
            }
        }

        ((Player)sender).openInventory(inventory);
        return false;
    }

    public static ItemStack setAmount(ItemStack item, int amount) {
        item.setAmount(amount);
        return item;
    }

    public ItemStack getFillerItem() {
        ItemStack itemStack = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData())).name(CC.translate("")).build();
        return itemStack;
    }
}
