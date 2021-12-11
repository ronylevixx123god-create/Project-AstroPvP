package org.arkhamnetwork.enchantments.mastery;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.cache.CacheBuilder;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.rit.sucy.CustomEnchantment;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.listeners.CustomEnchantListener;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

public class MasteryFeignDeath extends MasteryCustomEnchantment {
    public static ConcurrentMap<Object, Object> deathCooldown = CacheBuilder.newBuilder().expireAfterWrite(10L, TimeUnit.SECONDS).build().asMap();

    public MasteryFeignDeath() {
        super("Feign Death", GeneralUtil.armor, 1);
        this.max = 4;
        this.armorSet = "GHOST";
        Bukkit.getPluginManager().registerEvents(new FeignDeathListener(this), Enchantments.getInstance());
    }

    public static void vanishPlayer(final Player damaged, int enchantLevel) throws NoSuchFieldException, IllegalAccessException {
        final double seconds = enchantLevel;
        damaged.setMetadata("feign_vanish", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis() + seconds * 1000.0D));
        damaged.setMetadata("feign_hits_max", new FixedMetadataValue(Enchantments.getInstance(), enchantLevel));
        deathCooldown.put(damaged.getUniqueId(), System.currentTimeMillis() + 10000L);
        EntityPlayer player = ((CraftPlayer)damaged).getHandle();
        EntityPlayer fake = new EntityPlayer(player.server, (WorldServer)player.world, player.getProfile(), new PlayerInteractManager(player.world));
        fake.playerConnection = new PlayerConnection(player.server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), fake) {
            public void sendPacket(Packet packet) {}

            public void c() {}

            public void chat(String s, boolean async) {}
        };
        fake.setPositionRotation(player.locX, player.locY, player.locZ, player.yaw, player.pitch);
        for (int i = 0; i < (fake.getEquipment()).length; i++)
            fake.setEquipment(i, null);
        int entityId = fake.getId();
        PacketPlayOutNamedEntitySpawn fakePlayer = new PacketPlayOutNamedEntitySpawn(fake);
        PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(fake, (byte)3);
        Set<EntityPlayer> whoReceived = new HashSet<>();
        EntityTrackerEntry entry = (((WorldServer)player.world).getTracker()).trackedEntities.get(player.getId());
        if (entry != null) {
            Set<EntityPlayer> trackedPlayers = (Set<EntityPlayer>)ReflectionUtils.getObject(EntityTrackerEntry.class, entry, "trackedPlayers");
            for (EntityPlayer tracked : trackedPlayers) {
                if (tracked instanceof EntityPlayer) {
                    EntityPlayer pl = tracked;
                    if (pl.playerConnection == null || pl.playerConnection.isDisconnected() ||
                            AstroItemSkinsAPI.hasSkinEquipped(pl.getBukkitEntity().getPlayer(), SkinType.CHRISTMAS_AMULET_SNOWFLAKE))
                        continue;
                    pl.playerConnection.sendPacket(fakePlayer);
                    pl.playerConnection.sendPacket(status);
                    whoReceived.add(pl);
                }
            }
        }
        player.playerConnection.sendPacket(fakePlayer);
        player.playerConnection.sendPacket(status);
        whoReceived.add(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Enchantments.getInstance(), () -> {
            PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(entityId);
            for (EntityPlayer pl : whoReceived) {
                if (pl.playerConnection == null || pl.playerConnection.isDisconnected() || AstroItemSkinsAPI.hasSkinEquipped(pl.getBukkitEntity().getPlayer(), SkinType.CHRISTMAS_AMULET_SNOWFLAKE))
                    continue;
                pl.playerConnection.sendPacket(destroy);
            }
        },30L);
        damaged.sendTitle(new Title(CC.RedB + "Feign Death", CC.Red + NumberUtils.formatSeconds(seconds), 10, 25, 10));
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            boolean isHoldingGoblinAxe = false;
            if (CosmicArmorSetsAPI.getArmorSet(pl) != null) {
                if (CosmicArmorSetsAPI.getArmorSet(pl) == ArmorSet.GOBLIN) {
                    if (pl.getItemInHand() != null && pl.getItemInHand().getType().name().contains("AXE") && (new NBTWrapper(pl.getItemInHand())).getString("cosmicType").equals("GOBLIN_WEAPON"))
                        isHoldingGoblinAxe = true;
                }

            }

            if (!AstroItemSkinsAPI.hasSkinEquipped(pl, SkinType.CHRISTMAS_AMULET_SNOWFLAKE) && !isHoldingGoblinAxe)
                pl.hidePlayer(damaged);
        }
        damaged.playSound(damaged.getLocation(), Sound.WITHER_SHOOT, 3.0F, 0.9F);
        damaged.sendMessage(CC.DarkRedB + "* Feign Death - VANISHED [" + NumberUtils.formatSeconds(seconds) + "s] *");
        (new BukkitRunnable() {
            int counter = 0;

            public void run() {
                this.counter++;
                if ((int)seconds - this.counter == 0) {
                    cancel();
                } else {
                    Title title = new Title(CC.RedB + "Feign Death", CC.Red + NumberUtils.formatSeconds(seconds - this.counter), 5, 15, 5);
                    damaged.sendTitle(title);
                }
            }
        }).runTaskTimer(Enchantments.getInstance(), 20L, 20L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Enchantments.getInstance(), () -> {
            if (damaged.isValid() && damaged.hasMetadata("feign_vanish"))
                unvanish(damaged);
        },(long)(seconds * 20.0D));
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

    class FeignDeathListener extends CustomEnchantListener<MasteryFeignDeath> {
        public FeignDeathListener(MasteryFeignDeath enchant) {
            super(enchant);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerDamagedPlayer(EntityDamageByEntityEvent event) {
            if (!(event.getDamager() instanceof Player))
                return;
            Player player = (Player)event.getDamager();
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
                if (event.getEntity() instanceof Player && event.getFinalDamage() > 0.0D) {
                    Faction faction = FactionUtils.getFaction(player, false);
                    Faction other = FactionUtils.getFaction((Player)event.getEntity(), false);
                    String tag = CC.White;
                    if (faction != null && other != null)
                        tag = faction.describeToAsync(other) + " ";
                    Bukkit.getLogger().info(player.getName() + " is damaging " + event.getEntity().getName() + " for " + event.getFinalDamage() + " while invisible! " + hitCount + " / " + max);
                    event.getEntity().sendMessage(CC.Red + "-" + NumberUtils.formatSeconds(event.getFinalDamage()) + " HP (" + tag + player.getName() + "'s Ghost" + CC.Red + ")");
                }
            }
        }
    }
}
