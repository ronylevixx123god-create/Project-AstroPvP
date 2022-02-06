package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.perms.Relation;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import me.astropvp.astropvpskins.objects.skins.swords.SpinalTab;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InventoryBansheePet extends InventoryPet {
    public int petLevel = 5;

    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public InventoryPets getOwner() {
        return InventoryPets.BANSHEE;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList(CC.Gray + "Call upon the deadly screeming Banshee.", CC.Gray, CC.WhiteB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[RKO]:", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to all players(8s) ", CC.DarkRed + "* " + CC.Gray + "Immune to freezes (8s) ", CC.DarkRed + "* " + CC.Gray + "Gears V (8s) ", CC.DarkRed + "* " + CC.Gray + "AoE (8x8) Silence IV (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Phoenix (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Holy White Scrolls (5s) ",
                CC.Gray, CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(getCooldown(5)));
    }


    public int getExpForLevel(int level) {
        return 700 + 100 * level;
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getCooldown(int var1) {
        return 360;
    }

    public boolean onPetItemInteract(final Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(5));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
        int bansheeCooldown = 20;


        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        List<Player> nearbyEnemies = Lists.newArrayList();

        Faction faction = FactionUtils.getFaction(player);

        for (Entity other : player.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
            if (other instanceof Player) {
                Player nearPlayer = (Player) other;
                Faction nearFac = FactionUtils.getFaction(nearPlayer);
                if (nearPlayer.getGameMode() == GameMode.CREATIVE)
                    continue;
                if (nearFac != null && nearFac.getRelationTo((RelationParticipator) faction) != Relation.ENEMY && nearFac.getRelationTo((RelationParticipator) faction) != Relation.NEUTRAL)
                    continue;
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.SPRING_2020_AXE)) {
                    nearPlayer.sendMessage(CC.translate("&c&l* SPIKED BAT SKIN BLOCKED BANSHEE *"));
                    continue;
                }
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.THANKSGIVING_HELMET_WITCH) && (!GoldenAdmirer.isAdmirerSilenced(nearPlayer)) && !(((SpinalTab) SkinType.HALLOWEEN_2021_SWORD.getItemSkin())).getWitchSilencedPlayers().contains(nearPlayer.getName()) && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.BACK_TO_SCHOOL_2021_BELT)) {

                    nearPlayer.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &4Banshee Pet &7[&f" + player.getName() + "&7] &e&l*"));
                    player.sendMessage(CC.translate("&e&l* &f" + nearPlayer.getName() + " &7Blocked &4Banshee Pet &7[&e&lWitch Hat&7] &e&l*"));
                    continue;
                }


                if (CosmicSkyInventoryPets.get().getCooldownUtils().isOnCooldown(nearPlayer, "bansheeCooldown")) {
                    player.sendMessage(CC.translate("&c&lPET: &c" + nearPlayer.getName() + " is still on banshee cooldown for &n" + CosmicSkyInventoryPets.get().getCooldownUtils().getRemainingTimeFormatted(nearPlayer, "bansheeCooldown") + "&c."));
                    continue;
                }
                if (nearPlayer.getItemInHand() != null && WeaponBonusUtils.hasWeaponBonus(nearPlayer.getItemInHand())) {
                    if (WeaponBonusUtils.getWeaponBonusByItem(nearPlayer.getItemInHand()) == WeaponBonusType.DEMON && Math.random() < 0.35) {
                        nearPlayer.sendMessage(CC.translate("&4&l* DEMON WEAPON BONUS &7Blocked &4Banshee Pet &7[&f" + player.getName() + "&7] &4&l*"));
                        player.sendMessage(CC.translate("&4&l* &f" + nearPlayer.getName() + " &7Blocked &4Banshee Pet &7[&4&lDemon Weapon Bonus&7] &4&l*"));
                        continue;
                    }
                }
                nearbyEnemies.add(nearPlayer);
            }
        }

        if (nearbyEnemies.isEmpty()) {
            throw new PrefixedPetException("Could not find a valid target!");
        }

        petItem.setLastUsed(System.currentTimeMillis());


        int duration;


        if (MaskUtils.hasMaskEquipped(player, "ELF")) {
            duration = 12;
        } else {
            duration = 8;
        }

        nearbyEnemies.forEach(enemy -> {
            if (AstroItemSkinsAPI.hasSkinEquipped(enemy, SkinType.THANKSGIVING_HELMET_WITCH) && AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.BACK_TO_SCHOOL_2021_BELT)) {
                enemy.sendMessage(CC.translate("&8&l* " + player.getName() + "'s BANDOLIER BELT [&7Bypassed Witch Hat&8&l] *"));
                player.sendMessage(CC.translate("&8&l* " + player.getName() + "'s BANDOLIER BELT [&7Blocked " + enemy.getName() + "'s Witch Hat&8&l] *"));
            }


            CosmicSkyInventoryPets.get().getCooldownUtils().add(enemy, "bansheeCooldown", bansheeCooldown * 1000);
            enemy.playSound(enemy.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            enemy.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " [7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
            enemy.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.setMetadata("banshee_scrolls", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.sendMessage("");
            enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&6" + duration + "s&4&l] - RKO! **", new Object[]{Integer.valueOf(duration)})));
            enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", new Object[]{Integer.valueOf(duration)})));
            enemy.sendMessage("");

            DamageIndicatorUtils.sendDamageIndicator(enemy, CC.translate("&c&l* BANSHEE RKO [&7" + duration + "s&c&l] *"), "RKO");

        });
        CombatLog.flagCombat(player);
        player.setMetadata("immune_freeze", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 0.25f);
        player.setMetadata("banshee_active", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 5);
        player.sendMessage("");
        player.sendMessage(CC.translate("&4&lPET: ** BANSHEE [&6" + duration + "s&4&l] - RKO! **"));
        player.sendMessage("");

        (new BukkitRunnable() {
            public void run() {
                player.sendMessage(CC.translate("&4&lPET:  BANSHEE - RKO has worn off! "));
                player.removeMetadata("immune_freeze", (Plugin) CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", (Plugin) CosmicSkyInventoryPets.get());
                EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 5);
                nearbyEnemies.forEach(x -> {
                    x.removeMetadata("noDefenseProcs", (Plugin) CosmicSkyInventoryPets.get());
                    x.removeMetadata("banshee_scrolls", (Plugin) CosmicSkyInventoryPets.get());
                    x.sendMessage(CC.translate("&4&lPET: &4&l[RKO] &efrom " + player.getName() + "'s Banshee Pet &e&nEXPIRED!"));
                });
            }
        }).runTaskLater((Plugin) CosmicSkyInventoryPets.get(), (20 * duration));
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
        player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 2.0F, 2.0F);

        return true;
    }

    public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
        return false;
    }


    public static void startBansheeEffect(Player player, Player enemy) {

        int duration = 8;
        enemy.playSound(enemy.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
        enemy.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " 7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
        enemy.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        enemy.setMetadata("banshee_scrolls", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        enemy.sendMessage("");
        enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&6" + duration + "s&4&l] - RKO! **", new Object[]{Integer.valueOf(duration)})));
        enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", new Object[]{Integer.valueOf(duration)})));
        enemy.sendMessage("");


        DamageIndicatorUtils.sendDamageIndicator(enemy, CC.translate("&c&l* BANSHEE RKO [&7" + duration + "&c&l] *"), "RKO");


        CombatLog.flagCombat(player);
        player.setMetadata("immune_freeze", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 0.25f);
        player.setMetadata("banshee_active", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 5);
        player.sendMessage("");
        player.sendMessage(CC.translate("&4&lPET: ** BANSHEE [&6" + duration + "s&4&l] - RKO! **"));
        player.sendMessage("");

        (new BukkitRunnable() {
            public void run() {
                player.sendMessage(CC.translate("&4&lPET:  BANSHEE - RKO has worn off! "));
                player.removeMetadata("immune_freeze", (Plugin) CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", (Plugin) CosmicSkyInventoryPets.get());
                EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 5);

                enemy.removeMetadata("noDefenseProcs", (Plugin) CosmicSkyInventoryPets.get());
                enemy.removeMetadata("banshee_scrolls", (Plugin) CosmicSkyInventoryPets.get());
                enemy.sendMessage(CC.translate("&4&lPET: &4&l[RKO] &efrom " + player.getName() + "'s Banshee Pet &e&nEXPIRED!"));

            }
        }).runTaskLater((Plugin) CosmicSkyInventoryPets.get(), (20 * duration));
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
        player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 2.0F, 2.0F);
    }
}