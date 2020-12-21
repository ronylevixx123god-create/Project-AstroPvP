/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.loader.FileComponentLoader;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ClassLoaderComponentLoader
extends FileComponentLoader {
    private final URLClassLoader loader;
    private final File classDir;

    public ClassLoaderComponentLoader(Logger logger, final File classDir, File configDir) {
        super(logger, configDir);
        this.classDir = classDir;
        this.loader = AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>(){

            @Override
            public URLClassLoader run() {
                try {
                    return new URLClassLoader(new URL[]{classDir.toURI().toURL()}, this.getClass().getClassLoader());
                }
                catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public Collection<AbstractComponent> loadComponents() {
        ArrayList<AbstractComponent> components = new ArrayList<AbstractComponent>();
        for (String string : this.getClassNames()) {
            Class<?> clazz = null;
            try {
                clazz = this.loader.loadClass(string);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (!this.isComponentClass(clazz)) continue;
            try {
                components.add(this.instantiateComponent(clazz));
            }
            catch (Throwable t) {
                this.getLogger().warning("Error initializing component " + clazz + ": " + t.getMessage());
                t.printStackTrace();
            }
        }
        return components;
    }

    public Set<String> getClassNames() {
        return this.recursiveGetClasses(this.classDir, "");
    }

    public Set<String> recursiveGetClasses(File dir, String parentName) {
        HashSet<String> classNames = new HashSet<String>();
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                classNames.addAll(this.recursiveGetClasses(file, parentName + file.getName() + "."));
                continue;
            }
            if (!file.getName().endsWith(".class")) continue;
            classNames.add(parentName + this.formatPath(file.getName()));
        }
        return classNames;
    }
}

