package net.astropvp.antiphase;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Settings {
    private final AntiPhase plugin;
    private Set<Material> passable;
    private Set<Material> doors;
    private Set<Material> trapdoors;
    private Set<Material> gates;
    private Set<Material> fences;

    Settings(AntiPhase plugin) {
        this.plugin = plugin;
        this.load();
    }

    public int getConfigVersion() {
        return this.plugin.getConfig().getInt("config-version", 0);
    }

    public int getLatestConfigVersion() {
        return this.plugin.getConfig().getDefaults().getInt("config-version", 0);
    }

    public boolean isOutdated() {
        return this.getConfigVersion() < this.getLatestConfigVersion();
    }

    public boolean correctEnderpearlTeleportation() {
        return this.plugin.getConfig().getBoolean("correct-enderpearl-teleportation", false);
    }

    public boolean correctGroundTeleportation() {
        return this.plugin.getConfig().getBoolean("correct-ground-teleportation", false);
    }

    public boolean lenient() {
        return this.plugin.getConfig().getBoolean("lenient", false);
    }

    public boolean updateMaterialLists() {
        return this.plugin.getConfig().getBoolean("update-material-lists", true);
    }

    public String getMessage() {
        String message = this.plugin.getConfig().getString("message", "");
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public Set<Material> getPassable() {
        return this.passable;
    }

    public Set<Material> getDoors() {
        return this.doors;
    }

    public Set<Material> getTrapdoors() {
        return this.trapdoors;
    }

    public Set<Material> getGates() {
        return this.gates;
    }

    public Set<Material> getFences() {
        return this.fences;
    }

    private Set<Material> getMaterials(String name) {
        Set<Material> materials = new HashSet();
        Iterator var3 = this.plugin.getConfig().getStringList(name).iterator();

        while(var3.hasNext()) {
            String material = (String)var3.next();

            try {
                materials.add(Material.valueOf(material));
            } catch (IllegalArgumentException var6) {
                this.plugin.getLogger().info("Unknown material: " + material);
            }
        }

        return materials;
    }

    public void load() {
        this.passable = this.getMaterials("passable");
        this.trapdoors = this.getMaterials("trapdoors");
        this.doors = this.getMaterials("doors");
        this.gates = this.getMaterials("gates");
        this.fences = this.getMaterials("fences");
    }

    public void update() {
        List<Map<String, Object>> config = new ArrayList();
        Path path = Paths.get(this.plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(this.plugin.getResource("config.yml"));
        Iterator var4 = defaultConfig.getKeys(true).iterator();

        int indent;
        String current;
        while(var4.hasNext()) {
            String key = (String)var4.next();
            indent = key.split("\\.").length;
            current = StringUtils.repeat("  ", indent - 1) + key.split("\\.")[indent - 1] + ": ";
            Map<String, Object> section = new HashMap();
            section.put("key-real", key);
            section.put("key", current);
            Object value;
            if (key.equals("config-version")) {
                section.put("value", defaultConfig.get(key));
                value = defaultConfig.get(key);
            } else {
                if (this.updateMaterialLists() && key.matches("doors|trapdoors|gates|fences|passable")) {
                    return;
                }

                if (this.plugin.getConfig().get(key) instanceof MemorySection) {
                    value = null;
                } else {
                    value = this.plugin.getConfig().get(key);
                }
            }

            section.put("value", value);
            config.add(section);
        }

        Throwable var49;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(this.plugin.getResource("config.yml")));
            var49 = null;

            try {
                indent = 0;
                String previous = null;
                List<String> keyList = new ArrayList();

                for(ArrayList comments = new ArrayList(); (current = br.readLine()) != null; previous = current) {
                    if (previous != null && previous.matches("(| +)#.*")) {
                        comments.add(previous);
                    } else {
                        comments.clear();
                    }

                    if (current.matches("(?!(| +)-.*:.*)(| +).*:.*")) {
                        int currentIndent = 0;
                        char[] var12 = current.toCharArray();
                        int i = var12.length;

                        for(int var14 = 0; var14 < i; ++var14) {
                            Character c = var12[var14];
                            if (c != ' ') {
                                break;
                            }

                            ++currentIndent;
                        }

                        currentIndent /= 2;
                        String currentKey = current.trim().replaceAll(":.*", "");
                        System.out.println(currentIndent + "\t" + indent + "\t" + currentKey);
                        if (currentIndent > indent) {
                            keyList.add(currentKey);
                        } else if (currentIndent == indent) {
                            if (keyList.isEmpty()) {
                                keyList.add(currentKey);
                            } else {
                                keyList.set(keyList.size() - 1, currentKey);
                            }
                        } else {
                            for(i = 0; i < currentIndent; ++i) {
                                keyList.remove(keyList.size() == 0 ? 0 : keyList.size() - 1);
                            }

                            keyList.set(keyList.size() == 0 ? 0 : keyList.size() - 1, currentKey);
                        }

                        indent = currentIndent;
                    }

                    String key = Joiner.on(".").join(keyList);
                    Iterator var60 = config.iterator();

                    while(var60.hasNext()) {
                        Map<String, Object> section = (Map)var60.next();
                        if (section.get("key-real") != null && !comments.isEmpty() && section.get("key-real").toString().equals(key)) {
                            section.put("comments", new ArrayList(comments));
                        }
                    }
                }
            } catch (Throwable var44) {
                var49 = var44;
                throw var44;
            } finally {
                if (br != null) {
                    if (var49 != null) {
                        try {
                            br.close();
                        } catch (Throwable var40) {
                            var49.addSuppressed(var40);
                        }
                    } else {
                        br.close();
                    }
                }

            }
        } catch (IOException var46) {
            this.plugin.getLogger().severe("**CONFIG ERROR**");
            this.plugin.getLogger().severe("Failed to read from default config within plugin jar.");
            var46.printStackTrace();
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            var49 = null;

            try {
                for(indent = 0; indent < config.size(); ++indent) {
                    Map<String, Object> section = (Map)config.get(indent);
                    if (section.get("key") != null) {
                        Object comments = section.get("comments");
                        Object value;
                        if (comments != null && comments instanceof List) {
                            Iterator var55 = ((List)comments).iterator();

                            while(var55.hasNext()) {
                                value = var55.next();
                                writer.write(value.toString());
                                writer.newLine();
                            }
                        }

                        String key = section.get("key").toString();
                        writer.write(key);
                        value = section.get("value");
                        if (value instanceof String) {
                            writer.write("'" + value.toString() + "'");
                        } else if (value instanceof List) {
                            List list = (List)value;
                             indent = key.length() - key.replace(" ", "").length() - 1;
                            Iterator var63 = list.iterator();

                            while(var63.hasNext()) {
                                Object s = var63.next();
                                writer.newLine();
                                writer.write(StringUtils.repeat(" ", indent) + "  - '" + s.toString() + "'");
                            }
                        } else if (value != null) {
                            writer.write(value.toString());
                        }

                        if (config.size() > indent + 1) {
                            writer.newLine();
                            writer.newLine();
                        }
                    }
                }
            } catch (Throwable var41) {
                var49 = var41;
                throw var41;
            } finally {
                if (writer != null) {
                    if (var49 != null) {
                        try {
                            writer.close();
                        } catch (Throwable var39) {
                            var49.addSuppressed(var39);
                        }
                    } else {
                        writer.close();
                    }
                }

            }
        } catch (IOException var43) {
            this.plugin.getLogger().severe("**CONFIG ERROR**");
            this.plugin.getLogger().severe("Failed to write an updated config to the disk.");
            var43.printStackTrace();
        }

        this.plugin.reloadConfig();
        this.load();
    }
}