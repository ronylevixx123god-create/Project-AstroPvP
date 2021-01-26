package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.libs.Vector3D;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.PetUtils;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class InventoryBansheePet extends InventoryPet {
    public int petLevel = 0;

    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public InventoryPets getOwner() {
        return InventoryPets.BANSHEE;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList((Object[])new String[] {
                CC.Gray + "Call upon the deadly screeming Banshee.", CC.Gray, CC.YellowB + "Ability", CC.Gray + "Activates " + CC.Gray + "[RKO]:", CC.DarkRed + "* " + CC.DarkRed + "1.5x DMG to all players(8s) ", CC.DarkRed + "* " + CC.DarkRed + "Immune to freezes (8s) ", CC.DarkRed + "* " + CC.DarkRed + "Gears V (8s) ", CC.DarkRed + "* " + CC.DarkRed + "AoE (8x8) Silence IV (5s) ", CC.DarkRed + "* " + CC.DarkRed + "Negates enemy Phoenix (5s) ", CC.DarkRed + "* " + CC.DarkRed + "Negates enemy Holy White Scrolls (5s) ",
                CC.Gray, CC.YellowB + "Cooldown", CC.Gray + " " + CC.Gray });
    }

    public int getCooldown(int level) {
        if (level == 1)
            return 900;
        if (level == 2)
            return 840;
        if (level == 3)
            return 780;
        if (level == 4)
            return 720;
        if (level == 5)
            return 660;
        if (level == 6)
            return 600;
        if (level == 7)
            return 540;
        if (level == 8)
            return 480;
        if (level == 9)
            return 420;
        return (level == 10) ? 360 : 900;
    }

    public int getExpForLevel(int level) {
        return 700 + 100 * level;
    }

    public int getMaxLevel() {
        return 10;
    }

    public boolean onPetItemInteract(final Player player, ItemStack item, final InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(petItem.getLevel()));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

        if (petItem.getLevel() > 6) {
            petItem.setLevel(6);
            player.setItemInHand(petItem.build());
            return false;
        }
        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        if (!WorldGuardUtils.isPvPEnabled(player.getLocation()))
            throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "Not allowed in this region!");
        petItem.setLastUsed(System.currentTimeMillis());
        int xpGain = petItem.getLevel() * 5;
        petItem.addExperience(player, xpGain);
        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
        player.sendMessage(String.valueOf(CC.GreenB) + "+ " + CC.Green + NumberUtils.formatMoney(xpGain) + " Pet EXP " + CC.GreenB + "[" +
                CC.Gray + petItem.getExp() + "/" +
                petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
        int sum = petItem.getLevel() / 2 + 3;
        if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.REINDEER_ANTLERS) && Math.random() < 0.05D) {
            sum *= 2;
            player.sendMessage(String.valueOf(CC.RedB) + "* REINDEER ITEM SKIN " + CC.Gray + "(" + CC.White + "2x Pet Length" + CC.Gray + ")");
        }
        final List<Player> players = Lists.newArrayList();
        for (Entity entity : player.getNearbyEntities(sum, sum, sum)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player)entity;
            if (GeneralUtil.isAtleastTruce(p, player))
                continue;
            if (!WorldGuardUtils.isPvPEnabled(p.getLocation()))
                continue;
            boolean remove = false;
            for (int i = 0; i < p.getInventory().getSize(); i++) {
                ItemStack it = p.getInventory().getItem(i);
                if (it != null && it.getType() != Material.AIR) {
                    NBTWrapper wrapper = new NBTWrapper(it);
                    NBTTagCompoundBuilder tag = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();

                }
            }
            if (remove)
                continue;
            p.sendMessage(CC.translate("&c( " + sum + "s Silence, Phoenix - BLOCKED, Holy White Scrolls - BLOCKED )"));
            p.playSound(p.getLocation(), Sound.GHAST_MOAN, 2.0F, 2.0F);
            p.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Boolean.valueOf(true)));
            players.add(p);
        }
        ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, player.getLocation().add(0.0D, 1.0D, 0.0D), 1.0F, 3);
        player.sendMessage("");
        player.sendMessage(CC.translate("&4&l** BANSHEE [&68s&4&l] - RKO! **"));
        player.sendMessage("");
        player.removePotionEffect(PotionEffectType.SPEED);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (petItem.getLevel() / 2 + 3) * 20, 4));
        player.setMetadata("banshee_effect", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), null));
        Bukkit.getScheduler().runTaskLater((Plugin)CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                if (!player.isOnline())
                    return;
                if (player.hasMetadata("banshee_effect"))
                    player.removeMetadata("banshee_effect", (Plugin)CosmicSkyInventoryPets.get());
                for (Player player : players) {
                    if (player == null || !player.isOnline())
                        continue;
                    if (player.hasMetadata("noDefenseProcs"))
                        player.removeMetadata("noDefenseProcs", (Plugin) Enchantments.getInstance());
                }
                player.sendMessage(CC.translate("&4&lPET:  BANSHEE - RKO has worn off! "));
            }
        },(sum * 20));
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicSkyInventoryPets.get(), (Runnable)new BukkitRunnable() {
            int count = 0;

            public void run() {
                if (!player.isOnline())
                    return;
                if ((petItem.getLevel() / 2 + 3) * 2 <= this.count)
                    cancel();
                Iterator<PotionEffect> effects = player.getActivePotionEffects().iterator();
                while (effects.hasNext()) {
                    PotionEffect effect = effects.next();
                    if (effect.getType() == PotionEffectType.SLOW)
                        player.removePotionEffect(effect.getType());
                }
                this.count++;
            }
        },  10L);
        return true;
    }

    public ArrayList<Player> getPlayersInRadius(Player player, int radius) {
        ArrayList<Player> list = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player)entity;
            list.add(p);
        }
        return list;
    }

    public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
        return false;
    }

    public void addPotionEffectReturn(final Player player, final PotionEffect returned, final PotionEffect add) {
        player.removePotionEffect(returned.getType());
        player.addPotionEffect(add);
        Bukkit.getScheduler().runTaskLater((Plugin)CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                if (player.hasPotionEffect(add.getType()))
                    player.removePotionEffect(add.getType());
                player.addPotionEffect(returned);
            }
        },  add.getDuration());
    }

    public LivingEntity getTargetEntity(Player player, int maxRangeInBlocks) {
        Location observerPos = player.getEyeLocation();
        Vector3D observerDir = new Vector3D(observerPos.getDirection());
        Vector3D observerStart = new Vector3D(observerPos);
        Vector3D observerEnd = observerStart.add(observerDir.multiply(maxRangeInBlocks));
        LivingEntity target = null;
        for (Entity possibleTarget : player.getNearbyEntities(maxRangeInBlocks, maxRangeInBlocks,
                maxRangeInBlocks)) {
            if (!(possibleTarget instanceof LivingEntity))
                continue;
            Vector3D targetPos = new Vector3D(possibleTarget.getLocation());
            Vector3D minimum = targetPos.add(-0.5D, 0.0D, -0.5D);
            Vector3D maximum = targetPos.add(0.5D, 1.67D, 0.5D);
            if (!hasIntersection(observerStart, observerEnd, minimum, maximum) || (
                    target != null && target.getLocation().distanceSquared(observerPos) <= possibleTarget
                            .getLocation().distanceSquared(observerPos)))
                continue;
            target = (LivingEntity)possibleTarget;
        }
        return target;
    }

    private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
        double epsilon = 9.999999747378752E-5D;
        Vector3D d = p2.subtract(p1).multiply(0.5D);
        Vector3D e = max.subtract(min).multiply(0.5D);
        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5D));
        Vector3D ad = d.abs();
        return (Math.abs(c.x) <= e.x + ad.x && Math.abs(c.y) <= e.y + ad.y && Math.abs(c.z) <= e.z + ad.z &&
                Math.abs(d.y * c.z - d.z * c.y) <= e.y * ad.z + e.z * ad.y + 9.999999747378752E-5D &&
                Math.abs(d.z * c.x - d.x * c.z) <= e.z * ad.x + e.x * ad.z + 9.999999747378752E-5D &&
                Math.abs(d.x * c.y - d.y * c.x) <= e.x * ad.y + e.y * ad.x + 9.999999747378752E-5D);
    }
}
