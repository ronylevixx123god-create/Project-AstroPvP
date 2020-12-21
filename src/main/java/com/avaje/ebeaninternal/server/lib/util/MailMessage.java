/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import com.avaje.ebeaninternal.server.lib.util.MailAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MailMessage {
    ArrayList<String> bodylines;
    MailAddress senderAddress;
    HashMap<String, String> header = new HashMap();
    MailAddress currentRecipient;
    ArrayList<MailAddress> recipientList = new ArrayList();

    public MailMessage() {
        this.bodylines = new ArrayList();
    }

    public void setCurrentRecipient(MailAddress currentRecipient) {
        this.currentRecipient = currentRecipient;
    }

    public MailAddress getCurrentRecipient() {
        return this.currentRecipient;
    }

    public void addRecipient(String alias, String emailAddress) {
        this.recipientList.add(new MailAddress(alias, emailAddress));
    }

    public void setSender(String alias, String senderEmail) {
        this.senderAddress = new MailAddress(alias, senderEmail);
    }

    public MailAddress getSender() {
        return this.senderAddress;
    }

    public Iterator<MailAddress> getRecipientList() {
        return this.recipientList.iterator();
    }

    public void addHeader(String key, String val) {
        this.header.put(key, val);
    }

    public void setSubject(String subject) {
        this.addHeader("Subject", subject);
    }

    public String getSubject() {
        return this.getHeader("Subject");
    }

    public void addBodyLine(String line) {
        this.bodylines.add(line);
    }

    public Iterator<String> getBodyLines() {
        return this.bodylines.iterator();
    }

    public Iterator<String> getHeaderFields() {
        return this.header.keySet().iterator();
    }

    public String getHeader(String key) {
        return this.header.get(key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Sender: " + this.senderAddress + "\tRecipient: " + this.recipientList + "\n");
        for (String key : this.header.keySet()) {
            String hline = key + ": " + this.header.get(key) + "\n";
            sb.append(hline);
        }
        sb.append("\n");
        Iterator<String> e = this.bodylines.iterator();
        while (e.hasNext()) {
            sb.append(e.next()).append("\n");
        }
        return sb.toString();
    }
}

