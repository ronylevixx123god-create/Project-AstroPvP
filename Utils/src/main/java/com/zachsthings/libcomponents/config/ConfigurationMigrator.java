/*
 * Decompiled with CFR 0.150.
 */
package com.zachsthings.libcomponents.config;

import com.zachsthings.libcomponents.config.ConfigurationFile;
import java.io.File;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class ConfigurationMigrator {
    protected final ConfigurationFile config;
    protected final File oldFile;

    protected ConfigurationMigrator(File configFile, ConfigurationFile processor) {
        this.oldFile = configFile;
        this.config = processor;
    }

    protected abstract Map<String, String> getMigrationKeys();

    protected abstract boolean shouldMigrate();

    public String migrate() {
        if (!this.shouldMigrate()) {
            return null;
        }
        if (!this.oldFile.renameTo(new File(this.oldFile.getAbsolutePath() + ".old"))) {
            return "Unable to rename backup old configuration file!";
        }
        for (Map.Entry<String, String> entry : this.getMigrationKeys().entrySet()) {
            Object existing = this.config.getProperty(entry.getKey());
            this.config.removeProperty(entry.getKey());
            if (existing == null || entry.getValue() == null) continue;
            this.config.setProperty(entry.getValue().replaceAll("%", entry.getKey()), existing);
        }
        if (!this.config.save()) {
            return "Failed to save migrated configuration!";
        }
        return null;
    }
}

