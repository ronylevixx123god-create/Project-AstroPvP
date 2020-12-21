/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class DeployParser {
    protected static final char SINGLE_QUOTE = '\'';
    protected static final char COLON = ':';
    protected static final char UNDERSCORE = '_';
    protected static final char PERIOD = '.';
    protected boolean encrypted;
    protected String source;
    protected StringBuilder sb;
    protected int sourceLength;
    protected int pos;
    protected String word;
    protected char wordTerminator;

    protected abstract String convertWord();

    public abstract String getDeployWord(String var1);

    public abstract Set<String> getIncludes();

    public void setEncrypted(boolean encrytped) {
        this.encrypted = encrytped;
    }

    public String parse(String source) {
        if (source == null) {
            return source;
        }
        this.pos = -1;
        this.source = source;
        this.sourceLength = source.length();
        this.sb = new StringBuilder(source.length() + 20);
        while (this.nextWord()) {
            String deployWord = this.convertWord();
            this.sb.append(deployWord);
            if (this.pos >= this.sourceLength) continue;
            this.sb.append(this.wordTerminator);
        }
        return this.sb.toString();
    }

    private boolean nextWord() {
        if (!this.findWordStart()) {
            return false;
        }
        StringBuilder wordBuffer = new StringBuilder();
        wordBuffer.append(this.source.charAt(this.pos));
        while (++this.pos < this.sourceLength) {
            char ch = this.source.charAt(this.pos);
            if (this.isWordPart(ch)) {
                wordBuffer.append(ch);
                continue;
            }
            this.wordTerminator = ch;
            break;
        }
        this.word = wordBuffer.toString();
        return true;
    }

    private boolean findWordStart() {
        while (++this.pos < this.sourceLength) {
            char ch = this.source.charAt(this.pos);
            if (ch == '\'') {
                this.sb.append(ch);
                this.readLiteral();
                continue;
            }
            if (ch == ':') {
                this.sb.append(ch);
                this.readNamedParameter();
                continue;
            }
            if (this.isWordStart(ch)) {
                return true;
            }
            this.sb.append(ch);
        }
        return false;
    }

    private void readLiteral() {
        while (++this.pos < this.sourceLength) {
            char ch = this.source.charAt(this.pos);
            this.sb.append(ch);
            if (ch != '\'') continue;
            break;
        }
    }

    private void readNamedParameter() {
        while (++this.pos < this.sourceLength) {
            char ch = this.source.charAt(this.pos);
            this.sb.append(ch);
            if (!Character.isWhitespace(ch) && ch != ',') continue;
            break;
        }
    }

    private boolean isWordPart(char ch) {
        if (Character.isLetterOrDigit(ch)) {
            return true;
        }
        if (ch == '_') {
            return true;
        }
        return ch == '.';
    }

    private boolean isWordStart(char ch) {
        return Character.isLetter(ch);
    }
}

