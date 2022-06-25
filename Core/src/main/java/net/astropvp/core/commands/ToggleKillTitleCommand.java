package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ToggleKillTitleCommand implements CommandExecutor {


    @Getter
    private static List<UUID> toggled;
    private static File toggleFile;
    private static FileConfiguration config;

    public ToggleKillTitleCommand() {
        toggled = new ArrayList<>();
        load();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] strings) {
        if (!(sender instanceof Player)) return false;
        if (toggled.contains(((Player) sender).getUniqueId())) {
            toggled.remove(((Player) sender).getUniqueId());
            sender.sendMessage(CC.translate("&e&l(!)&e Kill titles have been &a&lENABLED&e."));
        } else {
            toggled.add(((Player) sender).getUniqueId());
            sender.sendMessage(CC.translate("&e&l(!)&e Kill titles have been &c&lDISABLED&e."));
        }
        return true;
    }

    public static boolean hasEnabled(UUID uuid) {
        return !toggled.contains(uuid);
    }



    public static void load() {
        try {
            toggleFile = new File(AstroCore.getInstance().getDataFolder(), "killTitlesToggled.yml");
            if (!toggleFile.exists()) {
                toggleFile.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(toggleFile);
        toggled.addAll(config.getStringList("kill-title-toggles").stream().map(UUID::fromString).collect(Collectors.toList()));
    }

    public static void save() {
        if (!toggleFile.exists()) {
            try {
                toggleFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> list = Lists.newArrayList();
        list.addAll(toggled.stream().map(UUID::toString).collect(Collectors.toList()));
        config.set("kill-title-toggles", list);
        try {
            config.save(toggleFile);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

}
