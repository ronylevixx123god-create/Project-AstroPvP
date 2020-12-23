package org.arkhamnetwork.Arkkit.plugins;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.PlayerVaultGUI;
import org.arkhamnetwork.Arkkit.patches.*;
import org.arkhamnetwork.Arkkit.patches.antiknockback.AntiKnockback;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceTop;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncer;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGC;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.patches.commandcooldown.CommandCooldowns;
import org.arkhamnetwork.Arkkit.patches.createtitle.CreateTitle;
import org.arkhamnetwork.Arkkit.patches.newtrade.BetterTrade;
import org.arkhamnetwork.Arkkit.patches.trading.NewTrade;
import org.arkhamnetwork.Arkkit.patches.voting.VotePatch;
import org.bukkit.plugin.Plugin;

public enum ArkPlugins {
    ANTI_DUPE(new AntiDupe(Arkkit.get()), "Blocks many common dupe exploits, adds /scanchests"),
    BOAT_BLOCKER(new BoatBlocker(Arkkit.get()), "Prevents placement of boats due to possible dupe exploits."),
    DROPPER_BLOCKER(new DropperBlocker(Arkkit.get()), "Prevents droppers from dispensing items, stopping throwable item dupe exploit on Factions."),
    POTION_STACK_LIMITER(new PotionStackLimiter(Arkkit.get()), "Prevents users from using potions with a stack size > 1."),
    ANTI_CRASH(new AntiCrash(Arkkit.get()), "Monitors and corrects meomry leaks and catches crash issues, also automatically reboots servers with unstoppable crashes."),
    CUSTOM_BROADCAST(new CustomBroadcast(Arkkit.get()), "Customize the otherwise bland essential's /broadcast prefix."),
    ASYNC_SPIGOT(new AsyncSpigot(Arkkit.get()), "Disables spigot's built in async method catcher."),
    NO_MOBGRIEFING(new NoMobGriefing(Arkkit.get()), "Disables mobGriefing gamerule on all worlds to prevent 1.8 dupe with hostile mob item pickup."),
    TICK_RATELIMIT(new TickRatelimit(Arkkit.get()), "Throttles water flow when no players are around or if an impossible amount of events occur in a short period of time."),
    CHUNK_GC(new ChunkGC(Arkkit.get()), "Advanced GC system for minecarft chunks. Greatly reduces RAM usage and CPU from tick iterations."),
    ENTITY_CLEANER(new EntityCleaner(Arkkit.get()), "Removes entities that are heading towards unloaded chunks. Saves RAM, CPU from tick iterations."),
    TILE_ENTITY_CLEANER(new TileEntityCleaner(Arkkit.get()), "Removes empty tile entities (chests) as chunks load to decrease CPU tick iterations."),
    WORLD_TICK_OPTIMIZER(new WorldTickOptimizer(Arkkit.get()), "Optimizes per-world tick iteration CPU cost. Growth, Tracking, etc."),
    BULK_SAVING(new BulkSaving(Arkkit.get()), "Disables all incremental I/O saving, only saves on server shutdown, costs RAM, saves (massive) CPU."),
    ANTI_REDSTONE_LAG(new AntiRedstoneLag(Arkkit.get()), "Localized redstone throttling to prevent user-made lag machines and infinite loops."),
    NO_FIRE_TICKING(new NoFireTicking(Arkkit.get()), "Exclude fire and firespread from server tick(), saves CPU."),
    NO_NATURAL_MOBS(new NoNaturalMobs(Arkkit.get()), "Excluse natural mob spawning from server tick(), saves CPU."),
    NO_LOGIN_QUEUE(new NoLoginQueue(Arkkit.get()), "Hijacks bukkit's 'Login Queue' functionality, which can often cause netty overloads and selector rebuilds."),
    ANTI_INVENTORY_HIJACK(new AntiInventoryHijack(Arkkit.get()), "Prevents destruction of entities and blocks that have an inventory actively being viewed, Fixes a dupe exploit on Factions."),
    COMMAND_BLOCKER(new CommandBlocker(Arkkit.get()), "Allows for dynamic command blocking / unblocking."),
    SPIGOT_BRANDING(new SpigotBranding(Arkkit.get()), "Improved default spigot language messages to be more informative, allows for custom store_url and global_prefix to be configured into the improved messages."),
    ESSENTIALS_WARP_UI(new EssentialsWarpUI(Arkkit.get()), "Enables a /warp inventory menu. Warps to be displayed can be configurred."),
    ESSENTIALS_WARP_ALIASES(new EssentialsWarpAliases(Arkkit.get()), "Enables per-warp /aliase commands. So /warp test will become /test, useful in prison server configurations."),
    GIVE_ALL(new GiveAll(Arkkit.get()), "Enables /giveall command for server giveaways, permission and OP restricted."),
    CHAT_LIMITER(new ChatLimiter(Arkkit.get()), "Enables a chat limiter with configurable delays between chat messages, and a custom VIP delay and permission node."),
    ITEM_LINKING(new ItemLinking(Arkkit.get()), "Enables /senditem command and ability to link items in chat by typing [ITEM]. Requires permission node, itemlinking.senditem."),
    PLUGIN_HIDER(new PluginHider(Arkkit.get()), "Prevents players from obtaining the server's plugin list through means such as /p l /plugin /? /help:, etc."),
    NO_NEW_CHUNKS(new NoNewChunks(Arkkit.get()), "Prevents new chunks from generating in worlds specified in no_new_chunks.worlds section of configuration."),
    ANTI_INVISIBILITY(new AntiInvisibility(Arkkit.get()), "Prevents non-OP'd players in survival from having the 'invisibility' potion effect."),
    TNT_LIMITER(new TNTLimiter(Arkkit.get()), "Enables dynamic adjustment of spigot's maxTntPerTntTick setting in spigot.yml."),
    CHAT_FILTER(new ChatFilter(Arkkit.get()), "Enables a dynamic word filtering system via /censor."),
    OP_PERMISSION(new OpPermission(Arkkit.get()), "Enables enforcement of automatically op'ing and de'oping players with(out) * or *.* permission node on login."),
    FORCE_SPAWN(new ForceSpawn(Arkkit.get()), "Enables /forcespawn <radius>, sending all players around executor to world's /spawn. Great for moderation."),
    CREATE_TITLE(new CreateTitle(Arkkit.get()), "Enables /createtitle and /addcreatetitle commands to purchase and create custom player titles."),
    NO_HORSE_RIDE(new NoHorseRide(Arkkit.get()), "Disables players from riding horses to prevent vanilla noclipping."),
    THANKSGIVING(new Thanksgiving(Arkkit.get()), "Adds random Thanksgiving Turkey spawns."),
    NO_VILLAGER_TRADING(new NoVillagerTrading(Arkkit.get()), "Disable vanilla villager trading mechanic in gamemode."),
    COMBAT_LOG(new CombatLog(Arkkit.get()), "Kills players who logout while within 10s of combat in pvp-enabled area."),
    CHAT_BUBBLES(new ChatBubbles(Arkkit.get()), "Enables chat bubbles over player's heads with the arkkit.chatbubble permission!"),
    BAR_ANNOUNCER(new BarAnnouncer(Arkkit.get()), "Enables boss bar announcement system."),
    VOTING(new VotePatch(Arkkit.get()), "Enables voting rewards and tracking."),
    NO_CAVE_SPIDERS(new NoCaveSpiders(Arkkit.get()), "Replaces all cave spiders with normal spiders as they spawn."),
    TRADING(new NewTrade(Arkkit.get()), "Enables /trade and ability to trade items with other players through GUI."),
    NO_COMBAT_FLYING(new NoCombatFlying(Arkkit.get()), "Clips wings of players who engage in PvP or PvE combat for 10s."),
    DEATHPOINT(new Deathpoint(Arkkit.get()), "Enables /deathpoint (/back alternative) that allows players to return to previous location of death."),
    COMMAND_COOLDOWNS(new CommandCooldowns(Arkkit.get()), "Enables per-player command cooldown functionality through command-cooldowns.yml!"),
    PVP_POTION_EFFECTS(new PvPPotionEffects(Arkkit.get()), "Clear potion effects whenever a player goes between a pvp and non-pvp WG flagged area."),
    NO_HOPPERS(new NoHoppers(Arkkit.get()), "Disables item transfers to and from hoppers."),
    ITEMFRAME_GODMODE(new ItemframeGodmode(Arkkit.get()), "Gives itemframes godmode from destruction from anything but direct players."),
    NETHER_PORTAL_TRAP(new NetherPortalTrap(Arkkit.get()), "Teleports users stuck in nether portal to spawn."),
    YOUTUBERS(new Youtubers(Arkkit.get()), "Enables some VIP youtuber commands."),
    VALENTINES_DAY(new ValentinesDay(Arkkit.get()), "Enables Valentines day specific features."),
    NO_CURSOR_DROP(new NoCursorDrop(Arkkit.get()), "Prevents users from losing item being held on cursor on inventory close."),
    NO_ENCHANTMENT_TABLE_BOOKS(new NoEnchantmentTableBooks(Arkkit.get()), "Prevents users from enchanting books on enchantment tables."),
    NO_MOB_MCMMO(new NoMobMcmmo(Arkkit.get()), "Disables all mcMMO EXP gains from monster slaying."),
    MCMMO_CREDITS(new McmmoCredits(Arkkit.get()), "Enables ability to claim mcmmo credit vouchers."),
    REWARD_NPC(new RewardNPC(Arkkit.get()), "Enables an array of specifically named NPC interaction events to give prizes to players."),
    NO_WEATHER(new NoWeather(Arkkit.get()), "Disables weather events on all worlds on local server."),
    EASTER(new Easter(Arkkit.get()), "Enables a number of different easter only events."),
    ANTI_PISTON_LAG(new AntiPistonLag(Arkkit.get()), "Prevents lag exploits from mass piston use."),
    PLAYER_HEALTH(new PlayerHealth(Arkkit.get()), "Display player health values under playernames."),
    ENCHANTMENT_ANVIL_FIX(new EnchantmentAnvilFix(Arkkit.get()), "Disables EnchantmentAPI anvil and enchantment table mechanics."),
    SMARTER_ESSENTIALS(new SmarterEssentials(Arkkit.get()), "Prevents /tpaccept exploitation into enemy faction bases."),
    NO_ARROW_BOUNCE(new NoArrowBounce(Arkkit.get()), "Prevents arrows from bouncing and hitting the shooter."),
    CHUNK_LOAD_FLUSH(new ChunkLoadFlush(Arkkit.get()), "Removes entities in chunks when they are loaded."),
    ANTI_TILE_ENTITY_LAG(new AntiTileEntityLag(Arkkit.get()), "Scans and removes excessive tile entities."),
    NO_LAVA_FLOW(new NoLavaFlow(Arkkit.get()), "Disables lava flow globally."),
    NERF_EXP_DROPS(new NerfExpDrops(Arkkit.get()), "Nerfs global exp drop rates from mobs/blocks by configurable value."),
    AUTO_CRAFT_TNT(new AutoCraftTNT(Arkkit.get()), "Enables /tnt - Automatically crafts all tnt ingredients in inventory."),
    WILDERNESS_COMMAND(new WildernessCommand(Arkkit.get()), "Enables /wilderness - Teleports caster to random location in Faction wilderness."),
    PLAYER_HEADS(new PlayerHeads(Arkkit.get()), "Enables ability to get player heads from PvP. Default chance is 5%."),
    DRINKS(new Drinks(Arkkit.get()), "Enables the ability to get drinks and get drunk, woo!"),
    EXP_BOTTLES(new ExpBottles(Arkkit.get()), "Enables ability to use /xpbottle to withdraw vanilla levels into exp bottles."),
    NO_EXPLOSION_PHYSICS(new NoExplosionPhysics(Arkkit.get()), "Disables block physics updates caused by exploding creepers."),
    GOLDEN_KNIFE(new GoldenKnife(Arkkit.get()), "Enables the golden knife to be obtained and used."),
    NO_LAVA_FLOW_REGION(new NoLavaFlowRegion(Arkkit.get()), "Fixes WorldGuard's lava-flow flag."),
    FACTION_TELEPORT_SANITY(new FactionTeleportSanity(Arkkit.get()), "Prevents teleporation to enemy faction claims."),
    ANTI_HACK(new AntiHack(Arkkit.get()), "Enables compilex's inhouse built anti-cheating suite."),
    NO_VOID_DAMAGE(new NoVoidDamage(Arkkit.get()), "Prevents void damage, replaces broken y:0 bedrock."),
    FILL_COMMAND(new FillCommand(Arkkit.get()), "Enables /fill which automatically fills dispensers with TNT from inventory."),
    SPAWNER_FEE(new SpawnerFee(Arkkit.get()), "Charges players $ to mine spawners."),
    YPPERIN(new Ypperin(Arkkit.get()), "Enables /xptop and /oenderchest"),
    MAX_HEIGHT_LOGIC(new MaxHeightLogic(Arkkit.get()), "Prevents explosions and sand stacking above y:255"),
    SHOP_COMMAND(new ShopCommand(Arkkit.get()), "Enables /shop to teleport to server shop location."),
    ANTI_GOD_APPLE_CRAFT(new AntiGodAppleCraft(Arkkit.get()), "Prevents god apples from being crafted."),
    ASYNC_MONSTERS(new AsyncMonsters(Arkkit.get()), "Enables monster spawner entity stacking."),
    NETHER_GLITCH_FIX(new NetherGlitchFix(Arkkit.get()), "Prevents players from building above nether, and creating portals in nether."),
    POTION_FIX(new PotionFix(Arkkit.get()), "Nerfs vanilla potion behavior to prevent crazy OP effects like STR III."),
    ANTI_LOOT_STEAL(new AntiLootSteal(Arkkit.get()), "Loot can only be picked up by the killer of the entity for a few seconds."),
    HOTBAR_ARMOR_SWAP(new HotbarArmorSwap(Arkkit.get()), "Enables right-click-to-equip mechanic for armor."),
    DYNAMIC_MAX_PLAYERS(new DynamicMaxPlayers(Arkkit.get()), "Enables /setmaxplayers to set the server slots dynamically."),
    XP_SANITY(new XPSanity(Arkkit.get()), "Automatically removes, resets, and bans users with impossible exp values."),
    REDEEM_MCMMO(new RedeemMCMMO(Arkkit.get()), "Enables /redeem mcmmo functionality."),
    ESSENTIALS_KIT_VIEWER(new EssentialsKitViewer(Arkkit.get()), "Enables GUI for essential's /kit"),
    ESSENTIALS_ADD_HOMES(new EssentialsAddHomes(Arkkit.get()), "Enables /addhomes to add extra essentials /homes to players."),
    XP_DIRECT_DEPOSIT(new XPDirectDeposit(Arkkit.get()), "Directly adds XP to the player who killed the monster."),
    NO_TALL_GRASS(new NoTallGrass(Arkkit.get()), "Allows all players to destroy double plants on left click."),
    AUTO_REBOOT(new AutoReboot(Arkkit.get()), "Reboots servers after 12h of uptime."),
    GOLDEN_APPLE_SICKNESS(new GoldenAppleSickness(Arkkit.get()), "Enables golden apple sickness."),
    CLEAR_INVENTORY_CONFIRM(new ClearInventoryConfirm(Arkkit.get()), "Adds confirmation support to /clearinventory commands."),
    BALANCE_TOP(new BalanceTop(Arkkit.get()), "Enables compilex optimized /baltop."),
    WITHDRAW_MCMMO(new WithdrawMCMMO(Arkkit.get()), "Enables /withdrawmcmmo ability to withdraw raw mcmmo xp."),
    ANTI_LAG(new AntiLag(Arkkit.get()), "Automatically reboots servers with >9H uptime and <17TPS"),
    SAFE_SHUTDOWN(new SafeShutdown(Arkkit.get()), "Safely unloads all loaded chunks on shutdown."),
    LAVA_SPONGE(new LavaSponge(Arkkit.get()), "Adds capability for sponges to absorb lava."),
    SKILL_GEMS(new SkillGems(Arkkit.get()), "Enables skill gem usage."),
    ITEM_NAMETAG(new ItemNametag(Arkkit.get()), "Enables item nametags to rename items."),
    HALLOWEEN(new Halloween(Arkkit.get()), "Enables spooky halloween effects."),
    XPLOSS(new XPLoss(Arkkit.get()), "Enables dropping XP on death in bottle form."),
    TP_WARMUP(new TPWarmup(Arkkit.get()), "Enables TP warmup times based on player EXP levels."),
    MAX_FLY_HEIGHT(new MaxFlyHeight(Arkkit.get()), "Prevents players from flying above y:256 while in survival mode."),
    NO_WHITELIST(new NoWhitelist(Arkkit.get()), "Prevents server from ever enabling whitelist."),
    ANTI_SPAM_BOT(new AntiSpamBot(Arkkit.get()), "Spam bot countermesasures, /viponly chat mode."),
    EXP_ORB(new ExpOrbs(Arkkit.get()), "Convert xp into EXP orbs with /xporb"),
    FINAL_BATTLE(new FinalBattle(Arkkit.get()), "Enables some silly mechanics for Rob in world_final_battle."),
    CHRISTMAS(new Christmas(Arkkit.get()), "Enables some modular jolly christmas patches!"),
    NEW_YEARS(new NewYears(Arkkit.get()), "Enables New Year specific special event items."),
    SPIGOT_DEBUG(new SpigotDebug(Arkkit.get()), "Toggles spigot debug boolean."),
    PARTY(new Party(Arkkit.get()), "Enables special party login listener."),
    NEAR(new Near(Arkkit.get()), "Custom /near command."),
    BALANCE(new Balance(Arkkit.get()), "Custom /balance command."),
    NO_MCMMO_DISARM(new NoMCMMODisarm(Arkkit.get()), "Disables mcMMO's unarmed ability to disarm players."),
    MYSTERY_MOB_SPAWNER(new MysteryMobSpawners(Arkkit.get()), "Enables mystery mob spawner item and sell signs."),
    DOUBLE_XP(new DoubleXp(Arkkit.get()), "Multiplies vanilla xp drops by specified amount, default 2x."),
    RAID_SHOP(new RaidShop(Arkkit.get()), "Enables /raidshop"),
    TW_AUTO_CRAFT_TNT(new TWAutoCraftTNT(Arkkit.get()), "Enables TreasureWars /tnt"),
    WEBSITE_LINKS(new WebsiteLinks(Arkkit.get()), "Enables /forum and /report hotlinks."),
    HIT_ME(new HitMe(Arkkit.get()), "Enables /hitme command to deal self inflicted damage."),
    WORLD_BORDER_FIXES(new WorldBorderFixes(Arkkit.get()), "Enables an array of WorldBorder fixes, incl. proper piston handling."),
    BUMP(new Bump(Arkkit.get()), "Enables /bump command to deal self inflicted damage in friendly areas."),
    SAND_STACK_LIMITER(new SandStackLimiter(Arkkit.get()), "Enables limitations on max number of physics enabled blocks that can be stacked in one x,z location."),
    ENDERPEARL_COOLDOWN(new EnderpearlCooldown(Arkkit.get()), "Enables enderpearl cooldowns."),
    OBSIDIAN_DESTROYER_FIXES(new ObsidianDestroyerFixes(Arkkit.get()), "Enables Obsidian Destroyer bugfixes, prevents invincible blocks."),
    TAPER_SLOTS(new TaperSlots(Arkkit.get()), "Enables slot taper, slowly increases max slots to prevent network thread dieing from spam join."),
    NO_VEHICLE_TELEPORT(new NoVehicleTeleport(Arkkit.get()), "Disables teleportation events while in a vehicle."),
    ANTI_MINECART(new AntiMinecart(Arkkit.get()), "Prevents known minecart dupes."),
    WARZONE_CLAIMS(new WarzoneClaims(Arkkit.get()), "Charge players for claiming chunks near warzones."),
    EULA(new EULA(Arkkit.get()), "Sells EULA compliant items for soft currency."),
    HACKER_HUB(new HackerHub(Arkkit.get()), "Enables moderation tools for the hacker hub."),
    NO_WATER_PROT_SPAWNERS(new NoWaterProtSpawners(Arkkit.get()), "Disbles water protection for mob spawners."),
    NO_MCMMO_LIMITS(new NoMCMMOLimits(Arkkit.get()), "Disables mcMMO level caps without allow them to become more powerful past presets."),
    GLOBAL_GAMEMODE(new GlobalGamemode(Arkkit.get()), "Makes creative gamemode persist between worlds."),
    SEASONAL_PREFIXES(new SeasonalPrefixes(Arkkit.get()), "Enables display of seasonal planet victory prefixes."),
    JULY_FREEDOM(new JulyFreedom(Arkkit.get()), "Enables some goofy 4th of July celebrations."),
    PARKOUR_REGION(new ParkourRegion(Arkkit.get()), "Enables parkour region functionality."),
    AUTO_RESPAWN(new AutoRespawn(Arkkit.get()), "Enables Auto Respawning for players when they die."),
    NO_SAFEZONE_MOBS(new NoSafezoneMobs(Arkkit.get()), "Automatically despawns monsters in pvp disabled regions when hit."),
    FAKE_VANISH(new FakeVanish(Arkkit.get()), "Vanish TBNRfrags and Woofless when not nearby."),
    HOME_GUI(new HomeGUI(Arkkit.get()), "Pretty looking gui for /home that adds customizable icons."),
    FWARP_GUI(new WarpGUI(Arkkit.get()), "Similar GUI to HomeGUI but for /f warp"),
    VAULT_GUI(new PlayerVaultGUI(Arkkit.get()), "GUI for /pv"),
    ANTIKB_DETECTION(new AntiKnockback(Arkkit.get()), "Detect obvious Anti Knockback hacks."),
    EXTRA_MUTES(new ExtraMute(Arkkit.get()), "Implement /facmute and /ipmute."),
    NO_BOOK_SIGN(new NoBookSign(Arkkit.get()), "Disables book signing."),
    ANTI_BLOCK_GLITCH(new AntiBlockGlitch(Arkkit.get()), "Fixes block glitching and jumping."),
    NAME_SANITY(new NameSanity(Arkkit.get()), "Fixes name spoofing exploit via &username="),
    VAULT_DISABLE(new VaultDisable(Arkkit.get()), "Disable adding items into PlayerVaults, (Only withdrawls allowed)"),
    GAMBLE(new Gamble(Arkkit.get()), "Enables players gambling commands such as /roll."),
    MORE_BUCKETS(new MoreBuckets(Arkkit.get()), "Enabled Enchanted Buckets with multi uses."),
    WARZONE_MONEY(new WarzoneMoney(Arkkit.get()), "Enables dynamic rewards from staying mobile in main warzone."),
    CONDENSE_COMMAND(new CondenseCommand(Arkkit.get()), "Enables combining of raw materials/ores with /combine."),
    COSMIC_CLIENT_HELPER(new CosmicClientHelper(Arkkit.get()), "Enables reward system for players on Cosmic Client."),
    ITEM_ID(new ItemID(Arkkit.get()), "Enables Item ID tracking."),
    HIDE_LORE(new HideLore(Arkkit.get()), "Hides specific lore and item attributes via ProtocolLib."),
    SHOWCASE_EXPANDER(new ShowcaseExpander(Arkkit.get()), "Enables showcase expander items."),
    ANTI_EXPLOIT(new AntiExploit(Arkkit.get()), "Prevents a number of general server exploits."),
    FACTION_CORNER_CLAIM(new FactionCornerClaim(Arkkit.get()), "Alerts when a corner chunk is claimed by a faction."),
    SUICIDE(new Suicide(Arkkit.get()), "Enables /suicide command for players to use."),
    DEATH_MESSAGES(new DeathMessages(Arkkit.get()), "Enables nice pvp death messages that display a JSON value of the item used."),
    END_GRINDING(new EndGrinding(Arkkit.get()), "Enables end grinding mob spawning and mechanics."),
    LORE_TAG(new LoreTag(Arkkit.get()), "Enables the lore tag item to add a custom line of lore text to an item"),
    BLOCK_VALUE(new BlockValue(Arkkit.get()), "Enables tracking of custom block values in claims."),
    BETTER_TRADE(new BetterTrade(Arkkit.get()), "A much safer trade system made in 2018 using OOP."),
    EQUIPMENT_LOOTBOX(new EquipmentLootboxes(Arkkit.get()), "Equipement lootboxes that give a random Armor Set item."),
    DOUBLE_DROPS(new DoubleDrops(Arkkit.get()), "Enable double gun powder drops for Creepers."),
    RESET_KITS(new ResetKit(Arkkit.get()), "Reset Kits similar to CosmicCrates made for Castle Planet."),
    CORE_PROTECT_CONFIG(new CoreProtectConfig(Arkkit.get()), "Allows editing in-memory config of Core Protect, automatically disables block logging."),
    NO_DUPE_EXPLOIT(new NoDupeExploit(Arkkit.get()), "Scans inventories for items with duplicated IID and removes them."),
    ADMIN_WARPS(new AdminWarps(Arkkit.get()), "Create admin warps that only admins have access to."),
    NO_FALL_REGIONS(new NoFallRegions(Arkkit.get()), "No Fall regions that need to have _nofall in them."),
    TRICK_OR_TREATERS(new TrickOrTreaters(Arkkit.get()), "Trick or Treaters spawn randomly on players!"),
    NO_SPAWN_HOMES(new NoSpawnHomes(Arkkit.get()), "Blocks players from setting homes or warps at /spawn."),
    END_DRAGON(new EndDragon(Arkkit.get()), "End Dragon boss spawning patch, with an egg to show timers as well."),
    FAT_BUCKETS(new FatBuckets(Arkkit.get()), "Enables Fat Buckets"),
    PORTAL_REGIONS(new PortalRegions(Arkkit.get()), "Enables warp portal regions."),
    SPAWN_POD(new SpawnPod(Arkkit.get()), "Enables legacy spawn pods on first join."),
    COMMAND_ITEMS(new CommandItems(Arkkit.get()), "Enables Command Item interact functionality."),
    NO_BEDS(new NoBeds(Arkkit.get()), "Disables sleeping in beds for players."),
    PVP_SANITY(new PvPSanity(Arkkit.get()), "Prevents 1-hit kill scenarios."),
    FACTION_TAG_RESERVE(new FactionTagReserve(Arkkit.get()), "Ability to reserve specific faction tags."),
    STACKABLE_SPAWNERS(new StackableSpawners(Arkkit.get()), "Ability to stack mob spawners!");

    private ArkPlugin ap = null;
    private String desc = null;

    ArkPlugins(ArkPlugin ap, String desc) {
        this.ap = ap;
        this.desc = desc;
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

    public ArkPlugin getArkPlugin() {
        return this.ap;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getConfigName() {
        return this.name().toLowerCase();
    }
}

