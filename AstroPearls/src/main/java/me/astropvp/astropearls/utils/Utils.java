package me.astropvp.astropearls.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

import me.astropvp.astropearls.AstroPearls;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Utils {
    private static long MINUTE;
    private static long HOUR;
    public static String STICK;
    public static String no_faction;
    public static String role;
    public static String faction_not_found;
    public static String no_ally;
    public static String no_permission;
    public static String no_console;
    public static String target_offline;
    private static Map<UUID, String> uuidToName;
    private static Map<String, UUID> nameToUUID;

    public static UUID uuid(String name) {
        return (UUID)nameToUUID.get(name.toLowerCase());
    }

    public static String name(UUID uuid) {
        return (String)uuidToName.get(uuid);
    }

    public static boolean ensure(UUID uuid) {
        if (String.valueOf(name(uuid)).equals("null")) {
            AstroPearls.getInstance().getLogger().warning(uuid + " didn't have a cached name.");
            return false;
        } else {
            return true;
        }
    }

    public static Location destringifyLocation(String string) {
        String[] split = string.substring(1, string.length() - 2).split(",");
        World world = Bukkit.getWorld(split[0]);
        if (world == null) {
            return null;
        } else {
            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            Location loc = new Location(world, x, y, z);
            loc.setYaw(yaw);
            loc.setPitch(pitch);
            return loc;
        }
    }

    public static List<String> getCompletions(String[] args, List<String> input) {
        return getCompletions(args, input);
    }

    public static String stringifyLocation(Location location) {
        return "[" + location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch() + "]";
    }

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemainingg(long millis, boolean milliseconds) {
        return getRemainingg(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        return milliseconds && duration < MINUTE ? ((DecimalFormat)(trail ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get()).format((double)duration * 0.001D) + 's' : DurationFormatUtils.formatDuration(duration, (duration >= HOUR ? "HH:" : "") + "mm:ss");
    }

    public static String getRemainingg(long duration, boolean milliseconds, boolean trail) {
        return milliseconds && duration < MINUTE ? ((DecimalFormat)(trail ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get()).format((double)duration * 0.001D) + 's' : DurationFormatUtils.formatDuration(duration, (duration >= HOUR ? "H:" : "") + "mm:ss");
    }

    public static String TimerFormat(double data) {
        int minutes = (int)(data / 60.0D);
        int seconds = (int)(data % 60.0D);
        String str = String.format("%02d:%02d", minutes, seconds);
        return str;
    }

    public static String formatSecondsToHours(double d) {
        int i = (int)(d / 3600.0D);
        int j = (int)(d % 3600.0D / 60.0D);
        int k = (int)(d % 60.0D);
        String str = String.format("%02d:%02d:%02d", i, j, k);
        return str;
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(value);

        try {
            return Optional.of(Enum.valueOf(enumClass, value));
        } catch (IllegalArgumentException var3) {
            return Optional.absent();
        }
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : Preconditions.checkNotNull(second);
    }

    public static boolean isOnline(CommandSender sender, Player player) {
        return player != null && (!(sender instanceof Player) || ((Player)sender).canSee(player));
    }

    public static List<Entity> getNearby(Location loc, int distance) {
        List<Entity> list = new ArrayList<>();
        for (Entity e : loc.getWorld().getEntities()) {
            if (e instanceof Player)
                continue;
            if (!e.getType().isAlive())
                continue;
            if (loc.distance(e.getLocation()) > distance)
                continue;
            list.add(e);
        }
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (online.getWorld() == loc.getWorld() && loc.distance(online.getLocation()) <= distance)
                list.add(online);
        }
        return list;
    }

    public static void setMaxPlayers(int amount) throws ReflectiveOperationException {
        String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        Object playerlist = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".CraftServer").getDeclaredMethod("getHandle", (Class[])null).invoke(Bukkit.getServer(), (Object[])null);
        Field maxplayers = playerlist.getClass().getSuperclass().getDeclaredField("maxPlayers");
        maxplayers.setAccessible(true);
        maxplayers.set(playerlist, amount);
    }

    public static Location getHighestLocation(Location origin) {
        return getHighestLocation(origin, (Location)null);
    }

    public static Location getHighestLocation(Location origin, Location def) {
        Preconditions.checkNotNull(origin, "The location cannot be null");
        Location cloned = origin.clone();
        World world = cloned.getWorld();
        int x = cloned.getBlockX();
        int y = world.getMaxHeight();
        int z = cloned.getBlockZ();

        Block block;
        do {
            if (y <= origin.getBlockY()) {
                return def;
            }

            --y;
            block = world.getBlockAt(x, y, z);
        } while(block.isEmpty());

        Location next = block.getLocation();
        next.setPitch(origin.getPitch());
        next.setYaw(origin.getYaw());
        return next;
    }

    static {
        MINUTE = TimeUnit.MINUTES.toMillis(1L);
        HOUR = TimeUnit.HOURS.toMillis(1L);
        no_faction = CC.translate("&cYou are not in a faction.");
        role = CC.translate("&cYour cannot do this with this role.");
        faction_not_found = CC.translate("&cFaction not found.");
        no_ally = CC.translate("&cAllies are currently disabled this map.");
        no_permission = CC.translate("&cNo permission.");
        no_console = CC.translate("&cNo console.");
        target_offline = CC.translate("&cThat player is currently offline.");
        STICK = "┃";
        uuidToName = new ConcurrentHashMap();
        nameToUUID = new ConcurrentHashMap();
    }
}
