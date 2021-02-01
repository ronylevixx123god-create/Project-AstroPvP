package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.utils.EntityUtils;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.command.*;
import org.arkhamnetwork.Arkkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.utils.*;
import org.bukkit.entity.*;
import java.util.*;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandClearLag implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

        if(!sender.isOp()) {
            return false;
        }

        sender.sendMessage(CC.Green + "Cleared all entities!");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(CC.translate("&b&l*** &3&lALL DROPPED ITEMS HAVE BEEN CLEARED &b&l***"));
        Bukkit.broadcastMessage("");
        EntityUtils.clearAllEntities();
        return false;
    }

}
