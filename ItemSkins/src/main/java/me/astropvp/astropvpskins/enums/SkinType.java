package me.astropvp.astropvpskins.enums;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.astropvp.astropvpskins.objects.skins.amulets.*;
import me.astropvp.astropvpskins.objects.skins.axes.*;
import me.astropvp.astropvpskins.objects.skins.backpacks.*;
import me.astropvp.astropvpskins.objects.skins.belts.*;
import me.astropvp.astropvpskins.objects.skins.boots.*;
import me.astropvp.astropvpskins.objects.skins.helmets.*;
import me.astropvp.astropvpskins.objects.skins.swords.*;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import me.astropvp.astropvpskins.objects.skins.bows.CupidBowSkin;
import me.astropvp.astropvpskins.objects.skins.pickaxes.RubberChickenSkin;

public enum SkinType {
    CUPID_HELMET(CC.RedB + "Cupid's Crown", ItemSkinType.HELMET, new MeatCleaverAxeSkin(), Arrays.asList(CC.Red + "5% to steal enemy health if below half health.") ),
    MEAT_CLEAVER_AXE(CC.RedB + "Meat Cleaver " + CC.DarkRedB + "Axe", ItemSkinType.AXE, new MeatCleaverAxeSkin(), Arrays.asList(new String[0]) ),
    CUPID_BELT(CC.RedB + "Divine Sash", ItemSkinType.BELT, new CupidBeltSkin(), Arrays.asList(CC.Red + "Auto Bless") ),
    SEXY_BELT(CC.LightPurpleB + "Gucci Belt", ItemSkinType.BELT, new SexyBeltSkin(), Arrays.asList(CC.Red + "-25% Incoming Projectile Damage", CC.Red + "All pearls act as escapist pearls") ),
    CORRUPT_BELT_POWER(CC.DarkPurpleB + "Corrupt Belt", ItemSkinType.BELT, new CorruptPowerBeltSkin(), Arrays.asList(CC.Red + "Godly Overload V") ),
    CORRUPT_BELT_UTILITY(CC.DarkPurpleB + "Utility Belt", ItemSkinType.BELT, new CorruptUtilityBeltSkin(), Arrays.asList(CC.Red + "Phoenix cannot be blocked", CC.Red + "Auto Feign Death (with Phoenix)") ),
    CUPID_BOW(CC.RedB + "Cupid's Bow", ItemSkinType.BOW, new CupidBowSkin(), Arrays.asList(new String[0]) ),
    CUPID_BOOTS(CC.RedB + "Cupid's Winged Boots", ItemSkinType.BOOTS, new CupidBootsSkin(), Arrays.asList(CC.Red + "Chance to push attacker away.") ),
    CORRUPT_BOOTS(CC.DarkPurpleB + "Rocket Boots", ItemSkinType.BOOTS, new CorruptBootsSkin(), Arrays.asList(CC.Red + "Immune to Sabotage", CC.Red + "Gears IV") ),
    SEXY_BOOTS(CC.LightPurpleB + "Bunny Slippers", ItemSkinType.BOOTS, new SexyBootsSkin(), Arrays.asList(CC.Red + "Web/Lava Walker", CC.Red + "2% Dodge") ),
    DEATH_KNIGHT_SKULL_BLADE(CC.AquaB + "Death Knight " + CC.BlueB + "Skull Blade", ItemSkinType.SWORD, new DeathKnightSkullBladeSkin(), Arrays.asList(CC.Red + "Equipped weapon can not get anti-hollied.") ),
    JOLLY_CANDY_SWORD(CC.RedB + "Jolly " + CC.WhiteB + "Candy " + CC.RedB + "Sword", ItemSkinType.SWORD, new JollyCandySwordSkin(), Arrays.asList(CC.Red + "+7.5% DMG") ),
    FLAMING_HALO(CC.YellowB + "Flaming " + CC.GoldB + "Halo", ItemSkinType.HELMET, new FlamingHaloSkin(), Arrays.asList(CC.Red + "+4% Outgoing Damage") ),
    REINDEER_ANTLERS(CC.GrayB + "Reindeer Antlers", ItemSkinType.HELMET, new ReindeerAntlersSkin(), Arrays.asList(CC.Red + "Chance to double active pet duration.") ),
    SANTA_HAT(CC.RedB + "Santa Hat", ItemSkinType.HELMET, new SantaHatSkin(), Arrays.asList(CC.Red + "+5% DMG", CC.Red + "-5% DMG") ),
    KOALA(CC.GrayB + "Koala", ItemSkinType.HELMET, new KoalaSkin(), Arrays.asList(CC.Red + "Immune to Fire/Blast DMG") ),
    CORRUPT_AXE(CC.DarkPurpleB + "Corrupt Greataxe", ItemSkinType.AXE, new CorruptAxeSkin(), Arrays.asList(CC.Red + "Bypass Defensive Armor Set Bonuses") ),
    CORRUPT_SWORD(CC.DarkPurpleB + "Corrupt Greatsword", ItemSkinType.SWORD, new CorruptSwordSkin(), Arrays.asList(CC.Red + "2x Chance to Neutralize enemies", CC.Red + "2x Blackout duration on enemies.") ),
    SEXY_SWORD(CC.LightPurpleB + "Selfie Stick", ItemSkinType.SWORD, new SelfieStickSkin(), Arrays.asList(new String[0]) ),
    CUPID_SWORD(CC.RedB + "Blade of Love", ItemSkinType.SWORD, new CupidSwordSkin(), Arrays.asList(CC.Red + "2% Chain Lifesteal") ),
    SEXY_HELMET(CC.LightPurpleB + "Chad Sunglasses", ItemSkinType.HELMET, new SexyHelmetSkin(), Arrays.asList(CC.Red + "50% Chance to Block World Destroyer") ),
    CORRUPT_HELMET(CC.DarkPurpleB + "Corrupt Cosmonaut Visor", ItemSkinType.HELMET, new CorruptHelmetSkin(), Arrays.asList(CC.Red + "50% Chance to block Neutralize", CC.Red + "50% Silence Duration") ),
    CUPID_AMULET(CC.RedB + "Amulet of Angels", ItemSkinType.AMULET, new CupidAmuletSkin(), Arrays.asList(CC.Red + "Immune to Nature's Wrath", CC.Red + "Immune to Revenge of Yijki") ),
    SEXY_AMULET(CC.LightPurpleB + "40K Gold Chain", ItemSkinType.AMULET, new SexyHelmetSkin(), Arrays.asList(CC.Red + "+2% /sell prices") ),
    CORRUPT_AMULET_CORRUPTION(CC.DarkPurpleB + "Amulet of Corruption", ItemSkinType.AMULET, new CorruptAmuletCorruptionSkin(), Arrays.asList(CC.Red + "Immune to enemy armor crystals of same type on attached item.") ),
    CORRUPT_AMULET_DESTRUCTION(CC.DarkPurpleB + "Amulet of Destruction", ItemSkinType.AMULET, new CorruptAmuletDestructionSkin(), Arrays.asList(CC.Red + "+7.5% Outgoing Damage") ),
    VIRUS_SWORD(CC.DarkGreenB + "Corona Beer Bottle", ItemSkinType.SWORD, new VirusSwordSkin(), Arrays.asList(CC.Red + "+3.5% Outgoing Damage", CC.Red + "Mighty Cleave V") ),
    SPRING_2020_SWORD(CC.GreenB + "Multi-Colored " + CC.YellowB + "Popsicle", ItemSkinType.SWORD, new SpringSwordSkin(), Arrays.asList(CC.Red + "10% Chance to bypass pet cooldown.") ),
    SPRING_2020_PICKAXE(CC.GreenB + "Rubber " + CC.YellowB + "Chicken", ItemSkinType.PICKAXE, new RubberChickenSkin(), Arrays.asList(new String[0]) ),
    VIRUS_BOOTS(CC.DarkGreenB + "Hazmat Boots", ItemSkinType.BOOTS, new VirusBootsSkin(), Arrays.asList(CC.Red + "Auto Bless", CC.Red + "Chance to activate Silence Pet", CC.Gray + "  (7x7 area for 12 seconds)") ),
    SPRING_2020_BOOTS(CC.GreenB + "Rainbow " + CC.YellowB + "Slides", ItemSkinType.BOOTS, new RainbowSlidesSkin(), Arrays.asList(CC.Red + "Gears IV", CC.Red + "Jump Boost 1 in all /dungeon parkour regions.", CC.Red + "5% chance to not consume /dungeon key.") ),
    SPRING_2020_AMULET(CC.GreenB + "Staff " + CC.YellowB + "Pass", ItemSkinType.AMULET, new StaffPassAmuletSkin(), Arrays.asList(CC.Red + "Immune to arrows") ),
    SPRING_2020_AXE(CC.GreenB + "Spiked " + CC.YellowB + "Baseball Bat", ItemSkinType.AXE, new SpikedBaseballBatSkin(), Arrays.asList(CC.Red + "-5% Incoming Damage from enemies wielding Swords", CC.Red + "+10% Outgoing Damage to enemies wielding Swords") ),
    VIRUS_BELT(CC.DarkGreenB + "First Aid Fannypack", ItemSkinType.BELT, new VirusBeltSkin(), Arrays.asList(CC.Red + "50% chance to negate Blackout proc") ),
    VIRUS_HELMET_HAZMAT(CC.DarkGreenB + "Hazmat Helmet", ItemSkinType.HELMET, new HazmatHelmetSkin(), Arrays.asList(CC.Red + "Immune to Freezes") ),
    VIRUS_HELMET_N95(CC.DarkGreenB + "Anti Corona Mask", ItemSkinType.HELMET, new AntiCoronaMaskSkin(), Arrays.asList(CC.Red + "Immune to Teleblock", CC.Red + "2% chance to proc Regeneration 2 for 25s") ),
    PEPE_WEAPON(CC.BlueB + "Pepe Clapper", ItemSkinType.WEAPON, new PepeWeaponSkin(), Arrays.asList(CC.Red + "Convert hit players into a rare pepe.") ),
    EASTER_HELMET(CC.RedB + "Bunny Ears", ItemSkinType.HELMET, new EasterHelmetSkin(), Arrays.asList(CC.Red + "Ethereal Dodge VI") ),
    EASTER_AMULET(CC.RedB + "Rabbit's Foot", ItemSkinType.AMULET, new RabitsFootAmuletSkin(), Arrays.asList(CC.Red + "Feign Death I", CC.Red + "5% chance to not consume /dungeon portals", CC.Red + "20% chance tp increase /titan attributes by and extra level", CC.Red + "-25% /dungeon loot cooldown") ),
    EASTER_BELT(CC.RedB + "Rainbow Belt", ItemSkinType.BELT, new EasterBeltSkin(), Arrays.asList(CC.Red + "-7.5% Incoming DMG") ),
    EASTER_SWORD(CC.RedB + "Jumbo Chocolate Sword", ItemSkinType.SWORD, new EasterSwordSkin(), Arrays.asList(CC.Red + "+15% Outgoing PvE DMG", CC.Red + "Devour V") ),
    EASTER_PICKAXE(CC.RedB + "Easter Basket", ItemSkinType.AXE, new EasterBasketSkin(), Arrays.asList(CC.Red + "-10% Incoming DMG") ),
    PEPE_HELMET(CC.BlueB + "Pepe Helmet", ItemSkinType.HELMET, new PepeHelmetSkin(), Arrays.asList(new String[0]) ),
    SCYTHE1(CC.RedB + "Skull Scythe", ItemSkinType.SWORD, new SkullScytheSkin(), Arrays.asList(CC.Red + "+2.5% Outgoing Damage", CC.Red + "10% chance to silence amulet of corruption for 10s") ),
    DOOMSDAY_SWORD(CC.DarkGreenB + "Doomsday Machete", ItemSkinType.SWORD, new DoomsdayMacheteSkin(), Arrays.asList(CC.Red + "Increases outgoing damage from soul enchantments by 1.25x") ),
    RUINS_SWORD(CC.DarkAquaB + "Trident of the Deep", ItemSkinType.SWORD, new TridentSkin(), Arrays.asList(CC.Red + "Blessed IV", CC.Red + "Hex V", CC.Red + "Mark of the Beast VI", CC.Red + "25% chance to bypass Full Armor Set", CC.Red + "or Armor Crystal defensive bonus") ),
    COSMIC_CITIZEN_BOOTS(CC.AquaB + "Moon Boots", ItemSkinType.BOOTS, new MoonBootsSkin(), Arrays.asList(CC.Red + "Increases armor set bonus/crystal damage by 1.2x") ),
    MEDIEVAL_BOOTS_KNIGHT(CC.GrayB + "Knight's Boots", ItemSkinType.BOOTS, new KnightBootsSkin(), Arrays.asList(CC.Red + "Immune to all but vanilla knockback") ),
    DOOMSDAY_BOOTS(CC.DarkGreenB + "Camouflage Yeezys", ItemSkinType.BOOTS, new CamouflageBootsSkin(), Arrays.asList(CC.Red + "40% chance to negate soul trap", CC.Red + "Demonic Gateway V") ),
    HALLOWEEN_BOOTS(CC.DarkGreenB + "Retaliation Boots", ItemSkinType.BOOTS, new RetaliationBoots(), Arrays.asList(CC.Red + "+15% Outgoing Damage to Bosses", CC.Red + "+10% Incoming Damage") ),
    COSMIC_CITIZEN_AMULET(CC.GoldB + "Starquest Amulet", ItemSkinType.AMULET, new StarquestAmuletSkin(), Arrays.asList(CC.Red + "4% chance to earn extra dungeon lootbag") ),
    HALLOWEEN_HELMET(CC.GoldB + "Candy Collector", ItemSkinType.HELMET, new CandyCollectorSkin(), Arrays.asList(CC.Red + "-20% incoming damage from enemies wielding swords", CC.Red + "1% chance to activate witcher ability") ),
    RUINS_HELMET(CC.translate("&3&lLeviathan Trophy"), ItemSkinType.HELMET, new LeviathanTrophySkin(), Arrays.asList(CC.translate("&cIncreased chance to drop Ancient Scrolls"), CC.translate("&cwhen killing Guardians in /ruins"), CC.translate("&cImmune to enemy Amulet of Corruption"), CC.translate("&c+1 Max Heart"), CC.translate("&c3% chance to silence enemy Soul Enchants"), CC.translate("  &7(7x7 blocks for 7s)")) ),
    DOOMSDAY_HELMET(CC.translate("&2&lDoomsday Helmet"), ItemSkinType.HELMET, new DoomsDayHelmetSkin(), Arrays.asList(CC.translate("&cAbility to see invisible players"), CC.translate("&7(Feign Death, Undead Ruse)")) ),
    VALENTINES_2021_HELMET(CC.translate("&c&lHeart Halo"), ItemSkinType.HELMET, new HearthHaloSkin(), Arrays.asList(CC.translate("&cPassively removes 1 Bleedstack every 2s"), CC.translate("&cImmune to Soul Harvester"), CC.translate("&c1.25% (Divine) Enlighted Healing")) ),
    CHRISTMAS_HELMET_STAR(CC.translate("&a&lNorth Star"), ItemSkinType.HELMET, new NorthStarSkin(), Arrays.asList(CC.translate("&c33% Chance to negate Monopoly Mask"), CC.translate("&c-33% enemy Blackout duration"), CC.translate("&cBlocks all enemy (Guided) Rocket Escape"), CC.translate("&cDivine Enlighted IV")) ),
    SUMMER_2021_HELMET(CC.translate("&6&lTiki Mask"), ItemSkinType.HELMET, new TikiMaskSkin(), Arrays.asList(CC.translate("&cLimites enemy amulet of corruption to 1 set type"), CC.translate("&c25% chance to block enemy Silence"), CC.translate("&cLimits enemy Silence to 60% duration")) ),
    NYE_HELMET(CC.translate("&d&lNeon Emotions"), ItemSkinType.HELMET, new NeonEmotionsSkin(), Arrays.asList(CC.translate("&c+6% Outgoing Damage"), CC.translate("&c-2% Incoming Damage"), CC.translate("&cImmune to Slowness Potion Effect")) ),
    THANKSGIVING_HELMET_WITCH(CC.translate("&e&lWitch Hat"), ItemSkinType.HELMET, new WitchHatSkin(), Arrays.asList(CC.translate("&c-25% Silence duration"), CC.translate("&cImmune to offensive Titan Attributes"), CC.translate("&7(Atlas, Ouranos and Prometheus)"), CC.translate("&cImmune to Trap"), CC.translate("&cImmune to Bloodhound & Banshee Pet"), CC.translate("&c10% chance to deflect incoming damage"), CC.translate("&conto nearby enemy"), CC.translate("&c2% chance to enable a LVL 10"), CC.translate("&cBanshee Pet effect when damaged")) ),
    THANKSGIVING_AMULET_AUTUMN(CC.translate("&e&lAutumn Amulet"), ItemSkinType.AMULET, new AutumnAmuletSkin(), Arrays.asList(CC.translate("&c+33% healing from Divine Enlighted"), CC.translate("&cEach time you enter combat:"), CC.translate("&c* No cooldown on 1st Phoenix Proc"), CC.translate("&c* -15% Incoming Damage for 15s"), CC.translate("&c* Absorption V for 20s")) ),
    SUMMER_2021_AMULET(CC.translate("&c&lLuau Lei"), ItemSkinType.AMULET, new LuauLei(), Arrays.asList(CC.translate("&c+25% Outgoing Soul Enchant damage"), CC.translate("&cChance to block Soul Enchant Damage Modifiers")) ),
    VALENTINES_2021_BELT(CC.translate("&c&lCupids Heart"), ItemSkinType.BELT, new CupidsHeart(), Arrays.asList(CC.translate("&cBypass enemy (Paladin) Armored"), CC.translate("&cHoly Aegis VII"), CC.translate("&c+10% Outgoing Damage to Silenced enemies")) ),
    CHRISTMAS_BOOTS_SLIPPERS(CC.translate("&c&lSnowflake Slippers"), ItemSkinType.BOOTS, new SnowflakeSlippersSkin(), Arrays.asList(CC.translate("&cGears IV"), CC.translate("&cImmune to Teleblock"), CC.translate("&cImmune to Slowness Potion Effect"), CC.translate("&cSnowflake Storm Ability"), CC.translate("&7(10% chance to spawn 4x4 snow floor for 10s."), CC.translate("&7While stood on the snow floor:"), CC.translate("&7* You will receive -20% Incoming Damage"), CC.translate("&7* Your allies will receive -5% Incoming Damage"), CC.translate("&7* Your enemies will receive Slowness II)")) ),
    THANKSGIVING_SWORD_HARVESTER(CC.translate("&e&lSoul Harvester"), ItemSkinType.SWORD, new SoulHarvesterSkin(), Arrays.asList(CC.translate("&cIncreases outgoing damage from Soul Enchants by 1.3x"), CC.translate("&c+10% Outgoing Damage to enemies with Soul Mode active"), CC.translate("&c50% chance to block Divine Immolation"), CC.translate("&c-25% Soul Enchant Costs")) ),
    VALENTINES_2021_BOOTS(CC.translate("&c&lTeddy Slippers"), ItemSkinType.BOOTS, new TeddySlippersSkin(), Arrays.asList(CC.translate("&cNerf enemy special Armor Set weapon Outgoing Damage bonuses by 50%"), CC.translate("&c33% Chance to reflect enemy (Titan) Trap for 1.25x duration"), CC.translate("&c-2% Incoming Damage"), CC.translate("&c+1% Outgoing Damage")) ),
    CHRISTMAS_SWORD_ORNAMENTAL(CC.translate("&a&lOrnamental Carnage"), ItemSkinType.SWORD, new OrnamentalCarnageSkin(), Arrays.asList(CC.translate("&c-5% Incoming Damage"), CC.translate("&c-15% Incoming Damage from enemies with Soul Mode active"), CC.translate("&cTitan Trap V"), CC.translate("&c+10% Outgoing Damage to trapped players"), CC.translate("&c+2 blocks Divine Immolation radius"), CC.translate("&c2% chance for Dimensional Shift Ability")) ),
    VALENTINES_2021_SWORD(CC.translate("&c&lHeartbreaker"), ItemSkinType.SWORD, new HeartBreakerSkin(), Arrays.asList(CC.translate("&cBypass enemy (Vengeful) Diminish"), CC.translate("&c+1% Damage for each enemy level of (Planetary) Deathbringer"), CC.translate("&cMartyr Valor V"), CC.translate("&cReality Inversion V")) ),
    SUMMER_2021_SWORD(CC.translate("&c&lMaui's Hook"), ItemSkinType.SWORD, new MauisHookSkin(), Arrays.asList(CC.translate("&c+15% Outgoing Soul Enchant Damage"), CC.translate("&cBlackout V"), CC.translate("&cNeutralize VI"), CC.translate("&cTranscendence ability"),CC.translate("&7(For each soul enchant that is"), CC.translate("&7blocked (not including immunity)"), CC.translate("&7gain +1 stack. At 10 stacks crouch"), CC.translate("&7to activate Transcendence massively"), CC.translate("&7damaging and silencing nearby"), CC.translate("&7players and pets.)")) ),
    RUINS_AMULET(CC.translate("&3&lLost Amulet"), ItemSkinType.AMULET, new LostAmulet(), Arrays.asList(CC.translate("&c50% Neutralize Negation"), CC.translate("&c-5% Incoming Damage"), CC.translate("&cIncreases outgoing damage from Soul Enchants by 1.1x")) ),
    DOOMSDAY_BACKPACK_BUGOUT(CC.GreenB + "Bug-Out Bag", ItemSkinType.BACKPACK, new BugOutBagSkin(), Arrays.asList(CC.translate("&c10% chance to keep each equipped item on death.")) ),
    DOOMSDAY_BACKPACK_RAINBOW(CC.GreenB + "Rainbow Backpack", ItemSkinType.BACKPACK, new RainbowBackpackSkin(), Arrays.asList(CC.translate("&c+1 Max Heart.")) ),
    DEEP_SPACE_BACKPACK(CC.DarkPurpleB + "Deep Space Backpack", ItemSkinType.BACKPACK, new DeepSpaceBackpackSkin(), Arrays.asList(CC.translate("&c+3% Outgoing Damage."), CC.translate("&cImmune to Soul Trap")) ),
    HALLOWEEN_BACKPACK(CC.GoldB + "Cursed Wings", ItemSkinType.BACKPACK, new CursedWingsSkin(), Arrays.asList(CC.translate("&c-75% Silence Duration"), CC.translate("&cBlocks all enemy Rocket Escape"), CC.translate("&c-3% Incoming Damage")) ),
    VALENTINES_2021_BACKPACK(CC.RedB + "Teddy Backpack", ItemSkinType.BACKPACK, new TeddyBackpackSkin(), Arrays.asList(CC.Red + "Immune to (Lethal) Sniper", CC.Red + "Immune to Hero Killer", CC.Red + "+2% Outgoing Damage") ),
    MEDIEVAL_HELMET_KING(CC.GoldB + "Royal Crown", ItemSkinType.HELMET, new RoyalCrown(), Arrays.asList(CC.Red + "25% Faster cap time at Citadel Assault", CC.translate("&c-5% Incoming Damage"), CC.translate("&cImmune to enemy Guardians/Undead Ruse")) ),
    MEDIEVAL_HELMET_KNIGHT(CC.GrayB + "Knight's Helmet", ItemSkinType.HELMET, new KnightHelmet(), Arrays.asList(CC.Red + "Immune to Teleblock", CC.translate("&cAquatic"), CC.translate("&cAll pearls act as Escapist Pearls"), CC.translate("&c2% Chance to prox Arcane Creeper when damaged")) ),
    COSMIC_CITIZEN_HELMET_DRINKING(CC.translate("&b&lDrinking Helmet"), ItemSkinType.HELMET, new DrinkingHelmet(), Arrays.asList(CC.Red + "5-20s shorter /dungeon portal time", CC.translate("&cGhostly Ghost III")) ),
    COSMIC_CITIZEN_HELMET_CG(CC.translate("&b&lCosmicGames Hat"), ItemSkinType.HELMET, new CosmicGamesHat(), Arrays.asList(CC.Red + "5% chance to not consume Slotbot Ticket on Spin") ),
    CHRISTMAS_HELMET_ICE(CC.translate("&c&lIce Crown"), ItemSkinType.HELMET, new IceCrown(), Arrays.asList(CC.Red + "-5% Incoming Damage", CC.translate("&cReflects (Titan) Trap effects on enemy")) ),
    MEDIEVAL_BELT_KING(CC.translate("&6&lRoyal Belt"), ItemSkinType.BELT, new RoyalBelt(), Arrays.asList(CC.Red + "25% chance to not proc Pet Cooldown") ),
    MEDIEVAL_BELT_KNIGHT(CC.translate("&7&lChain Belt"), ItemSkinType.BELT, new ChainBelt(), Arrays.asList(CC.Red + "Auto Bless", CC.translate("&c200% damage to Citadel Siege blocks"), CC.translate("&c+2.5% outgoing Damage")) ),
    COSMIC_CITIZEN_BELT(CC.translate("&b&lSpace Belt"), ItemSkinType.BELT, new SpaceBelt(), Arrays.asList(CC.Red + "Auto Bless", CC.translate("&c+5% Blackscroll Bonus"), CC.translate("&c+2% to not consume Book on apply")) ),
    DOOMSDAY_BELT(CC.translate("&2&lTactical Belt"), ItemSkinType.BELT, new TacticalBelt(), Arrays.asList(CC.translate("&cIncreases pet ability duration by 1.5x"), CC.translate("&7 (Shackled Golem, Stitches, Sell Bot, Worlock"), CC.translate("&7 Stronghold, Tesla, XP Booster, Anti-Teleblock)")) ),
    HALLOWEEN_BELT(CC.translate("&6&lCandy Sorting Belt"), ItemSkinType.BELT, new HalloweenBelt(), Arrays.asList(CC.translate("&c5% Chance to negate Infinity Luck"), CC.translate("&cper Heroic Armor piece equipped"), CC.translate("&cBypasses Candy Collector's Sugar Rush")) ),
    THANKSGIVING_BELT(CC.translate("&e&lPie Belt"), ItemSkinType.BELT, new ThanksGivingBelt(), Arrays.asList(CC.translate("&c+0.5% Outgoing Damage for each enemy Hunger missing"), CC.translate("&c5% chance to absorb and convert Incoming"), CC.translate("&cDamage into a Pie Hypnosis stack"), CC.translate("&7 (Each \"Pie Hypnosis\" stack will deal"), CC.translate("&7 +25% durability damage and increases"), CC.translate("&7 outgoing damage by 0.5%)"), CC.translate("&cPie Explosion Ability"), CC.translate("&7 (Eat a Pumpkin Pie when \"Pie Hypnosis\""), CC.translate("&7 stack reaches 10 to trigger \"Pie Explosion\","), CC.translate("&7 healing you for 33% of total HP, fixing"), CC.translate("&7 your armor for 25% per piece, slowing all"), CC.translate("&7 nearby enemies and knocking them away)")) ),
    CHRISTMAS_BELT_CANDY(CC.translate("&a&lCandy Buckle"), ItemSkinType.BELT, new CandyBuckle(), Arrays.asList(CC.translate("&cDisable enemy's (Alien) Implants for 10s each hit"), CC.translate("&c+1% Outgoing Damage for each Food level"), CC.translate("&cunder 20 that the enemy is missing (max +10%)"), CC.translate("&c10% chance for Speed IV for 4s when damaged")) ),

