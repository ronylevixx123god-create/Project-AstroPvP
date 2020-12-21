/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.PropertyMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

final class PropertyMapLoader {
    private static Logger logger = Logger.getLogger(PropertyMapLoader.class.getName());
    private static ServletContext servletContext;

    PropertyMapLoader() {
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        PropertyMapLoader.servletContext = servletContext;
    }

    public static PropertyMap load(PropertyMap p, String fileName) {
        InputStream is = PropertyMapLoader.findInputStream(fileName);
        if (is == null) {
            logger.severe(fileName + " not found");
            return p;
        }
        return PropertyMapLoader.load(p, is);
    }

    private static PropertyMap load(PropertyMap p, InputStream in) {
        Properties props = new Properties();
        try {
            props.load(in);
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (p == null) {
            p = new PropertyMap();
        }
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String key = ((String)entry.getKey()).toLowerCase();
            String val = (String)entry.getValue();
            if (val != null) {
                val = val.trim();
            }
            p.put(key, val);
        }
        p.evaluateProperties();
        String otherProps = p.remove("load.properties");
        if (otherProps == null) {
            otherProps = p.remove("load.properties.override");
        }
        if (otherProps != null) {
            InputStream is = PropertyMapLoader.findInputStream(otherProps = otherProps.replace("\\", "/"));
            if (is != null) {
                logger.fine("loading properties from " + otherProps);
                PropertyMapLoader.load(p, is);
            } else {
                logger.severe("load.properties " + otherProps + " not found.");
            }
        }
        return p;
    }

    private static InputStream findInputStream(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName is null?");
        }
        if (servletContext == null) {
            logger.fine("No servletContext so not looking in WEB-INF for " + fileName);
        } else {
            InputStream in = servletContext.getResourceAsStream("/WEB-INF/" + fileName);
            if (in != null) {
                logger.fine(fileName + " found in WEB-INF");
                return in;
            }
        }
        try {
            File f = new File(fileName);
            if (f.exists()) {
                logger.fine(fileName + " found in file system");
                return new FileInputStream(f);
            }
            InputStream in = PropertyMapLoader.findInClassPath(fileName);
            if (in != null) {
                logger.fine(fileName + " found in classpath");
            }
            return in;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static InputStream findInClassPath(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}

