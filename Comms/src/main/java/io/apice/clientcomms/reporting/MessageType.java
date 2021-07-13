/*
 * Decompiled with CFR 0.150.
 */
package io.apice.clientcomms.reporting;

public enum MessageType {
    REPORT("http://cosmicclient.com/api/report"),
    JOIN("http://cosmicclient.com/api/join"),
    EXIT("http://cosmicclient.com/api/exit");

    private String URL;

    private MessageType(String URL2) {
        this.URL = URL2;
    }

    public String getURL() {
        return this.URL;
    }
}

