/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CC
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.cmd.CmdFocus
 *  com.massivecraft.factions.cmd.CmdUnfocus
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets
 *  org.arkhamnetwork.enchantments.Enchantments
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CmdFocus;
import com.massivecraft.factions.cmd.CmdUnfocus;
import com.rit.sucy.EnchantmentAPI;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AncientArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&3&lAncient Cap", "&3&oPowerful, martime helmet of", "&3&othe great Leviathan legend.", "", "&3&lANCIENT SET BONUS", "&3&l* &3+35% Outgoing Damage.", "&3&l* &3+10% Outgoing Damage from Soul Enchants.", "&3&l* &3-15% Incoming Damage.", "&3&l* &3Immune to Abyssal Stronghold perks.", "&3&l* &3-50% Knockback.", "&3&l* &3Auto Bless.", "&3&l* &3Leviathan's Curse Ability.", "&7(&oRequires all 4 Ancient items.&3)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&3&lAncient Body", "&3&oPowerful, martime chestplate of", "&3&othe great Leviathan legend.", "", "&3&lANCIENT SET BONUS", "&3&l* &3+35% Outgoing Damage.", "&3&l* &3+10% Outgoing Damage from Soul Enchants.", "&3&l* &3-15% Incoming Damage.", "&3&l* &3Immune to Abyssal Stronghold perks.", "&3&l* &3-50% Knockback.", "&3&l* &3Auto Bless.", "&3&l* &3Leviathan's Curse Ability.", "&7(&oRequires all 4 Ancient items.&3)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&3&lAncient Legs", "&3&oPowerful, martime leggings of", "&3&othe great Leviathan legend.", "", "&3&lANCIENT SET BONUS", "&3&l* &3+35% Outgoing Damage.", "&3&l* &3+10% Outgoing Damage from Soul Enchants.", "&3&l* &3-15% Incoming Damage.", "&3&l* &3Immune to Abyssal Stronghold perks.", "&3&l* &3-50% Knockback.", "&3&l* &3Auto Bless.", "&3&l* &3Leviathan's Curse Ability.", "&7(&oRequires all 4 Ancient items.&3)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&3&lAncient Boots", "&3&oPowerful, martime boots of", "&3&othe great Leviathan legend.", "", "&3&lANCIENT SET BONUS", "&3&l* &3+35% Outgoing Damage.", "&3&l* &3+10% Outgoing Damage from Soul Enchants.", "&3&l* &3-15% Incoming Damage.", "&3&l* &3Immune to Abyssal Stronghold perks.", "&3&l* &3-50% Knockback.", "&3&l* &3Auto Bless.", "&3&l* &3Leviathan's Curse Ability.", "&7(&oRequires all 4 Ancient items.&3)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "",
                "&3&lANCIENT SET BONUS"
                , "&3&l➥ &3+35% Outgoing Damage."
                , "&3&l➥ &3+10% Outgoing Damage from Soul Enchants."
                , "&3&l➥ &3-15% Incoming Damage."
                , "&3&l➥ &3Immune to Abyssal Stronghold perks."
                , "&3&l➥ &3-50% Knockback."
                , "&3&l➥ &3Auto Bless."
                , "&3&l➥ &3Leviathan's Curse Ability.",
                "");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        if (event.isCancelled()) {
            return false;
        }
        this.removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.80);
        damaged.setVelocity(damaged.getVelocity().multiply(0.8));
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (Math.random() <= 0.005 || damager.isOp() && damager.isSneaking()) {
            this.removeResistance(damager);
            if (this.isBlockedOutpostWorld(damager)) {
                return false;
            }
            this.startAncientEffect(damager, (Player) event.getEntity());
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.35);
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (event.isCancelled()) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -3.0, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        this.modifyDamage(damager, event, 7.0, applied);

        if (Math.random() <= 0.001 * (double) applied || damager.isOp() && damager.isSneaking()) {
            this.removeResistance(damager);
            if (this.isBlockedOutpostWorld(damager)) {
                return false;
            }
            this.startAncientEffect(damager, (Player) event.getEntity());
        }
        return false;
    }

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }

    private void startAncientEffect(final Player player, final Player pTarget) {

        if (pTarget.hasMetadata("banshee_scrolls") || pTarget.hasMetadata("bloodHoundNoBless") || player.hasMetadata("banshee_active")) {
            return;
        }

        if (WeaponBonusUtils.hasWeaponBonus(((Player) pTarget).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player)pTarget).getItemInHand()) == WeaponBonusType.HERO && Math.random() < 0.5) {

            pTarget.sendMessage(CC.translate("&3&l** BLOCKED ENEMY ANCIENT ABILITY &7(Hero Weapon Bonus) &3&l**"));

            return;
        }

        this.focusTargetPlayer(player, pTarget);
        player.sendMessage("");
        player.sendMessage(CC.translate(("&3&l      * LEVIATHAN'S CURSE &7(" + pTarget.getName() + ")&3&l *")));
        player.sendMessage(CC.translate(("&3&l      * &7+150% damage to &f" + pTarget.getName() + "&7 for 10s")));
        player.sendMessage("");
        pTarget.setMetadata("noDefenseProcs", new FixedMetadataValue(Enchantments.getInstance(), true));
        pTarget.setMetadata("bloodHoundNoBless", new FixedMetadataValue(CosmicSkyInventoryPets.get(), "ancient"));
        pTarget.removePotionEffect(PotionEffectType.SLOW);
        pTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
        this.removeNearMeta(player, pTarget);
        pTarget.sendMessage("");
        pTarget.sendMessage(CC.translate(("&3&l* LEVIATHAN'S CURSE &7(" + player.getName() + "'s Ancient Armor Set) &3&l*")));
        pTarget.sendMessage(CC.translate(("&3&l   * &7+150% damage from " + player.getName() + " for 10s")));
        pTarget.sendMessage(CC.translate("&3&l      * &710s Silence and Slowness I"));
        pTarget.sendMessage(CC.translate("&3&l        * &730s visible on /near"));
        pTarget.sendMessage("");
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 10.0f, 10.0f);
        new BukkitRunnable() {

            public void run() {
                pTarget.removeMetadata("noDefenseProcs", Enchantments.getInstance());
                pTarget.removeMetadata("bloodHoundNoBless", CosmicSkyInventoryPets.get());
                CmdUnfocus.unfocusPlayer(FPlayers.i.get(player).getFaction(), pTarget);
            }
        }.runTaskLaterAsynchronously(CosmicSkyInventoryPets.get(), 100L);
    }

    private void removeNearMeta(Player player, Player pTarget) {
        if (pTarget.hasMetadata("heroicGhostEnchantment")) {
            pTarget.setMetadata("heroicGhostEnchantmentTemp", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
            pTarget.removeMetadata("heroicGhostEnchantment", Enchantments.getInstance());
        }
    }

    private void focusTargetPlayer(Player player, Player pTarget) {
        if (pTarget instanceof Player) {
            FPlayer fPlayer = FPlayers.i.get(player);
            Faction myFaction = fPlayer.getFaction();
            FPlayer targetPlayer = FPlayers.i.get(pTarget);
            if (myFaction.isNormal()) {
                CmdFocus.focusPlayer(fPlayer, myFaction, targetPlayer, false);
            }
        }
    }
}

