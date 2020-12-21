/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UrlPathHelper {
    private static final String PROTOCAL_PREFIX = "file:";

    public static URL[] convertToUrl(String[] paths) {
        ArrayList<URL> list = new ArrayList<URL>();
        for (int i = 0; i < paths.length; ++i) {
            URL url = UrlPathHelper.convertToUrl(paths[i]);
            if (url == null) continue;
            list.add(url);
        }
        return list.toArray(new URL[list.size()]);
    }

    public static URL convertToUrl(String path) {
        if (UrlPathHelper.isEmpty(path)) {
            return null;
        }
        try {
            return new URL(PROTOCAL_PREFIX + UrlPathHelper.convertUrlString(path));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertUrlString(String classpath) {
        File file;
        if (UrlPathHelper.isEmpty(classpath)) {
            return "";
        }
        if ((classpath = classpath.trim()).length() < 2) {
            return "";
        }
        if (classpath.charAt(0) != '/' && classpath.charAt(1) == ':') {
            classpath = "/" + classpath;
        }
        if (!classpath.endsWith("/") && (file = new File(classpath)).exists() && file.isDirectory()) {
            classpath = classpath.concat("/");
        }
        return classpath;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
}

