package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.helmets.UndertowHelmet;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.libs.Vector3D;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.CrankedAmuletUtil;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import me.kingsavaqe.cagepvp.AstroCagePvP;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InventoryWitcherPet extends InventoryPet {
    public InventoryPets getOwner() {
        return InventoryPets.ANTI_TELEBLOCK;
    }

    public List<String> getPetDescription(int level) {
        return Lists.newArrayList(CC.Gray + "Call upon the deadly screaming Banshee.", "",
                CC.WhiteB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[Witcher]:",
                CC.DarkRedB + "* " + CC.Gray + "Immune to almost all knockback (" + (5 * 2) + "s)",
                CC.DarkRedB + "* " + CC.Gray + "Teleblocks enemies in a 8x8 radius for (" + (5 * 2) + "s)",
                CC.DarkRedB + "* " + CC.Gray + "Negates enemy ragdoll in a 8x8 radius for (" + (5 * 2) + "s)",
                CC.DarkRedB + "* " + CC.Gray + "Silences enemy soul enchantments in a 8x8 radius for (" + (5 * 2) + "s)",
                CC.DarkRedB + "* " + CC.Gray + "Freezes enemies in a 8x8 radius for 1s", "",
                CC.WhiteB + "Cooldown",
                String.valueOf(CC.Gray) + (getCooldown(5) / 60) + " Minutes");
    }


    public int getExpForLevel(int level) {
        return 575 * level;
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getCooldown(int level) {
        return (int) TimeUnit.MINUTES.toSeconds((11 - 5));
    }

    public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        /*if (PetUtils.isOnCooldown(player, petItem.getPetType()) && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException(CC.Red + "Ability on cooldown: " + CC.RedU +
                    TimeUtils.formatSeconds(PetUtils.getSecondsOnCooldown(player, petItem.getPetType())));*/

        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis(this.getCooldown(5));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        //PetUtils.addPetCooldown(player, petItem.getPetType(), (getCooldown(5) * 1000));
        petItem.setLastUsed(System.currentTimeMillis());




        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
        int sum = petItem.getLevel() * 2;

 if(MaskUtils.hasMaskEquipped(player, "ELF")) {
            sum*=1.5;
        }

        procAbility(player, sum, 5);


        return true;
    }

    public static void procAbility(final Player player, int sum, int level) {
        final List<Player> players = Lists.newArrayList();
        for (Entity entity : player.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player) entity;
            if (GeneralUtil.isAtleastTruce(p, player))
                continue;
            if (!WorldGuardUtils.isPvPEnabled(p.getLocation()))
                continue;
            if (CosmicSkyInventoryPets.get().getCooldownUtils().isOnCooldown(p, "witcherCooldown")) {
                player.sendMessage(CC.translate("&c&lPET: &c" + p.getName() + "&c is still on witcher cooldown for &n" + CosmicSkyInventoryPets.get().getCooldownUtils().getRemainingTimeFormatted(p, "witcherCooldown") + "&c."));
                continue;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.BACK_TO_SCHOOL_2021_BOOTS)) {
                p.sendMessage(CC.translate("&8&l* BIG TIMBS &7Blocked &4Witcher Pet &7[" + player.getName() + "&7] &8&l*"));
                player.sendMessage(CC.translate("&8&l* &f" + p.getName() + " &7Blocked &4Witcher Pet &7[&8&lBig Timbs&7] &8&l*"));
                continue;
            }

            if (UndertowHelmet.undertowField.contains(p)) {
                p.sendMessage(CC.translate("&a&l* UNDERTOW HELMET &7Blocked &4Witcher Pet &7[&f" + player.getName() + "&7] &a&l*"));
                player.sendMessage(CC.translate("&a&l* &f" + p.getName() + " &7Blocked &4Witcher Pet &7[&a&lUndertow Helmet&7] &a&l*"));
                continue;
            }

            if (AstroCagePvP.getInstance().getWildPvPManager().hasWildPvP(player) && !AstroCagePvP.getInstance().getWildPvPManager().hasWildPvP(p) || AstroCagePvP.getInstance().getWildPvPManager().hasWildPvP(p) && !AstroCagePvP.getInstance().getWildPvPManager().hasWildPvP(player)) {
                continue;
            }

            /*if (WitcherCooldown.isOnWitcherCooldown(p)) {
                player.sendMessage(CC.translate("&c&lPET: &c" + p.getName() + "&c is still on Witcher Pet Cooldown! This lasts 60 seconds."));
                continue;
            }*/

            CrankedAmuletUtil.tryHatchAbility(p, player);

            p.sendMessage(CC.translate("&cWitcher Pet - ( " + sum + "s Ragdoll, Soul Enchantments - BLOCKED)"));
            p.playSound(p.getLocation(), Sound.GHAST_MOAN, 2.0F, 2.0F);
            p.setWalkSpeed(0.0F);
            p.setMetadata("witcher_ability", new FixedMetadataValue(CosmicSkyInventoryPets.get(), Boolean.valueOf(true)));

            int witcherCooldown = 30;
            CosmicSkyInventoryPets.get().getCooldownUtils().add(p, "witcherCooldown", witcherCooldown * 1000);
            if(!AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.CHRISTMAS_BOOTS_SLIPPERS)) {
                p.setMetadata("teleblock", new FixedMetadataValue(Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis() + (level * 2000))));
            } else {
                p.sendMessage(CC.translate("&c&lSNOWFLAKE SLIPPERS: &7Blocked &cTeleblock!"));
            }
            //WitcherCooldown.enterWitcherCooldown(p);
            players.add(p);
        }


        //ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, player.getLocation().add(0.0D, 1.0D, 0.0D), 1.0F, 3);
        player.sendMessage("");
        player.sendMessage(CC.translate("&4&l** WITCHER PET [&6" + sum + "s&4&l] AOE - Ragdoll, Soul Enchantments - BLOCKED **"));
        player.sendMessage("");
        player.setMetadata("witcher_effect", new FixedMetadataValue(CosmicSkyInventoryPets.get(), null));
        player.setMetadata("noknockback", new FixedMetadataValue(CosmicSkyInventoryPets.get(), null));

        DamageIndicatorUtils.sendDamageIndicator(player, CC.translate("&4&l* WITHER PET *"), "WITHER");



        Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                for (Player player : players) {
                    if (player == null || !player.isOnline())
                        continue;
                    player.setWalkSpeed(0.2F);
                }
            }
        }, 100L);

        Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                if (!player.isOnline())
                    return;
                if (player.hasMetadata("witcher_effect"))
                    player.removeMetadata("witcher_effect", CosmicSkyInventoryPets.get());
                if (player.hasMetadata("noknockback"))
                    player.removeMetadata("noknockback", CosmicSkyInventoryPets.get());
                for (Player player : players) {
                    if (player == null || !player.isOnline())
                        continue;
                    if (player.hasMetadata("witcher_ability"))
                        player.removeMetadata("witcher_ability", Enchantments.getInstance());
                    player.setWalkSpeed(0.2F);
                }
                player.sendMessage(CC.translate("&4&lPET:  Witcher Pet - WITCHER has worn off! "));
            }
        }, (sum * 20));
    }

    public ArrayList<Player> getPlayersInRadius(Player player, int radius) {
        ArrayList<Player> list = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player) entity;
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
        Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                if (player.hasPotionEffect(add.getType()))
                    player.removePotionEffect(add.getType());
                player.addPotionEffect(returned);
            }
        }, add.getDuration());
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
            target = (LivingEntity) possibleTarget;
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