    CHRISTMAS_BELT_CRYSTALINE(CC.translate("&c&lCrystalline Band"), ItemSkinType.BELT, new CrystallineBand(), Arrays.asList(
            CC.translate("&cImmune to Icy Wrath Ability"),
            CC.translate("&cObsidian Guardians X"),
            CC.translate("&c20% Chance to negate Blackout proc")) ),
    SPRING_2021_BELT(CC.translate("&a&lParty Belt"), ItemSkinType.BELT, new PartyBelt(), Arrays.asList(
            CC.translate("&cReduce freeze/slowness effects on walk speed by 50%"),
            CC.translate("&c25% Chance to reduce enemy Rage Stacks by 1 when hit"),
            CC.translate("&cImmune to Deep Bleed Slowness")) ),
    TRIALS_BELT(CC.translate("&6&lCinder Wolf Belt"), ItemSkinType.BELT, new CinderWolf(), Arrays.asList(
            CC.translate("&cCinder Ability"),
            CC.translate("&7 (5% chance when hit to:"),
            CC.translate("&7 * Spawn 4 Fireballs in each direction,"),
            CC.translate("&7 which upon impact with a player"),
            CC.translate("&7 deal 33% of their current health"),
            CC.translate("&7 * Receive Gears IV for 7s"),
            CC.translate("&7 * Deal +5 Outgoing Damage for 7s%"),
            CC.translate("&7 * AoE damage every 1s for 7s)")
    ) ),
    SUMMER_2021_BELT(CC.translate("&c&lHula Skirt"), ItemSkinType.BELT, new HulaSkirt(), Arrays.asList(
            CC.translate("&cImmune to Lifesteal"),
            CC.translate("&cChance for Bonus Mobs to drop Rare Mob loot in /invasions")
    ) ),
    /*INFINITY_BELT(CC.translate("&9&lInfinity Belt"), ItemSkinType.BELT, new InfinityBelt(), Arrays.asList(
            CC.translate("&cEquipped armor will regain to 10% durability"),
            CC.translate("&cinstead of breaking (10m cooldown)"),
            CC.translate("&cImmune to Soul Trap"),
            CC.translate("&cImmune to (Obsidian) Guardians"),
            CC.translate("&cNegates enemy (Reinforced) Tank")
    ) ),*/

