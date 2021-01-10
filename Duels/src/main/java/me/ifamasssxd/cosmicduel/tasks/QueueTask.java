/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicduel.tasks;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.struct.QueueManager;

public class QueueTask
implements Runnable {
    QueueManager queueManager;

    public QueueTask(CosmicDuel duel) {
        this.queueManager = duel.getQueueManager();
    }

    @Override
    public void run() {
        this.queueManager.updateQueue();
        this.queueManager.getRankedQueue().updateQueue();
    }
}

