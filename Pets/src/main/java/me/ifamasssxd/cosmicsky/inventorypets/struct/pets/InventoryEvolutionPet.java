package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicevolutionkits.structure.KitPlayer;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NoPetException;
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

public class InventoryEvolutionPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.EVOLUTION;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "One of the more helpful members", CC.Gray + "of the mystical Mimic race,", CC.Gray + "this object has the power to", CC.Gray + "grant its owner extreme power!", "", CC.WhiteB + "Ability", CC.Gray + "Increases level of lowest /vkit by 1,", CC.Gray + "and a " + level + "% chance to double level up.", "", CC.WhiteB + "Cooldown", CC.Gray + " 48 Hours"});
   }

   public int getExpForLevel(int level) {
      return 1000;
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.HOURS.toSeconds(48L);
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (!player.hasMetadata("inventoryPet_vkit") || player.isOp() && player.isSneaking()) {
            try {
               this.callUsage(player, petItem, false);

               try {
                  if (Math.random() <= 0.01D * (double)petItem.getLevel()) {
                     this.callUsage(player, petItem, true);
                  }
               } catch (CosmicException var12) {
                  player.sendMessage(var12.getMessage());
                  return true;
               }
            } catch (CosmicException var13) {
               petItem.setLastUsed(System.currentTimeMillis() - (delay - 10000L));
               petItem.getPetType().sendMsg(player, var13.getMessage());
               player.playSound(player.getLocation(), Sound.VILLAGER_NO, 3.0F, 0.7F);
            }

            return true;
         } else {
            throw new NonPrefixedPetException(CC.RedB + "(!) " + CC.Red + "You can only use 1 /vkit pet per account, per day.");
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public void callUsage(Player player, InventoryPetItem petItem, boolean second) throws CosmicException {
      if (!second) {
         player.setMetadata("inventoryPet_vkit", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
         petItem.setLastUsed(System.currentTimeMillis());
      }

      KitPlayer ekitPlayer = (KitPlayer)KitPlayer.kitPlayerMap.get(player.getUniqueId());
      if (ekitPlayer == null) {
         throw new NoPetException("Invalid player data!");
      } else {
         EvolutionKits lowestKit = null;
         int lowestLevel = -1;
         EvolutionKits[] var7 = EvolutionKits.values();
         int var8 = var7.length;
         EvolutionKits[] var9 = var7;
         int var10 = var7.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            EvolutionKits kit = var9[var11];
            int level = ekitPlayer.getLevel(kit);
            if (level > 0 && ekitPlayer.hasAccess(player, kit) && (lowestLevel == -1 || level < lowestLevel) && level < this.getMaxLevel()) {
               lowestKit = kit;
               lowestLevel = level;
            }
         }

         if (lowestKit != null && ekitPlayer.hasAccess(player, lowestKit)) {
            int finalLowestLevel = lowestLevel;
            List available = (List)Arrays.stream(EvolutionKits.values()).filter((f) -> {
               return ekitPlayer.getLevel(f) == finalLowestLevel && ekitPlayer.hasAccess(player, f);
            }).collect(Collectors.toList());
            if (!available.isEmpty()) {
               Collections.shuffle(available);
               lowestKit = (EvolutionKits)available.get(0);
               ekitPlayer.levelUp(player, lowestLevel + 1, lowestKit, "Evolution Pet");
               ekitPlayer.save(lowestKit);
               player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
               if (!second) {
                  int xp = 500;
                  petItem.addExperience(player, xp);
                  player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)xp) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
                  petItem.getPetType().sendMsg(player, CC.Green, CC.Green + "\"" + lowestKit.getName() + CC.Green + "\" leveled to Level " + CC.GreenU + ekitPlayer.getLevel(lowestKit) + CC.Green + "!");
               }

            } else {
               throw new NoPetException("No /vkit found to level up!");
            }
         } else {
            throw new NoPetException("No /vkit found to level up!");
         }
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
