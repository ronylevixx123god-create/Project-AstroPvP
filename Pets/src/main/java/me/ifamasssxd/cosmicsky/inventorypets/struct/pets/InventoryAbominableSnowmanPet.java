package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.perms.Relation;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryAbominableSnowmanPet extends InventoryPet {
   int petLevel = 3;

   public InventoryPets getOwner() {
      return InventoryPets.ABOMINABLE_SNOWMANPET;
   }

   public List<String> getPetDescription(int level) {
      return Lists.newArrayList(CC.Gray + "An enormous, monstrous spirit", String.valueOf(CC.Gray) + "well trained in illusionist magic.", (new StringBuilder(String.valueOf(CC.Gray))).toString(), String.valueOf(CC.WhiteB) + "Ability", "[" + CC.DarkRed + "Polymorph" + CC.White + "] " + CC.Gray + "target player, transforming", String.valueOf(CC.Gray) + "them into harmless, immoble snowman for: " + freezeDuration(this.petLevel) + "s", String.valueOf(CC.Gray) + "Any dammage dealt to them will break the spell", (new StringBuilder(String.valueOf(CC.Gray))).toString(),"", String.valueOf(CC.WhiteB) + "Cooldown", String.valueOf(CC.Gray) + " " + TimeUtils.formatSeconds(getCooldown(level)));
   }

   public int getCooldown(int level) {
         return 1200;
   }

   public int getMaxLevel() {
      return 3;
   }

   public int getExpForLevel(int level) {
      return 1700 * level;
   }

   public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
      long lastUse = petItem.getLastUsed();
      long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(5));
      long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);


      if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
         throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));


      Faction faction = FactionUtils.getFaction(player);
      if (player.hasMetadata("spectator"))
         return true;
      if (player.hasMetadata("in_duel"))
         return true;
      if (faction == null || faction.isNone())
         throw new PrefixedPetException("You must be in a faction to use this pet!");

      double closest = Double.MAX_VALUE;
      Entity closestp = null;
      for (Entity i : player.getNearbyEntities(3.0D, 3.0D, 3.0D)) {
         if (!(i instanceof Player))
            continue;
         Player p = (Player)i;
         if ( p.getGameMode().equals(GameMode.CREATIVE))
            continue;
         /*if (SnowmanCooldown.isOnSnowCooldown(p)) {
            player.sendMessage(CC.translate("&cThis player is still on snowman cooldown, this lasts 120s."));
            continue;
         }*/
         if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.SEXY_HELMET)) {
            p.sendMessage(CC.translate("&c&lBLOCKED - SNOWMAN"));
            continue;
         }
         if (p.hasMetadata("NPC"))
            continue;
         if (p.hasMetadata("in_duel"))
            return true;
         double dist = i.getLocation().distance(event.getPlayer().getLocation());
         if (closest != Double.MAX_VALUE && dist >= closest)
            continue;
         closest = dist;
         closestp = i;
      }
      if (closestp == null || !(closestp instanceof Player)) {
         player.sendMessage(String.valueOf(CC.Red) + "PET: No targets found.");
         return true;
      }
      Player p2 = (Player)closestp;
      if (p2.hasMetadata("frozenSnowman")) {
         p2.sendMessage(String.valueOf(CC.Red) + "Player is already a Snowman! ");
         return true;
      }
      FPlayer castingPlayer = (FPlayer)FPlayers.getInstance().getByPlayer(player);
      FPlayer surroundingPlayer = (FPlayer)FPlayers.getInstance().getByPlayer(p2);
      if (castingPlayer.getRelationTo((RelationParticipator)surroundingPlayer) == Relation.ENEMY || castingPlayer.getRelationTo((RelationParticipator)surroundingPlayer) == Relation.NEUTRAL) {
         petItem.setLastUsed(System.currentTimeMillis());
         freezeThePlayer(p2, player);
         player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
         player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
         return true;
      }
      return true;
   }

   private void freezeThePlayer(final Player p, Player caster) {
      //SnowmanCooldown.enterSnowCooldown(p);
      p.setFlying(false);
      MobDisguise snowman = new MobDisguise(DisguiseType.SNOWMAN);
      p.playSound(p.getLocation(), Sound.STEP_SNOW, 1.0F, 1.4F);
      DisguiseAPI.disguiseEntity(p, snowman);
      p.setWalkSpeed(0.0F);
      p.removePotionEffect(PotionEffectType.JUMP);
      p.removePotionEffect(PotionEffectType.SLOW);
      p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int)(1L * freezeDuration(this.petLevel) * 20L), 128));
      p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(1L * freezeDuration(this.petLevel) * 20L), 128));
      p.setMetadata("frozenSnowman", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Long.valueOf(System.currentTimeMillis())));

      DamageIndicatorUtils.sendDamageIndicator(p, CC.translate("&e&l* SNOWMAN PET *"), "RKO");

      p.sendMessage(String.valueOf(CC.YellowB) + "POLYMORPH'D [" + CC.Gray + freezeDuration(this.petLevel) + "s" + CC.YellowB + "] " + CC.YellowB + "[" + CC.Gray + caster.getName() + CC.YellowB + "]");
      caster.sendMessage(CC.YellowB + "POLYMORPH'D [" + CC.Gray + freezeDuration(this.petLevel) + "s" + CC.YellowB + "] " + CC.YellowB + "[" + CC.Gray + p.getName() + CC.YellowB + "]");
      (new BukkitRunnable() {
         public void run() {
            if (p.hasMetadata("cancelrunnable")) {
               p.removeMetadata("cancelrunnable", (Plugin)CosmicSkyInventoryPets.get());
            } else {
               InventoryAbominableSnowmanPet.this.removeDisguise(p);
            }
         }
      }).runTaskLater((Plugin)CosmicSkyInventoryPets.get(), 1L * freezeDuration(this.petLevel) * 20L);
   }

   private int freezeDuration(int level) {
      if (level == 1)
         return 10;
      if (level == 2)
         return 10;
      if (level == 3)
         return 10;
      return 10;
   }

   private void removeDisguise(Player player) {
      player.removeMetadata("frozenSnowman", (Plugin)CosmicSkyInventoryPets.get());
      player.removeMetadata("cancelrunnable", (Plugin)CosmicSkyInventoryPets.get());
      player.removePotionEffect(PotionEffectType.SLOW);
      player.removePotionEffect(PotionEffectType.JUMP);
      player.setWalkSpeed(0.2F);
      DisguiseAPI.undisguiseToAll((Entity)player);
      player.setFlying(true);
      player.sendMessage(String.valueOf(CC.Red) + "You are no longer a frozen Snowman!");
   }

   public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
      return false;
   }

   public static int getDistance(Player e) {
      Location loc = e.getLocation().clone();
      double y = loc.getBlockY();
      int distance = 0;
      double i;
      for (i = y; i >= 0.0D; i--) {
         loc.setY(i);
         if (loc.getBlock().getType().isSolid())
            break;
         distance++;
      }
      return distance;
   }

}