    COSMIC_CITIZEN_AXE(CC.translate("&b&lFirework Rocket"), ItemSkinType.AXE, new FireworkRocket(), Arrays.asList(
            CC.translate("&cTitan Trap III")
    ) ),
    SCYTHE2(CC.RedB + "Skull Scythe", ItemSkinType.AXE, new SkullScytheAxeSkin(), Arrays.asList(CC.Red + "+2.5% Outgoing Damage", CC.Red + "10% chance to silence amulet of corruption for 10s") ),

    HALLOWEEN_AXE(CC.translate("&6&lCorpse Grinder"), ItemSkinType.AXE, new CorpseGrinder(), Arrays.asList(
            CC.translate("&cDouble Strikg II"),
            CC.translate("&cBypasses Dodge"),
            CC.translate("&c2x Outgoing Durability Damage"),
            CC.translate("&c+15% Outgoing Damage for 30s"),
            CC.translate("&cwhenever you kill an Enemy Player"),
            CC.translate("&cDeath Coffin III")
    ) ),
    CHRISTMAS_AXE_SNOWFLAKE(CC.translate("&a&lSnowflake"), ItemSkinType.AXE, new Snowflake(), Arrays.asList(
            CC.translate("&cSnowflake Snowball Ability"),
            CC.translate("&7 (Right-Click&7 to launch snowflake snowball."),
            CC.translate("&7 Any enemy players hit by the snowflake"),
            CC.translate("&7 snowball will receive Slowness II for 2s"),
            CC.translate("&7 and will take +8% Incoming Damage for 3s,"),
            CC.translate("&7 8s cooldown)"),
            CC.translate("&cIcy Wrath Ability"),
            CC.translate("&7 (4% chance to \"Deep Freeze\" targets,"),
            CC.translate("&7 slowing them for 2s and increasing their"),
            CC.translate("&7 incoming damage by +5%"),
            CC.translate("&7 33% chance to AoE Silence enemy enchants"),
            CC.translate("&7 including active soul enchants for 2s?"),
            CC.translate("&7 Pulls nearby enemies towards you."),
            CC.translate("&7 Reduces outgoing enemy damage by 1%"),
            CC.translate("&7 each hit for 10s up to max of 10%)"),
            CC.translate("&c Icy Blood Ability"),
            CC.translate("&7 (Reduces enemy health regeneration by"),
            CC.translate("&7 1% for 6s for each bleed stack"),
            CC.translate("&7 they have up to a max of 20%)")
    ) ),

