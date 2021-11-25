// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.config;

import java.util.ArrayList;
import java.util.Set;
import net.astropvp.FactionShieldsPlugin;

import java.util.List;

public class ShieldConfig
{
    private static ShieldConfig instance;
    public static String TIMEZONE;
    public static int SHIELD_DURATION;
    public static String NO_PERMISSION;
    public static String INVALID_SYNTAX;
    public static String PLAYER_NOT_FOUND;
    public static String FACTION_NOT_FOUND;
    public static int SHIELD_BACKUP_INTERVAL;
    public static int SHIELD_RESET_COOLDOWN;
    public static String SHIELD_RESET_FAIL;
    public static String SHIELD_RESET_SUCCESS;
    public static int SHIELD_RESET_RADIUS;
    public static String SHIELD_RESET_PREVENT;
    public static String SHIELD_FORCE_RESET;
    public static String SHIELD_RESET_ALL;
    public static String NO_FACTION;
    public static String NO_FACTION_PERMISSION;
    public static String SHIELD_TITLE;
    public static String SHIELD_ACTIVE;
    public static String SHIELD_INACTIVE;
    public static String SHIELD_LOOKUP;
    public static String SHIELD_LOOKUP_FAILED;
    public static String REGION_SET;
    public static String REGION_SET_FAILURE;
    public static String REGION_SHIELD_FAILURE;
    public static String REGION_ALREADY_SET;
    public static List<String> REGION_REQUIRED;
    public static String INVENTORY_TITLE;
    public static int INVENTORY_SIZE;
    public static String SELECTED_BACKGROUND;
    public static String SELECTED_TIME;
    public static String ITEM_TYPES;
    public static String BORDER_COLOR;
    public static String NAMES;
    public static List<String> LORES;
    public static int START_SLOT;
    public static int END_SLOT;
    public static boolean IGNORE_SIDES;
    public static int STOP_BACKGROUND;
    public static String PLACEHOLDER_SHIELD_ACTIVE;
    public static String PLACEHOLDER_SHIELD_INACTIVE;
    public static String PLACEHOLDER_SHIELD_NONE;
    
    private ShieldConfig() {
        FactionShieldsPlugin.getInstance().saveDefaultConfig();
    }
    
    public Thread getLoadThread() {
        return new Thread(() -> {
            ShieldConfig.TIMEZONE = this.getString("timezone");
            ShieldConfig.SHIELD_DURATION = this.getInt("shield-duration");
            ShieldConfig.NO_PERMISSION = this.getString("no-permission");
            ShieldConfig.INVALID_SYNTAX = this.getString("invalid-syntax");
            ShieldConfig.PLAYER_NOT_FOUND = this.getString("player-not-found");
            ShieldConfig.FACTION_NOT_FOUND = this.getString("faction-not-found");
            ShieldConfig.SHIELD_BACKUP_INTERVAL = this.getInt("backup-interval");
            ShieldConfig.SHIELD_RESET_COOLDOWN = this.getInt("shield-reset-cooldown");
            ShieldConfig.SHIELD_RESET_FAIL = this.getString("shield-reset-fail");
            ShieldConfig.SHIELD_RESET_SUCCESS = this.getString("shield-reset-success");
            ShieldConfig.SHIELD_RESET_RADIUS = this.getInt("shield-reset-radius");
            ShieldConfig.SHIELD_RESET_PREVENT = this.getString("shield-reset-prevent");
            ShieldConfig.SHIELD_FORCE_RESET = this.getString("shield-force-reset");
            ShieldConfig.SHIELD_RESET_ALL = this.getString("shield-reset-all");
            ShieldConfig.NO_FACTION = this.getString("no-faction");
            ShieldConfig.NO_FACTION_PERMISSION = this.getString("no-faction-permission");
            ShieldConfig.SHIELD_TITLE = this.getString("shield-title");
            ShieldConfig.SHIELD_ACTIVE = this.getString("shield-active");
            ShieldConfig.SHIELD_INACTIVE = this.getString("shield-inactive");
            ShieldConfig.SHIELD_LOOKUP = this.getString("shield-lookup");
            ShieldConfig.SHIELD_LOOKUP_FAILED = this.getString("shield-lookup-failed");
            ShieldConfig.REGION_SET = this.getString("region-set");
            ShieldConfig.REGION_SET_FAILURE = this.getString("region-set-failure");
            ShieldConfig.REGION_SHIELD_FAILURE = this.getString("region-shield-failure");
            ShieldConfig.REGION_ALREADY_SET = this.getString("region-already-set");
            ShieldConfig.REGION_REQUIRED = this.getList("region-required");
            ShieldConfig.INVENTORY_TITLE = this.getString("inventory.title");
            ShieldConfig.INVENTORY_SIZE = this.getInt("inventory.size");
            ShieldConfig.SELECTED_BACKGROUND = this.getString("inventory.selected-background");
            ShieldConfig.SELECTED_TIME = this.getString("inventory.selected-time");
            ShieldConfig.ITEM_TYPES = this.getString("inventory.item-types");
            ShieldConfig.BORDER_COLOR = this.getString("inventory.border");
            ShieldConfig.NAMES = this.getString("inventory.names");
            ShieldConfig.LORES = this.getList("inventory.lores");
            ShieldConfig.START_SLOT = this.getInt("inventory.start-slot");
            ShieldConfig.END_SLOT = this.getInt("inventory.end-slot");
            ShieldConfig.IGNORE_SIDES = this.getBoolean("inventory.ignore-sides");
            ShieldConfig.STOP_BACKGROUND = this.getInt("inventory.stop-background");
            ShieldConfig.PLACEHOLDER_SHIELD_ACTIVE = this.getString("placeholder-shield-active");
            ShieldConfig.PLACEHOLDER_SHIELD_INACTIVE = this.getString("placeholder-shield-inactive");
            ShieldConfig.PLACEHOLDER_SHIELD_NONE = this.getString("placeholder-shield-none");
        });
    }
    
    public boolean getBoolean(final String path) {
        return FactionShieldsPlugin.getInstance().getConfig().getBoolean(path);
    }
    
    public String getString(final String path) {
        return FactionShieldsPlugin.getInstance().getConfig().getString(path).replaceAll("&", "§");
    }
    
    public Set<String> getSection(final String path) {
        return (Set<String>)FactionShieldsPlugin.getInstance().getConfig().getConfigurationSection(path).getKeys(false);
    }
    
    public int getInt(final String path) {
        return FactionShieldsPlugin.getInstance().getConfig().getInt(path);
    }
    
    public List<String> getList(final String path) {
        final List<String> converted = new ArrayList<String>();
        final List<String> atPath = (List<String>)FactionShieldsPlugin.getInstance().getConfig().getList(path);
        atPath.forEach(string -> converted.add(string.replaceAll("&", "§")));
        return converted;
    }
    
    public boolean contains(final String path) {
        return FactionShieldsPlugin.getInstance().getConfig().contains(path);
    }
    
    public static ShieldConfig get() {
        return ShieldConfig.instance;
    }
    
    static {
        ShieldConfig.instance = new ShieldConfig();
    }
}
