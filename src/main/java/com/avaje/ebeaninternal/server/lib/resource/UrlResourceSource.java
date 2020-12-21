/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package com.avaje.ebeaninternal.server.lib.resource;

import com.avaje.ebeaninternal.server.lib.resource.AbstractResourceSource;
import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
import com.avaje.ebeaninternal.server.lib.resource.UrlResourceContent;
import com.avaje.ebeaninternal.server.lib.util.GeneralException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;

public class UrlResourceSource
extends AbstractResourceSource
implements ResourceSource {
    ServletContext sc;
    String basePath;
    String realPath;

    public UrlResourceSource(ServletContext sc, String basePath) {
        this.sc = sc;
        this.basePath = basePath == null ? "/" : "/" + basePath + "/";
        this.realPath = sc.getRealPath(basePath);
    }

    public String getRealPath() {
        return this.realPath;
    }

    public ResourceContent getContent(String entry) {
        try {
            URL url = this.sc.getResource(this.basePath + entry);
            if (url != null) {
                return new UrlResourceContent(url, entry);
            }
            return null;
        }
        catch (MalformedURLException ex) {
            throw new GeneralException(ex);
        }
    }
}

