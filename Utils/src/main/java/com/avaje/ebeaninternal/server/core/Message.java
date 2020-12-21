/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Message {
    private static final String bundle = "com.avaje.ebeaninternal.api.message";

    public static String msg(String key, Object arg) {
        Object[] args = new Object[]{arg};
        return MessageFormat.format(Message.getPattern(key), args);
    }

    public static String msg(String key, Object arg, Object arg2) {
        Object[] args = new Object[]{arg, arg2};
        return MessageFormat.format(Message.getPattern(key), args);
    }

    public static String msg(String key, Object arg, Object arg2, Object arg3) {
        Object[] args = new Object[]{arg, arg2, arg3};
        return MessageFormat.format(Message.getPattern(key), args);
    }

    public static String msg(String key, Object[] args) {
        return MessageFormat.format(Message.getPattern(key), args);
    }

    public static String msg(String key) {
        return MessageFormat.format(Message.getPattern(key), new Object[0]);
    }

    private static String getPattern(String key) {
        try {
            ResourceBundle myResources = ResourceBundle.getBundle(bundle);
            return myResources.getString(key);
        }
        catch (MissingResourceException e) {
            return "MissingResource com.avaje.ebeaninternal.api.message:" + key;
        }
    }
}

