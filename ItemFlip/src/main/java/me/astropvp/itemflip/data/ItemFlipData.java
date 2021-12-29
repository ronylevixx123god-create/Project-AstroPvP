package me.astropvp.itemflip.data;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.struct.Relation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.astropvp.itemflip.enums.Color;
import me.astropvp.itemflip.utils.HoverText;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFlipData {
    private static List<ItemFlipData> flips = Lists.newArrayList();
    private int countdown = -1;
    private List<ItemFlipPlayerData> data = Lists.newArrayList();
    private boolean active;
    private Color active_color;

    public ItemFlipData(ItemFlipPlayerData data, ItemFlipPlayerData other) {
        this.data.add(data);
        this.data.add(other);
        flips.add(this);
    }

    public ItemFlipPlayerData findPlayer(Player player) {
        Iterator var2 = this.data.iterator();

        ItemFlipPlayerData data;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            data = (ItemFlipPlayerData) var2.next();
        } while (data.getPlayer() != player);

        return data;
    }

    public List<ItemFlipPlayerData> getData() {
        return this.data;
    }

    public int getCountdown() {
        return this.countdown;
    }

    public boolean isActive() {
        return this.active;
    }

    public Color getActiveColor() {
        return this.active_color;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void setActiveColor(Color active_color) {
        this.active_color = active_color;
    }

    public void remove() {
        if (this.isActive()) {
            Color color = this.getActiveColor();
            Color other_color = ((ItemFlipPlayerData) this.getData().get(0)).getColor() == color ? ((ItemFlipPlayerData) this.getData().get(1)).getColor() : ((ItemFlipPlayerData) this.getData().get(0)).getColor();
            Player winner = color == ((ItemFlipPlayerData) this.getData().get(0)).getColor() ? ((ItemFlipPlayerData) this.getData().get(0)).getPlayer() : ((ItemFlipPlayerData) this.getData().get(1)).getPlayer();
            Player loser = winner == ((ItemFlipPlayerData) this.getData().get(0)).getPlayer() ? ((ItemFlipPlayerData) this.getData().get(1)).getPlayer() : ((ItemFlipPlayerData) this.getData().get(0)).getPlayer();
            List<ItemStack> items = Lists.newArrayList();
            items.addAll(((ItemFlipPlayerData) this.getData().get(0)).getItems());
            items.addAll(((ItemFlipPlayerData) this.getData().get(1)).getItems());

            CollectPlugin.getInstance().getCollectManager().getPlayerCollect(winner).add(new CollectCategory(CollectType.ITEMFLIP, items.toArray(new ItemStack[items.size() - 1]), CC.RedB + loser.getName() + "'s ItemFlip Items"));



            loser.sendMessage(color.getPrefix() + CC.Bold + "(!) " + color.getPrefix() + winner.getName() + " has won the item flip with " + CC.Bold + color.getName() + "!");
            winner.sendMessage(color.getPrefix() + CC.Bold + "(!) " + color.getPrefix() + "You have won the item flip!");
            winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1.25F, 2.0F);
            winner.playSound(winner.getLocation(), Sound.ANVIL_LAND, 1.25F, 2.0F);
            FPlayer f_winner = FactionUtils.getFPlayer(winner);
            FPlayer f_loser = FactionUtils.getFPlayer(loser);
            Iterator var8 = this.getData().iterator();

            while (var8.hasNext()) {
                ItemFlipPlayerData flip_data = (ItemFlipPlayerData) var8.next();
                Player player = flip_data.getPlayer();
                FPlayer fp = FactionUtils.getFPlayer(player);
                Relation rel_one = fp.getRelationTo(f_winner);
                Relation rel_two = fp.getRelationTo(f_loser);
                player.sendMessage(CC.GrayB + "** " + color.getPrefix() + "■ " + rel_one.getColor() + winner.getName() + CC.Gray + " has defeated " + other_color.getPrefix() + "■ " + rel_two.getColor() + loser.getName() + CC.Gray + " in an /itemflip:");
                Iterator var14 = items.iterator();

                while (var14.hasNext()) {
                    ItemStack item = (ItemStack) var14.next();
                    JSONMessage msg = addItemToMessage(new JSONMessage(CC.GrayB + "* "), item);
                    msg.sendToPlayer(player);
                }
            }
            winner.sendMessage("");
            winner.sendMessage(CC.translate("&3&l(&b&lCOLLECT&3&l)&b The ItemFlip Items from &f" + loser.getName() + "&b have been put into your &f/collect&b."));
            winner.sendMessage("");
        }

        flips.remove(this);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static ItemFlipData findItemFlipData(Player player) {
        Iterator var1 = flips.iterator();

        ItemFlipData flip;
        ItemFlipPlayerData data;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            flip = (ItemFlipData) var1.next();
            data = flip.findPlayer(player);
        } while (data == null);

        return flip;
    }

    public static boolean isInMemory(ItemFlipData data) {
        return flips.contains(data);
    }

    public static ItemFlipPlayerData findItemFlipPlayerData(Player player) {
        ItemFlipData data = findItemFlipData(player);
        return data != null ? data.findPlayer(player) : null;
    }

    private static JSONMessage addItemToMessage(JSONMessage msg, ItemStack is) {
        HoverText text = is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? formatColors(is.getItemMeta().getDisplayName()) : null;
        msg.addItem(is, "" + ChatColor.WHITE + is.getAmount() + "x " + "" + (text != null ? ChatColor.RESET + text.getText() : StringUtils.capitalize(is.getType().name().toLowerCase().replace("_", " "))) + ChatColor.WHITE + " ", text != null && !text.getLastColors().isEmpty() ? (ChatColor) text.getLastColors().get(0) : ChatColor.WHITE);
        return msg;
    }

    private static HoverText formatColors(String s) {
        HoverText text = new HoverText();
        StringBuilder main = new StringBuilder();
        if (s.contains(" ")) {
            List<ChatColor> letterColors = new ArrayList();
            String[] var4 = s.split(" ");
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String word = var4[var6];
                boolean foundColorChar = false;
                boolean lastLetterLegit = true;
                StringBuilder newWord = new StringBuilder();
                char[] var11 = word.toCharArray();
                int var12 = var11.length;

                for (int var13 = 0; var13 < var12; ++var13) {
                    char c = var11[var13];
                    if (c == 167) {
                        foundColorChar = true;
                        if (lastLetterLegit) {
                            letterColors.clear();
                            lastLetterLegit = false;
                        }
                    } else if (foundColorChar) {
                        ChatColor color = ChatColor.getByChar(c);
                        if (color != null) {
                            letterColors.add(color);
                        }

                        foundColorChar = false;
                    } else {
                        Iterator var15 = letterColors.iterator();

                        while (var15.hasNext()) {
                            ChatColor color = (ChatColor) var15.next();
                            if (color != null) {
                                newWord.append(color.toString());
                            }
                        }

                        newWord.append(c);
                        lastLetterLegit = true;
                    }
                }

                newWord.append(" ");
                main.append(newWord.toString());
            }
        } else {
            main.append(s);
        }

        text.setText(main.toString().trim());
        return text;
    }

    public static List<ItemFlipData> getFlipData() {
        return flips;
    }
}