    VALENTINES_2021_AXE(CC.translate("&c&lGolden Admirer"), ItemSkinType.AXE, new GoldenAdmirer(), Arrays.asList(
            CC.translate("&cDisables enemy Cursed Wings and Witch Hat for 10s"),
            CC.translate("&cOutgoing damage has a 50% chance to remove any Trap/Slowness effect on you")
    ) ),

    //TODO Stormbreaker skin

    //TODO New Pickaxe Skins





    MEDIEVAL_AMULET_KING(CC.GoldB + "Amulet of Kings", ItemSkinType.AMULET, new AmuletOfKingsSkin(), Arrays.asList(CC.Red + "15% chance for /end envoy chest loot to auto-/collect") ),
    MEDIEVAL_AMULET_KNIGHT(CC.GrayB + "Diamond Necklace", ItemSkinType.AMULET, new DiamondNecklaceSkin(), Arrays.asList(CC.Red + "Increased view distance by 1 chunk") ),
    CHRISTMAS_AMULET_SNOWFLAKE(CC.RedB + "Preserved Snowflake", ItemSkinType.AMULET, new PreservedSnowflakeSkin(), Arrays.asList(CC.Red + "Immune to Soul Tether, Soul Trap", CC.Red + "Immune to Nature's Wrath", CC.Red + "Able to see invisible player", CC.Gray + " (Feign Death, Undead Ruse)") ),
    NYE_AMULET(CC.LightPurpleB + "Radioactive Amulet", ItemSkinType.AMULET, new RadioactiveAmulet(), Arrays.asList(CC.Red + "50% chance for double loot from Rare Mobs in /invasions", CC.Red + "-25% Incoming PvE Damage in /invasions", CC.Red + "2x buffs from Junk Mobs in /invasions") ),
    CHRISTMAS_AMULET_SANTA(CC.GreenB + "Secret Santa", ItemSkinType.AMULET, new SecretSantaSkin(), Arrays.asList(CC.Red + "Jolly Suprise ability", CC.Gray + " (5% chance to Feign Death for 2.5s, spawning", CC.Gray + " a \"Demonic Gateway\" where you vanished. On", CC.Gray + " your next attack, you will deal 2x damage", CC.Gray + " if dealt from behind, 30s Cooldown)", CC.Red + "Negates enemy Feign Death") ),
    HALLOWEEN_AMULET(CC.GoldB + "Silent Death", ItemSkinType.AMULET, new SilentDeathSkin(), Arrays.asList(CC.Red + "5% chance to afflict enemy", CC.Red + "with Silent Death ability", CC.Gray + " (Enemy will receive 20% Incoming DMG", CC.Gray + " until caster has been hit 5 times", CC.Gray + " or 15 seconds has elapsed.)", CC.Red + "Immune to Silent Death ability", CC.Red + "+3% Outgoing Damage") ),
    VALENTINES_2021_AMULET(CC.RedB + "Rose Amulet", ItemSkinType.AMULET, new RoseAmuletSkin(), Arrays.asList(CC.Red + "Immune to Snowflake Axe skin abilities", CC.Red + "-50% damage from enemy Rage", CC.Red + "Automatic Phoenix Feather (30m cooldown)") ),
    CHRISTMAS_BACKPACK_GOODIES(CC.GreenB + "Sack of Goodies", ItemSkinType.BACKPACK, new SackOfGoodiesSkin(), Arrays.asList(CC.Red + "-2% Incoming Damage", CC.Red + "+2% Outgoing Damage", CC.Red + "1% chance on incoming damage to:", CC.Gray + " * Activate \"Witcher\" pet effect", CC.Gray + " * Activate Titan \"Unbreakable\" pet effect", CC.Gray + " * Activate \"Skrrt\" pet effect", CC.Red + "1% chance on outgoing damage to:", CC.Gray + " * Activate Bloodhound \"Tag\" pet effect", CC.Gray + " * Activate Banshee \"RKO\" pet effect") ),
    CHRISTMAS_BACKPACK_REINDEER(CC.RedB + "Baby Reindeer", ItemSkinType.BACKPACK, new BabyReindeerSkin(), Arrays.asList(CC.Red + "Incoming damage has a 33% chance", CC.Red + "to break enemy Silence, Blackout", CC.Red + "Blood Pact ability", CC.Gray + " (50% chance for incoming damage that would", CC.Gray + " normally kill you or bring your health to <=6HP", CC.Gray + " to be redirected at a nearby in-combat ally", CC.Gray + " within 8 blocks for 2x the damage. If no", CC.Gray + " allies are within 8 blocks, you will receive", CC.Gray + " -25% incoming damage when <=6HP)", CC.Red + "Furry Friend ability", CC.Gray + " (Receive Absorbtion V for 8s if an incoming", CC.Gray + " attack brings your health to <=20HP, 30s", CC.Gray + " cooldown)") ),
    NYE_BACKPACK(CC.LightPurpleB + "Power Core", ItemSkinType.BACKPACK, new PowerCoreSkin(), Arrays.asList(CC.Red + "Aquatic I", CC.Red + "Lava Strider I", CC.Red + "Ghostly Ghost III", CC.Red + "Obsidianshield I", CC.Red + "Gears IV", CC.Red + "Obsidian you place spawns with 1 bonus", CC.Red + "durability until server reboot") ),
    SUMMER_2021_BACKPACK(CC.GoldB + "Surfboard", ItemSkinType.BACKPACK, new SurfboardSkin(), Arrays.asList(CC.Red + "Chance to not activate custom block cooldown") ),
    THANKSGIVING_BACKPACK_PICNIC(CC.YellowB + "Portable Picnic", ItemSkinType.BACKPACK, new PortablePicnicSkin(), Arrays.asList(CC.translate("&cNo Food loss"), CC.translate("&c-4% Incoming Damage"), CC.translate("&c+1 Food to nearby allies within"), CC.translate("&c10 blocks every 10s")) ),
    CHRISTMAS_SWORD_CANDY(CC.RedB + "Candy Blade", ItemSkinType.SWORD, new CandyBladeSkin(), Arrays.asList(CC.Red + "Chain Lifesteal X", CC.Red + "Sugar Burst Ability", CC.Gray + " (10% chance to deal damage to", CC.Gray + " enemies 20% more often for 3s)") ),
    TRIALS_SWORD(CC.GoldB + "Death and Taxes", ItemSkinType.SWORD, new DeathAndTaxesSkin(), Arrays.asList(CC.Red + "Death Toll ability", CC.Gray + " (8% chance to mark a player with \"Death Toll\",", CC.Gray + " which will track all damage you deal to", CC.Gray + " to them with Death and Taxes and damage", CC.Gray + " the player by 25% of damage dealt after 6s.)") ),
    MEDIEVAL_SWORD_KING(CC.GoldB + "King Sceptre", ItemSkinType.SWORD, new KingSceptreSkin(), Arrays.asList(CC.Red + "Master Inquisitive V", CC.Red + "2x mob drops from melee'd mobs", CC.Red + "2x vanilla EXP from melee'd mobs", CC.Red + "Auto /sell mob drops") ),
    SPRING_2021_SWORD(CC.GreenB + "Party Blade", ItemSkinType.SWORD, new PartyBladeSkin(), Arrays.asList(CC.Red + "Bypass enemy (Heroic) Aegis", CC.Red + "Immune to enemy Leadership", CC.Red + "+20% Outgoing damage when (Titan) Trapped/Frozen", CC.Red + "Inverted Knockback", CC.Gray + " (your enemy gets pulled to you each time you hit them)", CC.Red + "Silence enemy Amulet of Corruption for +3s per hit, max 30s") ),
    NYE_SWORD(CC.LightPurpleB + "Party Killer", ItemSkinType.SWORD, new PartyKillerSkin(), Arrays.asList(CC.Red + "Anti Gank II", CC.Red + "+1% Outgoing Damage per combat tagged", CC.Red + "enemy player in your chunk (max +5%)", CC.Red + "Attacks have a 20% chance to pull target", CC.Red + "slightly towards you and push nearby enemies", CC.Red + "within a 3x3 radius of target away", CC.Red + "Attacks combat tag all enemies", CC.Red + "within a 4x64x4 radius of a target", CC.Red + "50% Pet Leash Negation") ),
    NYE_2021_AXE(CC.translate("&9&lCosmic Key"), ItemSkinType.AXE, new CosmicKey(), Arrays.asList(CC.Red + "Up to 10% chance to dodge Deathbringer", CC.Red + "when health is below 10 HP", CC.Red + "Divine Immolation IV", CC.Red + "Titan Attribute: Atlas 3", CC.Red + "Up to +10% Outgoing Damage when health is below 15 HP") ),
    THANKSGIVING_2021_AXE(CC.translate("&6&lWhisk Taker"), ItemSkinType.AXE, new WhiskTaker(), Arrays.asList(CC.Red + "3% chance when hit to proc an AoE", CC.Red + "explosion which deals 5HP to all", CC.Red + "enemies in a 5x5 radius and makes", CC.Red + "deal +20% Outgoing Damage to", CC.Red + "all affected enemies for 6s", CC.Red + "+2.5 Outgoing Damage", CC.Red + "15% chance when your Rogue procs", CC.Red + "to make your enemy 45% slower for 1s", CC.Red + "5% chance to Teleblock", CC.Red + "4% chance to silence your enemies", CC.Red + "Divine Immolation for 8s") ),
    BACK_TO_SCHOOL_2021_AXE(CC.translate("&8&lBoosted Chainsaw"), ItemSkinType.AXE, new BoostedChainsaw(), Arrays.asList(CC.Red + "50% chance to bypass (Marty) Valor", CC.Red + "Double Strike III", CC.Red + "2.5% chance to silence enemy Soul Enchants for 8s", CC.Red + "Negate enemy Preserved Snowflake") ),
    JULY_2021_AXE(CC.translate("&f&lStormbreaker"), ItemSkinType.AXE,new StormBreaker(),Arrays.asList(CC.Red + "Blackout IV", CC.Red + "Divine Immolation I", CC.Red + "Titan Attribute: Kronos 8") ),
    CHRISTMAS_2021_SWORD(CC.translate("&4&lSeason's Beatings"), ItemSkinType.SWORD, new SeasonsBeatings(), Arrays.asList(CC.Red + "+7% Outgoing Damage to players wielding weapons with Hero Killer", CC.Red + "+10% Outgoing Damage to enemies with Soul Mode Activated", CC.Red + "3.5% chance to silence enemy (Reflective) Block for 8s", CC.Red + "Outgoing Damage while Frozen or Slowed has a 12.5% chance", CC.Red + "to remove the Freeze or Slow effect", CC.Red + "+10% Rage Damage") ),
    THANKSGIVING_2021_SWORD(CC.translate("&e&lCarver"), ItemSkinType.SWORD, new Carver(), Arrays.asList(CC.Red + "15% chance to deal 2x Divine Immolation tick damage", CC.Gray + "  (costs 2x extra souls on proc)", CC.Red + "33% chance to bypass Heroic Armor", CC.Red + "Damage you deal has a 3% chance to deal", CC.Red + "+50% Outgoing Soul Enchant Damage for 5s", CC.Red + "4% chance to silence your enemy's Rogue for 8s") ),
    HALLOWEEN_2021_SWORD(CC.translate("&6&lSpinal Tap"), ItemSkinType.SWORD, new SpinalTab(), Arrays.asList(CC.Red + "Hysteria Ability", CC.Gray + " (Every player you hit will grant you 1 Hysteria", CC.Gray + " stack up to a max of 5 stacks for 4s. For every", CC.Gray + " stack you have, you will receive +2% Outgoing", CC.Gray + " On your first hit at 5 stacks, you are", CC.Gray + " guaranteed to proc Blackout 4)", CC.Red + "2% chance to proc Rogue II", CC.Red + "when attacking enemies from behind", CC.Red + "4% chance to negate enemy Witch Hats for 10s") ),
    COSMIC_CITIZEN_SWORD(CC.AquaB + "Sparkler", ItemSkinType.SWORD, new SparklerSkin(), Arrays.asList(CC.Red + "Transforms any creeper you hit into a Raid Creeper") ),
    HALLOWEEN_SWORD(CC.GoldB + "Blade of Darkness", ItemSkinType.SWORD, new BladeOfDarknessSkin(), Arrays.asList(CC.Red + "2% chance to proc Revenge of Yijki", CC.Red + "4% Chain Lifesteal V", CC.Red + "33% chance to reflect Traps", CC.Red + "Soul Siphon III", CC.Red + "Headless III", CC.Red + "Immune to Blindness") ),
    JULY_2021_BACKPACK(CC.translate("&f&lCosmic Cape"), ItemSkinType.BACKPACK, new CosmicCapeSkin(), Arrays.asList(CC.Red + "Immunte to Hero Killer", CC.Red + "Decreases incoming damage from Soul Enchants by 15%", CC.Red + "2.5% chance to negate enemy Shockwave and (Guided) Rocket", CC.Red + "Escape on outgoing damage for 10s", CC.Red + "Increases outgoing damage by up to 15% when under 50% HP") ),
    THANKSGIVING_SWORD_FEAST(CC.YellowB + "The Feast", ItemSkinType.SWORD, new TheFeastSkin(), Arrays.asList(CC.Red + "Soul Tether V", CC.Red + "-1 Food on enemy on Outgoing Damage", CC.Red + "20% chance to gain 1HP as you feed on your enemy", CC.Red + "20% chance to afflict enemies with Hunger IV for 3s") ),

