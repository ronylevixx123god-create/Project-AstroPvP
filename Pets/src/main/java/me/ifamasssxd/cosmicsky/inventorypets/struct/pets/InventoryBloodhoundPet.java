package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.earth2me.essentials.utils.LocationUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CmdFocus;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.libs.Vector3D;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import me.ifamasssxd.cosmicsky.inventorypets.utils.PetUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryBloodhoundPet extends InventoryPet {
   static Map items = new HashMap();
   static Map armor = new HashMap();
   public int petLevel = 5;

   public InventoryPets getOwner() {
      return InventoryPets.BLOODHOUND;
   }

   public List getPetDescription(int level) {
      return Lists.newArrayList(CC.Gray + "Mark your target for assured destruction.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Affects target player with " + CC.DarkRedB + "[TAG]:", CC.DarkRed + "* " + CC.Gray + "Auto-/f focus target player ", CC.DarkRed + "* " + CC.Gray + "Anti-Bless Slowness I (10s) ", CC.DarkRed + "* " + CC.Gray + "Silence target player (10s) ", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to target players (10s) ", CC.DarkRed + "* " + CC.Gray + "Target is visable on /near for (30s) ", CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long) this.getCooldown(5)));
   }

   public int getCooldown(int level) {

      return 360;
   }
   
   public int getExpForLevel(int level) {
      return 1000 + 100 * level;
   }

   public int getMaxLevel() {
      return 6;
   }

   public boolean onPetItemInteract(final Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(5));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

      if (item.getAmount() > 1) {
         player.sendMessage(CC.translate("&cYou cannot use stacked pets!"));

         return false;
      }

     /* if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
         throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));*/

      if (PetUtils.isOnCooldown(player, petItem.getPetType()) && (!player.isOp() || !player.isSneaking()))
         throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU +
                 TimeUtils.formatSeconds(PetUtils.getSecondsOnCooldown(player, petItem.getPetType())));
      LivingEntity target = getTargetEntity(player, 10);
      if (target == null || !(target instanceof Player))
         throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "No target found!");
      final Player pTarget = (Player)target;
      if (!WorldGuardUtils.isPvPEnabled(pTarget.getLocation()))
         throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "No valid target found!");
      FPlayer fPlayer = FactionUtils.getFPlayer(player);
      Relation relation = FactionUtils.getFPlayer(pTarget).getRelationTo((RelationParticipator)fPlayer);
      if (relation == Relation.ALLY || relation == Relation.TRUCE || relation.isMember())
         throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "No valid target found!");
      //petItem.setLastUsed(System.currentTimeMillis());
      PetUtils.addPetCooldown(player, petItem.getPetType(), (getCooldown(5) * 1000));

      int xpGain = petItem.getLevel() * 5;
      player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.1F, 3.0F);
      final String name = player.getName();
      Faction faction = fPlayer.getFaction();

      if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.THANKSGIVING_HELMET_WITCH) && (!GoldenAdmirer.isAdmirerSilenced(pTarget))) {
         pTarget.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &4Banshee Pet &7[&f" + player.getName() + "&7] &e&l*"));
         player.sendMessage(CC.translate("&e&l* &f" + pTarget + " &7Blocked &4Bloodhound Pet &7[&e&lWitch Hat&7] &e&l*"));
         return false;
      }

      player.chat("/f t " + pTarget.getName());
      pTarget.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Boolean.valueOf(true)));
      pTarget.setMetadata("bloodhound_" + name, (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), null));
      pTarget.setMetadata("bloodhound", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), null));
      pTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (4 + 5) * 20, 0));
      FPlayer fPTarget = FactionUtils.getFPlayer(pTarget);
      if (fPTarget.hasFaction() && fPlayer.hasFaction())
         focusTargetPlayer(player, ((Player) target).getPlayer());
      int sum = 4 + 5;
      if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.REINDEER_ANTLERS) && Math.random() < 0.05D) {
         sum *= 2;
         player.sendMessage(String.valueOf(CC.RedB) + "* REINDEER ITEM SKIN " + CC.Gray + "(" + CC.White + "2x Pet Length" + CC.Gray + ")");
      }
      Bukkit.getScheduler().runTaskLater((Plugin)CosmicSkyInventoryPets.get(), new Runnable() {
         public void run() {
            if (!pTarget.isOnline())
               return;
            if (pTarget.hasMetadata("bloodhound_" + name))
               pTarget.removeMetadata("bloodhound_" + name, (Plugin)CosmicSkyInventoryPets.get());
            if (pTarget.hasMetadata("bloodhound"))
               pTarget.removeMetadata("bloodhound", (Plugin)CosmicSkyInventoryPets.get());
            if (pTarget.hasMetadata("noDefenseProcs"))
               pTarget.removeMetadata("noDefenseProcs", (Plugin)Enchantments.getInstance());
            player.sendMessage(CC.translate("&e&l(!) &eYour &4&l[Tag] &eon " + pTarget.getName() + "&e has &e&nEXPIRED!"));
         }
      },(sum * 20));
      return true;
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
      pTarget.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "** BLOODHOUND PET: (No Bless) for (" + ChatColor.GRAY + "10s" + ChatColor.RED + ") **");

      pTarget.sendMessage("");
      DamageIndicatorUtils.sendDamageIndicator(pTarget, CC.translate("&c&l* BLOODHOUND *"), "RKO");

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


   public LivingEntity getTargetEntity(Player player, int maxRangeInBlocks) {
      Location observerPos = player.getEyeLocation();
      Vector3D observerDir = new Vector3D(observerPos.getDirection());
      Vector3D observerStart = new Vector3D(observerPos);
      Vector3D observerEnd = observerStart.add(observerDir.multiply(maxRangeInBlocks));
      LivingEntity target = null;
      for (Entity possibleTarget : player.getNearbyEntities(maxRangeInBlocks, maxRangeInBlocks, maxRangeInBlocks)) {
         if (!(possibleTarget instanceof LivingEntity))
            continue;
         Vector3D targetPos = new Vector3D(possibleTarget.getLocation());
         Vector3D minimum = targetPos.add(-0.5D, 0.0D, -0.5D);
         Vector3D maximum = targetPos.add(0.5D, 1.67D, 0.5D);
         if (hasIntersection(observerStart, observerEnd, minimum, maximum))
            target = (LivingEntity)possibleTarget;
      }
      return target;
   }

   private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
      Vector3D d = p2.subtract(p1).multiply(0.5D);
      Vector3D e = max.subtract(min).multiply(0.5D);
      Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5D));
      Vector3D ad = d.abs();
      return (Math.abs(c.x) <= e.x + ad.x && Math.abs(c.y) <= e.y + ad.y && Math.abs(c.z) <= e.z + ad.z &&
              Math.abs(d.y * c.z - d.z * c.y) <= e.y * ad.z + e.z * ad.y &&
              Math.abs(d.z * c.x - d.x * c.z) <= e.z * ad.x + e.x * ad.z &&
              Math.abs(d.x * c.y - d.y * c.x) <= e.x * ad.y + e.y * ad.x);
   }

   public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
      return false;
   }
}
