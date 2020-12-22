/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;

import java.util.List;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

enum CandyGramMessages {
    YOUR_THE_ONE("&a#You'reTheOne", 10000, 0),
    CRAZY4YOU("&a#Crazy4You", 10000, 1),
    BE_MINE("&aWill you be mine?", 10000, 2),
    HEART_EYES("&a#HeartEyes", 10000, 3),
    SOUL_MATES("&a#SoulMates", 10000, 4),
    YOU_AUTOCOMPLETE("&aYou Auto-Complete me", 10000, 5),
    STAY_SINGLE("&a#StaySingleStayAwesome", 10000, 6),
    BE_MINE_VALENTINE("&aBe My Valentine", 10000, 7),
    YOUR_BASE_OR_MINE("&aYour Base or Mine?", 10000, 8),
    YOUR_LUCKY("&dYou're lucky to have me!", 100000, 18),
    ALONE("&d#4EverAlone", 100000, 19),
    TOLERATE("&dI Tolerate You", 100000, 20),
    YOU_SUCK_LESS("&dYou Suck Less Than Most People", 100000, 21),
    PUT_UP_OR("&dPut Up or Shut Up", 100000, 22),
    CHOCOLATE("&d#Chocolate", 100000, 23),
    SAVE_MONEY(CC.LightPurple + "Save Your Money", Lists.newArrayList("&dSave Your Money", "&dStay Single"), 1000000, 24),
    HEART("&d&l<3", 100000, 25),
    NETFLIX("&dNetflix and Grind", 100000, 26),
    ROSES_ARE_RED(CC.Red + "Roses are Red", Lists.newArrayList("&c&lRoses are Red", "&b&lViolets are blue", "&6&lI have a sword", "&6&lGimme your gear!"), 1000000, 36),
    TABLE_FOR_ONE("&c#TableForOne", 1000000, 37),
    NO_DATE("&cNo Date No Problem, Right?", 1000000, 38),
    TODAYS_SINGLE(CC.Red + "Singles Awareness", Lists.newArrayList("&c&lToday may be", "&c&lSingle's Awareness Day", "&c&lBut chin up,", "&c&lTomorrow's &e&lHalf Price Candy Day!"), 1000000, 39),
    SEND_NUBES("&c#SendNubes", 1000000, 40),
    LEMME_AXE_YOU(CC.Red + "Axe You A Question", Lists.newArrayList("&cLemme &4Axe you a question!", "&4Be Mine?"), 1000000, 41),
    MOM_VALENTINE("&cBet your Mom's your &4&lValentine", 1000000, 42),
    CALL_ME_DADDY("&cCall me Daddy", 1000000, 43),
    ALWAYS_USE_PROTECTION(CC.Red + "#2fa", Lists.newArrayList("&6&lAlways Use Protection", "&c&l#2fa"), 1000000, 44);

    private String displayName;
    private List<String> messages;
    private int cost;
    private int slot;

    private CandyGramMessages(String displayName, String msg, int cost, int slot) {
        this(ChatColor.translateAlternateColorCodes((char)'&', (String)displayName), Lists.newArrayList(msg), cost, slot);
    }

    private CandyGramMessages(String msg, int cost, int slot) {
        this(null, Lists.newArrayList(ChatColor.translateAlternateColorCodes((char)'&', (String)msg)), cost, slot);
    }

    private CandyGramMessages(String displayName, List<String> messages, int cost, int slot) {
        this.displayName = displayName;
        this.messages = messages;
        this.cost = cost;
        this.slot = slot;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public int getCost() {
        return this.cost;
    }

    public int getSlot() {
        return this.slot;
    }
}