    THANKSGIVING_2021_BOOTS(CC.translate("&8&lLoafers"), ItemSkinType.BOOTS, new LoafersSkin(), Arrays.asList(CC.Red + "Gears IV", CC.Red + "3.5% chance to make enemy (Vampiric) Devour heal", CC.Red + "instead of hurt you by the same % for 5s", CC.Red + "2.5% chance to silence enemy Pets in a 6x6 radius for 10s", CC.translate("&cGears V for 6s when affected by an enemy's Heroic Pet")) ),


    CHRISTMAS_2021_BOOTS(CC.translate("&2&lSleigh Riders"), ItemSkinType.BOOTS, new SleighRiders(), Arrays.asList(CC.Red + "Gears IV", CC.Red + "Limit enemy Amulet of Corruption to 1 armor set type", CC.Red + "When affected by any Slowness, you will receive -1%", CC.Red + "Incoming Damage (up to a max of 8%) and +1% Outgoing", CC.translate("&cDamage (up to a max of 15%) per 5% HP lost"), CC.translate("&c&l3% chance to clear your enemy's rage stacks and"), CC.translate("&csilence their ability to gain more for 7s"), CC.translate("&c&l7% chance to negate enemy Rogue for 8s when their"), CC.translate("&c&lhealth is below 20 HP")) ),


