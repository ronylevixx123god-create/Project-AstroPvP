/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.FactionChunkOwnershipCache
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.BlockCrops
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Furnace
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockGrowEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.FoodLevelChangeEvent
 *  org.bukkit.event.inventory.FurnaceBurnEvent
 *  org.bukkit.event.inventory.FurnaceSmeltEvent
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 */
package me.ifamasssxd.factionupgrades.listeners;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionChunkOwnershipCache;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCrops;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Random;

public class PerkListener
implements Listener {
    public PerkListener() {
        FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(Material.TNT), Material.SULPHUR);
        Bukkit.addRecipe((Recipe)recipe);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void EXPHarvest(EntityDeathEvent e) {
        int xpHarvestTier;
        if (e.getDroppedExp() <= 0) {
            return;
        }
        FLocation fl = new FLocation(e.getEntity().getLocation());
        Faction f = Board.getFactionAt((FLocation)fl);
        if (f.isNormal() && (xpHarvestTier = FactionUpgradeAPI.getPerkLevel(f, FactionUpgrade.EXP_HARVEST)) > 0) {
            double multi = 1.0;
            if (xpHarvestTier == 1) {
                multi = 1.05;
            }
            if (xpHarvestTier == 2) {
                multi = 1.15;
            }
            if (xpHarvestTier == 3) {
                multi = 1.25;
            }
            e.setDroppedExp((int)((double)e.getDroppedExp() * multi));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void MonsterFarm(CreatureSpawnEvent e) {
        int monsterFarmTier;
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER) {
            return;
        }
        Faction f = FactionChunkOwnershipCache.getCachedFactionChunkOwnership((Location)e.getLocation());
        if (f == null) {
            FLocation fl = new FLocation(e.getEntity().getLocation());
            f = Board.getFactionAt((FLocation)fl);
        }
        if ((monsterFarmTier = FactionUpgradeAPI.getPerkLevel(f, FactionUpgrade.MONSTER_FARM)) > 0) {
            double doubleSpawnChance = 0.0;
            if (monsterFarmTier == 1) {
                doubleSpawnChance = 0.05;
            }
            if (monsterFarmTier == 2) {
                doubleSpawnChance = 0.15;
            }
            if (monsterFarmTier == 3) {
                doubleSpawnChance = 0.25;
            }
            if (monsterFarmTier == 4) {
                doubleSpawnChance = 0.5;
            }
            if (Math.random() < doubleSpawnChance) {
                e.getEntity().setMetadata("monsterAmount", (MetadataValue)new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("Arkkit"), (Object)(e.getEntity().hasMetadata("monsterAmount") ? ((MetadataValue)e.getEntity().getMetadata("monsterAmount").get(0)).asInt() + 2 : 2)));
            }
        }
    }

    @EventHandler
    public void NaturalGrowth(BlockGrowEvent e) {
        FLocation fl = new FLocation(e.getBlock().getLocation());
        Faction f = Board.getFactionAt((FLocation)fl);
        int naturalGrowthTier = FactionUpgradeAPI.getPerkLevel(f, FactionUpgrade.NATURAL_GROWTH);
        if (naturalGrowthTier > 0) {
            org.bukkit.block.Block b;
            CraftWorld cw;
            Block nmsBlock;
            double d = 0.0;
            if (naturalGrowthTier == 1) {
                d = 0.25;
            }
            if (naturalGrowthTier == 1) {
                d = 0.5;
            }
            if (naturalGrowthTier == 1) {
                d = 1.0;
            }
            naturalGrowthTier /= 3;
            if (Math.random() < (double)naturalGrowthTier && (nmsBlock = (Block) (cw = (CraftWorld)(b = e.getBlock()).getWorld()).getHandle().getType(new BlockPosition(b.getX(), b.getY(), b.getZ()))) instanceof BlockCrops) {
                BlockCrops bc = (BlockCrops)nmsBlock;
                bc.m();
            }
        }
    }

    @EventHandler
    public void WellFed(FoodLevelChangeEvent e) {
        Player p = (Player)e.getEntity();
        if (e.getFoodLevel() == p.getFoodLevel() - 1) {
            int level = FactionUpgradeAPI.getPerkLevelIfAllowed(p, FactionUpgrade.HEROIC_WELL_FED);
            if (level > 0) {
                double chance = 0;
                double d = level == 1 ? 0.3 : (level == 2 ? 0.35 : (level == 3 ? 0.45 : (chance = level == 4 ? 0.5 : 0.0)));
                if (Math.random() < chance) {
                    e.setCancelled(true);
                    p.setSaturation(Math.max(0.0f, p.getSaturation() * (1.0f - (float)chance)));
                }
                return;
            }
            int wellFedPerk = FactionUpgradeAPI.getPerkLevel(p, FactionUpgrade.WELL_FED);
            if (wellFedPerk > 0) {
                double skipHungerChance = 0.0;
                if (wellFedPerk == 1) {
                    skipHungerChance = 0.05;
                }
                if (wellFedPerk == 2) {
                    skipHungerChance = 0.1;
                }
                if (wellFedPerk == 3) {
                    skipHungerChance = 0.15;
                }
                if (wellFedPerk == 4) {
                    skipHungerChance = 0.25;
                }
                if (Math.random() < skipHungerChance) {
                    e.setCancelled(true);
                    p.setSaturation(Math.max(0.0f, p.getSaturation() * (1.0f - (float)skipHungerChance)));
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void ExplosiveExpert(FurnaceSmeltEvent e) {
        if (e.getSource().getType() == Material.SULPHUR) {
            FLocation fl = new FLocation(e.getBlock().getLocation());
            Faction f = Board.getFactionAt((FLocation)fl);
            int explosiveExpertTier = FactionUpgradeAPI.getPerkLevel(f, FactionUpgrade.EXPLOSIVES_EXPERT);
            int sulphurCost = 0;
            if (explosiveExpertTier == 1) {
                sulphurCost = 12;
            }
            if (explosiveExpertTier == 2) {
                sulphurCost = 9;
            }
            if (explosiveExpertTier == 3 || explosiveExpertTier == 4) {
                sulphurCost = 7;
            }
            Furnace furnace = (Furnace)e.getBlock().getState();
            if (sulphurCost == 0 || e.getSource().getAmount() < sulphurCost) {
                e.setCancelled(true);
                e.setResult((ItemStack)null);
                furnace.setBurnTime((short)0);
                return;
            }
            ItemStack gp = e.getSource();
            if (gp.getAmount() == sulphurCost) {
                furnace.getInventory().setSmelting((ItemStack)null);
            } else {
                gp.setAmount(gp.getAmount() - sulphurCost);
                furnace.getInventory().setSmelting(gp);
            }
            if (explosiveExpertTier == 4 && furnace.getInventory().getResult() == null && Math.random() < 0.005) {
                int x = new Random().nextInt(3);
                if (x == 0) {
                    e.setResult(TNTTypes.getTNTItem((TNTTypes)TNTTypes.Silent));
                }
                if (x == 1) {
                    e.setResult(TNTTypes.getTNTItem((TNTTypes)TNTTypes.Lethal));
                }
                if (x == 2) {
                    e.setResult(TNTTypes.getTNTItem((TNTTypes)TNTTypes.Huge));
                }
            }
        }
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        if (e.getBlock().getState() instanceof Furnace) {
            Furnace furnace = (Furnace)e.getBlock().getState();
            if (furnace != null && furnace.getInventory() != null && furnace.getInventory().getSmelting() != null && furnace.getInventory().getSmelting().getType() == Material.SULPHUR) {
                FLocation fl = new FLocation(e.getBlock().getLocation());
                Faction f = Board.getFactionAt((FLocation)fl);
                int explosiveExpertTier = FactionUpgradeAPI.getPerkLevel(f, FactionUpgrade.EXPLOSIVES_EXPERT);
                if (explosiveExpertTier == 0) {
                    e.setCancelled(true);
                    return;
                }
                int sulphurCost = 0;
                if (explosiveExpertTier == 1) {
                    sulphurCost = 13;
                }
                if (explosiveExpertTier == 2) {
                    sulphurCost = 10;
                }
                if (explosiveExpertTier == 3 || explosiveExpertTier == 4) {
                    sulphurCost = 8;
                }
                if (furnace.getInventory().getSmelting().getAmount() < sulphurCost) {
                    e.setCancelled(true);
                }
            }
        } else if (e.getBlock().getType() != Material.AIR) {
            Bukkit.getLogger().info("[FactionUpgrades] Invalid FurnaceBurnEvent at " + (Object)e.getBlock().getLocation() + " TYPE " + e.getBlock().getType().name());
        }
    }
}

