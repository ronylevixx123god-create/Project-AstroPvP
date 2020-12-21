/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.util.yaml.YAMLFormat
 *  com.sk89q.util.yaml.YAMLNode
 *  com.sk89q.util.yaml.YAMLProcessor
 */
package com.sk89q.commandbook.session;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.session.AdministrativeSession;
import com.sk89q.commandbook.session.PersistentSession;
import com.sk89q.commandbook.session.ReflectiveSessionFactory;
import com.sk89q.commandbook.session.SessionFactory;
import com.sk89q.commandbook.session.UserSession;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLNode;
import com.sk89q.util.yaml.YAMLProcessor;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import com.zachsthings.libcomponents.bukkit.YAMLNodeConfigurationNode;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@ComponentInformation(friendlyName="Sessions", desc="Handles player sessions")
public class SessionComponent
extends BukkitComponent
implements Runnable,
Listener {
    public static final long CHECK_FREQUENCY = 1200L;
    private final Map<String, Map<Class<? extends PersistentSession>, PersistentSession>> sessions = new ConcurrentHashMap<String, Map<Class<? extends PersistentSession>, PersistentSession>>();
    private final Map<Class<? extends PersistentSession>, SessionFactory<?>> sessionFactories = new ConcurrentHashMap();
    private File sessionsDir;
    private final Map<String, YAMLProcessor> sessionDataStores = new HashMap<String, YAMLProcessor>();

    @Override
    public void enable() {
        CommandBook.server().getScheduler().scheduleSyncRepeatingTask(CommandBook.inst(), this, 1200L, 1200L);
        CommandBook.registerEvents(this);
        this.registerCommands(Commands.class);
        this.sessionsDir = new File(CommandBook.inst().getDataFolder(), "sessions");
        if (!this.sessionsDir.exists()) {
            this.sessionsDir.mkdirs();
        }
    }

    @Override
    public void disable() {
        for (Player player : CommandBook.server().getOnlinePlayers()) {
            for (PersistentSession session : this.getSessions(player)) {
                session.handleDisconnect();
                session.save(new YAMLNodeConfigurationNode(this.getSessionConfiguration(player.getName(), session.getClass())));
            }
            YAMLProcessor proc = this.getUserConfiguration(player.getName(), false);
            if (proc == null) continue;
            proc.save();
        }
    }

    @Deprecated
    public UserSession getSession(CommandSender user) {
        return this.getSession(UserSession.class, user);
    }

    @Deprecated
    public Map<String, UserSession> getSessions() {
        return this.getSessions(UserSession.class);
    }

    @Deprecated
    public AdministrativeSession getAdminSession(Player user) {
        return this.getSession(AdministrativeSession.class, user);
    }

    @Deprecated
    public Map<String, AdministrativeSession> getAdminSessions() {
        return this.getSessions(AdministrativeSession.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T extends PersistentSession> Map<String, T> getSessions(Class<T> type) {
        HashMap<String, T> ret = new HashMap<String, T>();
        Map<String, Map<Class<? extends PersistentSession>, PersistentSession>> map = this.sessions;
        synchronized (map) {
            for (Map.Entry<String, Map<Class<? extends PersistentSession>, PersistentSession>> entry : this.sessions.entrySet()) {
                PersistentSession session = entry.getValue().get(type);
                if (session == null) continue;
                ret.put(entry.getKey(), type.cast(session));
            }
        }
        return ret;
    }

    public Collection<PersistentSession> getSessions(CommandSender user) {
        Map<Class<PersistentSession>, PersistentSession> ret = this.sessions.get(user.getName());
        if (ret == null) {
            ret = Collections.emptyMap();
        }
        return Collections.unmodifiableCollection(ret.values());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T extends PersistentSession> T getSession(Class<T> type, CommandSender user) {
        Map<String, Map<Class<? extends PersistentSession>, PersistentSession>> map = this.sessions;
        synchronized (map) {
            PersistentSession session;
            Map<Class<? extends PersistentSession>, PersistentSession> userSessions = this.sessions.get(user.getName());
            if (userSessions == null) {
                userSessions = new HashMap<Class<? extends PersistentSession>, PersistentSession>();
                this.sessions.put(user.getName(), userSessions);
            }
            if ((session = (PersistentSession)type.cast(userSessions.get(type))) == null && (session = this.getSessionFactory(type).createSession(user)) != null) {
                YAMLNode node = this.getSessionConfiguration(user.getName(), type, false);
                if (node != null) {
                    session.load(new YAMLNodeConfigurationNode(node));
                }
                session.handleReconnect(user);
                userSessions.put(type, session);
            }
            return (T)session;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T extends PersistentSession> SessionFactory<T> getSessionFactory(Class<T> type) {
        Map<Class<? extends PersistentSession>, SessionFactory<?>> map = this.sessionFactories;
        synchronized (map) {
            ReflectiveSessionFactory factory = this.sessionFactories.get(type);
            if (factory == null) {
                factory = new ReflectiveSessionFactory(type);
                this.sessionFactories.put(type, factory);
            }
            return factory;
        }
    }

    public <T extends PersistentSession> void registerSessionFactory(Class<T> type, SessionFactory<T> factory) {
        this.sessionFactories.put(type, factory);
    }

    public void addSession(PersistentSession session, CommandSender user) {
        Map<Class<? extends PersistentSession>, PersistentSession> userSessions = this.sessions.get(user.getName());
        if (userSessions == null) {
            userSessions = new HashMap<Class<? extends PersistentSession>, PersistentSession>();
            this.sessions.put(user.getName(), userSessions);
        }
        userSessions.put(session.getClass(), session);
    }

    private YAMLProcessor getUserConfiguration(String player, boolean create) {
        YAMLProcessor processor = this.sessionDataStores.get(player);
        if (processor == null) {
            File userFile = new File(this.sessionsDir, player + ".yml");
            if (!userFile.exists()) {
                if (!create) {
                    return null;
                }
                try {
                    userFile.createNewFile();
                }
                catch (IOException e) {
                    CommandBook.logger().log(Level.WARNING, "Could not create sessions persistence file for user " + player, e);
                }
            }
            processor = new YAMLProcessor(userFile, false, YAMLFormat.COMPACT);
            try {
                processor.load();
            }
            catch (IOException e) {
                CommandBook.logger().log(Level.WARNING, "Error loading sessions persistence file for user " + player, e);
            }
            this.sessionDataStores.put(player, processor);
        }
        return processor;
    }

    private YAMLNode getSessionConfiguration(String player, Class<? extends PersistentSession> type) {
        return this.getSessionConfiguration(player, type, true);
    }

    private YAMLNode getSessionConfiguration(String player, Class<? extends PersistentSession> type, boolean create) {
        YAMLProcessor proc = this.getUserConfiguration(player, create);
        if (proc == null) {
            return null;
        }
        String className = type.getCanonicalName().replaceAll("\\.", "/");
        YAMLNode sessionNode = proc.getNode(className);
        if (sessionNode == null && create) {
            sessionNode = proc.addNode(className);
        }
        return sessionNode;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Map<String, Map<Class<? extends PersistentSession>, PersistentSession>> map = this.sessions;
        synchronized (map) {
            Iterator<Map.Entry<String, Map<Class<? extends PersistentSession>, PersistentSession>>> i = this.sessions.entrySet().iterator();
            block3: while (i.hasNext()) {
                Map.Entry<String, Map<Class<? extends PersistentSession>, PersistentSession>> entry = i.next();
                Iterator<PersistentSession> i2 = entry.getValue().values().iterator();
                while (i2.hasNext()) {
                    PersistentSession sess = i2.next();
                    if (sess.getOwner() != null) continue block3;
                    if (sess.isRecent()) continue;
                    i2.remove();
                    YAMLProcessor processor = this.getUserConfiguration(sess.getSenderName(), false);
                    if (processor == null) continue;
                    processor.removeProperty(sess.getClass().getCanonicalName().replaceAll("\\.", "/"));
                }
                if (entry.getValue().size() != 0) continue;
                i.remove();
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        for (PersistentSession session : this.getSessions(event.getPlayer())) {
            session.load(new YAMLNodeConfigurationNode(this.getSessionConfiguration(event.getPlayer().getName(), session.getClass())));
            session.handleReconnect(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        for (PersistentSession session : this.getSessions(event.getPlayer())) {
            session.handleDisconnect();
            session.save(new YAMLNodeConfigurationNode(this.getSessionConfiguration(event.getPlayer().getName(), session.getClass())));
        }
        YAMLProcessor proc = this.getUserConfiguration(event.getPlayer().getName(), false);
        if (proc != null) {
            proc.save();
        }
    }

    public class Commands {
        @Command(aliases={"confirm", "conf"}, desc="Confirm an action", max=0, flags="vc")
        public void confirm(CommandContext args, CommandSender sender) throws CommandException {
            UserSession session = SessionComponent.this.getSession(UserSession.class, sender);
            String cmd = session.getCommandToConfirm(false);
            if (cmd == null) {
                throw new CommandException("No command to confirm!");
            }
            if (args.hasFlag('v')) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Current command to confirm: " + cmd);
            } else if (args.hasFlag('c')) {
                session.getCommandToConfirm(true);
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Cleared command to confirm");
            } else {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Command confirmed: " + cmd);
                CommandBook.server().dispatchCommand(sender, cmd);
                session.getCommandToConfirm(true);
            }
        }
    }
}

