package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class LunaSet extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();
    public static List<UUID> lunaStrikedPlayers = new ArrayList<>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&7&lLuna Helmet Hood", CC.Gray + "The Astro Hood", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&a&lAstro Gauntlet", CC.Gray + "The Astro Gauntlet", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&a&lAstro Robe", CC.Gray + "The Astro Robe", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&a&lAstro Kicks", CC.Gray + "The Astro Kicks", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.GREEN + "" + ChatColor.BOLD + "",
                "&a&lASTRO SET BONUS"
                , "&a&l➥ &aYou deal 30% more damage."
                , "&a&l➥ &aAstro Killer Passive Ability"
                , "&a&l➥ &aGears IV."
                , "&a&l➥ &aYou take 10% less damage."
                , "");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
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
        this.modifyDamage(damaged, event, -4.0, multiplier);
        return true;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        this.modifyDamage(damager, event, 6, applied);
        Player damaged = ((Player) event.getEntity()).getPlayer();

        if (Math.random() < 0.05) {
            if (isLunarStriked(damager)) return false;
            lunaStrikedPlayers.add(damager.getUniqueId());
            damager.sendMessage("");
            damager.sendMessage(StringUtils.getCenteredMessage(CC.translate("&8&l✰ &7&lLUNA STRIKE ABILITY (" + damaged.getName() + ") &8&l✰")));
            damager.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Increased Rage Stack Limit")));
            damager.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Negate " + damaged.getName() + "'s Soul Enchants (5s)")));
            damager.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Negate all incoming Pet Damage modifiers (5s)")));
            damager.sendMessage("");

            Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicArmorSetsPlugin.get_(), () -> {
                if (isLunarStriked(damager)) {
                    lunaStrikedPlayers.remove(damager.getUniqueId());
                }
            }, 100L);
        }
        return true;
    }

    public static boolean isLunarStriked(Player player) {
        return lunaStrikedPlayers.contains(player.getUniqueId());
    }

    @Override
    public void onEquip(Player player) {

    }

    @Override
    public void onUnequip(Player player) {
    }

    public Block setBlock(Location location, Material material) {
        if (location.getBlock().getType() == Material.AIR) {
            location.getBlock().setType(material);
            return location.getBlock();
        }
        return null;
    }

    public List<Block> setOutlinedBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        return blocks;
    }

    public List<Block> setInsideBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location, material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -1.0), material));
        return blocks;
    }

    public void activateFalloutKillerAbility(Player player, final Player target) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.DIAMOND_BLOCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.SOUL_SAND));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 0.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 1.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 2.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.DIAMOND_BLOCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.DIAMOND_BLOCK));
        blockList.addAll(blocks);
        new BukkitRunnable() {
            int counter = 0;

            public void run() {
                ++this.counter;
                target.damage(3.5);
                if (this.counter == 6) {
                    List<Block> newBlocks = blocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
                    for (Block block : newBlocks) {
                        block.setType(Material.AIR);
                    }
                    blockList.removeAll(newBlocks);
                    this.cancel();
                }
            }
        }.runTaskTimer(CosmicArmorSetsPlugin.get_(), 0L, 20L);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

