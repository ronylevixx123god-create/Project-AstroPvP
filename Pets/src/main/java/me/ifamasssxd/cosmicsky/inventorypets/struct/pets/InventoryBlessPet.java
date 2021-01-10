package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class InventoryBlessPet extends InventoryPet {
   public InventoryPets getOwner() {
      return InventoryPets.BLESS;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(new String[]{CC.Gray + "A fragment of " + CC.WhiteI + "light" + CC.Gray + " designed", CC.Gray + "to remove " + CC.DarkPurpleI + "evil spells within" + CC.Gray + ".", " ", CC.WhiteB + "Ability", CC.Gray + "Summon the power of Light with", CC.Gray + "[" + CC.BlueB + "Blessings Light" + CC.Gray + "] ability.", " ", CC.WhiteB + "Cooldown", CC.Gray + "5 Minutes"});
   }

   public int getExpForLevel(int level) {
      return 1000 * level;
   }

   public int getMaxLevel() {
      return 5;
   }

   public int getCooldown(int level) {
      return (int)TimeUnit.MINUTES.toSeconds(5L);
   }

   public double getBlessRadius(int level) {
      return 1.0D + (double)level * 5.0D;
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         boolean inWarzone = Board.getFactionAt(new FLocation(player.getLocation())).isWarZone();
         int xpGain = inWarzone ? 50 : 5;
         this.getFplayersInRadius(this.getBlessRadius(petItem.getLevel()), player);
         player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "PET: ** BLESSED **");
         petItem.addExperience(player, xpGain);
         petItem.setLastUsed(System.currentTimeMillis());
         player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)xpGain) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
         return true;
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
      return false;
   }

   public void getFplayersInRadius(double radius, Player player) {
      FPlayer fplayer = (FPlayer)FPlayers.i.get(player);
      Faction fac = fplayer.getFaction();
      if (player.getUniqueId().toString().equalsIgnoreCase("6e1815b6-209d-40c9-9fb9-0dee04e73f7e")) {
         player.sendMessage(CC.RedI + "HOLA FUCKBOI");
      }

      Iterator var6 = fac.getOnlinePlayers().iterator();

      while(var6.hasNext()) {
         Player member = (Player)var6.next();
         if (player.getLocation().distance(member.getPlayer().getLocation()) > radius) {
            return;
         }

         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.BLINDNESS);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.CONFUSION);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.HARM);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.POISON);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.WEAKNESS);
         PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.WITHER);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.BLINDNESS);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.CONFUSION);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.HARM);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.POISON);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.SLOW);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.SLOW_DIGGING);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.WEAKNESS);
         PlayerPotionEffects.untrackPotionEffect(member, PotionEffectType.WITHER);
      }

   }
}
