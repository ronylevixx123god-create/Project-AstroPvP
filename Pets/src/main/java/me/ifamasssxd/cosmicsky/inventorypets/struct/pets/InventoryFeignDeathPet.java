package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.mastery.MasteryFeignDeath;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryFeignDeathPet extends InventoryPet {
   public InventoryPets getOwner() {
      return null;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A ghastly pet that can", CC.Gray + "control death itself.", "", CC.WhiteB + "Ability", CC.Gray + "Fake your own death.", CC.Gray + "[" + CC.DarkRed + "Feign Death" + CC.Gray + "]", "", CC.WhiteB + "Cooldown", CC.Gray + " 10 Minutes"});
   }

   public int getExpForLevel(int level) {
      return level * 750;
   }

   public int getMaxLevel() {
      return 4;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(10L);
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (MasteryFeignDeath.deathCooldown.containsKey(player.getUniqueId())) {
            throw new PrefixedPetException("You must wait at least 10s in between feign death use!");
         } else {
            boolean inCombat = CombatLog.inCombat(player);
            boolean inWarzone = Board.getFactionAt(new FLocation(player.getLocation())).isWarZone();
            int xpGain = inWarzone ? 50 : 5;
            if (!inCombat) {
               xpGain = 1;
            }

            MasteryFeignDeath.vanishPlayer(player, petItem.getLevel());
            petItem.addExperience(player, xpGain);
            petItem.setLastUsed(System.currentTimeMillis());
            player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)xpGain) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
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
