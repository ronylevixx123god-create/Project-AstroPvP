package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CmdFocus;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryBloodhoundPet extends InventoryPet {
   static Map items = new HashMap();
   static Map armor = new HashMap();
   public int petLevel = 0;

   public InventoryPets getOwner() {
      return InventoryPets.BLOODHOUND;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(CC.Gray + "Mark your target for assured destruction.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Affects target player with " + CC.DarkRedB + "[TAG]:", CC.DarkRed + "* " + CC.Gray + "Auto-/f focus target player ", CC.DarkRed + "* " + CC.Gray + "Anti-Bless Slowness I (10s) ", CC.DarkRed + "* " + CC.Gray + "Silence target player (10s) ", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to target players (10s) ", CC.DarkRed + "* " + CC.Gray + "Target is visable on /near for (30s) ", CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long) this.getCooldown(level)));
   }

   public int getCooldown(int level) {
      if (level == 1) {
         return 600;
      } else if (level == 2) {
         return 540;
      } else if (level == 3) {
         return 480;
      } else if (level == 4) {
         return 420;
      } else if (level == 5) {
         return 360;
      } else {
         return level == 6 ? 300 : 600;
      }
   }
   
   public int getExpForLevel(int level) {
      return 1000 + 100 * level;
   }

   public int getMaxLevel() {
      return 6;
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse > 0L && timeTillCanUse > 0L || (!player.isOp() && !player.isSneaking())) {

         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      } else if (player.hasMetadata("in_duel")) {
         return true;
      } else {
         Location locationClicked = player.getLocation();
         if (!player.hasMetadata("spectator") && WorldGuardUtils.isPvPEnabled(locationClicked)) {
            this.petLevel = petItem.getLevel();
            petItem.setLastUsed(System.currentTimeMillis());
            boolean xpGain = true;
            this.StartBoodHoundPet(player, (Player) player.getNearbyEntities(5, 5, 5).get(0));
            petItem.addExperience(player, 50);
            player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(50.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
            return true;

         } else {
            return true;

         }
      }
   }



   private void StartBoodHoundPet(Player player, final Player pTarget) {
      pTarget.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* BLOODHOUND PET: (" + ChatColor.GRAY + player.getName() + ChatColor.DARK_RED + ")");
      player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* BLOODHOUND PET: (" + ChatColor.GRAY + pTarget.getName() + ChatColor.DARK_RED + ")");
      pTarget.setMetadata("noDefenseProcs", new FixedMetadataValue(Enchantments.getInstance(), true));
      pTarget.setMetadata("bloodHoundNoBless", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
      pTarget.removePotionEffect(PotionEffectType.SLOW);
      pTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
      this.removeNearMeta(player, pTarget);
      pTarget.sendMessage("");
      pTarget.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "** BLOODHOUND PET: (No Bless) for (" + ChatColor.GRAY + "10s" + ChatColor.RED + "§l) **");
      pTarget.sendMessage("");
      (new BukkitRunnable() {
         public void run() {
            pTarget.removeMetadata("noDefenseProcs", Enchantments.getInstance());
            pTarget.removeMetadata("bloodHoundNoBless", CosmicSkyInventoryPets.get());
         }
      }).runTaskLater(CosmicSkyInventoryPets.get(), 200L);
   }

   private void removeNearMeta(Player player, final Player pTarget) {
      if (pTarget.hasMetadata("heroicGhostEnchantment")) {
         pTarget.setMetadata("heroicGhostEnchantmentTemp", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
         pTarget.removeMetadata("heroicGhostEnchantment", Enchantments.getInstance());
      }

      if (pTarget.hasMetadata("ghostEnchantment")) {
         pTarget.setMetadata("ghostEnchantmentTemp", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
         pTarget.removeMetadata("ghostEnchantment", Enchantments.getInstance());
      }

      (new BukkitRunnable() {
         public void run() {
            InventoryBloodhoundPet.this.saveAndRemoveBoots(pTarget);
         }
      }).runTaskLater(CosmicSkyInventoryPets.get(), 600L);
   }

   private void saveAndRemoveBoots(Player player) {
      UUID uuid = player.getUniqueId();
      ItemStack[] armcont = player.getInventory().getArmorContents();
      armor.put(uuid, armcont);
      player.getInventory().setBoots((ItemStack)null);
      player.updateInventory();
      this.giveBootsBack(player);
   }

   private void giveBootsBack(Player player) {
      UUID uuid = player.getUniqueId();
      ItemStack[] armorContents = (ItemStack[])armor.get(uuid);
      if (armorContents != null) {
         player.getInventory().setBoots(armorContents[0]);
         if (player.hasMetadata("heroicGhostEnchantmentTemp")) {
            EnchantmentAPI.getEnchantment("Ghostly Ghost").applyEquipEffect(player, 3);
         }

         if (player.hasMetadata("ghostEnchantmentTemp")) {
            EnchantmentAPI.getEnchantment("Ghost").applyEquipEffect(player, 3);
         }

         player.updateInventory();
      }

   }

   private void focusTargetPlayer(Player player, Player pTarget) {
      if (pTarget instanceof Player) {
         FPlayer fPlayer = (FPlayer)FPlayers.i.get(player);
         Faction myFaction = fPlayer.getFaction();
         FPlayer targetPlayer = (FPlayer)FPlayers.i.get(pTarget);
         if (myFaction.isNormal()) {
            CmdFocus.focusPlayer(fPlayer, myFaction, targetPlayer, false);
         }
      }

   }

   public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
      return false;
   }
}
