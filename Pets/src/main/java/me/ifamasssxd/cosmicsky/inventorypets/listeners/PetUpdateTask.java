package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicPersistentItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PetUpdateTask
        extends BukkitRunnable {
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            String oldBlob;
            NBTWrapper newWrapper;
            String newBlob;
            InventoryPetItem petItem;
            NBTWrapper wrapper;
            ItemStack item;
            int i;
            Inventory topInventory = p.getOpenInventory().getTopInventory();
            Inventory bottomInventory = p.getOpenInventory().getBottomInventory();
            for (i = 0; i < topInventory.getSize(); ++i) {
                item = topInventory.getItem(i);
                if (item == null || item.getType() != Material.SKULL_ITEM || !(wrapper = new NBTWrapper(item)).hasKey("cosmicType") || !wrapper.getString("cosmicType").equals("inventory_pet") || !wrapper.hasKey("SkullOwner") || (petItem = (InventoryPetItem)CosmicPersistentItem.constructItem(item, wrapper, InventoryPetItem.class)) == null || petItem.getPetType() == null || (newBlob = (newWrapper = new NBTWrapper(petItem.build())).getCompound("SkullOwner").get().getCompound("Properties").getList("textures", 10).get(0).getString("Value")).equals(oldBlob = wrapper.getCompound("SkullOwner").get().getCompound("Properties").getList("textures", 10).get(0).getString("Value"))) continue;
                topInventory.setItem(i, petItem.build());
                p.updateInventory();
            }
            for (i = 0; i < bottomInventory.getSize(); ++i) {
                item = bottomInventory.getItem(i);
                if (item == null || item.getType() != Material.SKULL_ITEM || !(wrapper = new NBTWrapper(item)).hasKey("cosmicType") || !wrapper.getString("cosmicType").equals("inventory_pet") || !wrapper.hasKey("SkullOwner") || (petItem = (InventoryPetItem)CosmicPersistentItem.constructItem(item, wrapper, InventoryPetItem.class)) == null || petItem.getPetType() == null || (newBlob = (newWrapper = new NBTWrapper(petItem.build())).getCompound("SkullOwner").get().getCompound("Properties").getList("textures", 10).get(0).getString("Value")).equals(oldBlob = wrapper.getCompound("SkullOwner").get().getCompound("Properties").getList("textures", 10).get(0).getString("Value"))) continue;
                bottomInventory.setItem(i, petItem.build());
                p.updateInventory();
            }
        }
    }
}
