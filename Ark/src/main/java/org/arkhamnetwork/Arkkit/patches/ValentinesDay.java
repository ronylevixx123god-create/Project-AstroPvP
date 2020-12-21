/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.google.common.collect.Lists
 *  net.minecraft.util.com.google.common.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.common.collect.Lists;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.util.com.google.common.reflect.TypeToken;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CandyGram;
import org.arkhamnetwork.Arkkit.patches.CandyGramListener;
import org.arkhamnetwork.Arkkit.patches.CommandCandyGram;
import org.arkhamnetwork.Arkkit.patches.ValentinesDayListener;
import org.arkhamnetwork.Arkkit.patches.ValentinesDayTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ValentinesDay
extends ArkPlugin {
    public Map<UUID, List<CandyGram>> pendingGrams = new HashMap<UUID, List<CandyGram>>();
    private Type token = new TypeToken<HashMap<UUID, List<CandyGram>>>(){}.getType();
    private File candyGramFile;

    public ValentinesDay(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new ValentinesDayListener());
        this.registerTask(new ValentinesDayTask().runTaskTimer((Plugin)Arkkit.get(), 6000L, 6000L), ValentinesDay.class);
        this.registerListener(new CandyGramListener());
        this.registerCommand(new CommandCandyGram(this));
        this.candyGramFile = JSONUtils.getOrCreateFile((File)this.getBukkitPlugin().getDataFolder(), (String)"candyGrams.json");
        this.pendingGrams = (Map)JSONUtils.fromJson((File)this.candyGramFile, (Type)this.token);
        if (this.pendingGrams == null) {
            this.pendingGrams = new HashMap<UUID, List<CandyGram>>();
        }
        this.registerTask(new BukkitRunnable(){

            public void run() {
            }
        }.runTaskTimer(this.getBukkitPlugin(), 72000L, 72000L), ValentinesDay.class);
    }

    @Override
    public void disable() {
        JSONUtils.saveJSONToFile((File)this.candyGramFile, this.pendingGrams, (Object)this.token);
        this.unregisterCommands();
        this.unregisterListeners();
    }

    public void giveCandyGram(UUID receiver, CandyGram gram) {
        this.pendingGrams.computeIfAbsent(receiver, gra -> Lists.newArrayList()).add(gram);
        Player online = Bukkit.getPlayer((UUID)receiver);
        if (online != null) {
            JSONMessage message = new JSONMessage("");
            message.addRunCommand(CC.GreenB + "(!) CANDY GRAM FROM: " + gram.getSender() + " (!)", ChatColor.GREEN, "/candygram");
            message.sendToPlayer(online);
            online.sendMessage(CC.Gray + "Click above or use " + CC.Green + "/candygram" + CC.Gray + " to claim it!");
        }
    }
}

