package me.astropvp.astropvpskins.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import me.astropvp.astropvpskins.objects.ItemSkin;
import me.astropvp.astropvpskins.objects.skins.amulets.*;
import me.astropvp.astropvpskins.objects.skins.axes.CorruptAxeSkin;
import me.astropvp.astropvpskins.objects.skins.axes.EasterBasketSkin;
import me.astropvp.astropvpskins.objects.skins.axes.MeatCleaverAxeSkin;
import me.astropvp.astropvpskins.objects.skins.axes.SpikedBaseballBatSkin;
import me.astropvp.astropvpskins.objects.skins.belts.*;
import me.astropvp.astropvpskins.objects.skins.boots.*;
import me.astropvp.astropvpskins.objects.skins.bows.CupidBowSkin;
import me.astropvp.astropvpskins.objects.skins.chestplates.DeepSpaceBackpack;
import me.astropvp.astropvpskins.objects.skins.helmets.*;
import me.astropvp.astropvpskins.objects.skins.pickaxes.RubberChickenSkin;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.CorruptSwordSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.CupidSwordSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.DeathKnightSkullBladeSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.DoomsdayMacheteSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.EasterSwordSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.JollyCandySwordSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.PepeWeaponSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.SelfieStickSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.SkullScytheSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.SpringSwordSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.TridentSkin;
/*     */ import me.astropvp.astropvpskins.objects.skins.swords.VirusSwordSkin;
/*     */ import me.astropvp.astropvpskins.utils.Utils;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;

