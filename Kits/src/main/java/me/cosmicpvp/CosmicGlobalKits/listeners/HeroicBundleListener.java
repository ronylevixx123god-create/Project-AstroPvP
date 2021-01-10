/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.cosmicpvp.CosmicGlobalKits.listeners;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import me.cosmicpvp.CosmicGlobalKits.utils.ItemUtils;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicBundleListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerUseHeroicBundle(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            boolean vkitBundle = false;
            boolean ghostBundle = false;
            boolean necroBundle = false;
            boolean deathKnightBundle = false;
            if (this.isHeroicFallenHeroBundle(e.getItem()) || (vkitBundle = this.isVkitFallenHeroBundle(e.getItem())) || (ghostBundle = this.isGhostMasteryBundle(e.getItem())) || (necroBundle = this.isNecromancerMasteryBundle(e.getItem())) || (deathKnightBundle = this.isDeathKnightMasteryBundle(e.getItem()))) {
                int toGive;
                Player p = e.getPlayer();
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                int n = toGive = vkitBundle || ghostBundle || necroBundle || deathKnightBundle ? 3 : 10;
                if (this.getEmptySlots(p) < toGive) {
                    p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have enough empty inventory slots.");
                    p.sendMessage((Object)ChatColor.GRAY + ChatColor.ITALIC.toString() + "REQ: " + toGive + "+ slots");
                    return;
                }
                if (e.getItem().getAmount() > 1) {
                    ItemStack i = e.getItem();
                    i.setAmount(i.getAmount() - 1);
                    p.setItemInHand(i);
                } else {
                    p.setItemInHand(null);
                }
                if (vkitBundle) {
                    ArrayList highChances = Lists.newArrayList((Object[])new EvolutionKits[]{EvolutionKits.TROLL, EvolutionKits.PHOENIX, EvolutionKits.ALCHEMIST, EvolutionKits.OGRE});
                    ArrayList lowChances = Lists.newArrayList((Object[])new EvolutionKits[]{EvolutionKits.JUDGEMENT, EvolutionKits.SLAYER, EvolutionKits.MIMIC});
                    for (int i = 0; i < 4; ++i) {
                        EvolutionKits random = Math.random() <= 0.65 ? (EvolutionKits)highChances.get(ThreadLocalRandom.current().nextInt(highChances.size())) : (EvolutionKits)lowChances.get(ThreadLocalRandom.current().nextInt(lowChances.size()));
                        ItemStack item = FallenHero.getFallenHeroItem(random.getKit());
                        PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)item, (int)-1);
                    }
                } else if (ghostBundle) {
                    this.giveFallenHeros(p, MasteryKits.GHOST.getKitsRequired());
                } else if (necroBundle) {
                    this.giveFallenHeros(p, MasteryKits.NECROMANCER.getKitsRequired());
                } else if (deathKnightBundle) {
                    this.giveFallenHeros(p, MasteryKits.DEATH_KNIGHT.getKitsRequired());
                } else {
                    ArrayList<GlobalKits> heroicKits = new ArrayList<GlobalKits>();
                    for (GlobalKits gkit : GlobalKits.values()) {
                        if (!gkit.getItemName().contains("Heroic")) continue;
                        heroicKits.add(gkit);
                    }
                    for (int i = 0; i < 10; ++i) {
                        ItemUtils.giveItemToPlayer(p, FallenHero.getFallenHeroItem((GlobalKits)((Object)heroicKits.get(new Random().nextInt(heroicKits.size())))));
                    }
                }
                p.updateInventory();
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)p.getEyeLocation(), (float)0.7f, (int)50);
                p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 2.0f);
            }
        }
    }

    private void giveFallenHeros(Player p, List<GlobalKits> kits) {
        for (GlobalKits kit : kits) {
            PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)ItemUtils.setAmount(FallenHero.getFallenHeroItem(kit), 5), (int)-1);
        }
    }

    private boolean isHeroicFallenHeroBundle(ItemStack i) {
        return i != null && i.getType() == Material.CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "Right-click to reveal " + (Object)ChatColor.UNDERLINE + "10 random");
    }

    private boolean isVkitFallenHeroBundle(ItemStack item) {
        return item.getType() == Material.CHEST && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && new NBTWrapper(item).getString("cosmicType").equals("vkitFHBundle");
    }

    private boolean isGhostMasteryBundle(ItemStack item) {
        return item.getType() == Material.CHEST && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && new NBTWrapper(item).getString("cosmicType").equals("ghostMasteryBundle");
    }

    private boolean isNecromancerMasteryBundle(ItemStack item) {
        return item.getType() == Material.CHEST && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && new NBTWrapper(item).getString("cosmicType").equals("necromancerMasteryBundle");
    }

    private boolean isDeathKnightMasteryBundle(ItemStack item) {
        return item.getType() == Material.CHEST && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && new NBTWrapper(item).getString("cosmicType").equals("deathKnightMasteryBundle");
    }

    private int getEmptySlots(Player p) {
        int i = 0;
        for (ItemStack is : p.getInventory()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++i;
        }
        return i;
    }
}

