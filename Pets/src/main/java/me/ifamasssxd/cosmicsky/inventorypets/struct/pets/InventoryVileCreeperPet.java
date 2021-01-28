package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.CosmicCustomTNT.CosmicCustomTNT;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InventoryVileCreeperPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.VILE_CREEPER;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(CC.Gray + "An extremely dangerous creeper,", CC.Gray + "species, famous for its vicious", CC.Gray + "modern warfare deployments.", "", CC.WhiteB + "Ability", CC.Gray + "Spawn a " + CC.DarkPurple + "Vile Creeper", CC.Gray + "Corrupts the area it explodes", CC.Gray + "in, applying [" + CC.Red + "Rot and Decay V" + CC.Gray + "]", CC.Gray + "for a 5 second duration.", "", CC.WhiteB + "Cooldown", CC.Gray + (10 - (level - 1)) + " Minutes");
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
         OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(player);
         if (outpostTier != OutpostTier.VANILLA && outpostTier != OutpostTier.TRAINEE && outpostTier != OutpostTier.HERO) {
            petItem.setLastUsed(System.currentTimeMillis());
            Creeper c = CosmicCustomTNT.get().getCreeperListener().processCustomCreeperPlaced(event, CreeperTypes.Vile);
            if (c == null) {
               throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Unable to spawn Vile Creeper!");
            } else {
               boolean xpGain = true;
               petItem.addExperience(player, 10);
               player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
               player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
               player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(10.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
               return true;
            }
         } else {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Cannot spawn Vile Creepers in Vanilla, Trainee, or Hero /outpost!");
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
