package me.astropvp.listener;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Relation;

import java.util.Iterator;
import java.util.UUID;

import me.astropvp.GenBucketPlugin;
import me.astropvp.command.GenBucketCommand;
import me.astropvp.command.GenTaskCommand;
import me.astropvp.struct.ActiveBucket;
import me.astropvp.struct.GenBucketDirection;
import me.astropvp.struct.GenBucketTask;
import me.astropvp.utils.KrizzUtil;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GenBucketListener implements Listener {
    private GenBucketPlugin plugin;
    private GenBucketCommand genBucketCommand;
    private GenTaskCommand genTaskCommand;

    public GenBucketListener(GenBucketPlugin plugin, GenBucketCommand genBucketCommand, GenTaskCommand genTaskCommand) {
        this.plugin = plugin;
        this.genBucketCommand = genBucketCommand;
        this.genTaskCommand = genTaskCommand;
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getItem() != null) {
                Player player = event.getPlayer();
                FPlayer me = FactionUtils.getFPlayer(player);
                BlockFace blockFace = event.getBlockFace();
                ActiveBucket bucket = ActiveBucket.of(player.getItemInHand());
                Board.getInstance();
                Faction locFaction = Board.getFactionAt(new FLocation(event.getClickedBlock().getLocation()));
                if (bucket != null) {
                    event.setCancelled(true);
                    player.updateInventory();

                    try {
                        boolean hasChunkAccess = false;
                        if (!me.getFaction().isNormal()) {
                            hasChunkAccess = false;
                        } else if (locFaction != null && locFaction.getId().equals(me.getFaction().getId())) {
                            hasChunkAccess = true;
                        }

                        if (!hasChunkAccess && (!locFaction.isNormal() || !locFaction.getRelationTo(me).isAtLeast(Relation.MEMBER))) {
                            KrizzUtil.sendMessage((LivingEntity) player, (String) "&c&l(!)&c You cannot place a genbucket in this territory.");
                            return;
                        }
                    } catch (NullPointerException var10) {
                    }


                    do {
                        if (this.isNearbyEnemy(me, 30)) {
                            KrizzUtil.sendMessage((LivingEntity) player, (String) "&c&l(!)&c You are within 30 blocks of a enemy/neutral player.");
                            return;
                        }

                        if (!bucket.getDirection().isApplicable(event.getBlockFace())) {
                            if (bucket.getDirection() == GenBucketDirection.HORIZONTAL) {
                                KrizzUtil.sendMessage((LivingEntity) player, (String) "&c&l(!)&c You must place this on the side of a block.");
                                event.setCancelled(true);
                                player.updateInventory();
                                return;
                            }

                            blockFace = bucket.getDirection().getDefaultBlockFace();
                        }

                        double price = bucket.getPrice();
                        if (!this.plugin.getEconomy().has(player, price)) {
                            KrizzUtil.sendMessage((LivingEntity) player, (String) "&c&l(!)&c You do not have enough money to use this bucket.");
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }

                        if (!GenBucketPlugin.isGenEnabled()) {
                            player.sendMessage(CC.translate("&c&l(!)&c Genbuckets are currently disabled! Wait until an administrator enables them again."));
                            event.setCancelled(true);
                            return;
                        }

                        this.plugin.getEconomy().withdrawPlayer(player, price);
                        KrizzUtil.sendMessage(player, "&c&l- " + this.plugin.getEconomy().format(price));
                        bucket.getBucket().startTask(player, event.getClickedBlock(), blockFace, event.getBlockFace(), bucket);
                        KrizzUtil.playSound(player, Sound.ORB_PICKUP);
                        return;


                    } while (true);

                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Your GenBuckets:")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null) {
                ItemStack item = event.getCurrentItem();
                NBTWrapper nbtItem = new NBTWrapper(item);
                if (!nbtItem.hasKey("uuid")) {
                    return;
                }

                try {
                    UUID uuid = UUID.fromString(nbtItem.getString("uuid"));
                    GenBucketTask.getTask((Player) event.getWhoClicked(), uuid).ifPresent((task) -> {
                        task.endTask();
                        this.genTaskCommand.openGenTaskInventory((Player) event.getWhoClicked());
                    });
                } catch (NullPointerException var5) {
                    return;
                }
            }
        }

    }

    public boolean isNearbyEnemy(FPlayer fPlayer, int radius) {
        Iterator var3 = fPlayer.getPlayer().getNearbyEntities((double) radius, (double) radius, (double) radius).iterator();

        Player player;
        FPlayer fOther;
        do {
            Entity entity;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                entity = (Entity) var3.next();
            } while (!(entity instanceof Player));

            player = (Player) entity;
            fOther = FactionUtils.getFPlayer(player);
        } while (fPlayer.getRelationTo(fOther) != Relation.ENEMY && (fPlayer.getRelationTo(fOther) != Relation.NEUTRAL || !CombatLog.inCombat(player)));

        return true;
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onHighest(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/gentask")) {
            event.setCancelled(true);
            event.getPlayer().closeInventory();
            this.genTaskCommand.onCommand(event.getPlayer(), (Command) null, "gentask", new String[0]);
        } else if (event.getMessage().equalsIgnoreCase("/genbucket")) {
            event.setCancelled(true);
            String[] args = event.getMessage().split(" ");
            String[] newArgs = new String[args.length - 1];

            for (int i = 1; i < args.length && args.length >= 2; ++i) {
                newArgs[i - 1] = args[i];
            }

            event.getPlayer().closeInventory();
            this.genBucketCommand.onCommand(event.getPlayer(), (Command) null, "genbucket", newArgs);
        }

    }
}
