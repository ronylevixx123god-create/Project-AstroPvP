/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import java.util.HashMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ArgParser {
    public static HashMap<String, String> parse(String args) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (args != null) {
            String[] split;
            for (String nameValuePair : split = args.split(";")) {
                String[] nameValue = nameValuePair.split("=");
                if (nameValue.length != 2) continue;
                map.put(nameValue[0].toLowerCase(), nameValue[1]);
            }
        }
        return map;
    }
}

