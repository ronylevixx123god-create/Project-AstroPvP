/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import com.avaje.ebeaninternal.server.lib.util.MailAddress;
import com.avaje.ebeaninternal.server.lib.util.MailEvent;
import com.avaje.ebeaninternal.server.lib.util.MailListener;
import com.avaje.ebeaninternal.server.lib.util.MailMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSender
implements Runnable {
    private static final Logger logger = Logger.getLogger(MailSender.class.getName());
    int traceLevel = 0;
    Socket sserver;
    String server;
    BufferedReader in;
    OutputStreamWriter out;
    MailMessage message;
    MailListener listener = null;
    private static final int SMTP_PORT = 25;

    public MailSender(String server) {
        this.server = server;
    }

    public void setMailListener(MailListener listener) {
        this.listener = listener;
    }

    public void run() {
        this.send(this.message);
    }

    public void sendInBackground(MailMessage message) {
        this.message = message;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void send(MailMessage message) {
        try {
            Iterator<MailAddress> i = message.getRecipientList();
            while (i.hasNext()) {
                MailAddress recipientAddress = i.next();
                this.sserver = new Socket(this.server, 25);
                this.send(message, this.sserver, recipientAddress);
                this.sserver.close();
                if (this.listener == null) continue;
                MailEvent event = new MailEvent(message, null);
                this.listener.handleEvent(event);
            }
        }
        catch (Exception ex) {
            if (this.listener != null) {
                MailEvent event = new MailEvent(message, ex);
                this.listener.handleEvent(event);
            }
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void send(MailMessage message, Socket sserver, MailAddress recipientAddress) throws IOException {
        String line;
        InetAddress localhost = sserver.getLocalAddress();
        String localaddress = localhost.getHostAddress();
        MailAddress sender = message.getSender();
        message.setCurrentRecipient(recipientAddress);
        if (message.getHeader("Date") == null) {
            message.addHeader("Date", new Date().toString());
        }
        if (message.getHeader("From") == null) {
            message.addHeader("From", sender.getAlias() + " <" + sender.getEmailAddress() + ">");
        }
        message.addHeader("To", recipientAddress.getAlias() + " <" + recipientAddress.getEmailAddress() + ">");
        this.out = new OutputStreamWriter(sserver.getOutputStream());
        this.in = new BufferedReader(new InputStreamReader(sserver.getInputStream()));
        String sintro = this.readln();
        if (!sintro.startsWith("220")) {
            logger.fine("SmtpSender: intro==" + sintro);
            return;
        }
        this.writeln("EHLO " + localaddress);
        if (!this.expect250()) {
            return;
        }
        this.writeln("MAIL FROM:<" + sender.getEmailAddress() + ">");
        if (!this.expect250()) {
            return;
        }
        this.writeln("RCPT TO:<" + recipientAddress.getEmailAddress() + ">");
        if (!this.expect250()) {
            return;
        }
        this.writeln("DATA");
        while (!(line = this.readln()).startsWith("3")) {
            if (line.startsWith("2")) continue;
            logger.fine("SmtpSender.send reponse to DATA: " + line);
            return;
        }
        Iterator<String> hi = message.getHeaderFields();
        while (hi.hasNext()) {
            String key = hi.next();
            this.writeln(key + ": " + message.getHeader(key));
        }
        this.writeln("");
        Iterator<String> e = message.getBodyLines();
        while (e.hasNext()) {
            String bline = e.next();
            if (bline.startsWith(".")) {
                bline = "." + bline;
            }
            this.writeln(bline);
        }
        this.writeln(".");
        this.expect250();
        this.writeln("QUIT");
    }

    private boolean expect250() throws IOException {
        String line = this.readln();
        if (!line.startsWith("2")) {
            logger.info("SmtpSender.expect250: " + line);
            return false;
        }
        return true;
    }

    private void writeln(String s) throws IOException {
        if (this.traceLevel > 2) {
            logger.fine("From client: " + s);
        }
        this.out.write(s + "\r\n");
        this.out.flush();
    }

    private String readln() throws IOException {
        String line = this.in.readLine();
        if (this.traceLevel > 1) {
            logger.fine("From server: " + line);
        }
        return line;
    }

    public void setTraceLevel(int traceLevel) {
        this.traceLevel = traceLevel;
    }

    public String getLocalHostName() {
        try {
            InetAddress ipaddress = InetAddress.getLocalHost();
            String localHost = ipaddress.getHostName();
            if (localHost == null) {
                return "localhost";
            }
            return localHost;
        }
        catch (UnknownHostException e) {
            return "localhost";
        }
    }
}