    JULY_2021_BELT(CC.translate("&9&lInfinity Belt"), ItemSkinType.BELT, new InfinityBeltSkin(), Arrays.asList(CC.Red + "Equipped armor will regain to 10% durability", CC.Red + "instead of breaking (10m cooldown)", CC.Red + "Immune to Soul trap", CC.Red + "Immune to (Obsidian) Guardians", CC.translate("&cNegates enemy (Reinforced) Tank")) ),


    BACK_TO_SCHOOL_2021_BOOTS(CC.translate("&8&lBig Timbs"), ItemSkinType.BOOTS, new BigTimbsSkin(), Arrays.asList(CC.Red + "35% chance on incoming damage to", CC.Red + "negate enemy Rage stack multipliers", CC.Red + "Immune to Hero Killer", CC.Red + "Immune to enemy outgoing Heroic Pet abilities", CC.translate("&cGears IV")) ),
    HALLOWEEN_2021_BOOTS(CC.translate("&8&lBloodstained Galoshes"), ItemSkinType.BOOTS, new BloodstainedGaloshesSkin(), Arrays.asList(CC.Red + "Gears IV", CC.Red + "Immune to World Destroyer Cage", CC.Red + "25% chance to reflect cage back at the user", CC.Red + "Immune to Slownesses/Freezes", CC.Red + "Increases Execute HP threshold to proc by 1.3x", CC.translate("&cPsychotic Pursuit Ability"), CC.translate("&7 (+5% chance to proc Dodge +7 Outgoing"), CC.translate("&7 Damage on successful (Ethereal) Dodge procs"), CC.translate("&7 for a short duration")) ),


