/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World$Environment
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerAnimationEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.antimacro;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.Random;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.Arkkit.patches.antimacro.AntiMacro;
import org.arkhamnetwork.Arkkit.patches.antimacro.CaptchaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class AntiMacroListener
implements Listener {
    AntiMacroListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerAnimation(PlayerAnimationEvent e) {
        if (e.getPlayer().hasMetadata("captchaKickTime")) {
            Player p = e.getPlayer();
            CaptchaManager.promptCaptcha(e.getPlayer());
            if (!p.hasMetadata("captchaReady")) {
                long kickTime = ((MetadataValue)p.getMetadata("captchaKickTime").get(0)).asLong();
                if (kickTime - System.currentTimeMillis() <= 1000L) {
                    p.setMetadata("captchaCPS_B", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(((MetadataValue)p.getMetadata("captchaCPS_B").get(0)).asInt() + 1)));
                } else if (kickTime - System.currentTimeMillis() > 1000L) {
                    p.setMetadata("captchaCPS_A", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(((MetadataValue)p.getMetadata("captchaCPS_A").get(0)).asInt() + 1)));
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer().hasMetadata("captchaReady")) {
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && ((Player)e.getDamager()).hasMetadata("captchaReady")) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.getPlayer().removeMetadata("captchaAttempts", (Plugin)Arkkit.get());
        e.getPlayer().removeMetadata("clickedChallengeItem", (Plugin)Arkkit.get());
        e.getPlayer().removeMetadata("commandDetection", (Plugin)Arkkit.get());
        if (e.getPlayer().hasMetadata("challengeItem")) {
            Bukkit.getLogger().info("[AntiMacro] " + (Object)e.getPlayer() + " has left without solving captcha challenge, will re-challenge on login.");
            AntiMacro.nextPlayerXpThreshold.put(e.getPlayer().getName(), 0);
            AntiMacro.playerXpGained.put(e.getPlayer().getName(), 0);
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (cmd.startsWith("/sell ") || cmd.equals("/tnt")) {
            int handSells;
            Player p = e.getPlayer();
            if (p.hasMetadata("captchaReady")) {
                e.setCancelled(true);
                e.setMessage("/null");
                CaptchaManager.promptCaptcha(p);
                return;
            }
            int x = p.hasMetadata("handSells") ? ((MetadataValue)p.getMetadata("handSells").get(0)).asInt() : 0;
            int n = handSells = p.hasMetadata("handSellsCheck") ? ((MetadataValue)p.getMetadata("handSellsCheck").get(0)).asInt() : AntiMacro.handSells;
            if (handSells == ++x) {
                Bukkit.getLogger().info("[Arkkit (AntiMacro)] Challenging " + p.getName() + " due to " + x + " /sell commands.");
                p.setMetadata("handSellsCheck", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(AntiMacro.handSells + new Random().nextInt(AntiMacro.handSells))));
                p.setMetadata("handSells", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)x));
                p.setMetadata("commandDetection", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                CaptchaManager.challengePlayer(p);
            } else {
                p.setMetadata("handSells", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)x));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerGainExp(EntityDeathEvent e) {
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            if (e.getEntity().hasMetadata("boss") || e.getEntity().hasMetadata("minion") || e.getEntity().getWorld().getName().startsWith("dungeon")) {
                return;
            }
            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent)e.getEntity().getLastDamageCause();
            Player p = null;
            if (edbee.getDamager() instanceof Projectile) {
                if (((Projectile)edbee.getDamager()).getShooter() instanceof Player) {
                    p = (Player)((Projectile)edbee.getDamager()).getShooter();
                }
            } else if (edbee.getDamager() instanceof Player) {
                p = (Player)edbee.getDamager();
            }
            if (p != null) {
                int xpThreshold;
                if (p.hasMetadata("captchaKickTime")) {
                    return;
                }
                if (CombatLog.inCombat(p)) {
                    return;
                }
                if (Board.getFactionAt((FLocation)new FLocation(p.getLocation())).isWarZone()) {
                    return;
                }
                if (p.getWorld().getEnvironment() == World.Environment.THE_END) {
                    return;
                }
                int xpGained = AntiMacro.addXpGained(p.getName(), e.getDroppedExp());
                if (xpGained >= (xpThreshold = AntiMacro.getNextPlayerThreshold(p))) {
                    p.setMetadata("lastCaptchaChallenge", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                    Bukkit.getLogger().info("[Arkkit (AntiMacro)] Challenging " + p.getName() + " due to gaining " + xpGained + " XP (Threshold=" + xpThreshold + ") from " + (e.getEntity() instanceof Player ? ((Player)e.getEntity()).getName() : e.getEntityType()));
                    CaptchaManager.challengePlayer(p);
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onInventoryClick(InventoryClickEvent e) {
        String invName = ChatColor.stripColor((String)e.getInventory().getName());
        if (invName.startsWith("CHALLENGE: ")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            final Player p = (Player)e.getWhoClicked();
            if (p.hasMetadata("clickedChallengeItem")) {
                return;
            }
            if (p.hasMetadata("challengeItem")) {
                p.setMetadata("clickedChallengeItem", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + 250L)));
                Material m = Material.valueOf((String)((MetadataValue)p.getMetadata("challengeItem").get(0)).asString());
                if (e.getCurrentItem() != null && e.getCurrentItem().getType() == m) {
                    long delay = System.currentTimeMillis() - ((MetadataValue)p.getMetadata("captchaPromptTime").get(0)).asLong();
                    if (delay <= 500L || delay >= 980L && delay <= 1040L) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tempban " + p.getName() + " 7d C-AC Rule #1: Client anti-captcha scripting"));
                        return;
                    }
                    p.removeMetadata("captchaKickTime", (Plugin)Arkkit.get());
                    p.removeMetadata("captchaCPS_A", (Plugin)Arkkit.get());
                    p.removeMetadata("captchaCPS_B", (Plugin)Arkkit.get());
                    p.removeMetadata("captchaReady", (Plugin)Arkkit.get());
                    p.removeMetadata("handSells", (Plugin)Arkkit.get());
                    p.removeMetadata("commandDetection", (Plugin)Arkkit.get());
                    p.removeMetadata("captchaPromptTime", (Plugin)Arkkit.get());
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "CHALLENGE COMPLETED, THANK YOU!");
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.6f);
                    Bukkit.getLogger().info("[Arkkit (AntiMacro)] " + p.getName() + " has completed their challenge correctly.");
                    p.setMetadata("captchaAttempts", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)0));
                    Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            p.closeInventory();
                            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                                @Override
                                public void run() {
                                    if (p.hasMetadata("clickedChallengeItem") && System.currentTimeMillis() > ((MetadataValue)p.getMetadata("clickedChallengeItem").get(0)).asLong()) {
                                        p.removeMetadata("clickedChallengeItem", (Plugin)Arkkit.get());
                                    }
                                }
                            }, 10L);
                        }
                    }, 1L);
                } else {
                    p.setMetadata("captchaAttempts", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(p.hasMetadata("captchaAttempts") ? ((MetadataValue)p.getMetadata("captchaAttempts").get(0)).asInt() + 1 : 1)));
                    Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            p.closeInventory();
                            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                                @Override
                                public void run() {
                                    if (p.hasMetadata("clickedChallengeItem") && System.currentTimeMillis() > ((MetadataValue)p.getMetadata("clickedChallengeItem").get(0)).asLong()) {
                                        p.removeMetadata("clickedChallengeItem", (Plugin)Arkkit.get());
                                    }
                                }
                            }, 10L);
                        }
                    }, 1L);
                }
            }
        }
    }
}

