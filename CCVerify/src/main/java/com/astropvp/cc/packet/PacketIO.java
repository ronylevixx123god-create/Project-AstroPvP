package com.astropvp.cc.packet;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.Debug;
import com.astropvp.cc.config.Config;
import com.astropvp.cc.packet.encryption.PacketEncryptor;
import com.astropvp.cc.packet.encryption.PacketSecurity;
import com.astropvp.cc.packet.handler.PacketHandler;
import com.astropvp.cc.packet.type.Packet;
import com.astropvp.cc.packet.type.in.PacketIn;
import com.astropvp.cc.packet.type.out.*;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
import com.astropvp.cc.util.Chat;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import me.astropvp.proxypach.ProxyPatch;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.managers.InventoryPetManager;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PacketIO {
    private static final long timeInit = System.currentTimeMillis();

    private static PacketIO instance;

    private BukkitTask sender;

    private BukkitTask receiver;

    private PacketAdapter adapter;

    private Map<String, List<String>> headers;

    private static PacketHandler packetHandler;

    private static PacketEncryptor encryptor;

    public static PacketIO get() {
        return instance;
    }

    private static final Map<UUID, ConcurrentLinkedDeque<FutureOutPacket>> outPacketQueue = new ConcurrentHashMap<>();

    private static final Map<UUID, ConcurrentLinkedDeque<FutureInPacket>> inPacketQueue = new ConcurrentHashMap<>();

    public static PacketIO create() {
        if (instance != null)
            return null;
        instance = new PacketIO();
        return instance;
    }

    public void shutdown() {
        if (this.sender != null)
            this.sender.cancel();
        if (this.receiver != null)
            this.receiver.cancel();
        if (this.adapter != null)
            ProtocolLibrary.getProtocolManager().removePacketListener((PacketListener)this.adapter);
    }

    private boolean verifyQueue(ConcurrentLinkedDeque<FutureInPacket> var1) {
        if (!var1.isEmpty()) {
            FutureInPacket var2 = var1.getLast();
            PacketContainer var3 = (PacketContainer)PacketContainer.GSON.fromJson(new String(var2.getData(), StandardCharsets.UTF_8), PacketContainer.class);
            if (var1.size() >= 1000000) {
                var1.clear();
                return false;
            }
            return !var3.isSplitNext();
        }
        return true;
    }

    private PacketIO() {
        createSendTask();
        createReceiveTask();
        packetHandler = new PacketHandler();
        this.headers = new HashMap<>();
        byte[] array = Base64.getDecoder().decode("13c9esdEOtLC+JPE+GaWFcF86+MqlLoLaY6yRedTwUp3Fx1NFWDwPnBYjCb3Y83yPV8rujPeaCkARIabBx5Ca+J7FstlvlUN2+j5WpCSFUFDvqjw2KG0k6/WoABk1aHQi0n/sypTUB6ZkG2JnyuaNTQh2ebhc5yMIdeAViwIcyW4t3I5p7+VYjcq9YBX4CfKRJjSZaVyJIFBHouCndqlNicc4PvKcmTnYwj66h6rI4t5Iq97ticrbvXdZifre9IGcVLVhOIpCtiXoLBA06t6beLFhOF8OatYVu86CXvT0TBUaidS2NO+l7Qqk4m9k2fDENna2zpOe237XLBm7uKviA==");
        array = PacketSecurity.a(array);
        encryptor = new PacketEncryptor(array, true);
        this.adapter = new PacketAdapter((Plugin)CCVerify.get(), ListenerPriority.LOWEST, new PacketType[] { PacketType.Play.Client.CUSTOM_PAYLOAD }) {
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD)
                    return;
                PacketPlayInCustomPayload customPayload = (PacketPlayInCustomPayload)event.getPacket().getHandle();
                String channelName = customPayload.a();
                if (!channelName.equals("cosmic"))
                    return;
                byte[] packetArray = customPayload.b().array();
                FutureInPacket received = new FutureInPacket(packetArray, event.getPlayer());
                Debug.log("Received packet ID " + received.getMyID() + " at " + (System.currentTimeMillis() - PacketIO.timeInit) + " from " + event.getPlayer() + "!");
                synchronized (PacketIO.inPacketQueue) {
                    if (PacketIO.inPacketQueue.containsKey(event.getPlayer().getUniqueId())) {
                        ((ConcurrentLinkedDeque<FutureInPacket>)PacketIO.inPacketQueue.get(event.getPlayer().getUniqueId())).add(received);
                    } else {
                        PacketIO.inPacketQueue.put(event.getPlayer().getUniqueId(), new ConcurrentLinkedDeque(Lists.newArrayList((Object[])new FutureInPacket[] { received })));
                    }
                }
            }
        };
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)this.adapter);
    }

    public void queuePacket(Player recipient, Packet container){
        synchronized (outPacketQueue){
            if(outPacketQueue.containsKey(recipient.getUniqueId())){
                outPacketQueue.get(recipient.getUniqueId()).add(new FutureOutPacket(container, recipient));
            }else{
                outPacketQueue.put(recipient.getUniqueId(), new ConcurrentLinkedDeque<>(Lists.newArrayList(new FutureOutPacket(container, recipient))));
            }
        }
    }

    public void addPlayer(Player player) {
        if (ProxyPatch.playerNames.contains(player.getName())) return;
        registerPlayer(player);
        CosmicPlayers.get().wrapPlayer(player);
        queuePacket(player, new PacketOutSessionKey("13c9esdEOtLC+JPE+GaWFcF86+MqlLoLaY6yRedTwUp3Fx1NFWDwPnBYjCb3Y83yPV8rujPeaCkARIabBx5Ca+J7FstlvlUN2+j5WpCSFUFDvqjw2KG0k6/WoABk1aHQi0n/sypTUB6ZkG2JnyuaNTQh2ebhc5yMIdeAViwIcyW4t3I5p7+VYjcq9YBX4CfKRJjSZaVyJIFBHouCndqlNicc4PvKcmTnYwj66h6rI4t5Iq97ticrbvXdZifre9IGcVLVhOIpCtiXoLBA06t6beLFhOF8OatYVu86CXvT0TBUaidS2NO+l7Qqk4m9k2fDENna2zpOe237XLBm7uKviA=="));
        queuePacket(player, new PacketOutFeatureSet("COSMICPVP"));
        queuePacket(player, new PacketOutAllowedEmotes(new HashSet(Config.getInst().getEmotes().getAllowedEmotes())));
        Map<String, List<Double>> cooldowns = new HashMap<>();
        //for(InventoryPets type : InventoryPets.values()) {
            List<Double> current = new ArrayList<>();
            //for(int i = 1; i < type.getInternalClass().getMaxLevel(); i++) {
                //current.add((double) 360);
            //}
            cooldowns.put("BANSHEE", Arrays.asList(360D));
        //}
        //queuePacket(player, new SItemCooldownsListPacket(cooldowns));
        if (Config.getInst().getTooltip().isSendDiscordTooltipOnJoin())
            queuePacket(player, new PacketOutTooltip(Chat.c(Config.getInst().getTooltip().getDiscordTooltipTitle()),
                    Chat.c(Config.getInst().getTooltip().getDiscordTooltipText()), null,

                    Config.getInst().getTooltip().getDiscordTooltipItem(), Config.getInst().getTooltip().getDiscordTooltipItemDamage()));
        addPlayerToHeader(player);
    }

    public void addPlayerToHeader(Player player) {
        Map<String, List<String>> map = Config.getInst().getHeaders().getAllHeaders();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (player.hasPermission("ccverify." + entry.getKey().toLowerCase())) {
                this.headers.put(player.getName(), entry.getValue());
                break;
            }
        }
        if (!this.headers.containsKey(player.getName()))
            this.headers.put(player.getName(), Lists.newArrayList());
        PacketOutPlayerHeaders playerHeaders = new PacketOutPlayerHeaders(this.headers, false);
        for (CosmicPlayer cp : CosmicPlayers.get().getAll())
            queuePacket(cp.getPlayer(), (Packet)playerHeaders);
    }

    public void removePlayerFromHeader(Player player) {
        this.headers.remove(player.getName());
        PacketOutPlayerHeaders playerHeaders = new PacketOutPlayerHeaders(this.headers, false);
        for (CosmicPlayer cp : CosmicPlayers.get().getAll())
            cp.sendPacket((Packet)playerHeaders);
    }

    public void removePlayer(Player player) {
        CosmicPlayers.get().unwrapPlayer(player);
        synchronized (inPacketQueue) {
            inPacketQueue.remove(player.getUniqueId());
        }
        synchronized (outPacketQueue) {
            outPacketQueue.remove(player.getUniqueId());
        }
        removePlayerFromHeader(player);
    }

    private void registerPlayer(Player player) {
        PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
        serializer.writeBytes("cosmic".getBytes(StandardCharsets.UTF_8));
        PacketPlayOutCustomPayload customPayload = new PacketPlayOutCustomPayload("REGISTER", serializer);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(customPayload);
    }

    public void pumpOutQueue() {
        synchronized (outPacketQueue) {
            for (UUID uuid : outPacketQueue.keySet()) {
                Iterator<FutureOutPacket> packetIter = ((ConcurrentLinkedDeque<FutureOutPacket>)outPacketQueue.get(uuid)).iterator();
                while (packetIter.hasNext()) {
                    FutureOutPacket packet = packetIter.next();
                    packetIter.remove();
                    String json = PacketContainer.GSON.toJson(packet.getPacket());
                    Iterable<String> splitter = Splitter.fixedLength(3000).split(json);
                    Iterator<String> spliterator = splitter.iterator();
                    while (spliterator.hasNext()) {
                        byte[] sent;
                        String content = spliterator.next();
                        PacketContainer container = new PacketContainer(packet.getPacket().getID(), spliterator.hasNext(), content);
                        if (container.getPacketID() == 33) {
                            sent = PacketContainer.GSON.toJson(container).getBytes(StandardCharsets.UTF_8);
                        } else {
                            String data = Base64.getEncoder().encodeToString(encryptor.a(container.getContent().getBytes(StandardCharsets.UTF_8), true));
                            sent = PacketContainer.GSON.toJson(new PacketContainer(container.getPacketID(), spliterator.hasNext(), data)).getBytes(StandardCharsets.UTF_8);
                        }
                        PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
                        serializer.writeBytes(sent);
                        PacketPlayOutCustomPayload customPayload = new PacketPlayOutCustomPayload("cosmic", serializer);
                        (((CraftPlayer)packet.getRecipient()).getHandle()).playerConnection.sendPacket(customPayload);
                    }
                }
            }
            outPacketQueue.clear();
        }
    }

    public void pumpInQueue() {
        synchronized (inPacketQueue) {
            for (UUID uuid : inPacketQueue.keySet()) {
                ConcurrentLinkedDeque<FutureInPacket> packetIter = inPacketQueue.get(uuid);
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    continue;
                while (!packetIter.isEmpty() && verifyQueue(packetIter)) {
                    FutureInPacket packet = packetIter.removeFirst();
                    PacketContainer container = (PacketContainer)PacketContainer.GSON.fromJson(new String(packet.getData(), StandardCharsets.UTF_8), PacketContainer.class);
                    short packetID = container.getPacketID();
                    Class<? extends PacketIn> packetClass = (Class)PacketCommons.getPacketClass(packetID);
                    if (packetClass == null) {
                        Debug.log("Unknown packet ID: " + packetID);
                        continue;
                    }
                    Debug.log("Received packet: " + packetClass.getSimpleName() + " (ID: " + packet.getMyID() + ")");
                    String content = container.getContent();
                    if (packetID != 7)
                        content = new String(encryptor.b(Base64.getDecoder().decode(content), true), StandardCharsets.UTF_8);
                    Debug.log("Initial content: " + content);
                    if (container.isSplitNext()) {
                        int nextSplit = 2;
                        do {
                            FutureInPacket nextPacket = packetIter.removeFirst();
                            container = (PacketContainer)PacketContainer.GSON.fromJson(new String(nextPacket.getData(), StandardCharsets.UTF_8), PacketContainer.class);
                            if (packetID != container.getPacketID())
                                throw new IllegalStateException("Split packet ID mismatch: " + packetID + ", " + container.getPacketID());
                            String extra = container.getContent();
                            if (packetID != 7)
                                extra = new String(encryptor.b(Base64.getDecoder().decode(extra), true), StandardCharsets.UTF_8);
                            content = content + extra;
                            Debug.log("Found split part " + nextSplit++ + " in packet queue!");
                        } while (container.isSplitNext());
                    }
                    try {
                        PacketIn received = (PacketIn)PacketContainer.GSON.fromJson(content, packetClass);
                        received.handle(packetHandler, player);
                        CosmicPlayer cosmicPlayer = CosmicPlayers.get().getCosmicPlayer(player);
                        if (cosmicPlayer == null)
                            cosmicPlayer = CosmicPlayers.get().wrapPlayer(player);
                        cosmicPlayer.setOnCosmicClient(true);
                    } catch (IllegalStateException exc) {
                        Bukkit.getLogger().info("Illegal formatting in JSON: \"" + content + "\"!");
                    }
                }
            }
        }
    }

    private void createSendTask() {
        this

                .sender = (new BukkitRunnable() {
            public void run() {
                PacketIO.this.pumpOutQueue();
            }
        }).runTaskTimerAsynchronously((Plugin)CCVerify.get(), 0L, 1L);
    }

    private void createReceiveTask() {
        this

                .receiver = (new BukkitRunnable() {
            public void run() {
                PacketIO.this.pumpInQueue();
            }
        }).runTaskTimerAsynchronously((Plugin)CCVerify.get(), 0L, 1L);
    }
}