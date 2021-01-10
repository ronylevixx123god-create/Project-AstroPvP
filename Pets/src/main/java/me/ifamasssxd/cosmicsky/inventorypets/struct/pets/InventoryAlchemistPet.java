package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
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

public class InventoryAlchemistPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.ALCHEMIST;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A mystical little imp creature", CC.Gray + "that pulls random items out of", CC.Gray + "other dimensions and gives them", CC.Gray + "to its owner!", "", CC.WhiteB + "Ability", CC.Gray + "Spawns up to " + CC.Gold + this.getItemsToGive(level) + "x items" + CC.Gray + " from the", CC.Gold + "Alchemist /vkit (LVL: " + level + ")" + CC.Gray + " loot table", "", CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long)this.getCooldown(level))});
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.HOURS.toSeconds(12L);
   }

   public int getExpForLevel(int level) {
      return 1000 + level * 250;
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getItemsToGive(int level) {
      return 3 + (level >= 5 ? 1 : (level == 10 ? 2 : 0));
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (this.getEmptySlots(player) < 3) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Not enough space (3 slots) in inventory!");
         } else {
            petItem.setLastUsed(System.currentTimeMillis());
            boolean xp = true;
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
            petItem.addExperience(player, 100);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(100.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
            List loot = EvolutionKits.ALCHEMIST.getKit().getItems(player, Math.max(3, petItem.getLevel()));
            Collections.shuffle(loot);
            Iterator var13 = loot.subList(0, this.getItemsToGive(petItem.getLevel()) - 1).iterator();

            while(var13.hasNext()) {
               ItemStack i = (ItemStack)var13.next();
               player.sendMessage(CC.Gray + "+ " + i.getAmount() + "x " + i.getItemMeta().getDisplayName());
               if (player.getInventory().firstEmpty() != -1) {
                  player.getInventory().addItem(new ItemStack[]{i});
               } else {
                  player.getWorld().dropItem(player.getLocation(), i);
               }
            }

            ASound.ITEM_PICKUP.play(player, 1.1F);
            return true;
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   private int getEmptySlots(Player p) {
      int i = 0;
      ListIterator var3 = p.getInventory().iterator();

      while(true) {
         ItemStack is;
         do {
            if (!var3.hasNext()) {
               return i;
            }

            is = (ItemStack)var3.next();
         } while(is != null && is.getType() != Material.AIR);

         ++i;
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