    BACK_TO_SCHOOL_2021_HELMET(CC.translate("&6&lPylon Head"), ItemSkinType.HELMET, new PylonHeadSkin(), Arrays.asList(CC.Red + "Reduces the duration of active Silence", CC.Red + "or Blackout by 40% on outgoing damage.", CC.Red + "Limits enemy Amulet of Corruption so that it", CC.Red + "affects defensive Armor Crystal attributes only") ),
    HALLOWEEN_2021_HELMET(CC.translate("&6&lMineman Muzzle"), ItemSkinType.HELMET, new MinemanMuzzleSkin(), Arrays.asList(CC.Red + "25% chance to curse enemies with +33% Incoming", CC.Red + "Damage when their Phoenix procs for 10s", CC.Gray + " (Can only proc once every 10m)", CC.Red + "3.5% chance to negate enemy Cosmic Cape for 10s", CC.Red + "Immune to Banshee RKO", CC.Red + "10% chance to reflect damage to enemies in a 3x3 radius") ),
    THANKSGIVING_2021_HELMET(CC.translate("&6&lBread Head"), ItemSkinType.HELMET, new BreadHatSkin(), Arrays.asList(CC.Red + "1.5% chance to deal 10% of your", CC.Red + "enemy's current HP when hit", CC.Red + "1% chance to silence enemy Soul", CC.Red + "Enchants for 7s when you're hit", CC.Red + "25% chance to reflect (Titan) Trap", CC.Red + "effects back onto your attacker", CC.Red + "-15% Incoming Titan Attribute Damage") ),
    CHRISTMAS_2021_HELMET(CC.translate("&2&lKrampus Horns"), ItemSkinType.HELMET, new KrampusHornsSkin(), Arrays.asList(CC.Red + "-10% Incoming Soul Enchant Damage", CC.Red + "Increases Deadly Disarmor health", CC.Red + "proc threshold by 4 HP", CC.Red + "-45% Silence Reduction", CC.Red + "75% chance to block enemy Ragdoll", CC.Red + "Immune to (Lethal) Sniper") ),

    THANKSGIVING_2021_AMULET(CC.translate("&e&lThe Crumble"), ItemSkinType.AMULET, new TheCrumbleSkin(), Arrays.asList(CC.Red + "7.5% chance to receive -2.5% Incoming", CC.Red + "Damage (up to a max of -15%) for 8s", CC.Red + "when receiving Divine Immolation damage", CC.Red + "Immune to (Obsidian) Guardians", CC.Red + "5% chance to silence your enemy's", CC.Red + "Phoenix for 6s when they're under 15 HP", CC.Red + "Immune to enemy Amulet of Corruption") ),
    CHRISTMAS_2021_AMULET(CC.translate("&4&lCranked Amulet"), ItemSkinType.AMULET, new CrankedAmuletSkin(), Arrays.asList(CC.Red + "Immune to Hero Killer", CC.Red + "8% chance to double proc offensive Heroic Enchants", CC.Red + "3% chance to silence enemy Soul Enchants for 6s", CC.Red + "Escape Hatch Ability", CC.Gray + " (If your health  is below 8HP and a player uses a", CC.Gray + " Heroic Pet on you, they are launched into the air", CC.Gray + " Cooldown: 2 minutes)") ),
    BACK_TO_SCHOOL_2021_BELT(CC.translate("&8&lBandolier Belt"), ItemSkinType.BELT, new BandolierBeltSkin(), Arrays.asList(CC.Red + "Bypass enemy Witch Hat", CC.Red + "Immune to Deep Bleed Slowness", CC.Red + "Limits an enemy stackable enchant level by 50%") ),

    HALLOWEEN_2021_MUTATED_SWORD(CC.translate("&2&l&nMutated&r &6&lSpinal Tap"), ItemSkinType.SWORD, new MutatedSpinalTab(), Arrays.asList(CC.Red + "Hysteria Ability", CC.Gray + " (Every player you hit will grant you 1 Hysteria", CC.Gray + " stack up to a max of 5 stacks for 4s. For every", CC.Gray + " stack you have, you will receive +2% Outgoing", CC.Gray + " On your first hit at 5 stacks, you are", CC.Gray + " guaranteed to proc Blackout 4)", CC.Red + "2% chance to proc Rogue II", CC.Red + "when attacking enemies from behind", CC.Red + "4% chance to negate enemy Witch Hats for 10s", CC.DarkGreen + "10% chance to block Divine Immolation")),
    CHRISTMAS_2021_MUTATED_SWORD(CC.translate("&2&l&nMutated&r &4&lSeason's Beatings"), ItemSkinType.SWORD, new MutatedSeasonsBeatings(), Arrays.asList(CC.Red + "+7% Outgoing Damage to players wielding weapons with Hero Killer", CC.Red + "+10% Outgoing Damage to enemies with Soul Mode Activated", CC.Red + "3.5% chance to silence enemy (Reflective) Block for 8s", CC.Red + "Outgoing Damage while Frozen or Slowed has a 12.5% chance", CC.Red + "to remove the Freeze or Slow effect", CC.Red + "+10% Rage Damage", CC.DarkGreen + "Soul Master VI") ),

