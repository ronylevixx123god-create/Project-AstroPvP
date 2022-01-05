package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EngineerSet extends ArmorSetInfo implements Listener, WeaponedArmorSet, OtherPlayerEventListenerSet {
    public static HashMap<Entity, Player> blocksAndPlayers = new HashMap<>();

    public static HashMap<Entity, Player> blocksAndProccer = Maps.newHashMap();

    public static List<String> uuidsE = new ArrayList<>();

    public static List<String> entityList = new ArrayList<>();

    private ChatColor color;

    public EngineerSet() {
        this.color = ChatColor.GOLD;
        Bukkit.getPluginManager().registerEvents(this, (Plugin)Enchantments.getInstance());
    }

    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    public ItemStack getHelmet(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_HELMET, "Engineer Face Mask", "helmet");
        NBTWrapper wrapper = new NBTWrapper(applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem);
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setString("armorSet", "engineer");
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_CHESTPLATE, "Engineer Explosion-Proof Vest", "chestplate");
        NBTWrapper wrapper = new NBTWrapper(applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem);
        com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setString("armorSet", "engineer");
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_LEGGINGS, "Engineer Protective Chaps", "leggings");
        NBTWrapper wrapper = new NBTWrapper(applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem);
        com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setString("armorSet", "engineer");
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }

    public ItemStack addEnchants(ItemStack item, List<String> enchants, double heroic) {
        for (String cen : enchants) {
            CustomEnchantment ce = EnchantmentAPI.getEnchantment(cen);
            if (ce == null)
                continue;
            if (ce instanceof org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCustomEnchantment) {
                if (heroic > 0.0D && Math.random() > heroic)
                    continue;
                ce.addToItem(item, ce.getMaxLevel());
                ce.addToItem(item, 1 + (new Random()).nextInt(ce.getMaxLevel()));
                continue;
            }
            ce.addToItem(item, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + this.random.nextInt(3)));
        }
        return item;
    }

    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        if (enchantment == null)
            return item;
        enchantment.addToItem(item, enchantment.getMaxLevel());
        return item;
    }

    public ItemStack addRandomLeveledEnchant(String enchantName, ItemStack item) {
        CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
        if (enchantment == null)
            return item;
        enchantment.addToItem(item, ((enchantment.getMaxLevel() > 1) ? this.random.nextInt(enchantment.getMaxLevel()) : 0) + 1);
        return item;
    }

    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_BOOTS, "Engineer Steel Toed Boots", "boots");
        NBTWrapper wrapper = new NBTWrapper(applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem);
        com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setString("armorSet", "engineer");
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }

    public List<String> getEquipDescription() {
        return (List<String>)Lists.newArrayList(new String[] { "&7", "&6&lENGINEER SET BONUS", "&6&l* &6Enemies deal 35% less damage to you", "&6&l* &6Invisible on /near", "&6&l* &6Immune to Teleblock", "&6&l* &6Gears IV", "&6&l* &6B-2 Bomber Passive Ability", "&7" }).stream().map(CC::translate).collect(Collectors.toList());
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (ThreadLocalRandom.current().nextInt(100) <= 4)
            onEngineerAbility(damaged);
        //DecimateCore.getInstance().getDamageUtils().setDamageReduce(35.0D, event, "&6&lEngineer Armor Set");
        return true;
    }

    public void addEntityUUIDE(UUID id) {
        String uuid = id.toString();
        uuidsE.add(uuid);
    }

    public void removeEntityBlockE(UUID id) {
        String uuid = id.toString();
        if (uuidsE.contains(uuid))
            entityList.remove(uuid);
    }

    public boolean containsBlockE(UUID id) {
        String uuid = id.toString();
        return uuidsE.contains(uuid);
    }

    public void onEngineerAbility(Player player) {
        List<Entity> affected = (List<Entity>)player.getNearbyEntities(25.0D, 25.0D, 25.0D).stream().filter(ent -> {
            if (!(ent instanceof Player))
                return false;
            Player p = (Player)ent;
            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.VIRUS_HELMET_HAZMAT)) {
                p.sendMessage(CC.translate("&2&l* PRISON BREAK &7(Hazmat Helmet) &2&l*"));
                return false;
            }
            if (p.hasMetadata("immune_freeze")) {
                p.sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "B-2 Bomb blocked!" + CC.DarkGrayB + "] *");
                return false;
            }
            Relation relation = FactionUtils.getRelationTo((RelationParticipator)FactionUtils.getFaction(p), (RelationParticipator)FactionUtils.getFaction(player));
            return (relation != Relation.TRUCE && relation != Relation.MEMBER && relation != Relation.ALLY);
        }).collect(Collectors.toList());
        for (Entity ent2 : affected) {
            FallingBlock block = ent2.getWorld().spawnFallingBlock(ent2.getLocation().add(0.0D, 10.0D, 0.0D), Material.TNT, (byte)9);
            addEntityUUIDE(block.getUniqueId());
            blocksAndPlayers.put(block, (Player)ent2);
            blocksAndProccer.put(block, player);
        }
        player.sendMessage(CC.translate("&a&l* B-2 BOMBER DEPLOYED [&7" + affected.size() + " &7players&a&l] *"));
    }

    @EventHandler
    public void onEntityChangeBlockEngineer(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock && containsBlockE(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
            FallingBlock block = (FallingBlock)event.getEntity();
            Player plr = blocksAndPlayers.get(block);
            plr.playSound(plr.getLocation(), Sound.ZOMBIE_WOOD, 10.0F, 1.2F);
            plr.setWalkSpeed(0.0F);
            plr.sendMessage(CC.translate("&c&l* B-2 BOMBED [&7" + ((Player)blocksAndProccer.get(block)).getName() + "&7 (Frozen for 3s)&c&l] *"));
            blocksAndPlayers.remove(block, plr);
            blocksAndProccer.remove(block, blocksAndProccer.get(block));
            Location loc = event.getEntity().getLocation();
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> plr.setWalkSpeed(0.2F), 60L);
            removeEntityBlockE(event.getEntity().getUniqueId());
        }
    }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        double chance = 0.75D;
        if (ThreadLocalRandom.current().nextDouble(101.0D) <= chance)
            onEngineerAbility(damager);
        return true;
    }

    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return false;
        if ((ThreadLocalRandom.current().nextInt(100) + 1) <= 0.45D)
            onEngineerAbility(damaged);
         modifyDamage(damaged, event, -9.0D, multiplier);
         return false;
    }

    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity)damager))
            return false;
        modifyDamage(damager, event, 5.0D, multiplier);
        return false;
    }

    public ItemStack createArmor(Material material, String itemName, String loreType) {
        String bold = this.color + CC.Bold + "* " + this.color;
        ItemStack armor = ItemUtils.buildItem(material, (short)0, this.color + CC.Bold + itemName + CC.Reset, new String[] { this.color + CC.Italic + "Next-gen Gnometech " + loreType + " for demolition specialists", "", this.color + CC.Bold + "ENGINEER SET BONUS", bold + "Enemies deal 35% less damage to you.", bold + "Invisible on /near", bold + "Immune to Teleblock", bold + "Gears IV", bold + "B-2 Bomber Passive Ability", CC.Gray + "(" + CC.GrayI + "Requires all 4 engineer set items" + CC.Gray + ")" });
        armor.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        armor.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return armor;
    }

    public ItemStack getWeapon(boolean p0) {
        NBTWrapper wrapper = (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.GoldB + "Engineer Hammer", new String[] { CC.Gray + "A titan-forged grandmaster", CC.Gray + "warhammer that crushes all.", "", CC.GoldB + "ENGINEER WEAPON BONUS", CC.Gold + "Deal +15% damage to all enemies.", CC.Gold + "120% B-2 Bomber Ability", CC.Gold + "Immune to World Destroyer Pet", CC.Gold + "Immune to Shockwave, Obliterate, Guardians", CC.Gray + "(" + CC.GrayI + "Requires all 4 engineer items." + CC.Gray + ")" })).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build())).setString("cosmicType", "ENGINEER_SWORD");
        com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder compound = wrapper.getCompoundOrCreate("cosmicData");
        compound.setString("armorSet", "engineer");
        wrapper.setNBTBase("cosmicData", (NBTBase)compound.get());
        return wrapper.build();
    }

    @Override
    public void onPlayerEvent(Event var1) {

    }
}