public enum SkinType {
    CUPID_HELMET((CC.RedB) + "Cupid's Crown", ItemSkinType.HELMET, new MeatCleaverAxeSkin(), Arrays.asList((CC.Red) + "5% to steal enemy health if below half health.")),
    MEAT_CLEAVER_AXE((CC.RedB) + "Meat Cleaver " + CC.DarkRedB + "Axe", ItemSkinType.AXE, new MeatCleaverAxeSkin(), Arrays.asList(new String[0])),
    CUPID_BELT((CC.RedB) + "Divine Sash", ItemSkinType.BELT, new CupidBeltSkin(), Arrays.asList((CC.Red) + "Auto Bless")),
    SEXY_BELT((CC.LightPurpleB) + "Gucci Belt", ItemSkinType.BELT, new SexyBeltSkin(), Arrays.asList((CC.Red) + "-25% Incoming Projectile Damage", (CC.Red) + "All pearls act as escapist pearls")),
    CORRUPT_BELT_POWER((CC.DarkPurpleB) + "Corrupt Belt", ItemSkinType.BELT, new CorruptPowerBeltSkin(), Arrays.asList((CC.Red) + "Godly Overload V")),
    CORRUPT_BELT_UTILITY((CC.DarkPurpleB) + "Utility Belt", ItemSkinType.BELT, new CorruptUtilityBeltSkin(), Arrays.asList((CC.Red) + "Phoenix cannot be blocked", (CC.Red) + "Auto Feign Death (with Phoenix)")),
    CUPID_BOW((CC.RedB) + "Cupid's Bow", ItemSkinType.BOW, new CupidBowSkin(), Arrays.asList(new String[0])),
    CUPID_BOOTS((CC.RedB) + "Cupid's Winged Boots", ItemSkinType.BOOTS, new CupidBootsSkin(), Arrays.asList((CC.Red) + "Chance to push attacker away.")),
    CORRUPT_BOOTS((CC.DarkPurpleB) + "Rocket Boots", ItemSkinType.BOOTS, new CorruptBootsSkin(), Arrays.asList((CC.Red) + "Immune to Sabotage", (CC.Red) + "Gears IV")),
    SEXY_BOOTS((CC.LightPurpleB) + "Bunny Slippers", ItemSkinType.BOOTS, new SexyBootsSkin(), Arrays.asList((CC.Red) + "Web/Lava Walker", (CC.Red) + "2% Dodge")),
    DEATH_KNIGHT_SKULL_BLADE((CC.AquaB) + "Death Knight " + CC.BlueB + "Skull Blade", ItemSkinType.SWORD, new DeathKnightSkullBladeSkin(), Arrays.asList((CC.Red) + "Equipped weapon can not get anti-hollied.")),
    JOLLY_CANDY_SWORD((CC.RedB) + "Jolly " + CC.WhiteB + "Candy " + CC.RedB + "Sword", ItemSkinType.SWORD, new JollyCandySwordSkin(), Arrays.asList((CC.Red) + "+7.5% DMG")),
    FLAMING_HALO((CC.YellowB) + "Flaming " + CC.GoldB + "Halo", ItemSkinType.HELMET, new FlamingHaloSkin(), Arrays.asList((CC.Red) + "+4% Outgoing Damage")),
    REINDEER_ANTLERS((CC.GrayB) + "Reindeer Antlers", ItemSkinType.HELMET, new ReindeerAntlersSkin(), Arrays.asList((CC.Red) + "Chance to double active pet duration.")),
    SANTA_HAT((CC.RedB) + "Santa Hat", ItemSkinType.HELMET, new SantaHatSkin(), Arrays.asList((CC.Red) + "+5% DMG", (CC.Red) + "-5% DMG")),
    KOALA((CC.GrayB) + "Koala", ItemSkinType.HELMET, new KoalaSkin(), Arrays.asList((CC.Red) + "Immune to Fire/Blast DMG")),
    CORRUPT_AXE((CC.DarkPurpleB) + "Corrupt Greataxe", ItemSkinType.AXE, new CorruptAxeSkin(), Arrays.asList((CC.Red) + "Bypass Defensive Armor Set Bonuses")),
    CORRUPT_SWORD((CC.DarkPurpleB) + "Corrupt Greatsword", ItemSkinType.SWORD, new CorruptSwordSkin(), Arrays.asList((CC.Red) + "2x Chance to Neutralize enemies", (CC.Red) + "2x Blackout duration on enemies.")),
    SEXY_SWORD((CC.LightPurpleB) + "Selfie Stick", ItemSkinType.SWORD, new SelfieStickSkin(), Arrays.asList(new String[0])),
    CUPID_SWORD((CC.RedB) + "Blade of Love", ItemSkinType.SWORD, new CupidSwordSkin(), Arrays.asList((CC.Red) + "2% Chain Lifesteal")),
    SEXY_HELMET((CC.LightPurpleB) + "Chad Sunglasses", ItemSkinType.HELMET, new SexyHelmetSkin(), Arrays.asList((CC.Red) + "50% Chance to Block World Destroyer")),
    CORRUPT_HELMET(CC.DarkPurpleB + "Corrupt Cosmonaut Visor", ItemSkinType.HELMET, new CorruptHelmetSkin(), Arrays.asList((CC.Red) + "50% Chance to block Neutralize", (CC.Red) + "50% Silence Duration")),
    CUPID_AMULET((CC.RedB) + "Amulet of Angels", ItemSkinType.AMULET, new CupidAmuletSkin(), Arrays.asList((CC.Red) + "Immune to Nature's Wrath", (CC.Red) + "Immune to Revenge of Yijki")),
    SEXY_AMULET((CC.LightPurpleB) + "40K Gold Chain", ItemSkinType.AMULET, new SexyHelmetSkin(), Arrays.asList((CC.Red) + "+2% /sell prices")),
    CORRUPT_AMULET_CORRUPTION((CC.DarkPurpleB) + "Amulet of Corruption", ItemSkinType.AMULET, new CorruptAmuletCorruptionSkin(), Arrays.asList((CC.Red) + "Immune to enemy armor crystals of same type on attached item.")),
    CORRUPT_AMULET_DESTRUCTION((CC.DarkPurpleB) + "Amulet of Destruction", ItemSkinType.AMULET, new CorruptAmuletDestructionSkin(), Arrays.asList((CC.Red) + "+7.5% Outgoing Damage")),
    VIRUS_SWORD((CC.DarkGreenB) + "Corona Beer Bottle", ItemSkinType.SWORD, new VirusSwordSkin(), Arrays.asList((CC.Red) + "+3.5% Outgoing Damage", (CC.Red) + "Mighty Cleave V")),
    SPRING_2020_SWORD((CC.GreenB) + "Multi-Colored " + CC.YellowB + "Popsicle", ItemSkinType.SWORD, new SpringSwordSkin(), Arrays.asList((CC.Red) + "10% Chance to bypass pet cooldown.")),
    SPRING_2020_PICKAXE((CC.GreenB) + "Rubber " + CC.YellowB + "Chicken", ItemSkinType.PICKAXE, new RubberChickenSkin(), Arrays.asList(new String[0])),
    VIRUS_BOOTS((CC.DarkGreenB) + "Hazmat Boots", ItemSkinType.BOOTS, new VirusBootsSkin(), Arrays.asList((CC.Red) + "Auto Bless", CC.Red + "Chance to activate Silence Pet", CC.Gray + "  (7x7 area for 12 seconds)")),
    SPRING_2020_BOOTS((CC.GreenB) + "Rainbow " + CC.YellowB + "Slides", ItemSkinType.BOOTS, new RainbowSlidesSkin(), Arrays.asList((CC.Red) + "Gears IV", (CC.Red) + "Jump Boost 1 in all /dungeon parkour regions.", (CC.Red) + "5% chance to not consume /dungeon key.")),
    SPRING_2020_AMULET((CC.GreenB) + "Staff " + CC.YellowB + "Pass", ItemSkinType.AMULET, new StaffPassAmuletSkin(), Arrays.asList((CC.Red) + "Immune to arrows")),
    SPRING_2020_AXE((CC.GreenB) + "Spiked " + CC.YellowB + "Baseball Bat", ItemSkinType.AXE, new SpikedBaseballBatSkin(), Arrays.asList((CC.Red) + "-5% Incoming Damage from enemies wielding Swords", (CC.Red) + "+10% Outgoing Damage " + "to enemies wielding Swords")),
    VIRUS_BELT((CC.DarkGreenB) + "First Aid Fannypack", ItemSkinType.BELT, new VirusBeltSkin(), Arrays.asList((CC.Red) + "50% chance to negate Blackout proc")),
    VIRUS_HELMET_HAZMAT((CC.DarkGreenB) + "Hazmat Helmet", ItemSkinType.HELMET, new HazmatHelmetSkin(), Arrays.asList((CC.Red) + "Immune to Freezes")),
    VIRUS_HELMET_N95((CC.DarkGreenB) + "Anti Corona Mask", ItemSkinType.HELMET, new AntiCoronaMaskSkin(), Arrays.asList((CC.Red) + "Immune to Teleblock", (CC.Red) + "2% chance to proc Regeneration 2 for 25s")),
    PEPE_WEAPON((CC.BlueB) + "Pepe Clapper", ItemSkinType.WEAPON, new PepeWeaponSkin(), Arrays.asList((CC.Red) + "Convert hit players into a rare pepe.")),
    EASTER_HELMET((CC.RedB) + "Bunny Ears", ItemSkinType.HELMET, new EasterHelmetSkin(), Arrays.asList((CC.Red) + "Ethereal Dodge VI")),
    EASTER_AMULET((CC.RedB) + "Rabbit's Foot", ItemSkinType.AMULET, new RabitsFootAmuletSkin(), Arrays.asList((CC.Red) + "+10% Outgoing DMG")),
    EASTER_BELT((CC.RedB) + "Rainbow Belt", ItemSkinType.BELT, new EasterBeltSkin(), Arrays.asList((CC.Red) + "-7.5% Incoming DMG")),
    EASTER_SWORD((CC.RedB) + "Jumbo Chocolate Sword", ItemSkinType.SWORD, new EasterSwordSkin(), Arrays.asList((CC.Red) + "+15% Outgoing PvE DMG", (CC.Red) + "Devour V")),
    EASTER_PICKAXE((CC.RedB) + "Easter Basket", ItemSkinType.AXE, new EasterBasketSkin(), Arrays.asList((CC.Red) + "-10% Incoming DMG")),
    PEPE_HELMET((CC.BlueB) + "Pepe Helmet", ItemSkinType.HELMET, new PepeHelmetSkin(), Arrays.asList(new String[0])),
    SKULL_SCYTHE((CC.RedB) + "Skull Scythe", ItemSkinType.SWORD, new SkullScytheSkin(), Arrays.asList((CC.Red) + "+2.5% Outgoing Damage", (CC.Red) + "10% chance to silence amulet of corruption for 10s")),
    DOOMSDAY_MACHETE((CC.DarkGreenB) + "Doomsday Machete", ItemSkinType.SWORD, new DoomsdayMacheteSkin(), Arrays.asList((CC.Red) + "Increases outgoing damage from soul enchantments by 1.25x")),
    TRIDENT_SWORD((CC.DarkAquaB) + "Trident of the Deep", ItemSkinType.SWORD, new TridentSkin(), Arrays.asList((CC.Red) + "Blessed IV", (CC.Red) + "Hex V", (CC.Red) + "Mark of the Beast VI", (CC.Red) + "25% chance to bypass Full Armor Set", (CC.Red) + "or Armor Crystal defensive bonus")),
    MOON_BOOTS((CC.AquaB) + "Moon Boots", ItemSkinType.BOOTS, new MoonBootsSkin(), Arrays.asList((CC.Red) + "Increases armor set bonus/crystal damage by 1.2x")),
    KNIGHT_BOOTS((CC.GrayB) + "Knight's Boots", ItemSkinType.BOOTS, new KnightBootsSkin(), Arrays.asList((CC.Red) + "Immune to all but vanilla knockback")),
    CAMOUFLAGE_BOOTS((CC.DarkGreenB) + "Camouflage Yeezys", ItemSkinType.BOOTS, new CamouflageBootsSkin(), Arrays.asList((CC.Red) + "40% chance to negate soul trap", (CC.Red) + "Demonic Gateway V")),
    BOOTS_RETALIATION((CC.DarkGreenB) + "Retaliation Boots", ItemSkinType.BOOTS, new RetaliationBoots(), Arrays.asList((CC.Red) + "+15% Outgoing Damage to Bosses", (CC.Red) + "+10% Incoming Damage")),
    STARQUEST_AMULET((CC.GoldB) + "Starquest Amulet", ItemSkinType.AMULET, new StarquestAmuletSkin(), Arrays.asList((CC.Red) + "4% chance to earn extra dungeon lootbag")),
    DEEPSPACE_BACKPACK((CC.DarkPurpleB) + "Deep Space Backpack", ItemSkinType.CHESTPLATE, new DeepSpaceBackpack(), Arrays.asList((CC.Red) + "+3% Outgoing Damage", (CC.Red) + "Immune to Soul Trap")),
    CANDY_COLLECTOR_HELMET((CC.GoldB) + "Candy Collector", ItemSkinType.HELMET, new CandyCollectorSkin(), Arrays.asList((CC.Red) + "-20% incoming damage from enemies wielding swords", (CC.Red) + "1% chance to activate witcher ability")),
    LEVIATHAN_TROPHY(CC.translate("&3&lLeviathan Trophy"), ItemSkinType.HELMET, new LeviathanTrophySkin(),
            Arrays.asList(
                    CC.translate("&cIncreased chance to drop Ancient Scrolls"),
                    CC.translate("&cwhen killing Guardians in /ruins"),
                    CC.translate("&cImmune to enemy Amulet of Corruption"),
                    CC.translate("&c+1 Max Heart"),
                    CC.translate("&c3% chance to silence enemy Soul Enchants"),
                    CC.translate("  &7(7x7 blocks for 7s)")
            )),
    DOOMSDAY_HELMET(CC.translate("&2&lDoomsday Helmet"), ItemSkinType.HELMET, new DoomsDayHelmetSkin(),
            Arrays.asList(
                    CC.translate("&cAbility to see invisible players"),
                    CC.translate("&7(Feign Death, Undead Ruse)")
            )),
    HEART_HALO(CC.translate("&c&lHeart Halo"), ItemSkinType.HELMET, new HearthHaloSkin(),
            Arrays.asList(
                    CC.translate("&cPassively removes 1 Bleedstack every 2s"),
                    CC.translate("&cImmune to Soul Harvester"),
                    CC.translate("&c1.25% (Divine) Enlighted Healing")
            )),
    NORTH_STAR(CC.translate("&a&lNorth Star"), ItemSkinType.HELMET, new NorthStarSkin(),
            Arrays.asList(
                    CC.translate("&c33% Chance to negate Monopoly Mask"),
                    CC.translate("&c-33% enemy Blackout duration"),
                    CC.translate("&cBlocks all enemy (Guided) Rocket Escape"),
                    CC.translate("&cDivine Enlighted IV")
            )),
    TIKI_MASK(CC.translate("&6&lTiki Mask"), ItemSkinType.HELMET, new TikiMaskSkin(),
            Arrays.asList(
                    CC.translate("&cLimites enemy amulet of corruption to 1 set type"),
                    CC.translate("&c25% chance to block enemy Silence"),
                    CC.translate("&cLimits enemy Silence to 60% duration")
            )),
    NEON_EMOTIONS(CC.translate("&d&lNeon Emotions"), ItemSkinType.HELMET, new NeonEmotionsSkin(),
            Arrays.asList(
                    CC.translate("&c+6% Outgoing Damage"),
                    CC.translate("&c-2% Incoming Damage"),
                    CC.translate("&cImmune to Slowness Potion Effect")
            )),
    WITCH_HAT(CC.translate("&e&lWitch Hat"), ItemSkinType.HELMET, new NeonEmotionsSkin(),
            Arrays.asList(
                    CC.translate("&c-25% Silence duration"),
                    CC.translate("&cImmune to offensive Titan Attributes"),
                    CC.translate("&7(Atlas, Ouranos and Prometheus)"),
                    CC.translate("&cImmune to Trap"),
                    CC.translate("&cImmune to Bloodhound & Banshee Pet"),
                    CC.translate("&c10% chance to deflect incoming damage"),
                    CC.translate("&conto nearby enemy"),
                    CC.translate("&c2% chance to enable a LVL 10"),
                    CC.translate("&cBanshee Pet effect when damaged")
            )),