    THANKSGIVING_2021_BACKPACK(CC.translate("&6&lTommy Tail"), ItemSkinType.BACKPACK, new TommyTail(), Arrays.asList(CC.Red + "+5% Outgoing PvP Damage when using a sword", CC.Red + "Immune to Hero Killer", CC.Red + "7.5% chance to block Soul Enchant Damage", CC.Red + "2% chance to activate a LVL 10", CC.Red + "Banshee Pet effect when damaged", CC.Red +"2x Outgoing Damage on any hit that makes", CC.Red + "your enemy's (Guided) Rocket Escape", CC.Red + "Immune to (Lethal) Sniper")),
    THANKSGIVING_2021_MUTATED_BACKPACK(CC.translate("&2&l&nMutated&r &6&lTommy Tail"), ItemSkinType.BACKPACK, new MutatedTommyTail(), Arrays.asList(CC.Red + "+5% Outgoing PvP Damage when using a sword", CC.Red + "Immune to Hero Killer", CC.Red + "7.5% chance to block Soul Enchant Damage", CC.Red + "2% chance to activate a LVL 10", CC.Red + "Banshee Pet effect when damaged", CC.Red +"2x Outgoing Damage on any hit that makes", CC.Red + "your enemy's (Guided) Rocket Escape", CC.Red + "Immune to (Lethal) Sniper", CC.DarkGreen + "Increases outgoing damage by up to 10%", CC.DarkGreen + "when under 60% HP")),
    JULY_2021_BOOTS(CC.translate("&c&lBarry's Bolts"), ItemSkinType.BOOTS, new BarrysBoltsSkin(), Arrays.asList(CC.Red + "Gears IV", CC.Red + "Immune to Slowness Potion Effect", CC.Red + "-15% damage from enemy (Bloody) Deep Wounds", CC.Red + "Flash Ability", CC.translate("&7 (10% chance when damaged to activate. When"), CC.translate("&7 active, all enemies you hit will have their"), CC.translate("&7 pets and Phoenix enchantment silenced, your"), CC.translate("&7 Soul Tether will do +20% damage and incoming"), CC.translate("&7 soul damage to your allies within a 4x4"), CC.translate("&7radius is reduced by -5%")));




    private final String display;

    private final ItemSkinType itemSkinType;

    private final List<String> lore;

    private final ItemSkin skin;


    public List<String> getLore() {
        return lore;
    }

    SkinType(String display, ItemSkinType itemSkinType, ItemSkin skin, List<String> extra) {
        this.lore = new ArrayList<>();
        this.display = display;
        this.itemSkinType = itemSkinType;
        this.skin = skin;
        this.lore.addAll(extra);
    }


    public String getDisplay() {
        return this.display;
    }
    public String getDisplayName() { return CC.WhiteB + "Item Skin (" + this.display + CC.WhiteB + ")"; }
    public String getName() { return this.display; }
    public String getAppliedLore() { return CC.WhiteB + "ITEM SKIN: " + this.display; }
    public Material getMaterial() { return Material.EYE_OF_ENDER; }
    public ItemSkinType getItemSkinType() { return this.itemSkinType; }
    public ItemSkin getItemSkin() { return this.skin; }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.WhiteB + "Item Skin (" + getName() + CC.WhiteB + ")");
        List<String> lore = new ArrayList<>();
        for (String line : getLore()) {
            lore.add(line);
        }
        lore.add("");
        lore.add(CC.GrayI + "Attach this skin to any " + CC.WhiteU + getItemSkinType().getModifiedName());
        lore.add(CC.Gray + "to override its visual appearance.");
        lore.add("");
        lore.add(CC.RedB + "NOTE: " + CC.Red + "Requires the Cosmic Client");
        lore.add(CC.Red + "to render custom item skins.");
        lore.add(CC.Gray + "* Download at " + CC.GrayU + "discord.astropvp.net");
        lore.add("");
        lore.add(CC.Gray + "Drag n' Drop onto item to attach.");
        lore.add(CC.Gray + "Right-Click item to detach skin.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setString("itemSkinType", name());
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");

        }
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        wrapper.setString("random", UUID.randomUUID().toString());
        wrapper.setString("cosmicType", "item_skin");

        return wrapper.build();
    }

    public ItemStack applyBackpack(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        if(!cosmicData.hasKey("itemSkinType")) {
            cosmicData.setString("itemSkinType", name());
        } else {
            String[] current = cosmicData.getString("itemSkinType").split(",");
            List<String> strings = new ArrayList<>(Arrays.asList(current));
            strings.add(name());
            String s = Arrays.toString(strings.toArray());
            s = s.replace("[", "");
            s = s.replace("]", "");
            s = s.replace(" ", "");

            cosmicData.setString("itemSkinType", s);
        }
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");

        }
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        return wrapper.build();
    }

    public ItemStack apply(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
        lore.add(CC.WhiteB + "ITEM SKIN: " + getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        if(!cosmicData.hasKey("itemSkinType")) {
            cosmicData.setString("itemSkinType", name());
        } else {
            String[] current = cosmicData.getString("itemSkinType").split(",");
            List<String> strings = new ArrayList<>(Arrays.asList(current));
            strings.add(name());
            String s = Arrays.toString(strings.toArray());
            s = s.replace("[", "");
            s = s.replace("]", "");
            s = s.replace(" ", "");
            cosmicData.setString("itemSkinType", s);
        }
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");
        }
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        return wrapper.build();
    }

    public ItemStack getMultiSkin(SkinType type) {
        ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.WhiteB + "Multi Item Skin (" + getName() + ", " + type.getName() + CC.WhiteB + ")");
        List<String> lore = new ArrayList<>();
        lore.add(type.display);
        lore.addAll(type.getLore());
        lore.add("");
        lore.add(display);
        lore.addAll(getLore());
        lore.add("");
        lore.add(CC.GrayI + "Attach this skin to any " + CC.WhiteU + getItemSkinType().getModifiedName());
        lore.add(CC.Gray + "to override its visual appearance.");
        lore.add("");
        lore.add(CC.RedB + "NOTE: " + CC.Red + "Requires the Cosmic Client");
        lore.add(CC.Red + "to render custom item skins.");
        lore.add(CC.Gray + "* Download at " + CC.GrayU + "discord.astropvp.net");
        lore.add("");
        lore.add(CC.Gray + "Drag n' Drop onto item to attach.");
        lore.add(CC.Gray + "Right-Click item to detach skin.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.setString("itemSkinType", name() + "," + type.name());
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");

        }
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        wrapper.setString("random", UUID.randomUUID().toString());
        wrapper.setString("cosmicType", "item_skin");

        return wrapper.build();
    }

    public ItemStack apply(ItemStack item, SkinType type) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
        lore.add(CC.WhiteB + "ITEM SKIN: " + getName());
        lore.add(CC.WhiteB + "ITEM SKIN: " + type.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        String[] current;
        if(cosmicData.hasKey("itemSkinType")) {
            current = cosmicData.getString("itemSkinType").split(",");
        } else {
            current = new String[0];
        }
        List<String> strings = new ArrayList<>(Arrays.asList(current));
        strings.add(name());
        strings.add(type.name());
        String s = Arrays.toString(strings.toArray());
        s = s.replace("[", "");
        s = s.replace("]", "");
        s = s.replace(" ", "");
        cosmicData.setString("itemSkinType", s);
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");

        }
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        return wrapper.build();
    }

    public ItemStack applyNoDisplay(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        if(!cosmicData.hasKey("itemSkinType")) {
            cosmicData.setString("itemSkinType", name());
        } else {
            String[] current = cosmicData.getString("itemSkinType").split(",");
            List<String> strings = new ArrayList<>(Arrays.asList(current));
            strings.add(name());
            String s = Arrays.toString(strings.toArray());
            s = s.replace("[", "");
            s = s.replace("]", "");
            s = s.replace(" ", "");

            cosmicData.setString("itemSkinType", s);
        }
        if (this.name().contains("MUTATED")) {
            cosmicData.setString("tintColor", "-10237440");

        }
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        return wrapper.build();
    }

}