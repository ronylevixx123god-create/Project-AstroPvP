/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.reader;

import org.yaml.snakeyaml.error.YAMLException;

public class ReaderException
extends YAMLException {
    private static final long serialVersionUID = 8710781187529689083L;
    private String name;
    private char character;
    private int position;

    public ReaderException(String name, int position, char character, String message) {
        super(message);
        this.name = name;
        this.character = character;
        this.position = position;
    }

    public String toString() {
        return "unacceptable character '" + this.character + "' (0x" + Integer.toHexString(this.character).toUpperCase() + ") " + this.getMessage() + "\nin \"" + this.name + "\", position " + this.position;
    }
}

