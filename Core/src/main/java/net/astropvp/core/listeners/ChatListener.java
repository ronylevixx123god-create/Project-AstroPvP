package net.astropvp.core.listeners;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.struct.ChatMode;
import net.astropvp.core.AstroCore;
import net.astropvp.core.conaxgames.staff.StaffChat;
import net.astropvp.core.titles.PlayerTitleAPI;
import net.astropvp.core.titles.utils.Clickable;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().endsWith("'s Inventory")) {
            event.setCancelled(true);
        }
    }

    // Chat Filter
    private final Pattern ipPattern = Pattern.compile("((?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[.,-:; ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9]))", Pattern.CASE_INSENSITIVE);
    private final Pattern webpattern = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");
    private final List<String> badWords = Arrays.asList("nigger", "jew", "kys", "demonicpvp", "jurassicpvp", "cursedpvp", "exoduspvp", "nig", "fag", "faggot", "ddos", "doss", "niggar", "hitler");

    @EventHandler(priority = EventPriority.HIGH)
    public void playerChat(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        event.setCancelled(true);

        Set<Player> recipients = event.getRecipients();
        Player player = event.getPlayer();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);

        if (fPlayer.getChatMode() != ChatMode.PUBLIC) return;

        Faction faction = fPlayer.getFaction();

        //Check if chat is disable
        if (ChatFilter.isChatHalted() && !player.isOp()) {
            event.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot use chat while chat is halted."));
            return;
        }

        if (event.getMessage().contains("[item]") && event.getMessage().contains("[brag]")) return;

        Matcher ipMatcher = ipPattern.matcher(event.getMessage());

        if (ipMatcher.find() && !player.isOp()) {
            player.sendMessage(CC.translate("&c&l(!)&c You are not allowed to send ip's in chat!"));
            StaffChat.sendGlobal(CC.translate("&c" + player.getName() + " &ctried sending an ip in chat &7(" + event.getMessage() + "&7)"));
            return;
        }

        Matcher webMatcher = webpattern.matcher(event.getMessage());

        if (webMatcher.find() && !player.isOp()) {
            player.sendMessage(CC.translate("&c&l(!)&c You are not allowed to send links in chat!"));
            StaffChat.sendGlobal(CC.translate("&c" + player.getName() + " &ctried sending a link in chat &7(" + event.getMessage() + "&7)"));
            return;
        }


        recipients.forEach(recipient -> {
            FPlayer fRecipient = FPlayers.getInstance().getByPlayer(recipient);
            Faction recipientFaction = fRecipient.getFaction();

            Clickable clickable = new Clickable("");
            if (!faction.isWilderness()) {
                String shieldStatus;
/*                if (shieldFeature.isShieldActive(faction.getId()) && shieldFeature.isSetup(faction.getId()) && shieldFeature.isTimeConfigured(faction.getId())) {
                    shieldStatus = "&aON &7(" + ChatColor.stripColor(shieldFeature.getShieldData(faction.getId(), true)) + "&7)";
                } else {
                    shieldStatus = "&cOFF";
                }*/

                clickable.add(faction.getRelationTo(recipientFaction).getColor() + fPlayer.getChatTag() + " ",
                        "&8(&a!&8) &7Faction Info: &a" + faction.getTag() + " \n" +
                                "&a* &7Strikes: &a" + faction.getStrikes().size() + " \n" +
                                "&a* &7Power: &a" + (int) faction.getPower() + "&8/&a" + (int) faction.getPowerMax() + " \n" +
                                //"&a* &7Shield " + shieldStatus + "\n" +
                                "&a* &7Online: &a" + faction.getOnlinePlayers().size() + " \n\n" +
                                "&7&oClick to view this faction",
                        "/f who " + player.getName()
                );
            }

            clickable.add(PermissionsAPI.getPermissionUser(player.getUniqueId()).getPrefix() + player.getName() + " " + getTag(player),
                    "&7Name: &a" + player.getName() + " \n" +
                            "&7Rank: " + PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup() + " \n" +
                            "&7Balance: &a$" + NumberUtils.formatMoney(EconomyUtils.getBalance(player)) + " \n" +
                            "&7&oClick to see when the player logged online",
                    "/seen " + player.getName()
            );


            if (AstroCore.getInstance().getCachedColors().containsKey(event.getPlayer())) {
                String message = event.getMessage();
                message = (event.getPlayer().isOp() ? ChatColor.DARK_RED : AstroCore.getInstance().getCachedColors().get(event.getPlayer())) + message;
                message = message.replaceAll(" ", " " + (event.getPlayer().isOp() ? ChatColor.DARK_RED : AstroCore.getInstance().getCachedColors().get(event.getPlayer())));
                event.setMessage(message);
            }
            ChatColor chatcolor;
            chatcolor = AstroCore.getInstance().getCachedColors().getOrDefault(event.getPlayer(), ChatColor.WHITE);


            String message = event.getMessage();
            clickable.add("&7: ");


            if (message.contains("[item]") || message.contains("[ITEM]")) {
                ItemStack itemStack = event.getPlayer().getItemInHand();
                if (itemStack == null || !itemStack.hasItemMeta() || itemStack.getType() == Material.AIR) {
                    player.sendMessage(CC.translate("&c&l(!)&c Please hold an item when using [item]"));
                    return;
                } else {
                    String name = itemStack.getItemMeta().getDisplayName();
                    int amount = itemStack.getAmount();
                    clickable.addItem(String.format("&f» " + name + " &7(%sx) &f«", amount), itemStack);
                }
                message = message.replace("[item]", "").replace("[ITEM]", "");
            }


            if (message.contains("[brag]") || message.contains("[BRAG]")) {
                String playerName = event.getPlayer().getName();
                clickable.add(String.format("&d&o%s's Inventory", playerName), String.format("&7View %s's Inventory", playerName), "/invsee " + playerName);
                message = message.replace("[brag]", "").replace("[BRAG]", "");
            }

            clickable.add(chatcolor + message);
            clickable.sendToPlayer(recipient);
        });
    }

    public String getTag(Player player) {
        String title = "";
        if (PlayerTitleAPI.getEquippedTitle(player.getUniqueId()) != null)
            if (PlayerTitleAPI.getEquippedTitle(player.getUniqueId()).equals("none")) {
                title = "";
            } else {
                title = " &8[&a" + PlayerTitleAPI.getEquippedTitle(player.getUniqueId()) + "&8]";
            }
        return title;
    }
}
