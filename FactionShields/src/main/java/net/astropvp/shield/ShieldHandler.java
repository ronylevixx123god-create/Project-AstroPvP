// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.shield;

import java.util.Collections;

import com.massivecraft.factions.struct.Role;
import org.bukkit.entity.Entity;
import org.bukkit.Location;
import com.massivecraft.factions.FLocation;
import java.util.concurrent.TimeUnit;
import net.astropvp.config.ShieldConfig;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.bukkit.entity.Player;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import org.bukkit.event.EventHandler;
import com.massivecraft.factions.event.FactionDisbandEvent;
import java.util.Date;
import java.lang.reflect.Type;
import java.io.Reader;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.File;
import java.io.Writer;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.FileWriter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.astropvp.FactionShieldsPlugin;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import java.util.List;
import java.text.SimpleDateFormat;
import org.bukkit.event.Listener;

public class ShieldHandler implements Listener
{
    private static final SimpleDateFormat backupFormat;
    private List<FactionShield> factionShields;
    private Map<UUID, Long> resetPending;
    
    public ShieldHandler() {
        this.factionShields = new ArrayList<FactionShield>();
        this.resetPending = new HashMap<UUID, Long>();
        this.updateShields();
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
    }
    
    private void updateShields() {
        new BukkitRunnable() {
            public void run() {
                ShieldHandler.this.factionShields.forEach(FactionShield::update);
            }
        }.runTaskTimerAsynchronously((Plugin)FactionShieldsPlugin.getInstance(), 0L, 20L);
    }
    
    public void saveAll(final String path) {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        this.factionShields.forEach(shield -> shield.setInventoryBase64(shield.getInventory()));
        try (final Writer writer = new FileWriter(plugin.getDataFolder() + "/" + path)) {
            final Gson gson = new Gson();
            gson.toJson((Object)this.factionShields, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadAll() {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        final File file = new File(plugin.getDataFolder() + "/shields.json");
        if (!file.exists()) {
            return;
        }
        try (final Reader reader = new FileReader(plugin.getDataFolder() + "/shields.json")) {
            final Gson gson = new Gson();
            final Type listType = new TypeToken<List<FactionShield>>() {}.getType();
            this.factionShields = (List<FactionShield>)gson.fromJson(reader, listType);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.factionShields.forEach(shield -> {
            plugin.registerListener((Listener)shield);
            shield.update();
        });
    }
    
    public void backup() {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        final File file = new File(plugin.getDataFolder() + "/backups");
        if (!file.exists()) {
            file.mkdirs();
        }
        final String time = ShieldHandler.backupFormat.format(new Date());
        final String fileName = String.format("%s.json", time);
        this.saveAll("backups/" + fileName);
    }
    
    @EventHandler
    public void onFactionDisband(final FactionDisbandEvent event) {
        this.destroyShield(event.getFaction());
    }
    
    @EventHandler
    public void onFactionLeave(final FPlayerLeaveEvent event) {
        this.destroyShield(event.getFaction());
    }
    
    private void destroyShield(final Faction faction) {
        for (int i = this.factionShields.size() - 1; i >= 0; --i) {
            final FactionShield factionShield;
            if ((factionShield = this.factionShields.get(i)).getFaction().getId().equalsIgnoreCase(faction.getId())) {
                factionShield.destroy();
            }
        }
    }
    
    public boolean hasShield(final Player player) {
        return this.factionShields.stream().anyMatch(shield -> shield.getFaction().equals(FPlayers.i.get(player).getFaction()));
    }
    
    public boolean hasShield(final Faction faction) {
        return this.factionShields.stream().anyMatch(shield -> shield.getFaction().equals(faction));
    }
    
    public FactionShield getShield(final Player player) {
        final FPlayer fplayer = FPlayers.i.get(player);
        return this.factionShields.stream().filter(shield -> shield.getFaction().getFPlayers().contains(fplayer)).findFirst().orElse(null);
    }
    
    public FactionShield getShield(final Faction faction) {
        return this.factionShields.stream().filter(shield -> shield.getFaction().equals(faction)).findFirst().orElse(null);
    }
    
    public boolean hasFaction(final FPlayer player) {
        return player.hasFaction() && !player.getFaction().isWilderness();
    }
    
    public void reset(final Player commandExecutor) {
        if (commandExecutor == null) {
            return;
        }
        final FPlayer fplayer = FPlayers.i.get(commandExecutor);
        if (!this.hasFaction(fplayer)) {
            commandExecutor.sendMessage(ShieldConfig.NO_FACTION);
            return;
        }
        final Role role = fplayer.getRole();
        if (role != Role.ADMIN && role != Role.COLEADER) {
            commandExecutor.sendMessage(ShieldConfig.NO_FACTION_PERMISSION);
            return;
        }
        final ShieldHandler shieldHandler = FactionShieldsPlugin.getInstance().getShieldHandler();
        if (!shieldHandler.hasShield(commandExecutor)) {
            commandExecutor.sendMessage(ShieldConfig.REGION_SHIELD_FAILURE);
            return;
        }
        final FactionShield shield = shieldHandler.getShield(commandExecutor);
        final long total = 3600000 * ShieldConfig.SHIELD_RESET_COOLDOWN;
        final long elapsed = System.currentTimeMillis() - shield.getSelectionTime();
        final long difference = total - elapsed;
        if (difference > 0L) {
            final String timeRemaining = String.format("%dh %dm %ds", TimeUnit.MILLISECONDS.toHours(difference) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(difference)), TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference)), TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));
            commandExecutor.sendMessage(ShieldConfig.SHIELD_RESET_FAIL.replaceAll("%time%", timeRemaining));
            return;
        }
        if (this.nearbyEnemyPlayers(shield)) {
            commandExecutor.sendMessage(ShieldConfig.SHIELD_RESET_PREVENT);
            return;
        }
        commandExecutor.sendMessage(ShieldConfig.SHIELD_RESET_SUCCESS);
        shield.destroy();
    }
    
