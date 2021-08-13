package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
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
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.*;
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

import java.util.*;
import java.util.concurrent.TimeUnit;

public class InventoryBansheePet extends InventoryPet {
    public int petLevel = 5;

    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public InventoryPets getOwner() {
        return InventoryPets.BANSHEE;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList((Object[]) new String[]{
                CC.Gray + "Call upon the deadly screeming Banshee.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Activates " + CC.Gray + "[RKO]:", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to all players(8s) ", CC.DarkRed + "* " + CC.Gray + "Immune to freezes (8s) ", CC.DarkRed + "* " + CC.Gray + "Gears V (8s) ", CC.DarkRed + "* " + CC.Gray + "AoE (8x8) Silence IV (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Phoenix (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Holy White Scrolls (5s) ",
                CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(getCooldown(5))});
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
        if (item.getAmount() > 1) {
            player.sendMessage(CC.translate("&cYou cannot use stacked pets!"));

            return false;
        }

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        if (!WorldGuardUtils.isPvPEnabled(player.getLocation()))
            throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "Not allowed in this region!");

        if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(player), OutpostType.COURTYARD) && Math.random() < 0.2) {
            player.sendMessage(CC.translate("&c&l** INFERNAL STRONGHOLD BONUS &7Prevented &dPet Cooldown &c&l**"));
        } else {
            petItem.setLastUsed(System.currentTimeMillis());
        }

        final List<Player> nearbyEnemies = Lists.newArrayList();

        Faction faction = FactionUtils.getFaction(player);
        if (player.getNearbyEntities(8.0D, 8.0D, 8.0D).isEmpty()) {
            throw new PrefixedPetException("Could not find a valid target!");

        }
        for (Entity other : player.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
            if (other instanceof Player) {
                Player nearPlayer = (Player) other;
                Faction nearFac = FactionUtils.getFaction(nearPlayer);
                if (CosmicArmorSetsAPI.getArmorSet(nearPlayer) == ArmorSet.KOTH)
                    continue;
                if (nearPlayer.getGameMode() == GameMode.CREATIVE)
                    continue;
                if (nearFac != null && nearFac.getRelationTo((RelationParticipator) faction) != Relation.ENEMY && nearFac.getRelationTo((RelationParticipator) faction) != Relation.NEUTRAL)
                    continue;
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.SPRING_2020_AXE)) {
                    nearPlayer.sendMessage(CC.translate("&c&l* SPIKED BAT SKIN *"));
                    continue;
                }
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.THANKSGIVING_HELMET_WITCH)) {
                    nearPlayer.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &4Banshee Pet &7[&f" + player.getName() + "&7] &e&l*"));
                    player.sendMessage(CC.translate("&e&l* &f" + nearPlayer.getName() + " &7Blocked &4Banshee Pet &7[&e&lWitch Hat&7] &e&l*"));
                    continue;
                }
                nearbyEnemies.add(nearPlayer);
            }
        }
        //ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE, player.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5F, 3);
        int duration;
        if (playerHasApollo(player)) {
            duration = 12;
        } else {
            duration = 8;
        }

        nearbyEnemies.forEach(enemy -> {
            enemy.playSound(enemy.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            enemy.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " [7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
            enemy.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.setMetadata("banshee_scrolls", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&6" + duration + "s&4&l] - RKO! **", new Object[]{Integer.valueOf(duration)})));
            enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", new Object[]{Integer.valueOf(duration)})));
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

    public boolean playerHasApollo(Player pl) {
        return CosmicArmorSetsAPI.getBonusCrystalsEquipped(pl).containsKey(ArmorSet.APOLLO) || CosmicArmorSetsAPI.getArmorSet(pl) == ArmorSet.APOLLO;
    }

    public static void startBansheeEffect(Player player, Player enemy) {

        int duration = 8;
        enemy.playSound(enemy.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
        enemy.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " 7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
        enemy.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        enemy.setMetadata("banshee_scrolls", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&6" + duration + "s&4&l] - RKO! **", new Object[]{Integer.valueOf(duration)})));
        enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", new Object[]{Integer.valueOf(duration)})));

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
