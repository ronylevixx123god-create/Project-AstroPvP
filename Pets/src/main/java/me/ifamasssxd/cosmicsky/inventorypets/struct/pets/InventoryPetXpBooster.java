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
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.buffs.XpPetBuff;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryPetXpBooster extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.XP_BOOSTER;
   }

   public void onEnable(CosmicSkyInventoryPets plugin) {
   }

   public List getPetDescription(int level) {
      double percent = this.getIncrease(level);
      return Lists.newArrayList(CC.Gray + "A mystical sprite that has", CC.Gray + "the unique ability to increase", CC.Gray + "the rate at which cosmonauts gain", CC.Gray + "experience and knowledge from combat.", "", CC.WhiteB + "Ability", CC.Gray + "Gain a " + NumberUtils.formatMoney(percent) + "x Vanilla XP", CC.Gray + "increase for " + TimeUtils.formatSeconds(this.getDuration(level)) + "!", "", CC.WhiteB + "Cooldown", CC.Gray + " 1 hour");
   }

   private double getIncrease(int level) {
      return Math.min(3.0D, 1.25D + (double)(level - 1) * 0.2D);
   }

   private int getDuration(int level) {
      return Math.min(1200, 300 + (level - 1) * 90);
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.HOURS.toSeconds(1L);
   }

   public int getExpForLevel(int level) {
      return level * 100;
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      event.setCancelled(true);
      long lastUse = petItem.getLastUsed();
      long cd = TimeUnit.SECONDS.toMillis(this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = cd - (System.currentTimeMillis() - lastUse);

      if (item.getAmount() > 1) {
         player.sendMessage(CC.translate("&cYou cannot use stacked pets!"));

         return false;
      }
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (CosmicSkyInventoryPets.get().getPetManager().getPetBuffExpiration().containsKey(player.getUniqueId())) {
            throw new PrefixedPetException("You must wait for your previous buff to expire!");
         } else {
            double increase = this.getIncrease(petItem.getLevel());
            petItem.setLastUsed(System.currentTimeMillis());
            boolean xp = true;
            int duration = this.getDuration(petItem.getLevel());
            CosmicSkyInventoryPets.get().getPetManager().getPetBuffExpiration().put(player.getUniqueId(), new XpPetBuff(increase, System.currentTimeMillis() + (long)(duration * 1000)));
            player.sendMessage(this.getOwner().getMsgWithPrefix(CC.Green) + CC.GreenB + " [" + CC.Gray + "XP Booster Pet (" + CC.Green + "+" + NumberUtils.formatMoney(increase) + CC.Gray + "x XP)" + CC.GreenB + "]");
            ASound.ORB_PICKUP.play(player, 1.0F, 3.0F);
            ASound.NOTE_PLING.play(player, 1.0F, 1.0F);
            ASound.VILLAGER_YES.play(player, 3.0F, 1.1F);
            return true;
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int)(timeTillCanUse / 1000L)));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
