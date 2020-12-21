/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceContent {
    public String getName();

    public long size();

    public long lastModified();

    public InputStream getInputStream() throws IOException;
}

