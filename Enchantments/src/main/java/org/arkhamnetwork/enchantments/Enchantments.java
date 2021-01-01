package org.arkhamnetwork.enchantments;

import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantPlugin;
import com.rit.sucy.EnchantmentAPI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.arkhamnetwork.enchantments.cmds.CommandAlchemist;
import org.arkhamnetwork.enchantments.cmds.CommandApply;
import org.arkhamnetwork.enchantments.cmds.CommandBlackscroll;
import org.arkhamnetwork.enchantments.cmds.CommandEnchBook;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.cmds.CommandEnchOrb;
import org.arkhamnetwork.enchantments.cmds.CommandEnchScroll;
import org.arkhamnetwork.enchantments.cmds.CommandEnchanter;
import org.arkhamnetwork.enchantments.cmds.CommandTinkerer;
import org.arkhamnetwork.enchantments.cmds.CommandTransmog;
import org.arkhamnetwork.enchantments.enchantments.armor.Aegis;
import org.arkhamnetwork.enchantments.enchantments.armor.Angelic;
import org.arkhamnetwork.enchantments.enchantments.armor.AntiGravity;
import org.arkhamnetwork.enchantments.enchantments.armor.Aquatic;
import org.arkhamnetwork.enchantments.enchantments.armor.Armored;
import org.arkhamnetwork.enchantments.enchantments.armor.ArrowDeflect;
import org.arkhamnetwork.enchantments.enchantments.armor.AvengingAngel;
import org.arkhamnetwork.enchantments.enchantments.armor.BloodLink;
import org.arkhamnetwork.enchantments.enchantments.armor.BloodLust;
import org.arkhamnetwork.enchantments.enchantments.armor.Cactus;
import org.arkhamnetwork.enchantments.enchantments.armor.Clarity;
import org.arkhamnetwork.enchantments.enchantments.armor.Commander;
import org.arkhamnetwork.enchantments.enchantments.armor.CreeperArmor;
import org.arkhamnetwork.enchantments.enchantments.armor.Curse;
import org.arkhamnetwork.enchantments.enchantments.armor.DeathGod;
import org.arkhamnetwork.enchantments.enchantments.armor.Deathbringer;
import org.arkhamnetwork.enchantments.enchantments.armor.Destruction;
import org.arkhamnetwork.enchantments.enchantments.armor.Diminish;
import org.arkhamnetwork.enchantments.enchantments.armor.Dodge;
import org.arkhamnetwork.enchantments.enchantments.armor.Drunk;
import org.arkhamnetwork.enchantments.enchantments.armor.EnderShift;
import org.arkhamnetwork.enchantments.enchantments.armor.EnderWalker;
import org.arkhamnetwork.enchantments.enchantments.armor.Enlighted;
import org.arkhamnetwork.enchantments.enchantments.armor.Frozen;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.enchantments.armor.Ghost;
import org.arkhamnetwork.enchantments.enchantments.armor.Glowing;
import org.arkhamnetwork.enchantments.enchantments.armor.Guardians;
import org.arkhamnetwork.enchantments.enchantments.armor.Hardened;
import org.arkhamnetwork.enchantments.enchantments.armor.Heavy;
import org.arkhamnetwork.enchantments.enchantments.armor.Immortal;
import org.arkhamnetwork.enchantments.enchantments.armor.Implants;
import org.arkhamnetwork.enchantments.enchantments.armor.Leadership;
import org.arkhamnetwork.enchantments.enchantments.armor.Lifebloom;
import org.arkhamnetwork.enchantments.enchantments.armor.Lucky;
import org.arkhamnetwork.enchantments.enchantments.armor.Marksman;
import org.arkhamnetwork.enchantments.enchantments.armor.Metaphysical;
import org.arkhamnetwork.enchantments.enchantments.armor.Molten;
import org.arkhamnetwork.enchantments.enchantments.armor.NatureWrath;
import org.arkhamnetwork.enchantments.enchantments.armor.Nutrition;
import org.arkhamnetwork.enchantments.enchantments.armor.Obsidianshield;
import org.arkhamnetwork.enchantments.enchantments.armor.Overload;
import org.arkhamnetwork.enchantments.enchantments.armor.Paradox;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.arkhamnetwork.enchantments.enchantments.armor.PlagueCarrier;
import org.arkhamnetwork.enchantments.enchantments.armor.Poisoned;
import org.arkhamnetwork.enchantments.enchantments.armor.Protection;
import org.arkhamnetwork.enchantments.enchantments.armor.Ragdoll;
import org.arkhamnetwork.enchantments.enchantments.armor.Reflect;
import org.arkhamnetwork.enchantments.enchantments.armor.RepairGuard;
import org.arkhamnetwork.enchantments.enchantments.armor.Resilience;
import org.arkhamnetwork.enchantments.enchantments.armor.RocketEscape;
import org.arkhamnetwork.enchantments.enchantments.armor.SelfDestruct;
import org.arkhamnetwork.enchantments.enchantments.armor.Shockwave;
import org.arkhamnetwork.enchantments.enchantments.armor.SmokeBomb;
import org.arkhamnetwork.enchantments.enchantments.armor.SpiritLink;
import org.arkhamnetwork.enchantments.enchantments.armor.Spirits;
import org.arkhamnetwork.enchantments.enchantments.armor.Springs;
import org.arkhamnetwork.enchantments.enchantments.armor.Sticky;
import org.arkhamnetwork.enchantments.enchantments.armor.Stormcaller;
import org.arkhamnetwork.enchantments.enchantments.armor.Tank;
import org.arkhamnetwork.enchantments.enchantments.armor.Trickster;
import org.arkhamnetwork.enchantments.enchantments.armor.UndeadRuse;
import org.arkhamnetwork.enchantments.enchantments.armor.Valor;
import org.arkhamnetwork.enchantments.enchantments.armor.Voodoo;
import org.arkhamnetwork.enchantments.enchantments.armor.Wither;
import org.arkhamnetwork.enchantments.enchantments.armor.boots.Nimble;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import org.arkhamnetwork.enchantments.enchantments.bow.ArrowLifesteal;
import org.arkhamnetwork.enchantments.enchantments.bow.Cowification;
import org.arkhamnetwork.enchantments.enchantments.bow.DimensionRift;
import org.arkhamnetwork.enchantments.enchantments.bow.EagleEye;
import org.arkhamnetwork.enchantments.enchantments.bow.Explosive;
import org.arkhamnetwork.enchantments.enchantments.bow.Farcast;
import org.arkhamnetwork.enchantments.enchantments.bow.Healing;
import org.arkhamnetwork.enchantments.enchantments.bow.Hellfire;
import org.arkhamnetwork.enchantments.enchantments.bow.Hijack;
import org.arkhamnetwork.enchantments.enchantments.bow.Infernal;
import org.arkhamnetwork.enchantments.enchantments.bow.Lightning;
import org.arkhamnetwork.enchantments.enchantments.bow.Longbow;
import org.arkhamnetwork.enchantments.enchantments.bow.Pacify;
import org.arkhamnetwork.enchantments.enchantments.bow.Piercing;
import org.arkhamnetwork.enchantments.enchantments.bow.Snare;
import org.arkhamnetwork.enchantments.enchantments.bow.Sniper;
import org.arkhamnetwork.enchantments.enchantments.bow.TargetTracking;
import org.arkhamnetwork.enchantments.enchantments.bow.Teleblock;
import org.arkhamnetwork.enchantments.enchantments.bow.Teleportation;
import org.arkhamnetwork.enchantments.enchantments.bow.Unfocus;
import org.arkhamnetwork.enchantments.enchantments.bow.Venom;
import org.arkhamnetwork.enchantments.enchantments.bow.Virus;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicArmored;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicAssassin;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicBlacksmith;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicBleed;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicBlock;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCactus;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCleave;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDeathbringer;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDetonate;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDiminish;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDodge;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicEnlighted;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicGhost;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicImplants;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicInquisitive;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicLifesteal;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicLuck;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicMetaphysical;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicOverload;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicPlagueCarrier;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicReflect;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicRocketEscape;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicSniper;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTank;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTeleportation;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTrap;
import org.arkhamnetwork.enchantments.enchantments.swords.*;
import org.arkhamnetwork.enchantments.enchantments.tools.AutoSmelt;
import org.arkhamnetwork.enchantments.enchantments.tools.Detonate;
import org.arkhamnetwork.enchantments.enchantments.tools.Experience;
import org.arkhamnetwork.enchantments.enchantments.tools.Fuse;
import org.arkhamnetwork.enchantments.enchantments.tools.Haste;
import org.arkhamnetwork.enchantments.enchantments.tools.ObsidianDestroyer;
import org.arkhamnetwork.enchantments.enchantments.tools.Oxygenate;
import org.arkhamnetwork.enchantments.enchantments.tools.Skilling;
import org.arkhamnetwork.enchantments.enchantments.tools.Telepathy;
import org.arkhamnetwork.enchantments.enchantments.weapons.Training;
import org.arkhamnetwork.enchantments.items.EMPPulseItem;
import org.arkhamnetwork.enchantments.listeners.CosmicAlchemistListener;
import org.arkhamnetwork.enchantments.listeners.EnchanterListener;
import org.arkhamnetwork.enchantments.listeners.EnchantmentLoginListener;
import org.arkhamnetwork.enchantments.listeners.EnchantmentOrbListener;
import org.arkhamnetwork.enchantments.listeners.ExploitListener;
import org.arkhamnetwork.enchantments.listeners.PlayerPotionEffectListener;
import org.arkhamnetwork.enchantments.listeners.TinkererListener;
import org.arkhamnetwork.enchantments.listeners.TinkererTradeListener;
import org.arkhamnetwork.enchantments.listeners.VanillaDamageListener;
import org.arkhamnetwork.enchantments.mastery.*;
import org.arkhamnetwork.enchantments.tasks.PlayerPotionEffectPersistTask;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Enchantments extends EnchantPlugin {
    private static Enchantments instance;

    private static Essentials ess3;

    public static volatile ConcurrentHashMap<Player, Location> playerLocations;

    public boolean dungeonsEnabled = false;

    public boolean armorsetEnabled = false;

    private MasteryPermafrost permafrostEnchant;

    private static Hex hex;

    private IceAspect iceAspectEnchant;

    private NatureWrath naturesWrathEnchant;

    public void onEnable() {
        instance = this;
        ess3 = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        playerLocations = new ConcurrentHashMap<>();
        WorldGuardUtils.hook();
        registerEnchantments();
        registerListeners();
        registerCommands();
        registerTasks();
        CustomEnchantment[] array = {
                new Lifesteal(), new Glowing(), new Blind(), new Deathbringer(), new Implants(), new Springs(), new Gears(), new Obsidianshield(), new Block(), new DeepWounds(),
                new Enlighted(), new Frozen(), new Hardened(), this.iceAspectEnchant = new IceAspect(), new Lightning(), new Molten(), new Poison(), new Poisoned(), new ThunderingBlow(), new Vampire(),
                new Trap(), new Overload(), new Headless(), new Haste(), new ObsidianDestroyer(), new DoubleStrike(), new EnderShift(), new Healing(), new Piercing(), new Curse(),
                new Infernal(), new Shockwave(), new Aquatic(), new Explosive(), new Venom(), new Wither(), new Tank(), new Featherweight(), new Cowification(), new Epicness(),
                new AntiGravity(), new Armored(), new Nutrition(), new SelfDestruct(), new SpiritLink(), new Berserk(), new Bleed(), new Blessed(), new Confusion(), new Decapitation(),
                new Ravenous(), new Reforged(), new Cleave(), new Commander(), new Valor(), new Inquisitive(), new Cactus(), new RocketEscape(), new Guardians(), new Teleportation(),
                new Lifebloom(), new PlagueCarrier(), new Obliterate(), new EnderWalker(), new Paralyze(), new Drunk(), new Insomnia(), new SkillSwipe(), new Silence(), new Angelic(),
                new Voodoo(), new Stormcaller(), new Execute(), new Disarmor(), new Demonforged(), new AutoSmelt(), new Experience(), new Detonate(), new ObsidianDestroyer(), new Oxygenate(),
                new Telepathy(), new Lucky(), new Leadership(), new SmokeBomb(), new Rage(), new ArrowLifesteal(), new Clarity(), new Spirits(), new UndeadRuse(), new Trickster(),
                new BloodLust(), new Devour(), new Protection(), new Destruction(), new Hellfire(), new Ragdoll(), new Sniper(), new Snare(), this.naturesWrathEnchant = new NatureWrath(), new DivineImmolation(),
                new Rogue(),
                new Ghost(), new CreeperArmor(), new KillAura(), new Shackle(), new Immortal(), new Pummel(), new Barbarian(), new Skilling(), new Training(), new Nimble(),
                new ArrowDeflect(), new Greatsword(), new Virus(), new Unfocus(), new Teleblock(), new Longbow(), new Inversion(), new Farcast(), new Paradox(), new Solitude(),
                new Heavy(), new Hijack(), new Enrage(), new Dodge(), new Insanity(), new Phoenix(), new Disintegrate(), new BloodLink(), new Resilience(),
                new Sticky(), new Metaphysical(), new Pacify(), new TargetTracking(), new DimensionRift(), new EagleEye(), new Marksman(), new Blacksmith(), new ArrowBreak(), new Assassin(),
                new Aegis(), new AntiGank(), new RepairGuard(), new Fuse(), new Dominate(), new DeathGod(), new Diminish(), new HeroicPlagueCarrier(), new HeroicOverload(), new HeroicInquisitive(),
                new HeroicDeathbringer(), new HeroicEnlighted(), new HeroicBlacksmith(), new HeroicSniper(), new HeroicDiminish(), new HeroicAssassin(), new HeroicDetonate(), new HeroicImplants(), new HeroicLifesteal(), new HeroicArmored(),
                new HeroicDodge(), new HeroKiller(), new BossSlayer(), new HeroicLuck(), new HeroicCleave(), new HeroicBleed(), this.hex = new Hex(), new Corrupt(), new MasteryMarkOfTheBeast(), new MasteryHorrify(),
                new MasteryPoltergeist(), new MasteryFeignDeath(), new HeroicRocketEscape(), new HeroicTeleportation(), new HeroicTrap(), new HeroicBlock(), new HeroicGhost(), new MasterySoulSiphon(), new MasteryMortalCoil(), new MasteryRotAndDecay(),
                new MasteryDemonicGateway(), new Reflect(), new AvengingAngel(), new HeroicReflect(), new MasteryLifesteal(), new MasteryDeathPact(), new MasteryTombstone(), new MasteryDiscombobulate(), new MasteryAutoSell(), new MasteryExplosivesExpert(),
                this.permafrostEnchant = new MasteryPermafrost(), new HeroicMetaphysical(), new MasteryBlackout(), new MasteryWebWalker(), new MasteryNeutralize(), new HeroicCactus(), new HeroicTank(), new Sabotage(), new MasteryLavaStrider(), new MasteryRevoke()};
        EnchantmentAPI.registerCustomEnchantments(array);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers())
                Enchantments.playerLocations.put(p, p.getLocation());
        }, 0L, 40L);
        (new File("plugins/Enchantments/config.yml")).delete();
        saveDefaultConfig();
        try {
            getConfig().load("plugins/Enchantments/config.yml");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        CommandEnchDust.dustEnabled = (new File("plugins/Enchantments/dust_enabled")).exists();
        CommandEnchDust.soulEnabled = (new File("plugins/Enchantments/soul_enabled")).exists();
        this.dungeonsEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicDungeons");
        this.armorsetEnabled = (new File("plugins/Enchantments.jar")).exists();
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            EMPPulseItem empPulseItem = new EMPPulseItem();
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(empPulseItem);
            Bukkit.getPluginManager().registerEvents(empPulseItem, this);
        }
    }

    public void registerEnchantments() {}

    public static Essentials getEssentials() {
        return ess3;
    }

    public void onDisable() {
        if (DimensionRift.blockRevertTasks.size() > 0)
            for (HashMap<Location, Material> blocks : DimensionRift.blockRevertTasks.values()) {
                for (Map.Entry<Location, Material> data : blocks.entrySet())
                    data.getKey().getBlock().setType(data.getValue());
            }
        if (this.permafrostEnchant != null)
            this.permafrostEnchant.onDisable();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new EnchantmentBookListener(), this);
        getServer().getPluginManager().registerEvents(new EnchanterListener(), this);
        getServer().getPluginManager().registerEvents(new TinkererListener(), this);
        getServer().getPluginManager().registerEvents(new TinkererTradeListener(), this);
        getServer().getPluginManager().registerEvents(new SoulModeListener(), this);
        getServer().getPluginManager().registerEvents(new CosmicAlchemistListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentOrbListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentLoginListener(), this);
        getServer().getPluginManager().registerEvents(new VanillaDamageListener(), this);
        getServer().getPluginManager().registerEvents(new ExploitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerPotionEffectListener(), this);
    }

    private void registerTasks() {
        getServer().getScheduler().runTaskTimer(this, new SoulModeTask(), 5L, 5L);
        getServer().getScheduler().runTaskTimer(this, new PlayerPotionEffectPersistTask(), 20L, 20L);
    }

    private void registerCommands() {
        getCommand("enchbook").setExecutor(new CommandEnchBook());
        getCommand("enchdust").setExecutor(new CommandEnchDust());
        getCommand("enchorb").setExecutor(new CommandEnchOrb());
        getCommand("enchscroll").setExecutor(new CommandEnchScroll());
        getCommand("transmog").setExecutor(new CommandTransmog());
        getCommand("tinkerer").setExecutor(new CommandTinkerer());
        getCommand("enchanter").setExecutor(new CommandEnchanter());
        getCommand("alchemist").setExecutor(new CommandAlchemist());
        getCommand("apply").setExecutor(new CommandApply());
        getCommand("blackscroll").setExecutor(new CommandBlackscroll());
    }

    public static Hex getHex() { return hex; }

    public static Enchantments getInstance() {
        return instance;
    }

    public IceAspect getIceAspectEnchant() {
        return this.iceAspectEnchant;
    }

    public NatureWrath getNaturesWrathEnchant() {
        return this.naturesWrathEnchant;
    }
}