package me.ifamasssxd.cosmicsky.inventorypets.struct;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.List;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.managers.BlockModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryPet {
   protected BlockModifier blockModifier;

   public BlockModifier getBlockModifier() {
      return this.blockModifier;
   }

   public abstract InventoryPets getOwner();

   public abstract List<String> getPetDescription(int level);

   public void onPlayerJoin(Player player, InventoryPetItem item) {
   }

   public void onPlayerLeave(Player player, InventoryPetItem item) {
   }

   public void onEnable(CosmicSkyInventoryPets plugin) {
   }

   public NBTWrapper onItemCreate(NBTWrapper wrapper, InventoryPetItem instance) {
      return wrapper;
   }

   public NBTWrapper onItemLoad(NBTWrapper wrapper, InventoryPetItem instance) {
      return wrapper;
   }

   public int getExpForLevel(int level) {
      return level == 2 ? 500 : (level == 3 ? 1500 : 1500);
   }

   public int getMaxLevel() {
      return 3;
   }

   public abstract int getCooldown(int var1);

   public abstract boolean onPetItemInteract(Player var1, ItemStack var2, InventoryPetItem var3, PlayerInteractEvent var4) throws Exception;

   public abstract boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3);
}
