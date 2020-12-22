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
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class DownloadForeignFileCommand
extends AbstractCommand {
    SchematicUpdater plugin;

    public DownloadForeignFileCommand(SchematicUpdater plugin, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.plugin = plugin;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 2) {
            try {
                File tempSchematic;
                File tempFileFolder;
                String url = args[0];
                String schematicName = args[1];
                if (!schematicName.endsWith(".schematic")) {
                    schematicName = schematicName + ".schematic";
                }
                if (!(tempFileFolder = new File("plugins/Arkkit/tempFiles")).exists()) {
                    tempFileFolder.mkdir();
                }
                if (!(tempSchematic = new File("plugins/Arkkit/tempFiles/" + schematicName)).exists()) {
                    tempSchematic.createNewFile();
                }
                URL website = new URL(url);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(tempSchematic);
                try {
                    fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
                    final String schem = schematicName;
                    if (tempSchematic.exists() && tempSchematic.length() > 0L) {
                        this.plugin.uploadFile(tempSchematic, schematicName, new UploadFinishCallback(){

                            @Override
                            public void onFinish() {
                                sender.sendMessage((Object)ChatColor.GREEN + "Uploaded " + schem + " to the cloud successfully (" + tempSchematic.length() + "bytes)");
                                tempSchematic.delete();
                                tempFileFolder.delete();
                            }
                        });
                    } else {
                        sender.sendMessage((Object)ChatColor.RED + "Unable to find downloaded file.");
                    }
                }
                finally {
                    if (Collections.singletonList(fos).get(0) != null) {
                        fos.close();
                    }
                }
            }
            catch (Exception e) {
                sender.sendMessage((Object)ChatColor.RED + "There was a problem downloading that file. Check the logs for more details.");
                e.printStackTrace();
            }
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /downloadforeignfile <url> <fileName>");
        return false;
    }
}

