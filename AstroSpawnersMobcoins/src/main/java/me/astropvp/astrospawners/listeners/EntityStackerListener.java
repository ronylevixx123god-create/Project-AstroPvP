package me.astropvp.astrospawners.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.astrospawners.utils.StackedMobDeathEvent;
import me.astropvp.astrospawners.utils.*;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import net.astropvp.core.utils.GrindingMessagesUtil;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.github.paperspigot.Title;

public class EntityStackerListener implements Listener {


    private final MobStackerUtils mobStackerUtils;
    private final PlayerDataManager manager;
    private final AstroSpawners plugin;


    public EntityStackerListener(AstroSpawners plugin ) {
        this.plugin = plugin;
        this.mobStackerUtils = plugin.getMobStackerUtils();
        this.manager = plugin.getManager();


    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMobSpawn(SpawnerSpawnEvent event) {
        mobStackerUtils.stackMobs(event);
    }

    @EventHandler
    public void onStackedEntityDeath(StackedMobDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getDamager() instanceof Player) {
            if (!mobStackerUtils.isStackedMob(entity)) return;

            if (e.getEntity().getLastDamageCause() != null) {
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroSpawners.getInstance(), () -> {
                int amount = mobStackerUtils.getStackSize(entity);
                if (amount <= 1) {
                    mobStackerUtils.removeStackedMob(entity);
                    return;
                }
                amount--;
                mobStackerUtils.setStackSize(entity, amount);


            }, 5L);

            Player p = (Player) e.getDamager();

            //Economy.add(p.getName(), 27);

            PlayerUtils.neutralizeKnockback((LivingEntity) entity);


            double exp = 300;

            //PlayerDataManager manager = AstroSpawners.getPlayerDataManager();
            PlayerData data = manager.getPlayerData((Player) e.getDamager());
            if (CosmicArmorSetsAPI.getArmorSet(p) != null && CosmicArmorSetsAPI.getArmorSet(p) == ArmorSet.GRINDER) {
                exp = exp * 1.5;


                if (Math.random() <0.05) {
                    data.setCoins(data.getCoins() + 2);
                    ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&6+2 Mobcoins &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");

                } else {
                    ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&a+1 Mobcoin &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");
                    data.setCoins(data.getCoins() + 1);

                }

            } else {
                data.setCoins(data.getCoins() + 1);
                ActionBarMessageUtils.sendMessage((Player) e.getDamager(), CC.translate("&a+1 Mobcoin &7(Total: &f&n") + data.getCoins() + CC.Gray + ")");

            }
            blazeLoot(entity, (Player) e.getDamager());


            if (((Player) e.getDamager()).getPlayer().getItemInHand().getType() == Material.DIAMOND_SWORD && ((Player) e.getDamager()).getPlayer().getItemInHand().getItemMeta().hasLore()) {
                exp = exp + 100;
                playRageEffect(e.getEntity().getLocation(), 6);
            }

            if (CustomBlockUtil.chunkHasCertainBlock(p, "TREASURECHEST")) {
                exp = exp * 1.5;
            }

            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(p), OutpostType.COURTYARD) && Math.random() < 0.1) {
                exp = exp *1.75;
            }


                p.giveExp((int) exp);


        }


    }

    private void playRageEffect(Location loc, int level) {
        sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }

    public static void sendWorldEffect(Block b, int materialID, int effectID) {
        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(effectID, new BlockPosition(b.getX(), b.getY(), b.getZ()), materialID, false);
        ParticleEffect.sendWorldEventPacket(particles, b.getLocation());
    }

    @EventHandler
    public void onMobDeath(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Creature)) return;

        if (e.getEntity().getLastDamageCause() != null) {
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
            if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
        }

        Creature entity = (Creature) e.getEntity();
        if (!mobStackerUtils.isStackedMob(entity)) {
            return;
        }
        ;
        //if (((Creature) e.getEntity()).getHealth() - e.getDamage() > 1) return;
        StackedMobDeathEvent stackedMobDeathEvent = new StackedMobDeathEvent(entity, e.getDamager());
        try {
            Bukkit.getPluginManager().callEvent(stackedMobDeathEvent);
        } catch (Exception ex) {

        }
        entity.setHealth(entity.getMaxHealth());
        e.setCancelled(true);
    }

    public void blazeLoot(Entity e, Player p) {
        if (p instanceof Player) {
            String player = p.getName();
            double num = Math.random();
            try {
                if (num >= 0 && num <= 0.1) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Lootbox"));
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " GrindingLootbox");

                    return;
                }

                /*else if (num >= 0 && num <= 0.15) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier II Lootbox"));
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding2");

                    return;
                } else if (num >= 0 && num <= 0.20) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier I Lootbox"));
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding1");
                    return;
                }*/

                if (Math.random() < 0.002) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5, 5);
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Deluxe Grinding Lootbox"));
                        p.sendTitle(new Title(CC.translate("&a&l+ Deluxe Grinding Lootbox"), CC.translate("&7You can find it in your inventory."), 20, 20, 20));
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " GrindingDeluxe");
                    return;
                }

            } catch (IllegalArgumentException ex) {
            }
        }
    }


}
