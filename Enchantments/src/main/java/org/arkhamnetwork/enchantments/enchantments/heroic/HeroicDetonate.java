package org.arkhamnetwork.enchantments.enchantments.heroic;

import java.util.concurrent.*;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.BlockUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import com.rit.sucy.*;
import org.bukkit.event.block.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.material.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.inventory.*;
import org.arkhamnetwork.enchantments.libs.*;
import org.bukkit.event.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.arkhamnetwork.enchantments.mastery.*;
import com.massivecraft.factions.struct.*;
import me.ifamasssxd.cosmicguishop.*;
import org.bukkit.*;
import com.massivecraft.factions.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import java.util.*;
import com.google.common.collect.*;

public class HeroicDetonate extends CustomEnchantment
{
    private static ConcurrentHashMap<UUID, BlockFace> loggedBlockFaces;
    protected static List<Material> pickaxeMaterials;
    protected static List<Material> spadeMaterials;
    protected static List<Material> blacklistedBlockDrops;
    
    public HeroicDetonate() {
        super("Atomic Detonate", GeneralUtil.tools, 6);
        this.max = 4;
        this.base = 10.0;
        this.interval = 10.0;
        HeroicDetonate.pickaxeMaterials = new ArrayList<Material>(Arrays.asList(Material.SANDSTONE, Material.STONE, Material.COBBLESTONE, Material.MOSSY_COBBLESTONE, Material.SMOOTH_BRICK, Material.OBSIDIAN, Material.MOB_SPAWNER, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.EMERALD_ORE, Material.NETHERRACK));
        HeroicDetonate.spadeMaterials = new ArrayList<Material>(Arrays.asList(Material.DIRT, Material.SAND, Material.GRAVEL, Material.GRASS, Material.MYCEL, Material.CLAY));
        Bukkit.getPluginManager().registerEvents((Listener)new AtomicDetonateListener(), (Plugin)Enchantments.getInstance());
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Detonate");
        }, 2L);
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final BlockBreakEvent bbe = (BlockBreakEvent)event;
            if (!bbe.isCancelled()) {
                final Material type = block.getType();
                if (type == Material.REDSTONE_COMPARATOR || type == Material.SPONGE || type == Material.REDSTONE_COMPARATOR_ON || type == Material.REDSTONE_COMPARATOR_OFF) {
                    return;
                }
                this.explode(player, bbe.getBlock(), enchantLevel);
            }
        }
    }
    
    public static void updateSavedBlockFace(final Player p, final BlockFace b) {
        HeroicDetonate.loggedBlockFaces.put(p.getUniqueId(), b);
    }
    
    private BlockFace getBlockFace(final Player p) {
        return HeroicDetonate.loggedBlockFaces.containsKey(p.getUniqueId()) ? HeroicDetonate.loggedBlockFaces.get(p.getUniqueId()) : null;
    }
    
    private void explode(final Player pl, final Block b, final int level) {
        final ItemStack pickaxeItem = pl.getItemInHand();
        final boolean hasAutoSmelt = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Auto Smelt");
        final boolean hasFuse = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Fuse");
        final boolean telepathy = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Telepathy");
        final boolean autoSell = EnchantmentAPI.itemHasEnchantment(pl.getItemInHand(), "Auto Sell");
        final boolean pickaxe = pl.getItemInHand().getType().name().contains("_PICKAXE");
        final boolean spade = pl.getItemInHand().getType().name().contains("_SPADE");
        final boolean explosivesExpert = pl.hasMetadata("explosivesExpertEnchantment");
        double autoSellMoney = 0.0;
        final boolean affectedLowerBlocks = b.getLocation().getY() <= 7.0;
        final Set<Block> blocks = BlockUtils.getAtomicSquare(b, this.getBlockFace(pl), level);
        blocks.add(b);
        boolean brokeBlock = false;
        boolean tookDurability = false;
        FPlayer fp = null;
        for (final Block bl : blocks) {
            final Material m = bl.getType();
            if (m != Material.LADDER && m != Material.GLOWSTONE && m != Material.BEDROCK && m != Material.WATER && m != Material.STATIONARY_WATER && m != Material.LAVA && m != Material.STATIONARY_LAVA && m != Material.SIGN && m != Material.SIGN_POST && m != Material.AIR && m != Material.OBSIDIAN && m != Material.SPONGE && m != Material.IRON_DOOR && m != Material.IRON_DOOR_BLOCK && m != Material.WOODEN_DOOR && m != Material.WOOD_DOOR && m != Material.HOPPER && m != Material.ANVIL && m != Material.REDSTONE_COMPARATOR && m != Material.REDSTONE_COMPARATOR_OFF) {
                if (m == Material.REDSTONE_COMPARATOR_ON) {
                    continue;
                }
                if (HeroicDetonate.pickaxeMaterials.contains(m) && !pickaxe && !explosivesExpert) {
                    continue;
                }
                if (HeroicDetonate.spadeMaterials.contains(m) && !spade && !explosivesExpert) {
                    continue;
                }
                if (bl.getLocation().getY() <= 0.0) {
                    continue;
                }
                if (affectedLowerBlocks && bl.getLocation().getY() > 200.0) {
                    continue;
                }
                final Block above = bl.getRelative(BlockFace.UP);
                final Material aboveType = above.getType();
                if (aboveType == Material.REDSTONE_COMPARATOR || aboveType == Material.REDSTONE_COMPARATOR_OFF || aboveType == Material.REDSTONE_COMPARATOR_ON || aboveType == Material.DIODE || aboveType == Material.DIODE_BLOCK_OFF) {
                    continue;
                }
                if (aboveType == Material.DIODE_BLOCK_ON) {
                    continue;
                }
                final FLocation floc = new FLocation(bl.getLocation());
                final Faction f = Board.getFactionAt(floc);
                if (!f.isNone()) {
                    final FactionWrapper wrapper = FactionWrappers.get(f);
                    if (fp == null) {
                        fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                    }
                    final FactionPermission permission = FactionPermission.getFromBlock(new MaterialData(m));
                    if (permission != null && !f.hasPermission(fp, floc, permission, "interact")) {
                        continue;
                    }
                    if (wrapper.isLocationTracked(floc)) {
                        if (!wrapper.doesPlayerHaveChunkAccess(floc, fp, true)) {
                            continue;
                        }
                    }
                    else {
                        if (!fp.getRelationToLocation().isMember()) {
                            continue;
                        }
                        if (!fp.getFactionId().equals(f.getId())) {
                            continue;
                        }
                    }
                }
                if (f.isWarZone()) {
                    continue;
                }
                EntityType spawnerType = null;
                if (m == Material.MOB_SPAWNER) {
                    final BlockState state = bl.getState();
                    if (state instanceof CreatureSpawner) {
                        spawnerType = ((CreatureSpawner)state).getSpawnedType();
                    }
                }
                ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, bl.getLocation().add(0.0, 0.5, 0.0), 0.025f, 1);
                if (bl.getDrops(pl.getItemInHand()).size() > 0) {
                    ItemStack loot = null;
                    try {
                        loot = (ItemStack)bl.getDrops(pl.getItemInHand()).toArray()[0];
                    }
                    catch (Exception err) {
                        continue;
                    }
                    final EnchantBreakBlockEvent enchantEvent = new EnchantBreakBlockEvent(pl, bl, m);
                    Bukkit.getPluginManager().callEvent((Event)enchantEvent);
                    if (enchantEvent.isCancelled()) {
                        continue;
                    }
                    if (loot.getType().name().startsWith("REDSTONE_COMPARATOR")) {
                        loot.setAmount(1);
                    }
                    if (hasAutoSmelt && (m == Material.IRON_ORE || m == Material.GOLD_ORE) && hasFuse) {
                        if (m == Material.IRON_ORE) {
                            loot.setType(Material.IRON_INGOT);
                        }
                        if (m == Material.GOLD_ORE) {
                            loot.setType(Material.GOLD_INGOT);
                        }
                        final CustomEnchantment enchantment = EnchantmentAPI.getEnchantment("Auto Smelt");
                        final Map<CustomEnchantment, Integer> enchants = (Map<CustomEnchantment, Integer>)EnchantmentAPI.getEnchantments(pl.getItemInHand());
                        if (enchantment != null && enchants != null) {
                            final Integer autoSmeltLevel = enchants.get(enchantment);
                            if (autoSmeltLevel != null) {
                                loot.setAmount((int)autoSmeltLevel);
                            }
                        }
                    }
                    brokeBlock = true;
                    CoreProtectUtils.logBreak(pl.getName(), bl.getLocation(), m.getId(), bl.getData());
                    bl.setTypeIdAndData(0, (byte)0, false);
                    if (loot.getType() == Material.RAILS) {
                        continue;
                    }
                    final double price = autoSell ? MasteryAutoSell.getSellPrice(loot.getData()) : 0.0;
                    if (price > 0.0) {
                        autoSellMoney += price;
                    }
                    else {
                        if (HeroicDetonate.blacklistedBlockDrops.contains(m)) {
                            continue;
                        }
                        if (telepathy && pl.getInventory().firstEmpty() != -1) {
                            pl.getInventory().addItem(new ItemStack[] { loot });
                        }
                        else {
                            pl.getWorld().dropItem(pl.getLocation(), loot);
                        }
                    }
                }
                else {
                    if (HeroicDetonate.blacklistedBlockDrops.contains(m)) {
                        brokeBlock = true;
                        CoreProtectUtils.logBreak(pl.getName(), bl.getLocation(), m.getId(), bl.getData());
                        bl.setTypeIdAndData(0, (byte)0, false);
                        continue;
                    }
                    CoreProtectUtils.logBreak(pl.getName(), bl.getLocation(), m.getId(), bl.getData());
                    bl.breakNaturally(pl.getItemInHand());
                    tookDurability = true;
                }
                if (m != Material.MOB_SPAWNER || f == null || !f.isNormal() || spawnerType == null) {
                    continue;
                }
                try {
                    P.p.blockListener.handleSpawnerUpdate(f, pl, new ItemStack(m, 1, spawnerType.getTypeId()), LogTimer.TimerSubType.SPAWNER_BREAK);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (autoSellMoney > 0.0) {
            CosmicGUIShop.getEconomy().depositPlayer((OfflinePlayer)pl, autoSellMoney);
        }
        if (brokeBlock && !tookDurability && pickaxe) {
            if (pickaxeItem.getDurability() >= pickaxeItem.getType().getMaxDurability() - 1) {
                if (pickaxeItem.equals((Object)pl.getItemInHand())) {
                    pl.setItemInHand((ItemStack)null);
                }
                else {
                    pl.getInventory().removeItem(new ItemStack[] { pickaxeItem });
                }
                pl.updateInventory();
                pl.playSound(pl.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.0f);
                Bukkit.getLogger().info("Destroying pickaxe " + pickaxeItem + " for " + pl.getName() + " due to durability: " + pickaxeItem.getDurability());
            }
            else {
                pickaxeItem.setDurability((short)(pickaxeItem.getDurability() + 1));
            }
        }
    }
    
    static {
        HeroicDetonate.loggedBlockFaces = new ConcurrentHashMap<UUID, BlockFace>();
        HeroicDetonate.blacklistedBlockDrops = (List<Material>)Lists.newArrayList(new Material[] { Material.COBBLESTONE, Material.STONE, Material.GRAVEL, Material.DIRT, Material.GRASS, Material.SANDSTONE, Material.NETHERRACK });
    }
}
