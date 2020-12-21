/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib;

import com.avaje.ebean.common.BootupEbeanManager;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebeaninternal.server.lib.ShutdownHook;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ShutdownManager {
    private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
    static final Vector<Runnable> runnables = new Vector();
    static boolean stopping;
    static BootupEbeanManager serverFactory;
    static final ShutdownHook shutdownHook;
    static boolean whyShutdown;

    private ShutdownManager() {
    }

    public static void registerServerFactory(BootupEbeanManager factory) {
        serverFactory = factory;
    }

    public static void touch() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isStopping() {
        Vector<Runnable> vector = runnables;
        synchronized (vector) {
            return stopping;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void deregister() {
        Vector<Runnable> vector = runnables;
        synchronized (vector) {
            block5: {
                try {
                    Runtime.getRuntime().removeShutdownHook(shutdownHook);
                }
                catch (IllegalStateException ex) {
                    if (ex.getMessage().equals("Shutdown in progress")) break block5;
                    throw ex;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void register() {
        Vector<Runnable> vector = runnables;
        synchronized (vector) {
            block5: {
                try {
                    Runtime.getRuntime().addShutdownHook(shutdownHook);
                }
                catch (IllegalStateException ex) {
                    if (ex.getMessage().equals("Shutdown in progress")) break block5;
                    throw ex;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void shutdown() {
        Vector<Runnable> vector = runnables;
        synchronized (vector) {
            if (stopping) {
                return;
            }
            if (whyShutdown) {
                try {
                    throw new RuntimeException("debug.shutdown.why=true ...");
                }
                catch (Throwable e) {
                    logger.log(Level.WARNING, "Stacktrace showing why shutdown was fired", e);
                }
            }
            stopping = true;
            ShutdownManager.deregister();
            BackgroundThread.shutdown();
            String shutdownRunner = GlobalProperties.get("system.shutdown.runnable", null);
            if (shutdownRunner != null) {
                try {
                    Runnable r = (Runnable)ClassUtil.newInstance(shutdownRunner);
                    r.run();
                }
                catch (Exception e) {
                    logger.log(Level.SEVERE, null, e);
                }
            }
            Enumeration<Runnable> e = runnables.elements();
            while (e.hasMoreElements()) {
                try {
                    Runnable r = e.nextElement();
                    r.run();
                }
                catch (Exception ex) {
                    logger.log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
            try {
                if (serverFactory != null) {
                    serverFactory.shutdown();
                }
                ThreadPoolManager.shutdown();
                DataSourceGlobalManager.shutdown();
            }
            catch (Exception ex) {
                String msg = "Shutdown Exception: " + ex.getMessage();
                System.err.println(msg);
                ex.printStackTrace();
                try {
                    logger.log(Level.SEVERE, null, ex);
                }
                catch (Exception exc) {
                    String ms = "Error Logging error to the Log. It may be shutting down.";
                    System.err.println(ms);
                    exc.printStackTrace();
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void register(Runnable runnable) {
        Vector<Runnable> vector = runnables;
        synchronized (vector) {
            runnables.add(runnable);
        }
    }

    static {
        shutdownHook = new ShutdownHook();
        ShutdownManager.register();
        whyShutdown = GlobalProperties.getBoolean("debug.shutdown.why", false);
    }
}

