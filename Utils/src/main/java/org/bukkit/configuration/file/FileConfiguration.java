/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.configuration.file;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

public abstract class FileConfiguration
extends MemoryConfiguration {
    public FileConfiguration() {
    }

    public FileConfiguration(Configuration defaults) {
        super(defaults);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save(File file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        Files.createParentDirs(file);
        String data = this.saveToString();
        FileWriter writer = new FileWriter(file);
        try {
            writer.write(data);
        }
        finally {
            writer.close();
        }
    }

    public void save(String file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        this.save(new File(file));
    }

    public abstract String saveToString();

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        this.load(new FileInputStream(file));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "Stream cannot be null");
        InputStreamReader reader = new InputStreamReader(stream);
        StringBuilder builder = new StringBuilder();
        BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }
        finally {
            input.close();
        }
        this.loadFromString(builder.toString());
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        this.load(new File(file));
    }

    public abstract void loadFromString(String var1) throws InvalidConfigurationException;

    protected abstract String buildHeader();

    @Override
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }
        return (FileConfigurationOptions)this.options;
    }
}

