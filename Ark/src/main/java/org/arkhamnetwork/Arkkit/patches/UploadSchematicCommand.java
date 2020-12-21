/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.File;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.SchematicUpdater;
import org.arkhamnetwork.Arkkit.patches.UploadFinishCallback;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class UploadSchematicCommand
extends AbstractCommand {
    SchematicUpdater plugin;

    public UploadSchematicCommand(SchematicUpdater plugin, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        File schematic;
        String schemName;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            schemName = args[0];
            if (!schemName.endsWith(".schematic")) {
                schemName = schemName + ".schematic";
            }
            if (!(schematic = new File("plugins/WorldEdit/schematics/" + schemName)).exists()) {
                sender.sendMessage((Object)ChatColor.RED + "No Schematic found in WorldEdit schematics folder.");
                return true;
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + "Invalid usage: /uploadschem <schematicName>");
            return true;
        }
        String finalSchemName = schemName;
        this.plugin.uploadFile(schematic, finalSchemName, new UploadFinishCallback(){

            @Override
            public void onFinish() {
                sender.sendMessage((Object)ChatColor.GREEN + "File uploaded successfully.");
            }
        });
        return false;
    }
}

