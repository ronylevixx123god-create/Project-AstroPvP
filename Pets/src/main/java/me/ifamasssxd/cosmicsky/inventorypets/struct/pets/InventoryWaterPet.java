package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryWaterPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.WATER;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A watery pet that is", CC.Gray + "capable of extracting water", CC.Gray + "from the air and condensing it.", "", CC.WhiteB + "Ability", CC.Gray + "Fills up to " + (level == this.getMaxLevel() ? "ALL" : this.getMaxFill(level) + "x"), CC.Gray + "empty buckets in your", CC.Gray + "inventory into water!", "", CC.WhiteB + "Cooldown", CC.Gray + " 5 Minutes"});
   }

   public int getMaxFill(int level) {
      return level == this.getMaxLevel() ? 1152 : level * 32;
   }

   public int getExpForLevel(int level) {
      return level * 500;
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(5L);
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (item.getAmount() > 1) {
         player.sendMessage(CC.translate("&cYou cannot use stacked pets!"));

         return false;
      }
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         int maxBuckets = this.getMaxFill(petItem.getLevel());
         petItem.setLastUsed(System.currentTimeMillis());
         int filled = 0;

         for(int i = 0; i < player.getInventory().getSize() && filled < maxBuckets; ++i) {
            ItemStack is = player.getInventory().getItem(i);
            if (is != null && is.getType() == Material.BUCKET && !is.hasItemMeta()) {
               int newFilled = filled + is.getAmount();
               if (newFilled > maxBuckets) {
                  int canFill = newFilled - maxBuckets;
                  int leftOver = is.getAmount() - canFill;
                  PlayerUtil.giveOrDropProtectedItem(player, new ItemStack(Material.BUCKET, leftOver), 60);
                  is.setType(Material.WATER_BUCKET);
                  is.setAmount(canFill);
                  filled += canFill;
               } else {
                  filled += is.getAmount();
                  is.setType(Material.WATER_BUCKET);
               }

               player.getInventory().setItem(i, is);
            }
         }

         if (filled > 0) {
            player.playSound(player.getLocation(), Sound.SPLASH, 1.1F, 1.0F);
            player.updateInventory();
         } else {
            petItem.setLastUsed(System.currentTimeMillis() - (delay - 10000L));
            petItem.getPetType().sendMsg(player, "No empty buckets in your inventory!");
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 3.0F, 0.7F);
         }

         return true;
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
