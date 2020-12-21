/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

public class EscapeJson {
    public static String escapeQuote(String value) {
        StringBuilder sb = new StringBuilder(value.length() + 2);
        sb.append("\"");
        EscapeJson.escape(value, sb);
        sb.append("\"");
        return sb.toString();
    }

    public static String escape(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        EscapeJson.escape(s, sb);
        return sb.toString();
    }

    public static void escape(String s, StringBuilder sb) {
        block10: for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\"': {
                    sb.append("\\\"");
                    continue block10;
                }
                case '\\': {
                    sb.append("\\\\");
                    continue block10;
                }
                case '\b': {
                    sb.append("\\b");
                    continue block10;
                }
                case '\f': {
                    sb.append("\\f");
                    continue block10;
                }
                case '\n': {
                    sb.append("\\n");
                    continue block10;
                }
                case '\r': {
                    sb.append("\\r");
                    continue block10;
                }
                case '\t': {
                    sb.append("\\t");
                    continue block10;
                }
                case '/': {
                    sb.append("\\/");
                    continue block10;
                }
                default: {
                    if (ch >= '\u0000' && ch <= '\u001f' || ch >= '\u007f' && ch <= '\u009f' || ch >= '\u2000' && ch <= '\u20ff') {
                        String hs = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int j = 0; j < 4 - hs.length(); ++j) {
                            sb.append('0');
                        }
                        sb.append(hs.toUpperCase());
                        continue block10;
                    }
                    sb.append(ch);
                }
            }
        }
    }
}

