/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.resource;

import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class UrlResourceContent
implements ResourceContent {
    String entryName;
    URLConnection con;

    public UrlResourceContent(URL url, String entryName) {
        this.entryName = entryName;
        try {
            this.con = url.openConnection();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(this.getName());
        sb.append("] size[").append(this.size());
        sb.append("] lastModified[").append(new Date(this.lastModified()));
        sb.append("]");
        return sb.toString();
    }

    public String getName() {
        return this.entryName;
    }

    public long lastModified() {
        return this.con.getLastModified();
    }

    public long size() {
        return this.con.getContentLength();
    }

    public InputStream getInputStream() throws IOException {
        return this.con.getInputStream();
    }
}

