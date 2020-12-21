/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.plugins;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.PlayerVaultGUI;
import org.arkhamnetwork.Arkkit.patches.AccountSecurity;
import org.arkhamnetwork.Arkkit.patches.AdminEvents;
import org.arkhamnetwork.Arkkit.patches.AdminWarps;
import org.arkhamnetwork.Arkkit.patches.AlertSystem;
import org.arkhamnetwork.Arkkit.patches.AntiBlockGlitch;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.patches.AntiDupe;
import org.arkhamnetwork.Arkkit.patches.AntiExploit;
import org.arkhamnetwork.Arkkit.patches.AntiGodAppleCraft;
import org.arkhamnetwork.Arkkit.patches.AntiHack;
import org.arkhamnetwork.Arkkit.patches.AntiInventoryHijack;
import org.arkhamnetwork.Arkkit.patches.AntiInvisibility;
import org.arkhamnetwork.Arkkit.patches.AntiLag;
import org.arkhamnetwork.Arkkit.patches.AntiLootSteal;
import org.arkhamnetwork.Arkkit.patches.AntiMinecart;
import org.arkhamnetwork.Arkkit.patches.AntiPistonLag;
import org.arkhamnetwork.Arkkit.patches.AntiRedstoneLag;
import org.arkhamnetwork.Arkkit.patches.AntiScoreboardLeak;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBot;
import org.arkhamnetwork.Arkkit.patches.AntiTileEntityLag;
import org.arkhamnetwork.Arkkit.patches.AsyncMonsters;
import org.arkhamnetwork.Arkkit.patches.AsyncSpigot;
import org.arkhamnetwork.Arkkit.patches.AutoCraftTNT;
import org.arkhamnetwork.Arkkit.patches.AutoEntityClear;
import org.arkhamnetwork.Arkkit.patches.AutoReboot;
import org.arkhamnetwork.Arkkit.patches.AutoRespawn;
import org.arkhamnetwork.Arkkit.patches.Balance;
import org.arkhamnetwork.Arkkit.patches.BlockValue;
import org.arkhamnetwork.Arkkit.patches.BoatBlocker;
import org.arkhamnetwork.Arkkit.patches.BulkSaving;
import org.arkhamnetwork.Arkkit.patches.Bump;
import org.arkhamnetwork.Arkkit.patches.BuycraftSecurity;
import org.arkhamnetwork.Arkkit.patches.ChatBubbles;
import org.arkhamnetwork.Arkkit.patches.ChatLimiter;
import org.arkhamnetwork.Arkkit.patches.Christmas;
import org.arkhamnetwork.Arkkit.patches.ChunkLoadFlush;
import org.arkhamnetwork.Arkkit.patches.ClearInventoryConfirm;
import org.arkhamnetwork.Arkkit.patches.CommandBlocker;
import org.arkhamnetwork.Arkkit.patches.CommandItems;
import org.arkhamnetwork.Arkkit.patches.CondenseCommand;
import org.arkhamnetwork.Arkkit.patches.ContentCreator;
import org.arkhamnetwork.Arkkit.patches.CoreProtectConfig;
import org.arkhamnetwork.Arkkit.patches.CosmicClientHelper;
import org.arkhamnetwork.Arkkit.patches.CustomBroadcast;
import org.arkhamnetwork.Arkkit.patches.CustomMOTD;
import org.arkhamnetwork.Arkkit.patches.DeathMessages;
import org.arkhamnetwork.Arkkit.patches.Deathpoint;
import org.arkhamnetwork.Arkkit.patches.DoubleDrops;
import org.arkhamnetwork.Arkkit.patches.DoubleXp;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.Arkkit.patches.DropperBlocker;
import org.arkhamnetwork.Arkkit.patches.DynamicMaxPlayers;
import org.arkhamnetwork.Arkkit.patches.EULA;
import org.arkhamnetwork.Arkkit.patches.Easter;
import org.arkhamnetwork.Arkkit.patches.EnchantmentAnvilFix;
import org.arkhamnetwork.Arkkit.patches.EndDragon;
import org.arkhamnetwork.Arkkit.patches.EndGrinding;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.Arkkit.patches.EntityCleaner;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomes;
import org.arkhamnetwork.Arkkit.patches.EssentialsKitViewer;
import org.arkhamnetwork.Arkkit.patches.EssentialsWarpAliases;
import org.arkhamnetwork.Arkkit.patches.EssentialsWarpUI;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.arkhamnetwork.Arkkit.patches.ExpOrbs;
import org.arkhamnetwork.Arkkit.patches.ExtraMute;
import org.arkhamnetwork.Arkkit.patches.FactionCornerClaim;
import org.arkhamnetwork.Arkkit.patches.FactionTagReserve;
import org.arkhamnetwork.Arkkit.patches.FactionTeleportSanity;
import org.arkhamnetwork.Arkkit.patches.FakeVanish;
import org.arkhamnetwork.Arkkit.patches.FallbackServers;
import org.arkhamnetwork.Arkkit.patches.FatBuckets;
import org.arkhamnetwork.Arkkit.patches.FillCommand;
import org.arkhamnetwork.Arkkit.patches.FinalBattle;
import org.arkhamnetwork.Arkkit.patches.ForceSpawn;
import org.arkhamnetwork.Arkkit.patches.Gamble;
import org.arkhamnetwork.Arkkit.patches.GiveAll;
import org.arkhamnetwork.Arkkit.patches.GlobalGamemode;
import org.arkhamnetwork.Arkkit.patches.GoldenAppleSickness;
import org.arkhamnetwork.Arkkit.patches.GoldenKnife;
import org.arkhamnetwork.Arkkit.patches.HackerHub;
import org.arkhamnetwork.Arkkit.patches.Halloween;
import org.arkhamnetwork.Arkkit.patches.HideLore;
import org.arkhamnetwork.Arkkit.patches.HideStream;
import org.arkhamnetwork.Arkkit.patches.HitMe;
import org.arkhamnetwork.Arkkit.patches.HomeGUI;
import org.arkhamnetwork.Arkkit.patches.HotbarArmorSwap;
import org.arkhamnetwork.Arkkit.patches.HubCommand;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.arkhamnetwork.Arkkit.patches.ItemNametag;
import org.arkhamnetwork.Arkkit.patches.ItemTracking;
import org.arkhamnetwork.Arkkit.patches.ItemframeGodmode;
import org.arkhamnetwork.Arkkit.patches.JulyFreedom;
import org.arkhamnetwork.Arkkit.patches.KitLegacy;
import org.arkhamnetwork.Arkkit.patches.LavaSponge;
import org.arkhamnetwork.Arkkit.patches.LoreTag;
import org.arkhamnetwork.Arkkit.patches.MaxFlyHeight;
import org.arkhamnetwork.Arkkit.patches.MaxHeightLogic;
import org.arkhamnetwork.Arkkit.patches.McmmoCredits;
import org.arkhamnetwork.Arkkit.patches.MoreBuckets;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners;
import org.arkhamnetwork.Arkkit.patches.NameSanity;
import org.arkhamnetwork.Arkkit.patches.Near;
import org.arkhamnetwork.Arkkit.patches.NerfExpDrops;
import org.arkhamnetwork.Arkkit.patches.NerfNetherSpawners;
import org.arkhamnetwork.Arkkit.patches.NetherGlitchFix;
import org.arkhamnetwork.Arkkit.patches.NetherPortalTrap;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.arkhamnetwork.Arkkit.patches.NoArrowBounce;
import org.arkhamnetwork.Arkkit.patches.NoBeds;
import org.arkhamnetwork.Arkkit.patches.NoBookSign;
import org.arkhamnetwork.Arkkit.patches.NoCaveSpiders;
import org.arkhamnetwork.Arkkit.patches.NoCombatFlying;
import org.arkhamnetwork.Arkkit.patches.NoCursorDrop;
import org.arkhamnetwork.Arkkit.patches.NoDupeExploit;
import org.arkhamnetwork.Arkkit.patches.NoEnchantmentTableBooks;
import org.arkhamnetwork.Arkkit.patches.NoExplosionPhysics;
import org.arkhamnetwork.Arkkit.patches.NoFallRegions;
import org.arkhamnetwork.Arkkit.patches.NoFireTicking;
import org.arkhamnetwork.Arkkit.patches.NoHoppers;
import org.arkhamnetwork.Arkkit.patches.NoHorseRide;
import org.arkhamnetwork.Arkkit.patches.NoLavaFlow;
import org.arkhamnetwork.Arkkit.patches.NoLavaFlowRegion;
import org.arkhamnetwork.Arkkit.patches.NoLoginQueue;
import org.arkhamnetwork.Arkkit.patches.NoMCMMODisarm;
import org.arkhamnetwork.Arkkit.patches.NoMCMMOLimits;
import org.arkhamnetwork.Arkkit.patches.NoMobGriefing;
import org.arkhamnetwork.Arkkit.patches.NoMobMcmmo;
import org.arkhamnetwork.Arkkit.patches.NoNaturalMobs;
import org.arkhamnetwork.Arkkit.patches.NoNewChunks;
import org.arkhamnetwork.Arkkit.patches.NoSafezoneMobs;
import org.arkhamnetwork.Arkkit.patches.NoSpawnHomes;
import org.arkhamnetwork.Arkkit.patches.NoTallGrass;
import org.arkhamnetwork.Arkkit.patches.NoVehicleTeleport;
import org.arkhamnetwork.Arkkit.patches.NoVillagerTrading;
import org.arkhamnetwork.Arkkit.patches.NoVoidDamage;
import org.arkhamnetwork.Arkkit.patches.NoWaterProtSpawners;
import org.arkhamnetwork.Arkkit.patches.NoWeather;
import org.arkhamnetwork.Arkkit.patches.NoWhitelist;
import org.arkhamnetwork.Arkkit.patches.ObsidianDestroyerFixes;
import org.arkhamnetwork.Arkkit.patches.OpPermission;
import org.arkhamnetwork.Arkkit.patches.ParkourRegion;
import org.arkhamnetwork.Arkkit.patches.Party;
import org.arkhamnetwork.Arkkit.patches.PlayerHeads;
import org.arkhamnetwork.Arkkit.patches.PlayerHealth;
import org.arkhamnetwork.Arkkit.patches.PlotMePatches;
import org.arkhamnetwork.Arkkit.patches.PluginHider;
import org.arkhamnetwork.Arkkit.patches.PortalRegions;
import org.arkhamnetwork.Arkkit.patches.PotionFix;
import org.arkhamnetwork.Arkkit.patches.PotionStackLimiter;
import org.arkhamnetwork.Arkkit.patches.PvPPotionEffects;
import org.arkhamnetwork.Arkkit.patches.PvPSanity;
import org.arkhamnetwork.Arkkit.patches.RaidShop;
import org.arkhamnetwork.Arkkit.patches.RedeemMCMMO;
import org.arkhamnetwork.Arkkit.patches.ResetKit;
import org.arkhamnetwork.Arkkit.patches.RewardNPC;
import org.arkhamnetwork.Arkkit.patches.SafeShutdown;
import org.arkhamnetwork.Arkkit.patches.SandStackLimiter;
import org.arkhamnetwork.Arkkit.patches.SchematicUpdater;
import org.arkhamnetwork.Arkkit.patches.SeasonalPasses;
import org.arkhamnetwork.Arkkit.patches.SeasonalPrefixes;
import org.arkhamnetwork.Arkkit.patches.ShopCommand;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpander;
import org.arkhamnetwork.Arkkit.patches.SkillGems;
import org.arkhamnetwork.Arkkit.patches.SmarterEssentials;
import org.arkhamnetwork.Arkkit.patches.SpawnPod;
import org.arkhamnetwork.Arkkit.patches.SpawnerFee;
import org.arkhamnetwork.Arkkit.patches.SpigotBranding;
import org.arkhamnetwork.Arkkit.patches.SpigotDebug;
import org.arkhamnetwork.Arkkit.patches.StackableSpawners;
import org.arkhamnetwork.Arkkit.patches.Suicide;
import org.arkhamnetwork.Arkkit.patches.TNTLimiter;
import org.arkhamnetwork.Arkkit.patches.TPWarmup;
import org.arkhamnetwork.Arkkit.patches.TWAutoCraftTNT;
import org.arkhamnetwork.Arkkit.patches.TabList;
import org.arkhamnetwork.Arkkit.patches.TaperSlots;
import org.arkhamnetwork.Arkkit.patches.Thanksgiving;
import org.arkhamnetwork.Arkkit.patches.TickRatelimit;
import org.arkhamnetwork.Arkkit.patches.TileEntityCleaner;
import org.arkhamnetwork.Arkkit.patches.TrickOrTreaters;
import org.arkhamnetwork.Arkkit.patches.ValentinesDay;
import org.arkhamnetwork.Arkkit.patches.VaultDisable;
import org.arkhamnetwork.Arkkit.patches.WarpGUI;
import org.arkhamnetwork.Arkkit.patches.WarzoneClaims;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoney;
import org.arkhamnetwork.Arkkit.patches.WebsiteLinks;
import org.arkhamnetwork.Arkkit.patches.WildernessCommand;
import org.arkhamnetwork.Arkkit.patches.WithdrawMCMMO;
import org.arkhamnetwork.Arkkit.patches.WorldBorderFixes;
import org.arkhamnetwork.Arkkit.patches.WorldTickOptimizer;
import org.arkhamnetwork.Arkkit.patches.XPDirectDeposit;
import org.arkhamnetwork.Arkkit.patches.XPLoss;
import org.arkhamnetwork.Arkkit.patches.XPSanity;
import org.arkhamnetwork.Arkkit.patches.Youtubers;
import org.arkhamnetwork.Arkkit.patches.Ypperin;
import org.arkhamnetwork.Arkkit.patches.antiknockback.AntiKnockback;
import org.arkhamnetwork.Arkkit.patches.antimacro.AntiMacro;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceTop;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncer;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGC;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.patches.commandcooldown.CommandCooldowns;
import org.arkhamnetwork.Arkkit.patches.createtitle.CreateTitle;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.newtrade.BetterTrade;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShops;
import org.arkhamnetwork.Arkkit.patches.teams.Teams;
import org.arkhamnetwork.Arkkit.patches.trading.NewTrade;
import org.arkhamnetwork.Arkkit.patches.voting.VotePatch;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XenforoSyncAccount;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public enum ArkPlugins {
    ANTI_DUPE(new AntiDupe((Plugin)Arkkit.get()), "Blocks many common dupe exploits, adds /scanchests"),
    BOAT_BLOCKER(new BoatBlocker((Plugin)Arkkit.get()), "Prevents placement of boats due to possible dupe exploits."),
    DROPPER_BLOCKER(new DropperBlocker((Plugin)Arkkit.get()), "Prevents droppers from dispensing items, stopping throwable item dupe exploit on Factions."),
    POTION_STACK_LIMITER(new PotionStackLimiter((Plugin)Arkkit.get()), "Prevents users from using potions with a stack size > 1."),
    ANTI_CRASH(new AntiCrash((Plugin)Arkkit.get()), "Monitors and corrects meomry leaks and catches crash issues, also automatically reboots servers with unstoppable crashes."),
    CUSTOM_BROADCAST(new CustomBroadcast((Plugin)Arkkit.get()), "Customize the otherwise bland essential's /broadcast prefix."),
    ASYNC_SPIGOT(new AsyncSpigot((Plugin)Arkkit.get()), "Disables spigot's built in async method catcher."),
    NO_MOBGRIEFING(new NoMobGriefing((Plugin)Arkkit.get()), "Disables mobGriefing gamerule on all worlds to prevent 1.8 dupe with hostile mob item pickup."),
    TICK_RATELIMIT(new TickRatelimit((Plugin)Arkkit.get()), "Throttles water flow when no players are around or if an impossible amount of events occur in a short period of time."),
    CHUNK_GC(new ChunkGC((Plugin)Arkkit.get()), "Advanced GC system for minecarft chunks. Greatly reduces RAM usage and CPU from tick iterations."),
    ENTITY_CLEANER(new EntityCleaner((Plugin)Arkkit.get()), "Removes entities that are heading towards unloaded chunks. Saves RAM, CPU from tick iterations."),
    TILE_ENTITY_CLEANER(new TileEntityCleaner((Plugin)Arkkit.get()), "Removes empty tile entities (chests) as chunks load to decrease CPU tick iterations."),
    WORLD_TICK_OPTIMIZER(new WorldTickOptimizer((Plugin)Arkkit.get()), "Optimizes per-world tick iteration CPU cost. Growth, Tracking, etc."),
    BULK_SAVING(new BulkSaving((Plugin)Arkkit.get()), "Disables all incremental I/O saving, only saves on server shutdown, costs RAM, saves (massive) CPU."),
    ANTI_REDSTONE_LAG(new AntiRedstoneLag((Plugin)Arkkit.get()), "Localized redstone throttling to prevent user-made lag machines and infinite loops."),
    NO_FIRE_TICKING(new NoFireTicking((Plugin)Arkkit.get()), "Exclude fire and firespread from server tick(), saves CPU."),
    NO_NATURAL_MOBS(new NoNaturalMobs((Plugin)Arkkit.get()), "Excluse natural mob spawning from server tick(), saves CPU."),
    NO_LOGIN_QUEUE(new NoLoginQueue((Plugin)Arkkit.get()), "Hijacks bukkit's 'Login Queue' functionality, which can often cause netty overloads and selector rebuilds."),
    ANTI_INVENTORY_HIJACK(new AntiInventoryHijack((Plugin)Arkkit.get()), "Prevents destruction of entities and blocks that have an inventory actively being viewed, Fixes a dupe exploit on Factions."),
    COMMAND_BLOCKER(new CommandBlocker((Plugin)Arkkit.get()), "Allows for dynamic command blocking / unblocking."),
    SPIGOT_BRANDING(new SpigotBranding((Plugin)Arkkit.get()), "Improved default spigot language messages to be more informative, allows for custom store_url and global_prefix to be configured into the improved messages."),
    FALLBACK_SERVERS(new FallbackServers((Plugin)Arkkit.get()), "Send players back to lobbies when a server shuts down. Supports LilyPad only."),
    ALERT_SYSTEM(new AlertSystem((Plugin)Arkkit.get()), "Global cross-server alert system. Supports LilyPad only."),
    NETWORK_EVENTS(new NetworkEvents((Plugin)Arkkit.get()), "Enables cross-server /message, /reply, and /find. Supports LilyPad only."),
    HUB_COMMAND(new HubCommand((Plugin)Arkkit.get()), "Enables /hub command that teleports users to a random fallback-server."),
    CUSTOM_MOTD(new CustomMOTD((Plugin)Arkkit.get()), "Enables /cmotd command along with a custom, configurable, delayed MOTD."),
    ESSENTIALS_WARP_UI(new EssentialsWarpUI((Plugin)Arkkit.get()), "Enables a /warp inventory menu. Warps to be displayed can be configurred."),
    ESSENTIALS_WARP_ALIASES(new EssentialsWarpAliases((Plugin)Arkkit.get()), "Enables per-warp /aliase commands. So /warp test will become /test, useful in prison server configurations."),
    GIVE_ALL(new GiveAll((Plugin)Arkkit.get()), "Enables /giveall command for server giveaways, permission and OP restricted."),
    CHAT_LIMITER(new ChatLimiter((Plugin)Arkkit.get()), "Enables a chat limiter with configurable delays between chat messages, and a custom VIP delay and permission node."),
    ITEM_LINKING(new ItemLinking((Plugin)Arkkit.get()), "Enables /senditem command and ability to link items in chat by typing [ITEM]. Requires permission node, itemlinking.senditem."),
    PLUGIN_HIDER(new PluginHider((Plugin)Arkkit.get()), "Prevents players from obtaining the server's plugin list through means such as /p l /plugin /? /help:, etc."),
    NO_NEW_CHUNKS(new NoNewChunks((Plugin)Arkkit.get()), "Prevents new chunks from generating in worlds specified in no_new_chunks.worlds section of configuration."),
    ANTI_INVISIBILITY(new AntiInvisibility((Plugin)Arkkit.get()), "Prevents non-OP'd players in survival from having the 'invisibility' potion effect."),
    PLOTME_PATCHES(new PlotMePatches((Plugin)Arkkit.get()), "Fixes a number of known bugs in PlotMe. Such as trapped chest protection not working."),
    TNT_LIMITER(new TNTLimiter((Plugin)Arkkit.get()), "Enables dynamic adjustment of spigot's maxTntPerTntTick setting in spigot.yml."),
    CHAT_FILTER(new ChatFilter((Plugin)Arkkit.get()), "Enables a dynamic word filtering system via /censor."),
    OP_PERMISSION(new OpPermission((Plugin)Arkkit.get()), "Enables enforcement of automatically op'ing and de'oping players with(out) * or *.* permission node on login."),
    FORCE_SPAWN(new ForceSpawn((Plugin)Arkkit.get()), "Enables /forcespawn <radius>, sending all players around executor to world's /spawn. Great for moderation."),
    CREATE_TITLE(new CreateTitle((Plugin)Arkkit.get()), "Enables /createtitle and /addcreatetitle commands to purchase and create custom player titles."),
    NO_HORSE_RIDE(new NoHorseRide((Plugin)Arkkit.get()), "Disables players from riding horses to prevent vanilla noclipping."),
    THANKSGIVING(new Thanksgiving((Plugin)Arkkit.get()), "Adds random Thanksgiving Turkey spawns."),
    XENFORO_SYNC_ACCOUNT(new XenforoSyncAccount((Plugin)Arkkit.get()), "Enables /syncaccount capability to sync minecraft accounts to forum accounts."),
    NO_VILLAGER_TRADING(new NoVillagerTrading((Plugin)Arkkit.get()), "Disable vanilla villager trading mechanic in gamemode."),
    COMBAT_LOG(new CombatLog((Plugin)Arkkit.get()), "Kills players who logout while within 10s of combat in pvp-enabled area."),
    CHAT_BUBBLES(new ChatBubbles((Plugin)Arkkit.get()), "Enables chat bubbles over player's heads with the arkkit.chatbubble permission!"),
    BAR_ANNOUNCER(new BarAnnouncer((Plugin)Arkkit.get()), "Enables boss bar announcement system."),
    VOTING(new VotePatch((Plugin)Arkkit.get()), "Enables voting rewards and tracking."),
    MARRIAGE(new Marriage((Plugin)Arkkit.get()), "Enables marriage and /marry for players on local server."),
    NO_CAVE_SPIDERS(new NoCaveSpiders((Plugin)Arkkit.get()), "Replaces all cave spiders with normal spiders as they spawn."),
    TEAMS(new Teams((Plugin)Arkkit.get()), "Enables /teams and the ability for players to create parties."),
    TRADING(new NewTrade((Plugin)Arkkit.get()), "Enables /trade and ability to trade items with other players through GUI."),
    PLAYER_SHOPS(new PlayerShops((Plugin)Arkkit.get()), "Enables /shop and ability to create player-owned shops."),
    NO_COMBAT_FLYING(new NoCombatFlying((Plugin)Arkkit.get()), "Clips wings of players who engage in PvP or PvE combat for 10s."),
    DEATHPOINT(new Deathpoint((Plugin)Arkkit.get()), "Enables /deathpoint (/back alternative) that allows players to return to previous location of death."),
    COMMAND_COOLDOWNS(new CommandCooldowns((Plugin)Arkkit.get()), "Enables per-player command cooldown functionality through command-cooldowns.yml!"),
    PVP_POTION_EFFECTS(new PvPPotionEffects((Plugin)Arkkit.get()), "Clear potion effects whenever a player goes between a pvp and non-pvp WG flagged area."),
    NO_HOPPERS(new NoHoppers((Plugin)Arkkit.get()), "Disables item transfers to and from hoppers."),
    ITEMFRAME_GODMODE(new ItemframeGodmode((Plugin)Arkkit.get()), "Gives itemframes godmode from destruction from anything but direct players."),
    NETHER_PORTAL_TRAP(new NetherPortalTrap((Plugin)Arkkit.get()), "Teleports users stuck in nether portal to spawn."),
    YOUTUBERS(new Youtubers((Plugin)Arkkit.get()), "Enables some VIP youtuber commands."),
    VALENTINES_DAY(new ValentinesDay((Plugin)Arkkit.get()), "Enables Valentines day specific features."),
    NO_CURSOR_DROP(new NoCursorDrop((Plugin)Arkkit.get()), "Prevents users from losing item being held on cursor on inventory close."),
    NO_ENCHANTMENT_TABLE_BOOKS(new NoEnchantmentTableBooks((Plugin)Arkkit.get()), "Prevents users from enchanting books on enchantment tables."),
    NO_MOB_MCMMO(new NoMobMcmmo((Plugin)Arkkit.get()), "Disables all mcMMO EXP gains from monster slaying."),
    MCMMO_CREDITS(new McmmoCredits((Plugin)Arkkit.get()), "Enables ability to claim mcmmo credit vouchers."),
    REWARD_NPC(new RewardNPC((Plugin)Arkkit.get()), "Enables an array of specifically named NPC interaction events to give prizes to players."),
    NO_WEATHER(new NoWeather((Plugin)Arkkit.get()), "Disables weather events on all worlds on local server."),
    EASTER(new Easter((Plugin)Arkkit.get()), "Enables a number of different easter only events."),
    ANTI_PISTON_LAG(new AntiPistonLag((Plugin)Arkkit.get()), "Prevents lag exploits from mass piston use."),
    PLAYER_HEALTH(new PlayerHealth((Plugin)Arkkit.get()), "Display player health values under playernames."),
    ENCHANTMENT_ANVIL_FIX(new EnchantmentAnvilFix((Plugin)Arkkit.get()), "Disables EnchantmentAPI anvil and enchantment table mechanics."),
    SMARTER_ESSENTIALS(new SmarterEssentials((Plugin)Arkkit.get()), "Prevents /tpaccept exploitation into enemy faction bases."),
    NO_ARROW_BOUNCE(new NoArrowBounce((Plugin)Arkkit.get()), "Prevents arrows from bouncing and hitting the shooter."),
    CHUNK_LOAD_FLUSH(new ChunkLoadFlush((Plugin)Arkkit.get()), "Removes entities in chunks when they are loaded."),
    ANTI_TILE_ENTITY_LAG(new AntiTileEntityLag((Plugin)Arkkit.get()), "Scans and removes excessive tile entities."),
    NO_LAVA_FLOW(new NoLavaFlow((Plugin)Arkkit.get()), "Disables lava flow globally."),
    NERF_EXP_DROPS(new NerfExpDrops((Plugin)Arkkit.get()), "Nerfs global exp drop rates from mobs/blocks by configurable value."),
    AUTO_CRAFT_TNT(new AutoCraftTNT((Plugin)Arkkit.get()), "Enables /tnt - Automatically crafts all tnt ingredients in inventory."),
    WILDERNESS_COMMAND(new WildernessCommand((Plugin)Arkkit.get()), "Enables /wilderness - Teleports caster to random location in Faction wilderness."),
    PLAYER_HEADS(new PlayerHeads((Plugin)Arkkit.get()), "Enables ability to get player heads from PvP. Default chance is 5%."),
    DRINKS(new Drinks((Plugin)Arkkit.get()), "Enables the ability to get drinks and get drunk, woo!"),
    EXP_BOTTLES(new ExpBottles((Plugin)Arkkit.get()), "Enables ability to use /xpbottle to withdraw vanilla levels into exp bottles."),
    NO_EXPLOSION_PHYSICS(new NoExplosionPhysics((Plugin)Arkkit.get()), "Disables block physics updates caused by exploding creepers."),
    GOLDEN_KNIFE(new GoldenKnife((Plugin)Arkkit.get()), "Enables the golden knife to be obtained and used."),
    NO_LAVA_FLOW_REGION(new NoLavaFlowRegion((Plugin)Arkkit.get()), "Fixes WorldGuard's lava-flow flag."),
    FACTION_TELEPORT_SANITY(new FactionTeleportSanity((Plugin)Arkkit.get()), "Prevents teleporation to enemy faction claims."),
    ANTI_HACK(new AntiHack((Plugin)Arkkit.get()), "Enables compilex's inhouse built anti-cheating suite."),
    NO_VOID_DAMAGE(new NoVoidDamage((Plugin)Arkkit.get()), "Prevents void damage, replaces broken y:0 bedrock."),
    FILL_COMMAND(new FillCommand((Plugin)Arkkit.get()), "Enables /fill which automatically fills dispensers with TNT from inventory."),
    SPAWNER_FEE(new SpawnerFee((Plugin)Arkkit.get()), "Charges players $ to mine spawners."),
    YPPERIN(new Ypperin((Plugin)Arkkit.get()), "Enables /xptop and /oenderchest"),
    MAX_HEIGHT_LOGIC(new MaxHeightLogic((Plugin)Arkkit.get()), "Prevents explosions and sand stacking above y:255"),
    SHOP_COMMAND(new ShopCommand((Plugin)Arkkit.get()), "Enables /shop to teleport to server shop location."),
    ANTI_GOD_APPLE_CRAFT(new AntiGodAppleCraft((Plugin)Arkkit.get()), "Prevents god apples from being crafted."),
    ASYNC_MONSTERS(new AsyncMonsters((Plugin)Arkkit.get()), "Enables monster spawner entity stacking."),
    NETHER_GLITCH_FIX(new NetherGlitchFix((Plugin)Arkkit.get()), "Prevents players from building above nether, and creating portals in nether."),
    HIDE_STREAM(new HideStream((Plugin)Arkkit.get()), "Hides vanilla player join, quit, and death messages."),
    POTION_FIX(new PotionFix((Plugin)Arkkit.get()), "Nerfs vanilla potion behavior to prevent crazy OP effects like STR III."),
    ANTI_LOOT_STEAL(new AntiLootSteal((Plugin)Arkkit.get()), "Loot can only be picked up by the killer of the entity for a few seconds."),
    HOTBAR_ARMOR_SWAP(new HotbarArmorSwap((Plugin)Arkkit.get()), "Enables right-click-to-equip mechanic for armor."),
    DYNAMIC_MAX_PLAYERS(new DynamicMaxPlayers((Plugin)Arkkit.get()), "Enables /setmaxplayers to set the server slots dynamically."),
    XP_SANITY(new XPSanity((Plugin)Arkkit.get()), "Automatically removes, resets, and bans users with impossible exp values."),
    REDEEM_MCMMO(new RedeemMCMMO((Plugin)Arkkit.get()), "Enables /redeem mcmmo functionality."),
    ESSENTIALS_KIT_VIEWER(new EssentialsKitViewer((Plugin)Arkkit.get()), "Enables GUI for essential's /kit"),
    ESSENTIALS_ADD_HOMES(new EssentialsAddHomes((Plugin)Arkkit.get()), "Enables /addhomes to add extra essentials /homes to players."),
    XP_DIRECT_DEPOSIT(new XPDirectDeposit((Plugin)Arkkit.get()), "Directly adds XP to the player who killed the monster."),
    NO_TALL_GRASS(new NoTallGrass((Plugin)Arkkit.get()), "Allows all players to destroy double plants on left click."),
    AUTO_REBOOT(new AutoReboot((Plugin)Arkkit.get()), "Reboots servers after 12h of uptime."),
    GOLDEN_APPLE_SICKNESS(new GoldenAppleSickness((Plugin)Arkkit.get()), "Enables golden apple sickness."),
    CLEAR_INVENTORY_CONFIRM(new ClearInventoryConfirm((Plugin)Arkkit.get()), "Adds confirmation support to /clearinventory commands."),
    BALANCE_TOP(new BalanceTop((Plugin)Arkkit.get()), "Enables compilex optimized /baltop."),
    WITHDRAW_MCMMO(new WithdrawMCMMO((Plugin)Arkkit.get()), "Enables /withdrawmcmmo ability to withdraw raw mcmmo xp."),
    ANTI_LAG(new AntiLag((Plugin)Arkkit.get()), "Automatically reboots servers with >9H uptime and <17TPS"),
    SAFE_SHUTDOWN(new SafeShutdown((Plugin)Arkkit.get()), "Safely unloads all loaded chunks on shutdown."),
    LAVA_SPONGE(new LavaSponge((Plugin)Arkkit.get()), "Adds capability for sponges to absorb lava."),
    SKILL_GEMS(new SkillGems((Plugin)Arkkit.get()), "Enables skill gem usage."),
    ITEM_NAMETAG(new ItemNametag((Plugin)Arkkit.get()), "Enables item nametags to rename items."),
    HALLOWEEN(new Halloween((Plugin)Arkkit.get()), "Enables spooky halloween effects."),
    XPLOSS(new XPLoss((Plugin)Arkkit.get()), "Enables dropping XP on death in bottle form."),
    TP_WARMUP(new TPWarmup((Plugin)Arkkit.get()), "Enables TP warmup times based on player EXP levels."),
    BUYCRAFT_SECURITY(new BuycraftSecurity((Plugin)Arkkit.get()), "Prevents Buycraft from auto-updating."),
    MAX_FLY_HEIGHT(new MaxFlyHeight((Plugin)Arkkit.get()), "Prevents players from flying above y:256 while in survival mode."),
    NO_WHITELIST(new NoWhitelist((Plugin)Arkkit.get()), "Prevents server from ever enabling whitelist."),
    ANTI_SPAM_BOT(new AntiSpamBot((Plugin)Arkkit.get()), "Spam bot countermesasures, /viponly chat mode."),
    EXP_ORB(new ExpOrbs((Plugin)Arkkit.get()), "Convert xp into EXP orbs with /xporb"),
    FINAL_BATTLE(new FinalBattle((Plugin)Arkkit.get()), "Enables some silly mechanics for Rob in world_final_battle."),
    CHRISTMAS(new Christmas((Plugin)Arkkit.get()), "Enables some modular jolly christmas patches!"),
    NEW_YEARS(new NewYears((Plugin)Arkkit.get()), "Enables New Year specific special event items."),
    NERF_NETHER_SPAWNERS(new NerfNetherSpawners((Plugin)Arkkit.get()), "Removes half of all vanilla nether spawners."),
    SPIGOT_DEBUG(new SpigotDebug((Plugin)Arkkit.get()), "Toggles spigot debug boolean."),
    PARTY(new Party((Plugin)Arkkit.get()), "Enables special party login listener."),
    NEAR(new Near((Plugin)Arkkit.get()), "Custom /near command."),
    BALANCE(new Balance((Plugin)Arkkit.get()), "Custom /balance command."),
    NO_MCMMO_DISARM(new NoMCMMODisarm((Plugin)Arkkit.get()), "Disables mcMMO's unarmed ability to disarm players."),
    MYSTERY_MOB_SPAWNER(new MysteryMobSpawners((Plugin)Arkkit.get()), "Enables mystery mob spawner item and sell signs."),
    DOUBLE_XP(new DoubleXp((Plugin)Arkkit.get()), "Multiplies vanilla xp drops by specified amount, default 2x."),
    ANTI_MACRO(new AntiMacro((Plugin)Arkkit.get()), "Enables anti macro captcha confirmation system."),
    RAID_SHOP(new RaidShop((Plugin)Arkkit.get()), "Enables /raidshop"),
    TW_AUTO_CRAFT_TNT(new TWAutoCraftTNT((Plugin)Arkkit.get()), "Enables TreasureWars /tnt"),
    WEBSITE_LINKS(new WebsiteLinks((Plugin)Arkkit.get()), "Enables /forum and /report hotlinks."),
    HIT_ME(new HitMe((Plugin)Arkkit.get()), "Enables /hitme command to deal self inflicted damage."),
    WORLD_BORDER_FIXES(new WorldBorderFixes((Plugin)Arkkit.get()), "Enables an array of WorldBorder fixes, incl. proper piston handling."),
    BUMP(new Bump((Plugin)Arkkit.get()), "Enables /bump command to deal self inflicted damage in friendly areas."),
    SAND_STACK_LIMITER(new SandStackLimiter((Plugin)Arkkit.get()), "Enables limitations on max number of physics enabled blocks that can be stacked in one x,z location."),
    TAB_LIST(new TabList((Plugin)Arkkit.get()), "Enables custom [TAB] list headers and footers."),
    ENDERPEARL_COOLDOWN(new EnderpearlCooldown((Plugin)Arkkit.get()), "Enables enderpearl cooldowns."),
    OBSIDIAN_DESTROYER_FIXES(new ObsidianDestroyerFixes((Plugin)Arkkit.get()), "Enables Obsidian Destroyer bugfixes, prevents invincible blocks."),
    TAPER_SLOTS(new TaperSlots((Plugin)Arkkit.get()), "Enables slot taper, slowly increases max slots to prevent network thread dieing from spam join."),
    NO_VEHICLE_TELEPORT(new NoVehicleTeleport((Plugin)Arkkit.get()), "Disables teleportation events while in a vehicle."),
    AUTO_ENTITY_CLEAR(new AutoEntityClear((Plugin)Arkkit.get()), "Activates /clearlagg-like task that removes all entities on a set interval."),
    ANTI_MINECART(new AntiMinecart((Plugin)Arkkit.get()), "Prevents known minecart dupes."),
    WARZONE_CLAIMS(new WarzoneClaims((Plugin)Arkkit.get()), "Charge players for claiming chunks near warzones."),
    EULA(new EULA((Plugin)Arkkit.get()), "Sells EULA compliant items for soft currency."),
    HACKER_HUB(new HackerHub((Plugin)Arkkit.get()), "Enables moderation tools for the hacker hub."),
    ANTI_SCOREBOARD_LEAK(new AntiScoreboardLeak((Plugin)Arkkit.get()), "Prevents resource leakage when using per-player scoreboards."),
    NO_WATER_PROT_SPAWNERS(new NoWaterProtSpawners((Plugin)Arkkit.get()), "Disbles water protection for mob spawners."),
    NO_MCMMO_LIMITS(new NoMCMMOLimits((Plugin)Arkkit.get()), "Disables mcMMO level caps without allow them to become more powerful past presets."),
    GLOBAL_GAMEMODE(new GlobalGamemode((Plugin)Arkkit.get()), "Makes creative gamemode persist between worlds."),
    SEASONAL_PREFIXES(new SeasonalPrefixes((Plugin)Arkkit.get()), "Enables display of seasonal planet victory prefixes."),
    JULY_FREEDOM(new JulyFreedom((Plugin)Arkkit.get()), "Enables some goofy 4th of July celebrations."),
    PARKOUR_REGION(new ParkourRegion((Plugin)Arkkit.get()), "Enables parkour region functionality."),
    ACCOUNT_SECURITY(new AccountSecurity((Plugin)Arkkit.get()), "Enables additional account security checks for ops."),
    AUTO_RESPAWN(new AutoRespawn((Plugin)Arkkit.get()), "Enables Auto Respawning for players when they die."),
    NO_SAFEZONE_MOBS(new NoSafezoneMobs((Plugin)Arkkit.get()), "Automatically despawns monsters in pvp disabled regions when hit."),
    FAKE_VANISH(new FakeVanish((Plugin)Arkkit.get()), "Vanish TBNRfrags and Woofless when not nearby."),
    HOME_GUI(new HomeGUI((Plugin)Arkkit.get()), "Pretty looking gui for /home that adds customizable icons."),
    FWARP_GUI(new WarpGUI((Plugin)Arkkit.get()), "Similar GUI to HomeGUI but for /f warp"),
    VAULT_GUI(new PlayerVaultGUI((Plugin)Arkkit.get()), "GUI for /pv"),
    ANTIKB_DETECTION(new AntiKnockback((Plugin)Arkkit.get()), "Detect obvious Anti Knockback hacks."),
    SCHEMATIC_UPDATER(new SchematicUpdater((Plugin)Arkkit.get()), "Schematic updater patch to enabled /uploadschem and /downloadschem."),
    EXTRA_MUTES(new ExtraMute((Plugin)Arkkit.get()), "Implement /facmute and /ipmute."),
    NO_BOOK_SIGN(new NoBookSign((Plugin)Arkkit.get()), "Disables book signing."),
    ANTI_BLOCK_GLITCH(new AntiBlockGlitch((Plugin)Arkkit.get()), "Fixes block glitching and jumping."),
    NAME_SANITY(new NameSanity((Plugin)Arkkit.get()), "Fixes name spoofing exploit via &username="),
    VAULT_DISABLE(new VaultDisable((Plugin)Arkkit.get()), "Disable adding items into PlayerVaults, (Only withdrawls allowed)"),
    GAMBLE(new Gamble((Plugin)Arkkit.get()), "Enables players gambling commands such as /roll."),
    MORE_BUCKETS(new MoreBuckets((Plugin)Arkkit.get()), "Enabled Enchanted Buckets with multi uses."),
    WARZONE_MONEY(new WarzoneMoney((Plugin)Arkkit.get()), "Enables dynamic rewards from staying mobile in main warzone."),
    CONDENSE_COMMAND(new CondenseCommand((Plugin)Arkkit.get()), "Enables combining of raw materials/ores with /combine."),
    COSMIC_CLIENT_HELPER(new CosmicClientHelper((Plugin)Arkkit.get()), "Enables reward system for players on Cosmic Client."),
    ITEM_ID(new ItemID((Plugin)Arkkit.get()), "Enables Item ID tracking."),
    HIDE_LORE(new HideLore((Plugin)Arkkit.get()), "Hides specific lore and item attributes via ProtocolLib."),
    SHOWCASE_EXPANDER(new ShowcaseExpander((Plugin)Arkkit.get()), "Enables showcase expander items."),
    ANTI_EXPLOIT(new AntiExploit((Plugin)Arkkit.get()), "Prevents a number of general server exploits."),
    FACTION_CORNER_CLAIM(new FactionCornerClaim((Plugin)Arkkit.get()), "Alerts when a corner chunk is claimed by a faction."),
    SUICIDE(new Suicide((Plugin)Arkkit.get()), "Enables /suicide command for players to use."),
    DEATH_MESSAGES(new DeathMessages((Plugin)Arkkit.get()), "Enables nice pvp death messages that display a JSON value of the item used."),
    END_GRINDING(new EndGrinding((Plugin)Arkkit.get()), "Enables end grinding mob spawning and mechanics."),
    LORE_TAG(new LoreTag((Plugin)Arkkit.get()), "Enables the lore tag item to add a custom line of lore text to an item"),
    BLOCK_VALUE(new BlockValue((Plugin)Arkkit.get()), "Enables tracking of custom block values in claims."),
    BETTER_TRADE(new BetterTrade((Plugin)Arkkit.get()), "A much safer trade system made in 2018 using OOP."),
    EQUIPMENT_LOOTBOX(new EquipmentLootboxes((Plugin)Arkkit.get()), "Equipement lootboxes that give a random Armor Set item."),
    SEASONAL_PASSES(new SeasonalPasses((Plugin)Arkkit.get()), "Seasonal Pass item for Nebula Planet."),
    DOUBLE_DROPS(new DoubleDrops((Plugin)Arkkit.get()), "Enable double gun powder drops for Creepers."),
    RESET_KITS(new ResetKit((Plugin)Arkkit.get()), "Reset Kits similar to CosmicCrates made for Castle Planet."),
    CORE_PROTECT_CONFIG(new CoreProtectConfig((Plugin)Arkkit.get()), "Allows editing in-memory config of Core Protect, automatically disables block logging."),
    NO_DUPE_EXPLOIT(new NoDupeExploit((Plugin)Arkkit.get()), "Scans inventories for items with duplicated IID and removes them."),
    ADMIN_WARPS(new AdminWarps((Plugin)Arkkit.get()), "Create admin warps that only admins have access to."),
    NO_FALL_REGIONS(new NoFallRegions((Plugin)Arkkit.get()), "No Fall regions that need to have _nofall in them."),
    ADMIN_EVENTS(new AdminEvents((Plugin)Arkkit.get()), "Allows uploading/download dynamic admin event maps and regions."),
    TRICK_OR_TREATERS(new TrickOrTreaters((Plugin)Arkkit.get()), "Trick or Treaters spawn randomly on players!"),
    ITEM_TRACKING(new ItemTracking((Plugin)Arkkit.get()), "Tracks high value IID items being dropped/picked up."),
    NO_SPAWN_HOMES(new NoSpawnHomes((Plugin)Arkkit.get()), "Blocks players from setting homes or warps at /spawn."),
    END_DRAGON(new EndDragon((Plugin)Arkkit.get()), "End Dragon boss spawning patch, with an egg to show timers as well."),
    FAT_BUCKETS(new FatBuckets((Plugin)Arkkit.get()), "Enables Fat Buckets"),
    PORTAL_REGIONS(new PortalRegions((Plugin)Arkkit.get()), "Enables warp portal regions."),
    SPAWN_POD(new SpawnPod((Plugin)Arkkit.get()), "Enables legacy spawn pods on first join."),
    KIT_LEGACY(new KitLegacy((Plugin)Arkkit.get()), "Enables /kit legacy for top rank+ players."),
    COMMAND_ITEMS(new CommandItems((Plugin)Arkkit.get()), "Enables Command Item interact functionality."),
    CONTENT_CREATOR(new ContentCreator((Plugin)Arkkit.get()), "Enables /setchannel command and functionality."),
    NO_BEDS(new NoBeds((Plugin)Arkkit.get()), "Disables sleeping in beds for players."),
    PVP_SANITY(new PvPSanity((Plugin)Arkkit.get()), "Prevents 1-hit kill scenarios."),
    FACTION_TAG_RESERVE(new FactionTagReserve((Plugin)Arkkit.get()), "Ability to reserve specific faction tags."),
    STACKABLE_SPAWNERS(new StackableSpawners((Plugin)Arkkit.get()), "Ability to stack mob spawners!");

    private ArkPlugin ap = null;
    private String desc = null;

    private ArkPlugins(ArkPlugin ap, String desc) {
        this.ap = ap;
        this.desc = desc;
    }

    public ArkPlugin getArkPlugin() {
        return this.ap;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getConfigName() {
        return this.name().toLowerCase();
    }

    public static ArkPlugins reflectArkPlugin(ArkPlugin ap) {
        for (ArkPlugins aps : ArkPlugins.values()) {
            if (!aps.getArkPlugin().getClass().getSimpleName().equals(ap.getClass().getSimpleName())) continue;
            return aps;
        }
        return null;
    }

    public static String getPatchList() {
        String s = "";
        for (ArkPlugins aps : ArkPlugins.values()) {
            s = s + aps.getConfigName() + ", ";
        }
        return s;
    }

    public static String getUnloadedPatches() {
        String s = "";
        for (ArkPlugins aps : ArkPlugins.values()) {
            if (aps.getArkPlugin().isRunning()) continue;
            s = s + aps.getConfigName() + ", ";
        }
        return s;
    }

    public static String getLoadedPatches() {
        String s = "";
        for (ArkPlugins aps : ArkPlugins.values()) {
            if (!aps.getArkPlugin().isRunning()) continue;
            s = s + aps.getConfigName() + ", ";
        }
        return s;
    }
}

