package io.apice.clientcomms.handshake;

import io.apice.clientcomms.ClientComms;

public class Cosmic {
    public static String getVersion() {
        return "2.0.7";
    }

    public static ClientComms getClient() {
        return ClientComms.getInstance();
    }
}

