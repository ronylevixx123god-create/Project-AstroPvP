/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.resource;

import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
import java.io.IOException;

public interface ResourceSource {
    public String getRealPath();

    public ResourceContent getContent(String var1);

    public String readString(ResourceContent var1, int var2) throws IOException;

    public byte[] readBytes(ResourceContent var1, int var2) throws IOException;
}

