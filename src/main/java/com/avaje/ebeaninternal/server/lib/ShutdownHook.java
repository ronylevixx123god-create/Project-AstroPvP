/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib;

import com.avaje.ebeaninternal.server.lib.ShutdownManager;

class ShutdownHook
extends Thread {
    ShutdownHook() {
    }

    public void run() {
        ShutdownManager.shutdown();
    }
}

