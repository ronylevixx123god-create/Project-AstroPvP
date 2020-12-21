/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.session;

import com.sk89q.commandbook.session.PersistentSession;
import com.zachsthings.libcomponents.config.Setting;

public class AdministrativeSession
extends PersistentSession {
    @Setting(value="mute")
    private boolean isMute;
    private boolean isFrozen;

    protected AdministrativeSession() {
        super(ONE_HOUR);
    }

    public boolean isMute() {
        return this.isMute;
    }

    public boolean setMute(boolean isMute) {
        boolean previous = this.isMute;
        this.isMute = isMute;
        return previous;
    }

    @Deprecated
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Deprecated
    public void setFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
}

