/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  de.dustplanet.silkspawners.SilkSpawners
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicCustomCreepers.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import de.dustplanet.silkspawners.SilkSpawners;
import de.dustplanet.util.SilkUtil;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static org.arkhamnetwork.Arkkit.patches.NoWaterProtSpawners.su;

final class NoWaterProtSpawnersListener
        implements Listener {
    int explosionDropChance = 0;
    int eggDropChance = 0;
    SilkSpawners ssPlugin = (SilkSpawners) Bukkit.getPluginManager().getPlugin("SilkSpawners");

    NoWaterProtSpawnersListener() {
        this.explosionDropChance = this.ssPlugin.config.getInt("explosionDropChance", 30);
        this.eggDropChance = this.ssPlugin.config.getInt("eggDropChance", 100);
    }


}

