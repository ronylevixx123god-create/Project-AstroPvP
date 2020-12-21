/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.blacklist.loggers;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.BlacklistEvent;
import com.sk89q.worldguard.blacklist.loggers.BlacklistLoggerHandler;
import com.sk89q.worldguard.blacklist.loggers.FileLoggerWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileLoggerHandler
implements BlacklistLoggerHandler {
    private static Pattern pattern = Pattern.compile("%.");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
    private int cacheSize = 10;
    private String pathPattern;
    private String worldName;
    private TreeMap<String, FileLoggerWriter> writers = new TreeMap();
    private final Logger logger;

    public FileLoggerHandler(String pathPattern, String worldName, Logger logger) {
        this.pathPattern = pathPattern;
        this.worldName = worldName;
        this.logger = logger;
    }

    public FileLoggerHandler(String pathPattern, int cacheSize, String worldName, Logger logger) {
        if (cacheSize < 1) {
            throw new IllegalArgumentException("Cache size cannot be less than 1");
        }
        this.pathPattern = pathPattern;
        this.cacheSize = cacheSize;
        this.worldName = worldName;
        this.logger = logger;
    }

    private String buildPath(String playerName) {
        GregorianCalendar calendar = new GregorianCalendar();
        Matcher m = pattern.matcher(this.pathPattern);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            String rep = "?";
            if (group.matches("%%")) {
                rep = "%";
            } else if (group.matches("%u")) {
                rep = playerName.toLowerCase().replaceAll("[^A-Za-z0-9_]", "_");
                if (rep.length() > 32) {
                    rep = rep.substring(0, 32);
                }
            } else if (group.matches("%w")) {
                rep = this.worldName.toLowerCase().replaceAll("[^A-Za-z0-9_]", "_");
                if (rep.length() > 32) {
                    rep = rep.substring(0, 32);
                }
            } else if (group.matches("%Y")) {
                rep = String.valueOf(calendar.get(1));
            } else if (group.matches("%m")) {
                rep = String.format("%02d", calendar.get(2));
            } else if (group.matches("%d")) {
                rep = String.format("%02d", calendar.get(5));
            } else if (group.matches("%W")) {
                rep = String.format("%02d", calendar.get(3));
            } else if (group.matches("%H")) {
                rep = String.format("%02d", calendar.get(11));
            } else if (group.matches("%h")) {
                rep = String.format("%02d", calendar.get(10));
            } else if (group.matches("%i")) {
                rep = String.format("%02d", calendar.get(12));
            } else if (group.matches("%s")) {
                rep = String.format("%02d", calendar.get(13));
            }
            m.appendReplacement(buffer, rep);
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    private void log(LocalPlayer player, String message, String comment) {
        block9: {
            String path = this.buildPath(player.getName());
            try {
                File file;
                File parent;
                String date = dateFormat.format(new Date());
                String line = "[" + date + "] " + player.getName() + ": " + message + (comment != null ? " (" + comment + ")" : "") + "\r\n";
                FileLoggerWriter writer = this.writers.get(path);
                if (writer != null) {
                    try {
                        BufferedWriter out = writer.getWriter();
                        out.write(line);
                        out.flush();
                        writer.updateLastUse();
                        return;
                    }
                    catch (IOException out) {
                        // empty catch block
                    }
                }
                if ((parent = (file = new File(path)).getParentFile()) != null && !parent.exists()) {
                    parent.mkdirs();
                }
                FileWriter stream = new FileWriter(path, true);
                BufferedWriter out = new BufferedWriter(stream);
                out.write(line);
                out.flush();
                writer = new FileLoggerWriter(path, out);
                this.writers.put(path, writer);
                if (this.writers.size() <= this.cacheSize) break block9;
                Iterator<Map.Entry<String, FileLoggerWriter>> it = this.writers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, FileLoggerWriter> entry = it.next();
                    try {
                        entry.getValue().getWriter().close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    it.remove();
                    if (this.writers.size() > this.cacheSize) continue;
                    break;
                }
            }
            catch (IOException e) {
                this.logger.log(Level.WARNING, "Failed to log blacklist event to '" + path + "': " + e.getMessage());
            }
        }
    }

    private String getCoordinates(Vector pos) {
        return "@" + pos.getBlockX() + "," + pos.getBlockY() + "," + pos.getBlockZ();
    }

    private void logEvent(BlacklistEvent event, String text, int id, Vector pos, String comment) {
        this.log(event.getPlayer(), "Tried to " + text + " " + FileLoggerHandler.getFriendlyItemName(id) + " " + this.getCoordinates(pos), comment);
    }

    @Override
    public void logEvent(BlacklistEvent event, String comment) {
        this.logEvent(event, event.getDescription(), event.getType(), event.getPosition(), comment);
    }

    private static String getFriendlyItemName(int id) {
        ItemType type = ItemType.fromID((int)id);
        if (type != null) {
            return type.getName() + " (#" + id + ")";
        }
        return "#" + id + "";
    }

    @Override
    public void close() {
        for (Map.Entry<String, FileLoggerWriter> entry : this.writers.entrySet()) {
            try {
                entry.getValue().getWriter().close();
            }
            catch (IOException iOException) {}
        }
        this.writers.clear();
    }
}

