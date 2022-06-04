package com.rit.sucy.enchanting;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.config.LanguageNode;
import com.rit.sucy.config.RootConfig;
import com.rit.sucy.config.RootNode;
import com.rit.sucy.service.ConfigNode;
import com.rit.sucy.service.ENameParser;
import com.rit.sucy.service.PermissionNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class EListener implements Listener {
    public static Material[] armor = new Material[] {
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS };

    EnchantmentAPI plugin;

    Hashtable<String, TableTask> tasks = new Hashtable<>();

    public static boolean excuse = false;


    public EListener(EnchantmentAPI plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, (Plugin)plugin);
        this.plugin = plugin;

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent event) throws Exception {
        if (excuse) {
            excuse = false;
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        boolean offenseProcs = true;
        if (event.getDamager().hasMetadata("shootForce") && (
                (MetadataValue)event.getDamager().getMetadata("shootForce").get(0)).asFloat() < 0.75F)
            offenseProcs = false;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        LivingEntity damager = (event.getDamager() instanceof LivingEntity) ? (LivingEntity)event.getDamager() : ((event.getDamager() instanceof Projectile) ? (LivingEntity)((Projectile)event.getDamager()).getShooter() : null);
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event
                .getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        if (damager != null && offenseProcs)
            if (damager instanceof Player) {
                Player pDamager = (Player)damager;
                int reflectLevel = getReflectLevel(damaged, "normal");
                int heroicLevel = getReflectLevel(damaged, "heroic");
                int mastery = getReflectLevel(damaged, "mastery");
                for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(pDamager.getItemInHand(), pDamager).entries()) {
                    CustomEnchantment enchant = entry.getKey();
                    int tier = enchant.getTier();
                    int reflectUsing = 0;
                    boolean reflect = false;
                    if (mastery > 0 && tier == 8 && mastery >= ((Integer)entry.getValue()).intValue()) {
                        reflectUsing = mastery;
                    } else if (heroicLevel > 0 && tier <= 7 && heroicLevel >= ((Integer)entry.getValue()).intValue()) {
                        reflectUsing = heroicLevel;
                    } else if (reflectLevel > 0 && tier <= 5 && reflectLevel >= ((Integer)entry.getValue()).intValue()) {
                        reflectUsing = reflectLevel;
                    }
                    if (reflectUsing > 0 && 0.02D + 0.01D * (reflectUsing / 3) >= Math.random())
                        reflect = true;
                    if (reflect) {
                        if (damaged instanceof Player)
                            enchant.applyEffect(damaged, damager, ((Integer)entry.getValue()).intValue(), event);
                        continue;
                    }
                    enchant.applyEffect(damager, damaged, ((Integer)entry.getValue()).intValue(), event);
                }
            } else {
                for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damager, getItems(damager)).entries())
                    ((CustomEnchantment)entry.getKey()).applyEffect(damager, damaged, ((Integer)entry.getValue()).intValue(), event);
            }
        boolean silenced = damaged.hasMetadata("noDefenseProcs");
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries()) {
            if (silenced && !((CustomEnchantment)entry.getKey()).name().equals("Phoenix"))
                continue;
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, damager, ((Integer)entry.getValue()).intValue(), (EntityDamageEvent)event);
        }
    }

    public int getReflectLevel(LivingEntity player, String key) {
        List<MetadataValue> found = player.getMetadata(key + "_reflect_enchant");
        if (found != null && !found.isEmpty())
            return ((MetadataValue)found.get(0)).asInt();
        return 0;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        if (!(damaged instanceof Player) && (((CraftLivingEntity)damaged).getHandle()).fromMobSpawner)
            return;
        boolean silenced = damaged.hasMetadata("noDefenseProcs");
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries()) {
            if (silenced && !((CustomEnchantment)entry.getKey()).name().equals("Phoenix") &&
                    Math.random() < 0.5D)
                continue;
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, null, ((Integer)entry.getValue()).intValue(), event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageByBlockEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity damaged = (LivingEntity)event.getEntity();
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(damaged, getItems(damaged)).entries())
            ((CustomEnchantment)entry.getKey()).applyDefenseEffect(damaged, null, ((Integer)entry.getValue()).intValue(), (EntityDamageEvent)event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamageBlock(BlockDamageEvent event) {
        if (event.getItemInHand().getType() != Material.DIAMOND_PICKAXE && event
                .getItemInHand().getType() != Material.IRON_PICKAXE)
            return;
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), ((Integer)entry.getValue()).intValue(), (BlockEvent)event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreakBlock(BlockBreakEvent event) {
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyToolEffect(event.getPlayer(), event.getBlock(), ((Integer)entry.getValue()).intValue(), (BlockEvent)event);
        if (event.getBlock().getType() == Material.ENCHANTMENT_TABLE)
            for (TableTask task : this.tasks.values())
                task.restore();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasBlock() && event.hasItem() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) &&
                event.getClickedBlock().getType() == Material.OBSIDIAN)
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItemInHand((LivingEntity)event.getPlayer())).entries())
                ((CustomEnchantment)entry.getKey()).applyMiscEffect(event.getPlayer(), ((Integer)entry.getValue()).intValue(), event);
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getMaxStackSize() == 1 && isArmor(event.getItem()))
            (new EEquip(event.getPlayer())).runTaskLater((Plugin)this.plugin, 1L);
    }

    public static boolean isArmor(ItemStack i) {
        for (Material m : armor) {
            if (i.getType() == m)
                return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEntityEvent event) {
        for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getPlayer(), getItems((LivingEntity)event.getPlayer())).entries())
            ((CustomEnchantment)entry.getKey()).applyEntityEffect(event.getPlayer(), ((Integer)entry.getValue()).intValue(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEquip(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("container.crafting")) {
            if (event.getCurrentItem() == null && event.getCursor() == null)
                return;
            if (!event.isShiftClick()) {
                if (event.getSlotType() != InventoryType.SlotType.ARMOR)
                    return;
            } else if (event.getCursor() == null && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                if (event.getCurrentItem().getMaxStackSize() != 1)
                    return;
            }
            (new EEquip(this.plugin.getServer().getPlayer(event.getWhoClicked().getName()))).runTaskLater((Plugin)this.plugin, 1L);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        (new EEquip(e.getPlayer())).runTaskLater((Plugin)this.plugin, 1L);
    }

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        (new EEquip(event.getPlayer())).runTaskLater((Plugin)this.plugin, 1L);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConnect(PlayerJoinEvent event) {
        EEquip.loadPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDisconnect(PlayerQuitEvent event) {
        EEquip.unequipPlayer(event.getPlayer(), false);
        TableTask found = this.tasks.remove(event.getPlayer().getName());
        if (found != null)
            found.cancel();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() == null)
            return;
        if (event.getEntity().hasMetadata("shootForce") && (
                (MetadataValue)event.getEntity().getMetadata("shootForce").get(0)).asFloat() < 0.75F)
            return;
        if (event.getEntity().getShooter() instanceof Player) {
            Player pShooter = (Player)event.getEntity().getShooter();
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments(pShooter.getItemInHand(), pShooter).entries())
                ((CustomEnchantment)entry.getKey()).applyProjectileEffect((LivingEntity)pShooter, ((Integer)entry.getValue()).intValue(), event);
        } else {
            for (Map.Entry<CustomEnchantment, Integer> entry : (Iterable<Map.Entry<CustomEnchantment, Integer>>)getValidEnchantments((LivingEntity)event.getEntity().getShooter(), getItems((LivingEntity)event.getEntity().getShooter())).entries())
                ((CustomEnchantment)entry.getKey()).applyProjectileEffect((LivingEntity)event.getEntity().getShooter(), ((Integer)entry.getValue()).intValue(), event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (event.getInventory().getType() == InventoryType.ENCHANTING && event.isShiftClick() &&
                this.tasks.containsKey(event.getWhoClicked().getName()))
            ((TableTask)this.tasks.get(event.getWhoClicked().getName())).restore();
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING && event.getPlayer().hasPermission(PermissionNode.TABLE.getNode()))
            this.tasks.put(event.getPlayer().getName(), new TableTask((Plugin)this.plugin, this.plugin.getServer().getPlayer(event.getPlayer().getName())));
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof org.bukkit.inventory.EnchantingInventory &&
                this.tasks.containsKey(event.getPlayer().getName())) {
            ((TableTask)this.tasks.get(event.getPlayer().getName())).restore();
            ((TableTask)this.tasks.get(event.getPlayer().getName())).cancel();
            this.tasks.remove(event.getPlayer().getName());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        if (!event.getEnchanter().hasPermission(PermissionNode.TABLE.getNode()) || !this.tasks.containsKey(event.getEnchanter().getName()))
            return;
        event.setCancelled(true);
        if (EnchantmentAPI.getEnchantments(event.getItem()).size() > 0)
            return;
        if (event.getEnchanter().getLevel() < event.getExpLevelCost() && event
                .getEnchanter().getGameMode() != GameMode.CREATIVE)
            return;
        ItemStack storedItem = ((TableTask)this.tasks.get(event.getEnchanter().getName())).stored;
        if (storedItem.getAmount() > 1) {
            storedItem.setAmount(storedItem.getAmount() - 1);
            event.getEnchanter().getInventory().addItem(new ItemStack[] { storedItem.clone() });
            storedItem.setAmount(1);
        }
        int maxEnchants = ((RootConfig)this.plugin.getModuleForClass(RootConfig.class)).getInt((ConfigNode)RootNode.MAX_ENCHANTS);
        EnchantResult result = EEnchantTable.enchant(event.getEnchanter(), storedItem, event.getExpLevelCost(), maxEnchants);
        ItemStack item = result.getItem();
        if (item == null)
            return;
        event.getInventory().clear();
        event.getEnchantsToAdd().clear();
        boolean randomName = ((RootConfig)this.plugin.getModuleForClass(RootConfig.class)).getBoolean((ConfigNode)RootNode.ITEM_LORE);
        if (randomName && event.getEnchanter().hasPermission(PermissionNode.NAMES.getNode())) {
            String name = "";
            int random = (int)(Math.random() * 14.0D) + 49;
            if (random > 57)
                random += 39;
            String format = this.plugin.getConfig().getStringList(LanguageNode.NAME_FORMAT.getFullPath()).get(0);
            format = format.replace("{adjective}", this.plugin.getAdjective((result.getLevel() / 11 + 1 > 4) ? 4 : (result.getLevel() / 11 + 1)));
            format = format.replace("{weapon}", this.plugin.getWeapon(item.getType().name()));
            format = format.replace("{suffix}", this.plugin.getSuffix(result.getFirstEnchant()));
            name = name + (char)random + format;
            ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : this.plugin.getServer().getItemFactory().getItemMeta(item.getType());
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        event.getInventory().addItem(new ItemStack[] { item });
        if (event.getEnchanter().getGameMode() != GameMode.CREATIVE)
            event.getEnchanter().setLevel(event.getEnchanter().getLevel() - event.getExpLevelCost());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareEnchant(PrepareItemEnchantEvent event) {
        if (event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore() && event
                .getItem().getItemMeta().getLore().contains(((TableTask)this.tasks.get(event.getEnchanter().getName())).cantEnchant))
            event.setCancelled(true);
    }

    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(LivingEntity le, ArrayList<ItemStack> items) {
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        for (ItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null ||
                    !meta.hasLore())
                continue;
            for (String lore : meta.getLore()) {
                String name = ENameParser.parseName(lore);
                int level = ENameParser.parseLevel(lore);
                if (name != null &&
                        level != 0 &&
                        EnchantmentAPI.isRegistered(name)) {
                    CustomEnchantment ce = EnchantmentAPI.getEnchantment(name);
                    int tier = TieredEnchantments.getEnchantmentTier(name);

                    if (le.getWorld().getEnvironment() == World.Environment.THE_END && tier > 5)
                        continue;
                    if (arrayListMultimap.containsKey(ce) && !enchantmentStacks(ce))
                        if (((Integer)arrayListMultimap.get(ce).toArray()[0]).intValue() < level) {
                            arrayListMultimap.removeAll(ce);
                        } else {
                            continue;
                        }
                    arrayListMultimap.put(ce, Integer.valueOf(level));
                }
            }
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap;
    }

    @Deprecated
    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(ArrayList<ItemStack> items) {
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        for (ItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            if (meta == null ||
                    !meta.hasLore())
                continue;
            for (String lore : meta.getLore()) {
                String name = ENameParser.parseName(lore);
                int level = ENameParser.parseLevel(lore);
                if (name != null &&
                        level != 0 &&
                        EnchantmentAPI.isRegistered(name)) {
                    CustomEnchantment ce = EnchantmentAPI.getEnchantment(name);
                    if (arrayListMultimap.containsKey(ce) && !enchantmentStacks(ce))
                        if (((Integer)arrayListMultimap.get(ce).toArray()[0]).intValue() < level) {
                            arrayListMultimap.removeAll(ce);
                        } else {
                            continue;
                        }
                    arrayListMultimap.put(ce, Integer.valueOf(level));
                }
            }
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap;
    }

    public static Multimap<CustomEnchantment, Integer> getValidEnchantments(ItemStack item, Player p) {
        ArrayListMultimap arrayListMultimap1 = ArrayListMultimap.create();
        ArrayListMultimap arrayListMultimap2 = ArrayListMultimap.create();
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
        if (!meta.hasLore())
            return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
        int maxEnchantments = EnchantmentBook.getMaxUsableEnchantAllowed(p);
        for (String lore : meta.getLore()) {
            String name = ENameParser.parseName(lore);
            int level = ENameParser.parseLevel(lore);
            if (name != null &&
                    level != 0 &&
                    EnchantmentAPI.isRegistered(name)) {
                int tier = TieredEnchantments.getEnchantmentTier(name);

                if (p.getWorld().getEnvironment() == World.Environment.THE_END && tier > 5)
                    continue;
                CustomEnchantment ce = EnchantmentAPI.getEnchantment(name);
                if (arrayListMultimap1.containsKey(ce) && !enchantmentStacks(ce))
                    if (((Integer)arrayListMultimap1.get(ce).toArray()[0]).intValue() < level) {
                        arrayListMultimap1.removeAll(ce);
                    } else {
                        continue;
                    }
                arrayListMultimap1.put(ce, Integer.valueOf(level));
            }
        }
        if (!p.getWorld().getName().startsWith("world_duels") &&
                arrayListMultimap1.size() > maxEnchantments) {
            int maxRarity = 0;
            while (arrayListMultimap2.size() < maxEnchantments) {
                maxRarity++;
                for (Map.Entry<CustomEnchantment, Integer> ce : (Iterable<Map.Entry<CustomEnchantment, Integer>>)arrayListMultimap1.entries()) {
                    if (arrayListMultimap2.size() == maxEnchantments)
                        break;
                    if (TieredEnchantments.getEnchantmentTier(((CustomEnchantment)ce.getKey()).name()) <= maxRarity && !arrayListMultimap2.containsKey(ce.getKey()))
                        arrayListMultimap2.put(ce.getKey(), ce.getValue());
                }
            }
            // todo; fix this?
//            if (!p.hasMetadata("lastWeaponAlert") || System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastWeaponAlert").get(0)).asLong() > 60000L) {
//                String disabledEnchantments = "";
//                int curRarity = 6;
//                while (curRarity-- > 0) {
//                    for (CustomEnchantment ce : arrayListMultimap1.keySet()) {
//                        if (!arrayListMultimap2.containsKey(ce) && TieredEnchantments.getEnchantmentTier(ce.name()) == curRarity)
//                            disabledEnchantments = disabledEnchantments + TieredEnchantments.getTierColor(ce) + ce.name() + ", ";
//                    }
//                }
//                if (disabledEnchantments.endsWith(", "))
//                    disabledEnchantments = disabledEnchantments.substring(0, disabledEnchantments.length() - 2);
//                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.RED + " You are not skilled enough to fully use your weapon.");
//                p.sendMessage(ChatColor.RED.toString() + ChatColor.UNDERLINE + "Locked:" + ChatColor.GRAY + " " + disabledEnchantments);
//                if (maxEnchantments < 15)
//                    p.sendMessage(ChatColor.GRAY + "** Purchase a Rank at " + ChatColor.RED + ChatColor.UNDERLINE + "buy.cosmicpvp.com" + ChatColor.GRAY + " to increase the maximum custom enchantments you can use!");
//                p.setMetadata("lastWeaponAlert", (MetadataValue)new FixedMetadataValue((Plugin)EnchantmentAPI.get(), Long.valueOf(System.currentTimeMillis())));
//            }
            return (Multimap<CustomEnchantment, Integer>)arrayListMultimap2;
        }
        return (Multimap<CustomEnchantment, Integer>)arrayListMultimap1;
    }

    private static boolean enchantmentStacks(CustomEnchantment ce) {
        String n = ce.name();
        if (n.equals("Armored") || n.equals("Paladin Armored") || n.equals("Valor") || n.equals("Tank") || n.equals("Angelic") || n.equals("Heavy") || n.equals("Martyr Valor") || n.equals("Reinforced Tank"))
            return true;
        if (n.equals("Anti Gravity") || n
                .equals("Aquatic") || n
                .equals("Drunk") || n
                .equals("Gears") || n
                .equals("Glowing") || n
                .equals("Obsidianshield") || n
                .equals("Overload") || n
                .equals("Springs"))
            return true;
        return false;
    }

    private ArrayList<ItemStack> getItems(LivingEntity entity) {
        ItemStack weapon = entity.getEquipment().getItemInHand();
        ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(entity.getEquipment().getArmorContents()));
        items.add(weapon);
        return items;
    }

    private ArrayList<ItemStack> getItemInHand(LivingEntity entity) {
        ItemStack weapon = entity.getEquipment().getItemInHand();
        return new ArrayList<>(Arrays.asList(new ItemStack[] { weapon }));
    }
}
