/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.loader;

import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.loader.FileComponentLoader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class JarFilesComponentLoader
extends FileComponentLoader {
    private final File jarDir;

    public JarFilesComponentLoader(Logger logger, File jarDir, File configDir) {
        super(logger, configDir);
        this.jarDir = jarDir;
    }

    @Override
    public Collection<AbstractComponent> loadComponents() {
        ArrayList<AbstractComponent> components = new ArrayList<AbstractComponent>();
        for (final File file : this.jarDir.listFiles()) {
            ClassLoader loader;
            JarFile jarFile;
            if (!file.getName().endsWith(".jar")) continue;
            try {
                jarFile = new JarFile(file);
                loader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                    @Override
                    public ClassLoader run() {
                        try {
                            return new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
                        }
                        catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            catch (IOException e) {
                continue;
            }
            Enumeration<JarEntry> en = jarFile.entries();
            while (en.hasMoreElements()) {
                JarEntry next = en.nextElement();
                if (!next.getName().endsWith(".class")) continue;
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(this.formatPath(next.getName()), true, loader);
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
        }
        return components;
    }
}

