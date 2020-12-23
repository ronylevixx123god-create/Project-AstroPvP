/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ChatClickable
 *  net.minecraft.server.v1_7_R4.ChatComponentText
 *  net.minecraft.server.v1_7_R4.ChatModifier
 *  net.minecraft.server.v1_7_R4.EnumChatFormat
 *  net.minecraft.server.v1_7_R4.EnumClickAction
 *  net.minecraft.server.v1_7_R4.IChatBaseComponent
 */
package me.ifamasssxd.cosmicbosses;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.v1_8_R3.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CraftChatMessage {
    private static Map<Character, EnumChatFormat> formatMap = CraftChatMessage.setupFormat();
    private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf('\u00a7') + "[0-9a-fk-or])|(\\n)|((?:(?:https?)://)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('\u00a7') + " \\n]|$))))", 2);

    public static Map<Character, EnumChatFormat> setupFormat() {
        ImmutableMap.Builder<Character, EnumChatFormat> builder = ImmutableMap.builder();
        for (EnumChatFormat format : EnumChatFormat.values()) {
            // TODO: SPIGOT LOOKING FOR C WHICH IS STRING, NEEDS CHAR (couble be #z() but that is private)
            builder.put(Character.valueOf(Character.toLowerCase(format.c())), format);
        }
        return builder.build();
    }

    public static IChatBaseComponent[] fromString(String message) {
        int index;
        ArrayList<ChatComponentText> list = new ArrayList<ChatComponentText>();
        ChatComponentText currentChatComponent = new ChatComponentText("");
        ChatModifier modifier = new ChatModifier();
        IChatBaseComponent[] output = new IChatBaseComponent[]{currentChatComponent};
        int currentIndex = 0;
        if (message == null) {
            return output;
        }
        list.add(currentChatComponent);
        Matcher matcher = INCREMENTAL_PATTERN.matcher(message);
        String match = null;
        while (matcher.find()) {
            int index2;
            int groupId = 0;
            if (currentIndex < message.length() && (index2 = matcher.start(groupId)) > currentIndex) {
                IChatBaseComponent addition = new ChatComponentText(message.substring(currentIndex, index2)).setChatModifier(modifier);
                currentIndex = index2;
                modifier = modifier.clone();
                if (currentChatComponent == null) {
                    currentChatComponent = new ChatComponentText("");
                    list.add(currentChatComponent);
                }
                currentChatComponent.addSibling(addition);
                output = list.toArray(new IChatBaseComponent[0]);
            }
            block0 : switch (groupId) {
                case 1: {
                    EnumChatFormat format = formatMap.get(match.toLowerCase().charAt(1));
                    if (format == EnumChatFormat.RESET) {
                        modifier = new ChatModifier();
                        break;
                    }
                    if (format.isFormat()) {
                        switch (format) {
                            case BOLD: {
                                modifier.setBold(Boolean.TRUE);
                                break block0;
                            }
                            case ITALIC: {
                                modifier.setItalic(Boolean.TRUE);
                                break block0;
                            }
                            case STRIKETHROUGH: {
                                modifier.setStrikethrough(Boolean.TRUE);
                                break block0;
                            }
                            case UNDERLINE: {
                                modifier.setUnderline(Boolean.TRUE);
                                break block0;
                            }
                            case OBFUSCATED: {
                                modifier.setRandom(Boolean.TRUE);
                                break block0;
                            }
                        }
                        throw new AssertionError((Object)"Unexpected message format");
                    }
                    modifier = new ChatModifier().setColor(format);
                    break;
                }
                case 2: {
                    currentChatComponent = null;
                    break;
                }
                case 3: {
                    if (!match.startsWith("http://") && !match.startsWith("https://")) {
                        match = "http://" + match;
                    }
                    modifier.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.OPEN_URL, match));
                    int index3 = matcher.end(groupId);
                    if (index3 > currentIndex) {
                        IChatBaseComponent addition = new ChatComponentText(message.substring(currentIndex, index3)).setChatModifier(modifier);
                        currentIndex = index3;
                        modifier = modifier.clone();
                        if (currentChatComponent == null) {
                            currentChatComponent = new ChatComponentText("");
                            list.add(currentChatComponent);
                        }
                        currentChatComponent.addSibling(addition);
                        output = list.toArray((new IChatBaseComponent[list.size()]));
                    }
                    modifier.setChatClickable((ChatClickable)null);
                }
            }
            currentIndex = matcher.end(groupId);
        }
        if (currentIndex < message.length() && (index = message.length()) > currentIndex) {
            IChatBaseComponent addition = new ChatComponentText(message.substring(currentIndex, index)).setChatModifier(modifier);
            currentIndex = index;
            modifier = modifier.clone();
            if (currentChatComponent == null) {
                currentChatComponent = new ChatComponentText("");
                list.add(currentChatComponent);
            }
            currentChatComponent.addSibling(addition);
            output = list.toArray(new IChatBaseComponent[list.size()]);
        }
        modifier = null;
        return output;
    }
}

