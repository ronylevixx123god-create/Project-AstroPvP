package org.arkhamnetwork.enchantments.mastery;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.Faction;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.listeners.CustomEnchantListener;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;


public class MasteryFeignDeath extends MasteryCustomEnchantment {



    public MasteryFeignDeath() {
        super("Feign Death", GeneralUtil.armor, 1);
        this.max = 4;
        this.armorSet = "GHOST";
        Bukkit.getPluginManager().registerEvents(new FeignDeathListener(this), Enchantments.getInstance());
    }

    public static void vanishPlayer(Player damaged, int enchantLevel) throws NoSuchFieldException, IllegalAccessException {
        double seconds = enchantLevel * 1.5D;
        damaged.setMetadata("feign_vanish", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis() + seconds * 1000.0D));
        damaged.setMetadata("feign_hits_max", new FixedMetadataValue(Enchantments.getInstance(), enchantLevel));
        EntityPlayer player = ((CraftPlayer) damaged).getHandle();
        EntityPlayer fake = new EntityPlayer(player.server, (WorldServer) player.world, player.getProfile(), new PlayerInteractManager(player.world));
        fake.playerConnection = new PlayerConnection(player.server, new NetworkManager(EnumProtocolDirection.SERVERBOUND), fake) {
            public void sendPacket(Packet packet) {
            }

            public NetworkManager a() {
                return null;
            }

            public void chat(String s, boolean async) {
            }
        };
        fake.setPositionRotation(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
        for (int i = 0; i < (fake.getEquipment()).length; i++)
            fake.setEquipment(i, null);
        int entityId = fake.getId();
        PacketPlayOutNamedEntitySpawn fakePlayer = new PacketPlayOutNamedEntitySpawn(fake);
        PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(fake, (byte) 3);
        Set<EntityPlayer> whoReceived = new HashSet<>();
        EntityTrackerEntry entry = (((WorldServer) player.world).getTracker()).trackedEntities.get(player.getId());
        /*if (entry != null) {
            CopyOnWriteArrayList<EntityPlayer> trackedPlayers =
                    (CopyOnWriteArrayList<EntityPlayer>) ReflectionUtils.getObject(EntityTrackerEntry.class, entry, "trackedPlayers");
            Iterator<EntityPlayer> var12 = trackedPlayers.iterator();
            while (var12.hasNext()) {
                Object tracked = var12.next();
                if (tracked instanceof EntityPlayer) {
                    EntityPlayer pl = (EntityPlayer) tracked;
                    if (pl.playerConnection != null && !pl.playerConnection.isDisconnected()) {
                        pl.playerConnection.sendPacket((Packet) fakePlayer);
                        pl.playerConnection.sendPacket((Packet) status);
                        whoReceived.add(pl);
                    }
                }
            }
        }*/
        player.playerConnection.sendPacket(fakePlayer);
        player.playerConnection.sendPacket(status);
        whoReceived.add(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Enchantments.getInstance(), () -> {
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entityId);
            for (EntityPlayer pl : whoReceived) {
                if (pl.playerConnection != null && !pl.playerConnection.isDisconnected())
                    pl.playerConnection.sendPacket(destroy);
            }
        }, 30L);
        //TitleManager.sendTitle(damaged, CC.RedB + "Feign Death", TitleManager.TitleColor.RED);
        //TitleManager.sendSubTitle(damaged, CC.Red + NumberUtils.formatSeconds(seconds), TitleManager.TitleColor.RED);
        for (Player pl : Bukkit.getServer().getOnlinePlayers())
            pl.hidePlayer(damaged);
        damaged.playSound(damaged.getLocation(), Sound.WITHER_SHOOT, 3.0F, 0.9F);
        damaged.sendMessage(CC.DarkRedB + "* Feign Death - VANISHED [" + NumberUtils.formatSeconds(seconds) + "s] *");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Enchantments.getInstance(), () -> {
            if (damaged.isValid() && damaged.hasMetadata("feign_vanish"))
                unvanish(damaged);
        }, (long) (seconds * 20.0D));
    }

    public static void unvanish(Player player) {
        Bukkit.getLogger().info("Unvanishing " + player.getName());
        player.removeMetadata("feign_vanish", Enchantments.getInstance());
        player.removeMetadata("feign_hits", Enchantments.getInstance());
        player.removeMetadata("feign_hits_max", Enchantments.getInstance());
        player.sendMessage(CC.DarkRedB + "* Feign Death - UNVANISHED *");
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.hasMetadata("spectator"))
                pl.showPlayer(player);
        }
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity damagedEnt, LivingEntity attacker, int enchantLevel, EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent) || !(attacker instanceof Player) || event.isCancelled())
            return;
        Player damaged = (Player) damagedEnt;
        double percentHealth = damaged.getHealth() / damaged.getMaxHealth();
        double percent = enchantLevel + (1.0D - percentHealth) * 5.0D;
        if (Math.random() <= percent * 0.01D && damaged.getHealth() > event.getDamage(EntityDamageEvent.DamageModifier.BASE) && !damaged.isDead()) {
            if (damaged.hasMetadata("feign_vanish"))
                damaged.removeMetadata("feign_vanish", Enchantments.getInstance());
            if (!(new CustomEnchantProcOnEntityEvent(damaged, attacker, event, this, enchantLevel)).callEvent())
                return;
            vanishPlayer(damaged, enchantLevel);
        }
    }

    class FeignDeathListener extends CustomEnchantListener<MasteryFeignDeath> {
        public FeignDeathListener(MasteryFeignDeath enchant) {
            super(enchant);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerDamagedPlayer(EntityDamageByEntityEvent event) {
            if (!(event.getDamager() instanceof Player))
                return;
            Player player = (Player) event.getDamager();
            if (player.hasMetadata("feign_vanish")) {
                long vanishProc = player.getMetadata("feign_vanish").get(0).asLong();
                if (vanishProc < System.currentTimeMillis()) {
                    MasteryFeignDeath.unvanish(player);
                    return;
                }
                List<MetadataValue> hitCounts;
                int hitCount = ((hitCounts = player.getMetadata("feign_hits")) != null && !hitCounts.isEmpty()) ? hitCounts.get(0).asInt() : 0;
                List<MetadataValue> maxCounts;
                int max = ((maxCounts = player.getMetadata("feign_hits_max")) != null && !maxCounts.isEmpty()) ? maxCounts.get(0).asInt() : 0;
                hitCount++;
                if (max <= 0 || hitCount >= max) {
                    MasteryFeignDeath.unvanish(player);
                    Bukkit.getLogger().info("Unvanishing " + player.getName() + " after " + hitCount + " / " + max + " hits!");
                    return;
                }
                player.setMetadata("feign_hits", new FixedMetadataValue(Enchantments.getInstance(), Integer.valueOf(hitCount)));
                if (event.getEntity() instanceof Player && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D) {
                    Faction faction = FactionUtils.getFaction(player, false);
                    Faction other = FactionUtils.getFaction((Player) event.getEntity(), false);
                    String tag = CC.White;
                    if (faction != null && other != null)
                        tag = faction.describeToAsync(other) + " ";
                    Bukkit.getLogger().info(player.getName() + " is damaging " + event.getEntity().getName() + " for " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + " while invisible! " + hitCount + " / " + max);
                    event.getEntity().sendMessage(CC.Red + "-" + NumberUtils.formatSeconds(event.getDamage(EntityDamageEvent.DamageModifier.BASE)) + " HP (" + tag + player.getName() + "'s Ghost" + CC.Red + ")");
                }
            }
        }
    }
}