package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
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

public class InventorySkrrtPet extends InventoryPet {
   public int petLevel = 0;

   public InventoryPets getOwner() {
      return InventoryPets.SKRRT;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(CC.Gray + "Yeeeeeeeeeeet.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[Skrrt]:", CC.DarkRed + "* " + CC.Gray + "Freezes enemy players within 16x16 for " + this.freezeDuration(this.petLevel) + "s", CC.DarkRed + "* " + CC.Gray + "Disables /fly, combat tags enemy player within 16x16 ", CC.DarkRed + "* " + CC.Gray + "Apply Gears V for (" + this.freezeDuration(this.petLevel) + "s)", CC.DarkRed + "* " + CC.Gray + "Invulnerable for (" + this.freezeDuration(this.petLevel) + "s)", CC.DarkRed + "* " + CC.Gray + "Cannot attack for (" + this.freezeDuration(this.petLevel) + "s)", CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long)this.getCooldown(level)));
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
      } else {
         return level == 5 ? 360 : 600;
      }
   }

   private int freezeDuration(int level) {
      if (level == 1) {
         return 2;
      } else if (level == 2) {
         return 3;
      } else if (level == 3) {
         return 4;
      } else if (level == 4) {
         return 5;
      } else {
         return level == 5 ? 6 : 2;
      }
   }

   public int getExpForLevel(int level) {
      return 1000 * level;
   }

   public int getMaxLevel() {
      return 5;
   }

   public boolean onPetItemInteract(Player player, ItemStack itemStack, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long)this.getCooldown(petItem.getLevel()));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
      if (lastUse <= 0L || timeTillCanUse <= 0L || player.isOp() && player.isSneaking()) {
         if (player.hasMetadata("spectator")) {
            return true;
         } else if (player.hasMetadata("in_duel")) {
            return true;
         } else {
            Faction faction = FactionUtils.getFaction(player);
            if (faction != null && !faction.isNone()) {
               Location locationClicked = player.getLocation();
               if (!WorldGuardUtils.isPvPEnabled(locationClicked)) {
                  player.sendMessage(ChatColor.RED + "Combat is disabled in this region.");
                  return true;
               } else {
                  petItem.setLastUsed(System.currentTimeMillis());
                  this.petLevel = petItem.getLevel();
                  this.givePlayerGearFive(player);
                  this.skrrtAbilities(player);
                  boolean xpGain = true;
                  petItem.addExperience(player, 50);
                  player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
                  player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
                  player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(50.0D) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
                  return true;
               }
            } else {
               throw new PrefixedPetException("You must be in a faction to use this pet!");
            }
         }
      } else {
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((long)((int)(timeTillCanUse / 1000L))));
      }
   }

   private void givePlayerGearFive(final Player player) {
      EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 5);
      (new BukkitRunnable() {
         public void run() {
            EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 5);
         }
      }).runTaskLater(CosmicSkyInventoryPets.get(), 1L * (long)this.freezeDuration(this.petLevel) * 20L);
   }

   private void skrrtAbilities(final Player player) {
      Iterator var2 = player.getNearbyEntities(16.0D, 16.0D, 16.0D).iterator();

      while(true) {
         Player p;
         do {
            do {
               do {
                  Entity ent;
                  do {
                     if (!var2.hasNext()) {
                        return;
                     }

                     ent = (Entity)var2.next();
                  } while(!(ent instanceof Player));

                  p = (Player)ent;
               } while(EssentialsUtils.isVanished(p));
            } while(p.hasMetadata("NPC"));
         } while(p.hasMetadata("in_duel"));

         FPlayer castingPlayer = (FPlayer)FPlayers.i.get(player);
         FPlayer surroundingPlayer = (FPlayer)FPlayers.i.get(p);
         if (castingPlayer.getRelationTo(surroundingPlayer) == Relation.ENEMY || castingPlayer.getRelationTo(surroundingPlayer) == Relation.NEUTRAL) {
            p.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* SKRRT: (" + ChatColor.GRAY + player.getName() + ChatColor.DARK_RED + ")");
            player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* SKRRT: (" + ChatColor.GRAY + p.getName() + ChatColor.DARK_RED + ")");
            CombatLog.flagCombat(p);
            this.freezeThePlayer(p);
            player.setMetadata("frozenSkrrt", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
         }

         (new BukkitRunnable() {
            public void run() {
               player.removeMetadata("frozenSkrrt", CosmicSkyInventoryPets.get());
            }
         }).runTaskLater(CosmicSkyInventoryPets.get(), (long) this.freezeDuration(this.petLevel) * 20L);
      }
   }

   public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
      return false;
   }

   private void freezeThePlayer(final Player p) {
      p.setFlying(false);
      p.playSound(p.getLocation(), Sound.STEP_SNOW, 1.0F, 1.4F);
      p.setWalkSpeed(0.0F);
      p.removePotionEffect(PotionEffectType.JUMP);
      p.removePotionEffect(PotionEffectType.SLOW);
      p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int)(1L * (long)this.freezeDuration(this.petLevel) * 20L), 128));
      p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(1L * (long)this.freezeDuration(this.petLevel) * 20L), 128));
      p.setMetadata("frozenSkrrt", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
      WorldUtil.sendWorldEffect(p.getLocation().getBlock(), 57, 2001);
      (new BukkitRunnable() {
         public void run() {
            p.removeMetadata("frozenSkrrt", CosmicSkyInventoryPets.get());
            p.removePotionEffect(PotionEffectType.SLOW);
            p.removePotionEffect(PotionEffectType.JUMP);
            p.setWalkSpeed(0.2F);
            p.setFlying(true);
         }
      }).runTaskLater(CosmicSkyInventoryPets.get(), 1L * (long)this.freezeDuration(this.petLevel) * 20L);
   }
}