    AUTUMN_AMULET(CC.translate("&e&lAutumn Amulet"), ItemSkinType.AMULET, new AutumnAmuletSkin(),
            Arrays.asList(
                    CC.translate("&c+33% healing from Divine Enlighted"),
                    CC.translate("&cEach time you enter combat:"),
                    CC.translate("&c* No cooldown on 1st Phoenix Proc"),
                    CC.translate("&c* -15% Incoming Damage for 15s"),
                    CC.translate("&c* Absorption V for 20s")
            )),
    LUAU_LEI(CC.translate("&c&lLuau Lei"), ItemSkinType.AMULET, new LuauLei(),
            Arrays.asList(
                    CC.translate("&c+25% Outgoing Soul Enchant damage"),
                    CC.translate("&cChance to block Soul Enchant Damage Modifiers")
            )),
    CUPIDS_HEART(CC.translate("&c&lCupids Heart"), ItemSkinType.BELT, new CupidsHeart(),
            Arrays.asList(
                    CC.translate("&cBypass enemy (Paladin) Armored"),
                    CC.translate("&cHoly Aegis VII"),
                    CC.translate("&c+10% Outgoing Damage to Silenced enemies")
            )),
    SNOWFLAKE_SLIPPERS(CC.translate("&c&lSnowflake Flippers"), ItemSkinType.BOOTS, new SnowflakeFlippersSkin(),
            Arrays.asList(
                    CC.translate("&cGears IV"),
                    CC.translate("&cImmune to Teleblock"),
                    CC.translate("&cImmune to Slowness Potion Effect"),
                    CC.translate("&cSnowflake Storm Ability"),
                    CC.translate("&7(10% chance to spawn 4x4 snow floor for 10s."),
                    CC.translate("&7While stood on the snow floor:"),
                    CC.translate("&7* You will receive -20% Incoming Damage"),
                    CC.translate("&7* Your allies will receive -5% Incoming Damage"),
                    CC.translate("&7* Your enemies will receive Slowness II)")
            )),
    LOST_AMULET(CC.translate("&3&lLost Amulet"), ItemSkinType.AMULET, new LostAmulet(),
            Arrays.asList(CC.translate("&c50% Neutralize Negation"), CC.translate("&c-5% Incoming Damage"), CC.translate("&cIncreases outgoing damage from Soul Enchants by 1.1x")))
    ;

    private String display;
    private ItemSkinType itemSkinType;
    private List<String> lore;
    private ItemSkin skin;

    private SkinType(String display, ItemSkinType itemSkinType, ItemSkin skin, List<String> extra) {
        this.lore = new ArrayList<String>();
        this.display = display;
        this.itemSkinType = itemSkinType;
        this.skin = skin;
        this.lore.addAll(extra);
    }

    public String getDisplayName() {
        return (CC.WhiteB) + "Item Skin (" + this.display + CC.WhiteB + ")";
    }

    public String getName() {
        return this.display;
    }

    public String getAppliedLore() {
        return (CC.WhiteB) + "ITEM SKIN: " + this.display;
    }

    public Material getMaterial() {
        return Material.EYE_OF_ENDER;
    }

    public ItemSkinType getItemSkinType() {
        return this.itemSkinType;
    }

    public ItemSkin getItemSkin() {
        return this.skin;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.getMaterial());
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.addAll(this.lore);
        lore.addAll(this.itemSkinType.getLore());
        meta.setLore(lore);
        meta.setDisplayName(this.getDisplayName());
        item.setItemMeta(meta);
        item = Utils.addSkinNBT(this.name(), item);
        return item;
    }
}
