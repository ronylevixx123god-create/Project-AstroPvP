/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.CandyGram;
import org.arkhamnetwork.Arkkit.patches.CandyGramClaimGUI;
import org.arkhamnetwork.Arkkit.patches.ValentinesDay;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandCandyGram
extends AbstractCommand {
    private ValentinesDay patch;

    public CommandCandyGram(ValentinesDay patch) {
        super("candygram", "/<command>");
        this.patch = patch;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        List<CandyGram> grams = this.patch.pendingGrams.get(player.getUniqueId());
        if (grams == null || grams.isEmpty()) {
            player.sendMessage(CC.RedB + "</3 " + CC.Red + "You do not have any pending Candy Grams!");
            player.sendMessage(CC.Gray + "You can send Candy Grams by visiting the " + CC.GreenB + "Candy Man" + CC.Gray + " at spawn!");
            return true;
        }
        new CandyGramClaimGUI(player, grams).openGUI(Arkkit.get());
        return false;
    }
}

