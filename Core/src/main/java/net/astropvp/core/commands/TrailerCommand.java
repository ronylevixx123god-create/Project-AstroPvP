package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.astropvp.core.AstroCore;
import net.astropvp.core.cooldown.RealtimeUUIDCooldown;
import org.apache.commons.io.IOUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class TrailerCommand implements CommandExecutor {
    public static String REQUIRED_VIDEO_TITLE = "AstroPvP Map 11 Trailer | CosmicTest";

    public static String API_KEY = "AIzaSyAgSAZGQ2AyoQOGk_IYzGkYJym3LxeLeVc";

    public static Map<String, String> claimedVideoIds = Maps.newHashMap();

    private Map<UUID, VideoInfoFetchTask> fetcherTaskMap = Maps.newHashMap();

    private RealtimeUUIDCooldown cooldownMap = new RealtimeUUIDCooldown("trailer");

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(CC.translate("&c&l(!) &cPlease enter a video url!"));
            return true;
        }
        final String videoId = getVideoIdFromUrl(args[0]);
        if (this.fetcherTaskMap.containsKey(player.getUniqueId())) {
            player.sendMessage( "&cPlease wait...");
            return true;
        }
        if (claimedVideoIds.containsKey(player.getName())) {
            player.sendMessage( "&c&l(!) Rewards Already Claimed (!)");
            player.sendMessage( "&7You have already claimed your rewards!");
            return true;
        }
        if (videoId == null) {
            player.sendMessage( "&c&l(!) Link Invalid (!)");
            player.sendMessage( "&7You must have a valid youtube video url.");
            player.sendMessage( "&7(It should be in this format: https://www.youtube.com/watch?v=)");
            return true;
        }
        if (claimedVideoIds.containsValue(videoId)) {
            player.sendMessage( "&c&l(!) Video Already Claimed (!)");
            player.sendMessage( "&7You must have a valid youtube video url.");
            return true;
        }
        if (this.cooldownMap.isOnCooldown(player, "trailer")) {
            player.sendMessage( "&c&l(!) &cPlease wait a little bit before doing that.");
            return true;
        }
        this.cooldownMap.add(player, 15000L);
        player.sendMessage( "&aPlease wait while your video is verified...");
        final VideoInfoFetchTask task = new VideoInfoFetchTask(player.getUniqueId(), videoId);
        (new BukkitRunnable() {
            public void run() {
                if (player == null || !player.isOnline()) {
                    TrailerCommand.this.fetcherTaskMap.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                String videoTitle = task.getTitle();
                String videoDescription = task.getDescription();
                if (videoTitle == null || videoDescription == null) {
                    player.sendMessage( "&c&lERROR&c: Please try again in a bit.");
                    TrailerCommand.this.fetcherTaskMap.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                if (!videoTitle.equalsIgnoreCase(TrailerCommand.REQUIRED_VIDEO_TITLE)) {
                    player.sendMessage( "&c&l(!) Invalid Video Title (!)");
                    player.sendMessage( "&7Your video title doesn't match the required title.");
                    player.sendMessage( "");
                    player.sendMessage( "&c&lYour Title:");
                    player.sendMessage( "&7\"&c&m" + videoTitle + "&7\"");
                    player.sendMessage( "");
                    player.sendMessage( "&a&lRequired Title:");
                    player.sendMessage( "&7\"&a" + TrailerCommand.REQUIRED_VIDEO_TITLE + "&7\"");
                    player.sendMessage( "");
                    player.sendMessage( "&7After changing title, allow time to update.");
                    TrailerCommand.this.fetcherTaskMap.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                if (!videoDescription.toLowerCase().contains(sender.getName().toLowerCase())) {
                    player.sendMessage( "&c&l(!) Confirmation Required (!)");
                    player.sendMessage( "");
                    player.sendMessage( videoDescription);
                    player.sendMessage( "");
                    player.sendMessage( "&7Please insert &f'" + sender.getName() + "'&7 in the description of your video.");
                    player.sendMessage( "&7Then please redo &f/claim <link> &7to confirm once again.");
                    TrailerCommand.this.fetcherTaskMap.remove(player.getUniqueId());
                    cancel();
                    return;
                }
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                player.sendMessage( "&a&l(!) Rewards Claimed (!)");
                player.sendMessage( "&7You have successfully claimed your SOTW rewards.");
                TrailerCommand.claimedVideoIds.put(player.getName(), videoId);
                TrailerCommand.this.fetcherTaskMap.remove(player.getUniqueId());
                //give item here
            }
        }).runTaskLater((Plugin)AstroCore.getInstance(), 100L);
        return false;
    }

    public String getVideoIdFromUrl(String url) {
        if (!url.toLowerCase().contains("youtube.com/watch?v="))
            return null;
        String id = url.split("v=")[1];
        if (id.length() < 6)
            return null;
        return id;
    }

    public class VideoInfoFetchTask extends BukkitRunnable {
        private UUID playerUUID;

        private String videoId;

        private String title;

        private String description;

        public UUID getPlayerUUID() {
            return this.playerUUID;
        }

        public String getVideoId() {
            return this.videoId;
        }

        boolean complete = false;

        public boolean isComplete() {
            return this.complete;
        }

        public VideoInfoFetchTask(UUID uuid, String videoId) {
            TrailerCommand.this.fetcherTaskMap.put(uuid, this);
            this.playerUUID = uuid;
            this.videoId = videoId;
            runTaskAsynchronously((Plugin) AstroCore.getInstance());
        }

        public void run() {
            HttpURLConnection con = null;
            InputStream inputStream = null;
            try {
                String urlString = "https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id=" + this.videoId + "&key=" + TrailerCommand.API_KEY;
                URL url = new URL(urlString);
                con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                inputStream = con.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer);
                String responseString = writer.toString();
                Gson gson = new Gson();
                JsonObject jObject = (JsonObject)gson.fromJson(responseString, JsonObject.class);
                JsonArray jArray = jObject.getAsJsonArray("items");
                JsonElement mainObject = jArray.get(0);
                JsonElement snippet = mainObject.getAsJsonObject().get("snippet");
                this.title = snippet.getAsJsonObject().get("title").getAsString();
                this.description = snippet.getAsJsonObject().get("description").getAsString();
                inputStream.close();
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.complete = true;
                try {
                    inputStream.close();
                    con.disconnect();
                } catch (Exception exception) {}
            }
            this.complete = true;
        }

        public String getDescription() {
            if (!this.complete)
                return null;
            return this.description;
        }

        public String getTitle() {
            if (!this.complete)
                return null;
            return this.title;
        }
    }
}
