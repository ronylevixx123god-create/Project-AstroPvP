/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.cron;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloWorld
implements Runnable {
    private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

    public String toString() {
        return "Hello World";
    }

    public void run() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS ");
            String now = sdf.format(new Date());
            logger.info("Hello World " + now + "  ... sleeping 20 secs");
            Thread.sleep(20000L);
            logger.info("Hello World finished.");
        }
        catch (InterruptedException ex) {
            logger.log(Level.SEVERE, "", ex);
        }
    }
}

