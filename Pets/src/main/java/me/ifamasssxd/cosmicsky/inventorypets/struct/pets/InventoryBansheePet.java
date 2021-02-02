package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.libs.Vector3D;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
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
    public int petLevel = 0;

    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public InventoryPets getOwner() {
        return InventoryPets.BANSHEE;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList((Object[])new String[] {
                CC.Gray + "Call upon the deadly screeming Banshee.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Activates " + CC.Gray + "[RKO]:", CC.DarkRed + "* " + CC.Gray + "1.5x DMG to all players(8s) ", CC.DarkRed + "* " + CC.Gray + "Immune to freezes (8s) ", CC.DarkRed + "* " + CC.Gray + "Gears V (8s) ", CC.DarkRed + "* " + CC.Gray + "AoE (8x8) Silence IV (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Phoenix (5s) ", CC.DarkRed + "* " + CC.Gray + "Negates enemy Holy White Scrolls (5s) ",
                CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(getCooldown(level)) });
    }

    public int getCooldown(int level) {
        if (level == 1)
            return 450;
        if (level == 2)
            return 420;
        if (level == 3)
            return 390;
        if (level == 4)
            return 360;
        if (level == 5)
            return 330;
        if (level == 6)
            return 300;
        if (level == 7)
            return 540;
        if (level == 8)
            return 480;
        if (level == 9)
            return 420;
        return (level == 10) ? 360 : 900;
    }

    public int getExpForLevel(int level) {
        return 700 + 100 * level;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean onPetItemInteract(final Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(petItem.getLevel()));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
        if (petItem.getLevel() > 6) {
            petItem.setLevel(6);
            player.setItemInHand(petItem.build());
            return false;
        }
        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        if (!WorldGuardUtils.isPvPEnabled(player.getLocation()))
            throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "Not allowed in this region!");
        petItem.setLastUsed(System.currentTimeMillis());
        final List<Player> nearbyEnemies = Lists.newArrayList();
        Faction faction = FactionUtils.getFaction(player);
        for (Entity other : player.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
            if (other instanceof Player) {
                Player nearPlayer = (Player)other;
                Faction nearFac = FactionUtils.getFaction(nearPlayer);
                if (CosmicArmorSetsAPI.getArmorSet(nearPlayer) == ArmorSet.KOTH)
                    continue;
                if (nearPlayer.getGameMode() == GameMode.CREATIVE)
                    continue;
                if (nearFac != null && nearFac.getRelationTo((RelationParticipator)faction) != Relation.ENEMY && nearFac.getRelationTo((RelationParticipator)faction) != Relation.NEUTRAL)
                    continue;
                if (AstroItemSkinsAPI.hasSkinEquipped(nearPlayer, SkinType.SPRING_2020_AXE)) {
                    nearPlayer.sendMessage(CC.translate("&c&l* SPIKED BAT SKIN *"));
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
            enemy.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " 7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
            enemy.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.setMetadata("banshee_scrolls", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
            enemy.sendMessage(CC.translate(String.format("&4&l*** " + player.getName() + "&4&l's BANSHEE PET [&6" + duration + "s&4&l] - RKO! **", new Object[] { Integer.valueOf(duration) })));
            enemy.sendMessage(CC.translate(String.format("&c( %ds Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )", new Object[] { Integer.valueOf(duration) })));
            try {
                if (DamageIndicatorUtils.isPlayerIndicating((Player) player)) {
                    DamageIndicatorUtils.createDamageIndicator((Player) player,
                            enemy.getLocation().add(1.0D, 2.0D, 0.0D),
                            new String[] { CC.RedB + "* BANSHEED §7[" + duration + "§7] §cl*" });
                }
            } catch (Exception ex) {

            }
        });
        CombatLog.flagCombat(player);
        player.setMetadata("immune_freeze", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 0.25f);
        player.setMetadata("banshee_active", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 5);        player.sendMessage("");
        player.sendMessage(CC.translate("&4&lPET: ** BANSHEE [&6" + duration + "s&4&l] - RKO! **"));
        player.sendMessage("");
        (new BukkitRunnable() {
            public void run() {
                player.sendMessage(CC.translate("&4&lPET:  BANSHEE - RKO has worn off! "));
                player.removeMetadata("immune_freeze", (Plugin)CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", (Plugin)CosmicSkyInventoryPets.get());
                EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 5);
                nearbyEnemies.forEach(x -> {
                    x.removeMetadata("noDefenseProcs", (Plugin)CosmicSkyInventoryPets.get());
                    x.removeMetadata("banshee_scrolls", (Plugin)CosmicSkyInventoryPets.get());
                    x.sendMessage(CC.translate("&4&lPET: &4&l[RKO] &efrom " + player.getName() + "'s Banshee Pet &e&nEXPIRED!"));
                });
            }
        }).runTaskLater((Plugin)CosmicSkyInventoryPets.get(), (20 * duration));
        int xp = 40 + (new Random()).nextInt(40 + petItem.getLevel());
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1F, 1.0F);
            petItem.addExperience(player, xp);
        player.sendMessage(String.valueOf(CC.GreenB) + "+ " + CC.Green + NumberUtils.formatMoney(xp) + " Pet EXP " + CC.GreenB + "[" +
                CC.Gray + petItem.getExp() + "/" +
                petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 0.2F, 2.0F);
        player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 2.0F, 2.0F);
        return true;
    }

    public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
        return false;
    }

    public boolean playerHasApollo(Player pl) {
        return CosmicArmorSetsAPI.getBonusCrystalsEquipped(pl).containsKey(ArmorSet.APOLLO) || CosmicArmorSetsAPI.getArmorSet(pl) == ArmorSet.APOLLO;
    }
}
