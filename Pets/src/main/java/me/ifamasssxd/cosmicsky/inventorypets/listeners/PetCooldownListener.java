package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicsky.inventorypets.managers.InventoryPetManager;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;



import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PetCooldownListener implements Listener {


    @EventHandler
    public void openInventory(InventoryClickEvent e) {

            ItemStack[] contents = e.getWhoClicked().getInventory().getContents();
        Map<Integer, ItemStack> slotToSkullMap = IntStream.range(0, contents.length)
                .filter(slot -> contents[slot] != null)
                .boxed()
                .collect(Collectors.toMap(slot -> slot, slot -> contents[slot]));


        for (Map.Entry<Integer, ItemStack> itemStackEntry : slotToSkullMap.entrySet()) {

            NBTWrapper wrapper = new NBTWrapper(itemStackEntry.getValue());
            if (wrapper != null && wrapper.hasKey("cosmicData") && itemStackEntry.getValue().getType().name().startsWith("SKULL_")) {


                String petType = wrapper.getCompound("cosmicData").getString("petType");


                InventoryPet petItem = InventoryPets.valueOf(petType).getInternalClass();

                InventoryPetItem.constructItem(itemStackEntry.getValue(), wrapper, InventoryPetItem.class);

                long lastUse = wrapper.getCompound("cosmicData").getLong("lastUsed");
                long delay = TimeUnit.SECONDS.toMillis((long) petItem.getCooldown(5));
                long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

                if (!(lastUse > 0L && timeTillCanUse > 0L)) {
                    e.getWhoClicked().getInventory().setItem(itemStackEntry.getKey(), InventoryPetItem.constructItem(itemStackEntry.getValue(), wrapper, InventoryPetItem.class).build());
                    //InventoryPetItem.constructItem(itemStackEntry.getValue(), wrapper, InventoryPetItem.class).build()
                }
            }


        }
    }
}
