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
import org.arkhamnetwork.Arkkit.patches.FatBuckets;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryLavaPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.LAVA;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A fiery pet that is", CC.Gray + "capable of conjuring lava", CC.Gray + "and giving it to its owner.", "", CC.WhiteB + "Ability", CC.Gray + "Fills up to " + (level == this.getMaxLevel() ? "ALL" : this.getMaxFill(level) + "x"), CC.Gray + "empty buckets in your", CC.Gray + "inventory into lava!", CC.WhiteB + "      +", CC.Gray + "Adds " + this.getFatBucketFill(level) + "x Fat Bucket", CC.Gray + "charges to all Fat Buckets!", "", CC.WhiteB + "Cooldown", CC.Gray + " 5 Minutes"});
   }

   public int getMaxFill(int level) {
      return level == this.getMaxLevel() ? 1152 : level * 32;
   }

   public int getFatBucketFill(int level) {
      return level == this.getMaxLevel() ? 256 : level * 25;
   }

   public int getExpForLevel(int level) {
      return level * 1000;
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
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         int maxBuckets = this.getMaxFill(petItem.getLevel());
         petItem.setLastUsed(System.currentTimeMillis());
         int filled = 0;
         int filledXp = 0;

         for(int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack is = player.getInventory().getItem(i);
            int fatBucketUses;
            int dif;
            int amount;
            if (is != null && filled < maxBuckets && is.getType() == Material.BUCKET && !is.hasItemMeta()) {
               fatBucketUses = filled + is.getAmount();
               if (fatBucketUses > maxBuckets) {
                  dif = fatBucketUses - maxBuckets;
                  amount = is.getAmount() - dif;
                  PlayerUtil.giveOrDropProtectedItem(player, new ItemStack(Material.BUCKET, amount), 60);
                  is.setType(Material.LAVA_BUCKET);
                  is.setAmount(dif);
                  filled += dif;
               } else {
                  filled += is.getAmount();
                  is.setType(Material.LAVA_BUCKET);
               }

               player.getInventory().setItem(i, is);
            }

            if (FatBuckets.isFatBucket(is)) {
               fatBucketUses = FatBuckets.getFatBucketUses(is);
               if (fatBucketUses < 256) {
                  dif = Math.min(this.getFatBucketFill(petItem.getLevel()), 256 - fatBucketUses);
                  amount = is.getAmount();
                  ItemStack newBucket = FatBuckets.setFatBucketUses(is, fatBucketUses + dif);
                  newBucket.setAmount(amount);
                  player.getInventory().setItem(i, newBucket);
                  filledXp += dif * amount;
               }
            }
         }

         filledXp += filled;
         if (filledXp > 0) {
            petItem.addExperience(player, filledXp);
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
            player.updateInventory();
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)filledXp) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
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
