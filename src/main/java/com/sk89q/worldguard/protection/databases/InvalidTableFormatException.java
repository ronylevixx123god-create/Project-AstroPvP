/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.databases;

import com.sk89q.worldguard.util.FatalConfigurationLoadingException;

public class InvalidTableFormatException
extends FatalConfigurationLoadingException {
    private static final long serialVersionUID = 1L;
    protected String updateFile;

    public InvalidTableFormatException(String updateFile) {
        this.updateFile = updateFile;
    }

    @Override
    public String toString() {
        return "You need to update your database to the latest version.\n\t\tPlease see " + this.updateFile;
    }
}

