/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent
 *  com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.listeners;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class mcMMOListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerLeaveLowest(PlayerQuitEvent e) {
        if (e.getPlayer().hasMetadata("no_mcmmo_skills")) {
            e.getPlayer().removeMetadata("no_mcmmo_skills", (Plugin)CosmicDuel.get());
        }
    }

    @EventHandler
    public void onMCMMOEXP(McMMOPlayerXpGainEvent e) {
        NewDuel d;
        if (DuelAPI.hasDuel(e.getPlayer()) && DuelAPI.isDuelValid(d = DuelAPI.getDuel(e.getPlayer()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMcMMOAbility(McMMOPlayerAbilityActivateEvent event) {
        if ((DuelAPI.isSpectator(event.getPlayer()) || DuelAPI.hasDuel(event.getPlayer())) && event.getSkill() == SkillType.TAMING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDisarmed(McMMOPlayerDisarmEvent e) {
        NewDuel d;
        if (DuelAPI.hasDuel(e.getDefender()) && DuelAPI.isDuelValid(d = DuelAPI.getDuel(e.getDefender())) && d.getSettings().hasSetting(Setting.MCMMO)) {
            e.setCancelled(true);
            e.getDefender().sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You cannot disarm players in a duel.");
        }
    }
}

