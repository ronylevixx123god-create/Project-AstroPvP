/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

final class WarzoneTopManager {
    WarzoneTopManager() {
    }

    public static class TopManager {
        private LinkedHashMap<Integer, TopPlayer> TopPlayers = new LinkedHashMap();
        private long lastTopUpgrade;
        private boolean loading = false;

        private static <T extends Comparable> LinkedHashMap<UUID, T> sortByComparator(Map<UUID, T> unsortMap) {
            LinkedList<Map.Entry<UUID, T>> list = new LinkedList<Map.Entry<UUID, T>>(unsortMap.entrySet());
            Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            LinkedHashMap sortedMap = new LinkedHashMap();
            for (Map.Entry entry : list) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }
            return sortedMap;
        }

        public void loadTopFund(Consumer<LinkedHashMap<Integer, TopPlayer>> callback) {
            if (this.loading || this.lastTopUpgrade != 0L && System.currentTimeMillis() - this.lastTopUpgrade < TimeUnit.SECONDS.toMillis(10L)) {
                if (callback != null) {
                    callback.accept(this.TopPlayers);
                }
                return;
            }
            this.loading = true;
            CompletableFuture.runAsync(() -> {
                long start = System.currentTimeMillis();
                LinkedHashMap<UUID, Integer> sorted = TopManager.sortByComparator(WarzoneMoney.warzoneTimes);
                LinkedHashMap copy = new LinkedHashMap();
                AtomicInteger place = new AtomicInteger(1);
                sorted.forEach((id, amount) -> copy.put(place.getAndIncrement(), new TopPlayer(id, amount, null)));
                this.TopPlayers = copy;
                this.loading = false;
                this.lastTopUpgrade = System.currentTimeMillis();
                System.out.println("[WarzoneMoney Top Manager] Loaded " + this.TopPlayers.size() + " top warzone players in " + (System.currentTimeMillis() - start) + "ms !");
                if (callback != null) {
                    callback.accept(this.TopPlayers);
                }
            }, ForkJoinPool.commonPool());
        }
    }
}

