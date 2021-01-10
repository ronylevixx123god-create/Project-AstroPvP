package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.util.TimeUtil;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.buffs.TeslaPetBuff;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryPetTesla extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.TESLA;
   }

   public void onEnable(CosmicSkyInventoryPets plugin) {
   }

   public List getPetDescription(int level) {
      int duration = this.getDuration(level);
      return Lists.newArrayList(new String[]{CC.Gray + "A legendary creature of myth", CC.Gray + "that acts as a temporarily", CC.Gray + "infinite source of player souls.", "", CC.WhiteB + "Ability", CC.Gray + "Remove all soul costs from", CC.Gray + "any action for " + TimeUtils.formatSeconds((long)duration), "", CC.WhiteB + "Cooldown", CC.Gray + " 5 minutes"});
   }

   private int getDuration(int level) {
      return (int)Math.min(30.0D, 5.0D + (double)(level - 1) * 2.5D);
   }

   public int getMaxLevel() {
      return 10;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(5L);
   }

   public int getExpForLevel(int level) {
      return (int)((double)level * 1000.0D * 1.5D);
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      event.setCancelled(true);
      long lastUse = petItem.getLastUsed();
      long cd = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = cd - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (CosmicSkyInventoryPets.get().getPetManager().getTeslaBuffs().containsKey(player.getUniqueId())) {
            throw new PrefixedPetException("You must wait for your previous buff to expire!");
         } else {
            int duration = this.getDuration(petItem.getLevel());
            petItem.setLastUsed(System.currentTimeMillis());
            boolean inCombat = CombatLog.inCombat(player);
            boolean inWarzone = Board.getFactionAt(new FLocation(player.getLocation())).isWarZone();
            int xp = inCombat && inWarzone ? 10 : 4;
            petItem.addExperience(player, xp);
            CosmicSkyInventoryPets.get().getPetManager().getTeslaBuffs().put(player.getUniqueId(), new TeslaPetBuff(1.0D, System.currentTimeMillis() + (long)(duration * 1000)));
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)xp) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
            player.sendMessage(this.getOwner().getMsgWithPrefix(CC.Green) + CC.GreenB + " [" + CC.Gray + "Tesla Pet Boost (" + CC.Green + "No Soul Costs" + CC.Gray + ")" + CC.GreenB + "]");
            ASound.ORB_PICKUP.play(player, 1.0F, 3.0F);
            ASound.NOTE_PLING.play(player, 1.0F, 1.0F);
            ASound.VILLAGER_YES.play(player, 3.0F, 1.1F);
            return true;
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtil.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }
}
