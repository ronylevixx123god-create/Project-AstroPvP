package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicCustomCreepers.CosmicCustomCreepers;
import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryRaidCreeperPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.RAID_CREEPER;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A mythical hybrid creeper,", CC.Gray + "a rare cross between the", CC.Gray + "Lucky and Tactical species.", "", CC.WhiteB + "Ability", CC.Gray + "Spawn a " + CC.DarkRed + "Raid Creeper", CC.Gray + "(Lucky + Tactical)", CC.Gray + "(100% Spawner Drop Rate)", "", CC.WhiteB + "Cooldown", CC.Gray + (10 - (level - 1)) + " Minutes"});
   }

   public int getExpForLevel(int level) {
      return 2500 * level;
   }

   public int getMaxLevel() {
      return 5;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds((long)(10 - (level - 1)));
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         petItem.setLastUsed(System.currentTimeMillis());
         Creeper c = CosmicCustomCreepers.get().getCreeperListener().processCustomCreeperPlaced(event, CreeperTypes.Raid);
         if (c == null) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Unable to spawn Raid Creeper!");
         } else {
            boolean xpGain = true;
            petItem.addExperience(player, 10);
            player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(10.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
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
