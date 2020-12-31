package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.listeners.BansheeListener;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class ChampionArmorSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<>();

    public ItemStack getHelmet(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&6&lChampion Crown", CC.Gray + "The Champion Crown"
                , ""
                , "&6&lCHAMPION SET BONUS"
                , "&6&l* &6You deal 40% more damage."
                , "&6&l* &6Champion Wrath Ability"
                , "&6&l* &6Auto Bless."
                , "&6&l* &6You take 20% less damage."
                , "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{
                EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")}))));
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&6&lChampion Body", CC.Gray + "The Champion Body"
                , ""
                , "&6&lCHAMPION SET BONUS"
                , "&6&l* &6You deal 40% more damage."
                , "&6&l* &6Champion Wrath Ability"
                , "&6&l* &6Auto Bless."
                , "&6&l* &6You take 20% less damage."
                , "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&6&lChampion Jeans", CC.Gray + "The Champion Jeans"
                , ""
                , "&6&lCHAMPION SET BONUS"
                , "&6&l* &6You deal 40% more damage."
                , "&6&l* &6Champion Wrath Ability"
                , "&6&l* &6Auto Bless."
                , "&6&l* &6You take 20% less damage."
                , "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")}))));
    }

    public ItemStack getBoots(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&6&lChampion Kicks", CC.Gray + "The Champion Boots"
                , ""
                , "&6&lCHAMPION SET BONUS"
                , "&6&l* &6You deal 40% more damage."
                , "&6&l* &6Champion Wrath Ability"
                , "&6&l* &6Auto Bless."
                , "&6&l* &6You take 20% less damage."
                , "&7(&oRequires all 4 Champion items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")}))));
    }

    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.GOLD + "" + ChatColor.BOLD + "CHAMPION SET BONUS: ", "§6§l* §6+40% DMG", "§6§l*§6 Enemies deal 20% less damage", "§6§l*§6 Champion Wrath Ability", "§6§l* §6Auto Bless");
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity) damaged))
            return false;
        if (event.getEntity() instanceof Player)
            removeResistance((Player) event.getEntity());
        if (event.isCancelled())
            return false;
        removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8);
        return true;
    }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        removeResistance(damager);
        if (isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 3, damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.40D);

        if (Math.random() <= 0.05) {
            activateChampionAbility(damager, (Player) event.getEntity());
        }


        return true;
    }

    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return false;
        if (isBlockedOutpostWorld((Entity) damaged))
            return false;
        modifyDamage(damaged, event, -5.0D, multiplier);
        return false;
    }

    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity) damager)) return false;
        if (Math.random() <= 0.005 * applied) {
            activateChampionAbility(damager, (Player) event.getEntity());
        }
        return modifyDamage(damager, event, 12.0D, applied);


    }

    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }


    public void activateChampionAbility(Player player, final Player target) {
        player.sendMessage("");
        player.sendMessage(CC.translate("&6&l* CHAMPION WRATH PROC *"));
        player.sendMessage(CC.translate("&a&lBUFF: &cBanshee RKO Proc &aActivated"));
        player.sendMessage(CC.translate("&a&lBUFF: &cAnti Utility Proc &aActivated"));
        player.sendMessage(CC.translate("&a&lBUFF: &cWorld Destroyer Ticks &aActivated"));
        player.sendMessage(CC.translate("&4&lTARGET: &f") + target.getName());
        player.sendMessage("");

        target.sendMessage(CC.translate("&6&l* CHAMPION WRATH &7[&c" + player.getName() + "&7]"));
        player.setMetadata("immune_freeze", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        player.setMetadata("banshee_active", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.setMetadata("noDefenseProcs", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.setMetadata("bansheed", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.setMetadata("championed", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.playSound(target.getLocation(), Sound.BLAZE_DEATH, 10, 10);
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(new Gears(), 5), new PotionEffect(PotionEffectType.SPEED, 20 * 8, 4));

        new BukkitRunnable() {
            public void run() {
                player.removeMetadata("immune_freeze", CosmicSkyInventoryPets.get());
                player.removeMetadata("banshee_active", CosmicSkyInventoryPets.get());
                target.removeMetadata("noDefenseProcs", CosmicSkyInventoryPets.get());
                target.removeMetadata("bansheed", CosmicSkyInventoryPets.get());
                target.removeMetadata("championed", CosmicSkyInventoryPets.get());
                target.sendMessage(CC.translate("&6&l* CHAMPION WRATH FROM &7" + player.getName() + " &c&lEXPIRED &6&l*"));

                PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SPEED);
                PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(new Gears(), 3), new PotionEffect(PotionEffectType.SPEED, 20 * 8, 3));
            }
        }.runTaskLater(CosmicSkyInventoryPets.get(), 20 * 8);
    }

    public void onPlayerEvent(Event paramEvent) {
    }
}