package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class InventoryStrongholdPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.STRONGHOLD;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A beast born from the chaos of", CC.Gray + "the contested strongholds of this planet.", "", CC.WhiteB + "Ability", CC.Gray + "Enable /sh sell for: " + CC.White + this.getDuration(level) + "s", "", CC.WhiteB + "Cooldown", CC.Gray + " 30 Minutes"});
   }

   public int getExpForLevel(int level) {
      return 500;
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(30L);
   }

   public int getDuration(int level) {
      return 10 + level * 5;
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (player.hasMetadata("strongholdPet") && ((MetadataValue)player.getMetadata("strongholdPet").get(0)).asLong() > System.currentTimeMillis()) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You already have an active /sh sell effect!");
         } else {
            player.setMetadata("strongholdPet", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis((long)this.getDuration(petItem.getLevel()))));
            boolean xpGain = true;
            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "PET: /sh sell enabled for: " + this.getDuration(petItem.getLevel()) + "s!");
            petItem.addExperience(player, 5);
            petItem.setLastUsed(System.currentTimeMillis());
            player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(5.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
            Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), () -> {
               player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "PET: /sh sell ability expired!");
            }, (long)(this.getDuration(petItem.getLevel()) * 20));
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
