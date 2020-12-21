/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.session;

import com.sk89q.commandbook.session.PersistentSession;
import org.bukkit.command.CommandSender;

public interface SessionFactory<T extends PersistentSession> {
    public T createSession(CommandSender var1);
}

