package me.astropvp.factionlogs.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import me.astropvp.factionlogs.FactionLogs;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HastebinUtils {

    private static final DateTimeFormatter DATE_FORMATED = DateTimeFormatter.ofPattern("EEE, MMM dd | HH:mm");


    public static String post(String text, boolean raw) throws IOException {
        byte[] postData = text.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        String requestURL = "https://www.toptal.com/developers/hastebin/documents";
        URL url = new URL(requestURL);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Hastebin Java Api");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);

        String response = null;
        DataOutputStream wr;
        try {
            wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.contains("\"key\"")) {
            response = response.substring(response.indexOf(":") + 2, response.length() - 2);

            String postURL = raw ? "https://www.toptal.com/developers/hastebin/raw/" : "https://www.toptal.com/developers/hastebin/";
            response = postURL + response;
        }

        return response;
    }

    public static void sendLog(Player sender, String factionId, LogType logType, boolean raw) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
        String date = localDateTime.format(DATE_FORMATED);
        StringBuilder text = new StringBuilder();
        text.append(logType.getDisplayName()).append(" Generated on ").append(date).append(" (EST)").append(" for faction ").append(FactionUtils.getFactionById(factionId).getTag()).append("\n");
        List<FactionLog> factionLogsList = FactionsLogsAPI.getFactionLogs(factionId, logType);
        for (FactionLog factionLog : factionLogsList) {
            text.append(" ").append(factionLog.toString()).append("\n");
        }

        ;

        try {
            String url = post(text.toString(), raw);
            sender.sendMessage(CC.translate("&6&lFactions &8⎜ &e" + logType.getDisplayName() + ": &a&n" + url));
        } catch (IOException e) {
            sender.sendMessage(CC.translate("&6&lFactions &8⎜ &cError&8: &eAn error occurred, please try again!"));
            e.printStackTrace();
        }
    }

}