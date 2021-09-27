// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.File;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

public class FilesManager
{
    private JavaPlugin plugin;
    private static FilesManager instance;
    private HashMap<String, ConfigFile> filesMap;
    
    public static FilesManager getInstance(final JavaPlugin plugin) {
        return (FilesManager.instance == null) ? (FilesManager.instance = new FilesManager(plugin)) : FilesManager.instance;
    }
    
    public FilesManager(final JavaPlugin plugin) {
        this.filesMap = new HashMap<String, ConfigFile>();
        this.plugin = plugin;
    }
    
    public void folderSetup(final String folderName, final String... folderPathName) {
        String path = "";
        for (final String s : folderPathName) {
            path = path + "/" + s;
        }
        final File folder = new File(this.plugin.getDataFolder() + "/" + path, folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    public void folderSetup(final String folderName, final String folderPathName) {
        final File folder = new File(this.plugin.getDataFolder() + "/" + folderPathName, folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    public void folderSetup(final String folderName) {
        final File folder = new File(this.plugin.getDataFolder(), folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    public ConfigFile fileSetup(final String fileName, final String folderName) {
        final ConfigFile file = new ConfigFile(this.plugin, fileName, folderName);
        this.filesMap.put("default/" + folderName + "/" + fileName + ".yml", file);
        return file;
    }
    
    public ConfigFile fileSetup(final String fileName) {
        final ConfigFile file = new ConfigFile(this.plugin, fileName);
        this.filesMap.put("default/" + fileName + ".yml", file);
        return file;
    }
    
    public void deleteFile(final ConfigFile fileName) {
        if (fileName.getConfigFile().exists()) {
            fileName.getConfigFile().delete();
            this.filesMap.remove(fileName.getFolderPath());
        }
    }
    
    public ConfigFile getFile(final String fileName) {
        return this.filesMap.get(fileName);
    }
    
    public HashMap<String, ConfigFile> getAllRegisteredConfigs() {
        return this.filesMap;
    }
    
    public HashMap<String, ConfigFile> getAllConfigs(final String filesPath) {
        final HashMap<String, ConfigFile> map = new HashMap<String, ConfigFile>();
        for (final Map.Entry file : this.filesMap.entrySet()) {
            final String fileName = this.filesMap.get(file.getKey()).getConfigFile().getName();
            final String path = file.getKey().toString().replace("/" + fileName, "");
            if (path.equals(filesPath)) {
                map.put(file.getKey() + "", (ConfigFile) file.getValue());
            }
        }
        return map;
    }
    
    public HashMap<String, ConfigFile> getAllFiles(final String filesPath) {
        final HashMap<String, ConfigFile> map = new HashMap<String, ConfigFile>();
        String folderPath;
        if (filesPath.contains("/")) {
            folderPath = filesPath.replace("default/", "");
        }
        else {
            folderPath = "default";
        }
        if (folderPath.equals("default")) {
            final File folder = new File(this.plugin.getDataFolder() + "");
            if (!folder.exists()) {
                return map;
            }
            for (final File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""));
                    map.put(folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put(folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        else {
            final File folder = new File(this.plugin.getDataFolder() + "/" + folderPath);
            if (!folder.exists()) {
                return map;
            }
            for (final File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""), folderPath);
                    map.put("default/" + folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put("default/" + folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        return map;
    }
    
    public LinkedHashMap<String, ConfigFile> getAllFilesByDateCreated(final String filesPath) {
        final LinkedHashMap<String, ConfigFile> map = new LinkedHashMap<String, ConfigFile>();
        String folderPath;
        if (filesPath.contains("/")) {
            folderPath = filesPath.replace("default/", "");
        }
        else {
            folderPath = "default";
        }
        if (folderPath.equals("default")) {
            final File folder = new File(this.plugin.getDataFolder() + "");
            final File[] files = folder.listFiles();
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (final File file : files) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""));
                    map.put(folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put(folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        else {
            final File folder = new File(this.plugin.getDataFolder() + "/" + folderPath);
            final File[] files = folder.listFiles();
            sortFilesByDateCreated(files);
            for (final File file : files) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""), folderPath);
                    map.put("default/" + folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put("default/" + folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        return map;
    }
    
    public LinkedHashMap<String, ConfigFile> getAllFilesByLastModified(final String filesPath) {
        final LinkedHashMap<String, ConfigFile> map = new LinkedHashMap<String, ConfigFile>();
        String folderPath;
        if (filesPath.contains("/")) {
            folderPath = filesPath.replace("default/", "");
        }
        else {
            folderPath = "default";
        }
        if (folderPath.equals("default")) {
            final File folder = new File(this.plugin.getDataFolder() + "");
            final File[] files = folder.listFiles();
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            for (final File file : files) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""));
                    map.put(folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put(folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        else {
            final File folder = new File(this.plugin.getDataFolder() + "/" + folderPath);
            final File[] files = folder.listFiles();
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            for (final File file : files) {
                if (!file.isDirectory()) {
                    final ConfigFile confFile = this.fileSetup(file.getName().replace(".yml", ""), folderPath);
                    map.put("default/" + folderPath + "/" + file.getName(), confFile);
                    this.filesMap.put("default/" + folderPath + "/" + file.getName(), confFile);
                }
            }
        }
        return map;
    }
    
    public static void sortFilesByDateCreated(final File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(final File f1, final File f2) {
                final long l1 = FilesManager.getFileCreationEpoch(f1);
                final long l2 = FilesManager.getFileCreationEpoch(f2);
                return Long.valueOf(l1).compareTo(l2);
            }
        });
    }
    
    public static long getFileCreationEpoch(final File file) {
        try {
            final BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class, new LinkOption[0]);
            return attr.creationTime().toInstant().toEpochMilli();
        }
        catch (IOException e) {
            throw new RuntimeException(file.getAbsolutePath(), e);
        }
    }
}
