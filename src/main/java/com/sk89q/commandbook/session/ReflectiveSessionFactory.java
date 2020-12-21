/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.session;

import com.sk89q.commandbook.session.PersistentSession;
import com.sk89q.commandbook.session.SessionFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.command.CommandSender;

public class ReflectiveSessionFactory
implements SessionFactory<PersistentSession> {
    private Constructor<? extends PersistentSession> constructor;

    public ReflectiveSessionFactory(Class<? extends PersistentSession> type) {
        try {
            this.constructor = type.getDeclaredConstructor(new Class[0]);
            this.constructor.setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            this.constructor = null;
        }
    }

    @Override
    public PersistentSession createSession(CommandSender user) {
        if (this.constructor == null) {
            return null;
        }
        try {
            return this.constructor.newInstance(new Object[0]);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e) {
            return null;
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}

