/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import com.avaje.ebeaninternal.server.lib.util.MailMessage;

public class MailEvent {
    Throwable error;
    MailMessage message;

    public MailEvent(MailMessage message, Throwable error) {
        this.message = message;
        this.error = error;
    }

    public MailMessage getMailMessage() {
        return this.message;
    }

    public boolean wasSuccessful() {
        return this.error == null;
    }

    public Throwable getError() {
        return this.error;
    }
}

