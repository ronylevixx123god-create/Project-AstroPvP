/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases;

import com.sk89q.worldguard.domains.DefaultDomain;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegionDBUtil {
    private static Pattern groupPattern = Pattern.compile("(?i)^[G]:(.+)$");

    private RegionDBUtil() {
    }

    public static void addToDomain(DefaultDomain domain, String[] split, int startIndex) {
        for (int i = startIndex; i < split.length; ++i) {
            String s = split[i];
            Matcher m = groupPattern.matcher(s);
            if (m.matches()) {
                domain.addGroup(m.group(1));
                continue;
            }
            domain.addPlayer(s);
        }
    }

    public static void removeFromDomain(DefaultDomain domain, String[] split, int startIndex) {
        for (int i = startIndex; i < split.length; ++i) {
            String s = split[i];
            Matcher m = groupPattern.matcher(s);
            if (m.matches()) {
                domain.removeGroup(m.group(1));
                continue;
            }
            domain.removePlayer(s);
        }
    }

    public static DefaultDomain parseDomainString(String[] split, int startIndex) {
        DefaultDomain domain = new DefaultDomain();
        for (int i = startIndex; i < split.length; ++i) {
            String s = split[i];
            Matcher m = groupPattern.matcher(s);
            if (m.matches()) {
                domain.addGroup(m.group(1));
                continue;
            }
            domain.addPlayer(s);
        }
        return domain;
    }

    public static String preparePlaceHolders(int length) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < length) {
            builder.append("?");
            if (++i >= length) continue;
            builder.append(",");
        }
        return builder.toString();
    }

    public static void setValues(PreparedStatement preparedStatement, String ... values) throws SQLException {
        for (int i = 0; i < values.length; ++i) {
            preparedStatement.setString(i + 1, values[i]);
        }
    }
}

