package me.ifamasssxd.cosmicbosses.struct.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Enforcer;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Exterminator;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Marksman;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.UndeadAssassin;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.YijkiDisciple;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbomination;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public enum BossType {
    SKELETON_HORSE(UndeadAssassin.class, ChatColor.RED + ChatColor.BOLD.toString() + "Undead Assassin", EntityType.SKELETON.getTypeId(), Sound.SKELETON_WALK, 0.4f, "A legendary assassin that rides a skeleton horse. Stories have told that the reason this assassin cannot be stopped is because he is already dead."),
    DEMONIC_WITHER(Yijki.class, ChatColor.GOLD + ChatColor.BOLD.toString() + "Yijki, World Destroyer", EntityType.WITHER.getTypeId(), Sound.WITHER_SPAWN, 1.3f, "An entity with powers on paar with the god of destruction itself. No heroes in recorded Cosmonaut history have faced this absolute evil and lived to tell the tale."),
    ENFORCER(Enforcer.class, ChatColor.RED.toString() + ChatColor.BOLD + "Enforcer", EntityType.PIG_ZOMBIE.getTypeId(), Sound.ZOMBIE_PIG_ANGRY, 1.0f, "An ancient protector of the gods. Protects all Conquest loot."),
    EXTERMINATOR(Exterminator.class, ChatColor.RED.toString() + ChatColor.BOLD + "Exterminator", EntityType.PIG_ZOMBIE.getTypeId(), Sound.ZOMBIE_PIG_ANGRY, 1.0f, "An ancient protector of the gods. Protects all Conquest loot."),
    MARKSMAN(Marksman.class, ChatColor.RED.toString() + ChatColor.BOLD + "Marksman", EntityType.SKELETON.getTypeId(), Sound.SKELETON_IDLE, 10.0f, "An ancient marksman who wants to stop all from stealing their loot."),
    DUNGEON_YIJKI(DungeonYijki.class, ChatColor.RED.toString() + ChatColor.BOLD + "Dungeon Yijki", EntityType.WITHER.getTypeId(), Sound.WITHER_SPAWN, 1.4f, "An ancient deadly creature only found within the Cosmic Dungeons."),
    DUNGEON_DISCIPLE(YijkiDisciple.class, CC.RedB + "Disciple of Yijki", EntityType.SKELETON.getTypeId(), Sound.WITHER_SPAWN, 1.4f, "A deadly disciple of the Goddess, Mother of Yijki."),
    MOTHER_OF_YIJKI(MotherOfYijki.class, ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Mother of Yijki", EntityType.WITHER.getTypeId(), Sound.WITHER_SPAWN, 1.4f, "An ancient deadly creature only found within the Cosmic Dungeons."),
    EVIL_ABOMINATION(EvilAbomination.class, CC.LightPurpleB + "Evil Abomination", EntityType.ZOMBIE.getTypeId(), Sound.WITHER_SPAWN, 1.4f, "An ancient plague carrier who seeks to spread the plague to all.");

    Class<? extends Boss> bossClass;
    String eggName;
    short eggData;
    Sound spawnSound;
    float speed;
    String lore;

    public Boss createInstance(Location l) {
        try {
            Boss boss = this.bossClass.getDeclaredConstructor(Location.class, BossType.class).newInstance(l, this);
            return boss;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BossType getBossType(String name) {
        for (BossType type : BossType.values()) {
            if (!type.name().toLowerCase().equals(name.toLowerCase())) continue;
            return type;
        }
        return null;
    }

    private BossType(Class<? extends Boss> bossClass, String eggName, short eggData, Sound spawnSound, float speed, String lore) {
        this.bossClass = bossClass;
        this.eggName = eggName;
        this.eggData = eggData;
        this.spawnSound = spawnSound;
        this.speed = speed;
        this.lore = lore;
    }

    public Class<? extends Boss> getBossClass() {
        return this.bossClass;
    }

    public String getEggName() {
        return this.eggName;
    }

    public short getEggData() {
        return this.eggData;
    }

    public Sound getSpawnSound() {
        return this.spawnSound;
    }

    public float getSpeed() {
        return this.speed;
    }

    public String getLore() {
        return this.lore;
    }
}