    public void forceReset(final Player commandSender, final Faction target) {
        if (target == null) {
            return;
        }
        final FactionShield shield;
        if ((shield = this.getShield(target)) == null) {
            if (commandSender != null) {
                commandSender.sendMessage(ShieldConfig.SHIELD_LOOKUP_FAILED);
            }
            return;
        }
        if (commandSender != null) {
            commandSender.sendMessage(ShieldConfig.SHIELD_FORCE_RESET.replaceAll("%faction%", shield.getFaction().getTag()));
        }
        shield.destroy();
    }
    
    public void resetAll(final Player commandSender) {
        if (commandSender != null) {
            final UUID uuid = commandSender.getUniqueId();
            if (this.resetPending.containsKey(uuid)) {
                final long elapsed = (System.currentTimeMillis() - this.resetPending.get(uuid)) / 1000L;
                if (elapsed <= 10L) {
                    for (int i = this.factionShields.size() - 1; i >= 0; --i) {
                        final FactionShield shield = this.factionShields.get(i);
                        shield.destroy();
                    }
                    this.resetPending.remove(uuid);
                    commandSender.sendMessage(ShieldConfig.SHIELD_RESET_ALL);
                    return;
                }
            }
            this.resetPending.put(uuid, System.currentTimeMillis());
            commandSender.sendMessage("§cAre you sure you want to reset ALL shields?");
            commandSender.sendMessage("§cRetype the command within 10 seconds to confirm.");
        }
    }
    
    private boolean nearbyEnemyPlayers(final FactionShield shield) {
        final FLocation chunk = shield.getRegion().get(0);
        final Location region = new Location(chunk.getWorld(), (double)chunk.getX(), 100.0, (double)chunk.getZ());
        final int radius = ShieldConfig.SHIELD_RESET_RADIUS;
        final List<Player> online = (List<Player>)shield.getFaction().getOnlinePlayers();
        for (final Entity entity : chunk.getWorld().getNearbyEntities(region, (double)radius, (double)radius, (double)radius)) {
            if (entity instanceof Player && !online.contains(entity)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasAdminPermission(final Player player) {
        return player.isOp() || player.hasPermission("factionshields.admin");
    }
    
    public String getEndTime(String dayOrNight, final int start) {
        int localTime = start;
        for (int i = 0; i < ShieldConfig.SHIELD_DURATION; ++i) {
            if (++localTime == 12) {
                dayOrNight = (dayOrNight.equals("AM") ? "PM" : "AM");
            }
            else if (localTime > 12) {
                localTime = 1;
            }
        }
        return String.format("%d %s %s", localTime, dayOrNight, ShieldConfig.TIMEZONE);
    }
    
    public void addShield(final FactionShield shield) {
        this.factionShields.add(shield);
    }
    
    public void removeShield(final FactionShield shield) {
        this.factionShields.remove(shield);
    }
    
    public List<FactionShield> getFactionShields() {
        return Collections.unmodifiableList((List<? extends FactionShield>)this.factionShields);
    }
    
    static {
        backupFormat = new SimpleDateFormat("hh mm ss");
    }
}
