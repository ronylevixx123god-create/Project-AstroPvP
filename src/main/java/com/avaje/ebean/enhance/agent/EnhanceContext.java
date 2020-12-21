/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.agent.ArgParser;
import com.avaje.ebean.enhance.agent.ClassBytesReader;
import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.agent.ClassMetaReader;
import com.avaje.ebean.enhance.agent.IgnoreClassHelper;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnhanceContext {
    private static final Logger logger = Logger.getLogger(EnhanceContext.class.getName());
    private final IgnoreClassHelper ignoreClassHelper;
    private final boolean subclassing;
    private final HashMap<String, String> agentArgsMap;
    private final boolean readOnly;
    private final boolean transientInternalFields;
    private final boolean checkNullManyFields;
    private final ClassMetaReader reader;
    private final ClassBytesReader classBytesReader;
    private PrintStream logout;
    private int logLevel;
    private HashMap<String, ClassMeta> map = new HashMap();

    public EnhanceContext(ClassBytesReader classBytesReader, boolean subclassing, String agentArgs) {
        this.ignoreClassHelper = new IgnoreClassHelper(agentArgs);
        this.subclassing = subclassing;
        this.agentArgsMap = ArgParser.parse(agentArgs);
        this.logout = System.out;
        this.classBytesReader = classBytesReader;
        this.reader = new ClassMetaReader(this);
        String debugValue = this.agentArgsMap.get("debug");
        if (debugValue != null) {
            try {
                this.logLevel = Integer.parseInt(debugValue);
            }
            catch (NumberFormatException e) {
                String msg = "Agent debug argument [" + debugValue + "] is not an int?";
                logger.log(Level.WARNING, msg);
            }
        }
        this.readOnly = this.getPropertyBoolean("readonly", false);
        this.transientInternalFields = this.getPropertyBoolean("transientInternalFields", false);
        this.checkNullManyFields = this.getPropertyBoolean("checkNullManyFields", true);
    }

    public byte[] getClassBytes(String className, ClassLoader classLoader) {
        return this.classBytesReader.getClassBytes(className, classLoader);
    }

    public String getProperty(String key) {
        return this.agentArgsMap.get(key.toLowerCase());
    }

    public boolean getPropertyBoolean(String key, boolean dflt) {
        String s = this.getProperty(key);
        if (s == null) {
            return dflt;
        }
        return s.trim().equalsIgnoreCase("true");
    }

    public boolean isIgnoreClass(String className) {
        return this.ignoreClassHelper.isIgnoreClass(className);
    }

    public void setLogout(PrintStream logout) {
        this.logout = logout;
    }

    public ClassMeta createClassMeta() {
        return new ClassMeta(this, this.subclassing, this.logLevel, this.logout);
    }

    public ClassMeta getSuperMeta(String superClassName, ClassLoader classLoader) {
        try {
            if (this.isIgnoreClass(superClassName)) {
                return null;
            }
            return this.reader.get(false, superClassName, classLoader);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ClassMeta getInterfaceMeta(String interfaceClassName, ClassLoader classLoader) {
        try {
            if (this.isIgnoreClass(interfaceClassName)) {
                return null;
            }
            return this.reader.get(true, interfaceClassName, classLoader);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addClassMeta(ClassMeta meta) {
        this.map.put(meta.getClassName(), meta);
    }

    public ClassMeta get(String className) {
        return this.map.get(className);
    }

    public void log(int level, String msg) {
        if (this.logLevel >= level) {
            this.logout.println(msg);
        }
    }

    public void log(String className, String msg) {
        if (className != null) {
            msg = "cls: " + className + "  msg: " + msg;
        }
        this.logout.println("transform> " + msg);
    }

    public boolean isLog(int level) {
        return this.logLevel >= level;
    }

    public void log(Throwable e) {
        e.printStackTrace(this.logout);
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public boolean isTransientInternalFields() {
        return this.transientInternalFields;
    }

    public boolean isCheckNullManyFields() {
        return this.checkNullManyFields;
    }
}

