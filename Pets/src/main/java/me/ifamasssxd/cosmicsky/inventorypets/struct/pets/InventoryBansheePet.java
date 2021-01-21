package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.utils.HolyWhiteScrollUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;

import java.util.*;
import java.util.concurrent.TimeUnit;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicmasks.MaskType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryBansheePet extends InventoryPet {
    public int petLevel = 0;
    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public InventoryPets getOwner() {
        return InventoryPets.BANSHEE;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList(new String[]{CC.Gray + "Call upon the deadly screeming Banshee.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[RKO]:", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to all players(8s) ", CC.DarkRed + "* " + CC.Gray + "Immune to freezes (8s) ", CC.DarkRed + "* " + CC.Gray + "Gears V (8s) ", CC.DarkRed + "* " + CC.Gray + "AoE (8x8) Silence IV (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Phoenix (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Holy White Scrolls (5s) ", CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long) this.getCooldown(level))});
    }

    public int getCooldown(int level) {
        if (level == 1) {
            return 900;
        } else if (level == 2) {
            return 840;
        } else if (level == 3) {
            return 780;
        } else if (level == 4) {
            return 720;
        } else if (level == 5) {
            return 660;
        } else if (level == 6) {
            return 600;
        } else if (level == 7) {
            return 540;
        } else if (level == 8) {
            return 480;
        } else if (level == 9) {
            return 420;
        } else {
            return level == 10 ? 360 : 900;
        }
    }

    public int getExpForLevel(int level) {
        return 700 + 100 * level;
    }

    public int getMaxLevel() {
        return 10;
    }

    public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
       long lastUse = petItem.getLastUsed();
       long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(petItem.getLevel()));
       long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        Location locationClicked = player.getLocation();
        if (!WorldGuardUtils.isPvPEnabled(locationClicked)) {
            player.sendMessage(ChatColor.RED + "Combat is disabled in this region.");
            return true;
        }

        final List<Player> nearbyEnemies = Lists.newArrayList();
        Faction faction = FactionUtils.getFaction(player);

        for (final Entity other : player.getNearbyEntities(8.0, 8.0, 8.0)) {
            if (other instanceof Player) {
                final Player nearPlayer = (Player) other;
                final Faction nearFac = FactionUtils.getFaction(nearPlayer);

//                todo: new staffmode
//                if (StaffPlus.get().vanish.isActive(nearPlayer.getName())) {
//                    continue;
//                }
//
//                if (StaffPlus.get().freeze.isActive(nearPlayer.getName())) {
//                    continue;
//                }
                if (CosmicArmorSetsAPI.getArmorSet(nearPlayer) == ArmorSet.KOTH) {
                    continue;
                }

                if (nearPlayer.getGameMode() == GameMode.CREATIVE)
                    continue;

                if (nearFac != null && (nearFac.getRelationTo(faction) != Relation.ENEMY && nearFac.getRelationTo(faction) != Relation.NEUTRAL)) {
                    continue;
                }
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.SPRING_2020_AXE)) {
                    nearPlayer.sendMessage("§e§l* SPIKED BAT §e§lBLOCKED §7[Banshee Pet§7] §e§l*");
                    continue;
                }
                nearbyEnemies.add(nearPlayer);
            }
        }
        if (nearbyEnemies.isEmpty()) {
            throw new PrefixedPetException("Could not find a valid target!");
        }


        ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE, player.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 3);

        int duration = 8;
        nearbyEnemies.forEach(enemy -> {
            enemy.setMetadata("noDefenseProcs", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
            enemy.setMetadata("bansheed", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
            enemy.setMetadata("banshee_scrolls", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
            enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&c%ds&4&l] - RKO! **", duration)));
            enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", duration)));

        });
        CombatLog.flagCombat(player);

        player.setMetadata("immune_freeze", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        player.setMetadata("banshee_active", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));

        org.arkhamnetwork.enchantments.libs.PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(new Gears(), 5), new PotionEffect(PotionEffectType.SPEED, 20 * 8, 4));

        player.sendMessage("");
        player.sendMessage(CC.translate("&4&lPET: ** BANSHEE [&68s&4&l] - RKO! **"));
        player.sendMessage("");

        new BukkitRunnable() {
            public void run() {
                player.removeMetadata("immune_freeze", CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", CosmicSkyInventoryPets.get());
                player.removePotionEffect(PotionEffectType.SPEED);
                PlayerPotionEffects.applyPotionEffects(player, false);
                nearbyEnemies.forEach(x -> {
                    x.removeMetadata("noDefenseProcs", CosmicSkyInventoryPets.get());
                    x.removeMetadata("bansheed", CosmicSkyInventoryPets.get());
                    x.removeMetadata("banshee_active", CosmicSkyInventoryPets.get());
                    x.removeMetadata("banshee_scrolls", CosmicSkyInventoryPets.get());

                    x.sendMessage(CC.translate("&e&l(!) &4&l[RKO] &efrom " + player.getName() + "'s Banshee Pet &e&nEXPIRED!"));

                });
            }
        }.runTaskLater(CosmicSkyInventoryPets.get(), 20 * duration);
        final int xp = 40 + new Random().nextInt(40 + petItem.getLevel());
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1f, 1.0f);
        final NBTTagCompoundBuilder cosmicData = petItem.getWrapper().getconaxData();
        boolean skipped = false;
        if (cosmicData.hasKey("lastExpGained")) {
            final long gained = cosmicData.getLong("lastExpGained");
            final long timeSince = System.currentTimeMillis() - gained;
            if (timeSince < TimeUnit.DAYS.toMillis(1L)) {
                CosmicSkyInventoryPets.get().getLogger().info("Skipping exp gain since: " + timeSince);
                skipped = true;
            }
        }
        if (!skipped) {
            petItem.addExperience(player, xp);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(xp) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
        }
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 0.2f, 2.0f);
        player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 2.0f, 2.0f);
        petItem.setLastUsed(System.currentTimeMillis());

        return true;

    }

    public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
        return false;
    }

}
