package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
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
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class AstroSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<>();

    public ItemStack getHelmet(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&a&lAstro Hood", new String[]{CC.Gray + "The Astro Hood", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"}), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{
                EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")}))));
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&a&lAstro Gauntlet", new String[]{CC.Gray + "The Astro Gauntlet", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"}), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{
                EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&a&lAstro Robe", new String[]{CC.Gray + "The Astro Robe", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"}), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{
                EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")}))));
    }

    public ItemStack getBoots(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&a&lAstro Kicks", new String[]{CC.Gray + "The Astro Kicks", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"}), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{
                EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")}))));
    }

    public List<String> getEquipDescription() {
        return Lists.newArrayList(new String[]{ChatColor.GREEN + "" + ChatColor.BOLD + "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage."});
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity) damaged))
            return false;
        if (event.getEntity() instanceof Player)
            removeResistance((Player) event.getEntity());
        if (event.isCancelled())
            return false;
        removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9D);
        return true;
    }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (Math.random() < 0.01) {

            OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(event.getDamager());
            if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO)
                return false;
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2D);
            if (event.getEntity() instanceof Player && event.getEntity().getLocation().distance(damager.getLocation()) <= 2.0D && ((Player) event.getEntity()).getHealth() < damager.getHealth()) {
                if (damager.hasMetadata("FalloutAbility") && System.currentTimeMillis() - ((MetadataValue) damager.getMetadata("FalloutAbility").get(0)).asLong() <= 120000L)
                    return false;
                damager.setMetadata("FalloutAbility", (MetadataValue) new FixedMetadataValue((Plugin) CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis())));
                FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) damager);
                for (Entity nearbyEnt : damager.getNearbyEntities(32.0D, 64.0D, 32.0D)) {
                    if (nearbyEnt instanceof Player) {
                        Player pNear = (Player) nearbyEnt;
                        FPlayer fpNear = (FPlayer) FPlayers.i.get((OfflinePlayer) pNear);
                        if (fpNear.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE))
                            continue;
                        if (!WorldGuardUtils.isPvPEnabled(pNear.getLocation()))
                            continue;
                        pNear.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "** ASTRO KILLER (" + ChatColor.GRAY + fp.getNameAsync() + " [6s]" + ChatColor.GREEN + ChatColor.BOLD + ") **");
                    }
                    damager.getLocation().getWorld().playSound(damager.getLocation(), Sound.GHAST_SCREAM, 1.0F, 1.0F);
                    activateFalloutKillerAbility(damager, (Player) event.getEntity());
                }
            }
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
        if (Math.random() < 0.01) {
            if (isBlockedOutpostWorld((Entity) damager))
                return false;
            if (event.getDamager().getWorld().getName().equals("world_duels")) {
                return false;
            }
            modifyDamage(damager, event, 5.0D, applied);
            if (event.getEntity() instanceof Player && event.getEntity().getLocation().distance(damager.getLocation()) <= 2.0D && ((Player) event.getEntity()).getHealth() < damager.getHealth()) {
                if (damager.hasMetadata("FalloutAbility") && System.currentTimeMillis() - ((MetadataValue) damager.getMetadata("FalloutAbility").get(0)).asLong() <= 65000L)
                    return false;
                damager.setMetadata("FalloutAbility", (MetadataValue) new FixedMetadataValue((Plugin) CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis())));
                FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) damager);
                for (Entity nearbyEnt : damager.getNearbyEntities(32.0D, 64.0D, 32.0D)) {
                    if (nearbyEnt instanceof Player) {
                        Player pNear = (Player) nearbyEnt;
                        FPlayer fpNear = (FPlayer) FPlayers.i.get((OfflinePlayer) pNear);
                        if (fpNear.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE))
                            continue;
                        if (!WorldGuardUtils.isPvPEnabled(pNear.getLocation()))
                            continue;
                        pNear.sendMessage("");
                        pNear.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "** ASTRO KILLER (" + ChatColor.GRAY + fp.getNameAsync() + " [6s]" + ChatColor.GREEN + ChatColor.BOLD + ") **");
                        pNear.sendMessage("");
                    }
                }
                damager.getLocation().getWorld().playSound(damager.getLocation(), Sound.GHAST_SCREAM, 1.0F, 1.0F);
                activateFalloutKillerAbility(damager, (Player) event.getEntity());
            }
        }
        return true;
    }

    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    public Block setBlock(Location location, Material material) {
        if (location.getBlock().getType() == Material.AIR) {
            location.getBlock().setType(material);
            return (Block) location.getBlock();
        }
        return null;
    }

    public List<Block> setOutlinedBlocks(Location location, Material material) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(setBlock(location.clone().add(2.0D, 0.0D, 0.0D), material));
        blocks.add(setBlock(location.clone().add(-2.0D, 0.0D, 0.0D), material));
        blocks.add(setBlock(location.clone().add(0.0D, 0.0D, 2.0D), material));
        blocks.add(setBlock(location.clone().add(0.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(2.0D, 0.0D, 2.0D), material));
        blocks.add(setBlock(location.clone().add(-2.0D, 0.0D, 2.0D), material));
        blocks.add(setBlock(location.clone().add(2.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(-2.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(-2.0D, 0.0D, -1.0D), material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, 2.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, 2.0D), material));
        blocks.add(setBlock(location.clone().add(2.0D, 0.0D, 1.0D), material));
        blocks.add(setBlock(location.clone().add(2.0D, 0.0D, -1.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(-2.0D, 0.0D, 1.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, -2.0D), material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, -2.0D), material));
        return blocks;
    }

    public List<Block> setInsideBlocks(Location location, Material material) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(setBlock(location, material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, 0.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, 0.0D), material));
        blocks.add(setBlock(location.clone().add(0.0D, 0.0D, 1.0D), material));
        blocks.add(setBlock(location.clone().add(0.0D, 0.0D, -1.0D), material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, 1.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, 1.0D), material));
        blocks.add(setBlock(location.clone().add(-1.0D, 0.0D, -1.0D), material));
        blocks.add(setBlock(location.clone().add(1.0D, 0.0D, -1.0D), material));
        return blocks;
    }

    public void activateFalloutKillerAbility(Player player, final Player target) {
        final List<Block> blocks = new ArrayList<>();
        blocks.addAll(setOutlinedBlocks(player.getLocation().add(0.0D, -1.0D, 0.0D), Material.DIAMOND_BLOCK));
        blocks.addAll(setInsideBlocks(player.getLocation().add(0.0D, -1.0D, 0.0D), Material.SOUL_SAND));
        blocks.addAll(setOutlinedBlocks(player.getLocation().clone().add(0.0D, 0.0D, 0.0D), Material.IRON_FENCE));
        blocks.addAll(setOutlinedBlocks(player.getLocation().clone().add(0.0D, 1.0D, 0.0D), Material.IRON_FENCE));
        blocks.addAll(setOutlinedBlocks(player.getLocation().clone().add(0.0D, 2.0D, 0.0D), Material.IRON_FENCE));
        blocks.addAll(setOutlinedBlocks(player.getLocation().clone().add(0.0D, 3.0D, 0.0D), Material.DIAMOND_BLOCK));
        blocks.addAll(setInsideBlocks(player.getLocation().clone().add(0.0D, 3.0D, 0.0D), Material.DIAMOND_BLOCK));
        blockList.addAll(blocks);
        (new BukkitRunnable() {
            int counter = 0;

            public void run() {
                this.counter++;
                target.damage(3.5);
                if (this.counter == 6) {
                    List<Block> newBlocks = (List<Block>) blocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
                    for (Block block : newBlocks)
                        block.setType(Material.AIR);
                    AstroSet.blockList.removeAll(newBlocks);
                    cancel();
                }
            }
        }).runTaskTimer((Plugin) CosmicArmorSetsPlugin.get_(), 0L, 20L);
    }

    public void onPlayerEvent(Event paramEvent) {
    }
}