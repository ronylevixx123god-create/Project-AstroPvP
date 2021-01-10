/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CompilexPermissions.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadUtils {
    public static InputStream downloadInputStream(URL fromURL) throws IOException {
        return fromURL.openConnection().getInputStream();
    }
}

