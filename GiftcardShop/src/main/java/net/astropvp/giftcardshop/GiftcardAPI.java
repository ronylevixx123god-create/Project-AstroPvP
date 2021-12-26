package net.astropvp.giftcardshop;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.net.MediaType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.astropvp.giftcardshop.data.AddGiftcards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class GiftcardAPI {

    public static void createGiftCard(Player player, double amount) throws IOException, UnirestException, ParseException {

        player.sendMessage(CC.translate("&a&l(!)&a Generating a &f$" + NumberUtils.formatMoney(amount) +  " &aGiftcard for you..."));

        Unirest.setTimeouts(0, 0);
        HttpResponse response = Unirest.post("https://plugin.tebex.io/gift-cards?amount=" + amount + "&note=" + player.getName() + " Generated Giftcard")
                .header("X-Tebex-Secret", GiftcardShopPlugin.getInstance().getConfig().getString("tebex-secret"))
                .header("Content-Type", "text/plain")
                .body("012345678910")
                .asString();

        String code = String.valueOf(response.getBody());

        code = code.split("\"code\":")[1];

        code = code.split(",")[0].replace("\"", "");

        code = java.util.Arrays.toString(code.split("(?<=\\G.{4})"));

        code = code.replace("[", "").replace("]", "").replace(",", "");

        String codeAndAmount = code+":"+amount;

        AddGiftcards.addNewGiftcard(player.getUniqueId(), codeAndAmount);
        player.sendMessage(CC.translate("&a&l(!)&a A giftcard has been added to your &f/giftcards&a."));
        player.sendMessage(CC.translate("&7* The code is &f" + code));


    }


}
