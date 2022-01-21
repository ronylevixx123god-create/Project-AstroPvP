package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.Relation;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class InventorySmitePet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.SMITE;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "An electrified skeleton totem", CC.Gray + "that contains powerful elemental", CC.Gray + "forces of nature.", "", CC.WhiteB + "Ability", CC.Gray + "Casts a powerful " + CC.White + "/smite", CC.Gray + "at the target entity. If affecting a player,", CC.Gray + "they will also be " + CC.Underline + "frozen in place for " + this.getFreezeTimeInSeconds(level) + "s", "", CC.WhiteB + "Cooldown", CC.Gray + "2 Minutes"});
   }

   public int getExpForLevel(int level) {
      return 1000 * level;
   }

   public int getMaxLevel() {
      return 5;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(2L);
   }

   public double getFreezeTimeInSeconds(int level) {
      return 1.0D + (double)level * 0.25D;
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
         if (player.hasMetadata("smitePetUse") && System.currentTimeMillis() - ((MetadataValue)player.getMetadata("smitePetUse").get(0)).asLong() < 1000L) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You cannot attempt to use Smite pets that often!");
         } else {
            player.setMetadata("smitePetUse", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
            petItem.setLastUsed(System.currentTimeMillis());
            LivingEntity target = this.getTargetEntityFromBlock(player, 48);
            if (target == null) {
               throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "No valid target found!");
            } else {
               int xpGain = 1;
               if (target instanceof Player) {
                  Player pTarget = (Player)target;
                  if (!WorldGuardUtils.isPvPEnabled(pTarget.getLocation())) {
                     throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "No valid target found!");
                  }

                  player.getWorld().strikeLightning(pTarget.getLocation());
                  pTarget.setWalkSpeed(0.0F);
                  pTarget.sendMessage(CC.RedB + "(!) " + CC.Red + "Frozen by " + player.getName() + "'s Smite Pet [" + this.getFreezeTimeInSeconds(petItem.getLevel()) + "s]!");
                  CosmicSkyInventoryPets.get().getServer().getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), () -> {
                     pTarget.setWalkSpeed(0.2F);
                  }, (long)(this.getFreezeTimeInSeconds(petItem.getLevel()) * 20.0D));
               } else {
                  player.getWorld().strikeLightning(target.getLocation());
               }

               return true;
            }
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }

   public LivingEntity getTargetEntityFromBlock(Player player, int maxRangeInBlocks) {
      FPlayer fp = (FPlayer)FPlayers.getInstance().getByPlayer(player);
      Block b = player.getTargetBlock((Set<Material>) null, maxRangeInBlocks);
      double targetDistance = 4.0D;
      LivingEntity target = null;
      Iterator var8 = player.getNearbyEntities((double)maxRangeInBlocks, (double)maxRangeInBlocks, (double)maxRangeInBlocks).iterator();

      while(true) {
         Entity possibleTarget;
         double d;
         FPlayer pTarget;
         do {
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        return target;
                     }

                     possibleTarget = (Entity)var8.next();
                  } while(!(possibleTarget instanceof LivingEntity));

                  d = possibleTarget.getLocation().distanceSquared(b.getLocation());
               } while(d >= 4.0D);
            } while(d >= targetDistance);

            if (!(possibleTarget instanceof Player)) {
               break;
            }

            pTarget = (FPlayer)FPlayers.getInstance().getByPlayer((Player)possibleTarget);
         } while(pTarget.getFaction().isNormal() && pTarget.getRelationTo(fp).isAtLeast(Relation.ALLY));

         target = (LivingEntity)possibleTarget;
         targetDistance = d;
      }
   }
}
