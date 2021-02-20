package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class InventoryEnchanterPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.ENCHANTER;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A powerful, magical creature", CC.Gray + "skilled in the many forms of", CC.Gray + "alchemy and enchantment.", "", CC.WhiteB + "Ability", CC.Gray + "Increase the % success rate of", CC.Gray + "the next enchantment book you", CC.Gray + "attempt to apply by " + CC.White + level + "%", "", CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long)this.getCooldown(level))});
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.HOURS.toSeconds(1L);
   }

   public int getExpForLevel(int level) {
      return 250 + 1000 * level;
   }

   public int getMaxLevel() {
      return 10;
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
         petItem.setLastUsed(System.currentTimeMillis());
         if (player.hasMetadata("enchanterPet_Effect")) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You already have an active Enchanter Pet applied. Use an Enchantment Book before attempting to use another Enchanter Pet.");
         } else {
            boolean xp = true;
            player.setMetadata("enchanterPet_Effect", new FixedMetadataValue(CosmicSkyInventoryPets.get(), petItem.getLevel()));
            player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
            player.sendMessage(CC.GreenB + "PET: " + CC.Green + "The next Enchantment Book you use will have a +" + petItem.getLevel() + "% modifier applied to its success rate.");
            ASound.ITEM_PICKUP.play(player, 1.1F);
            return true;
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
