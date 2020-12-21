/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.querydefn;

public class SimpleTextParser {
    private final String oql;
    private final char[] chars;
    private final int eof;
    private int pos;
    private String word;
    private String lowerWord;
    private int openParenthesisCount;

    public SimpleTextParser(String oql) {
        this.oql = oql;
        this.chars = oql.toCharArray();
        this.eof = oql.length();
    }

    public String getOql() {
        return this.oql;
    }

    public String getWord() {
        return this.word;
    }

    public String peekNextWord() {
        int origPos = this.pos;
        String nw = this.nextWordInternal();
        this.pos = origPos;
        return nw;
    }

    public boolean isMatch(String lowerMatch, String nextWordMatch) {
        String nw;
        if (this.isMatch(lowerMatch) && (nw = this.peekNextWord()) != null) {
            nw = nw.toLowerCase();
            return nw.equals(nextWordMatch);
        }
        return false;
    }

    public boolean isFinished() {
        return this.word == null;
    }

    public int findWordLower(String lowerMatch, int afterPos) {
        this.pos = afterPos;
        return this.findWordLower(lowerMatch);
    }

    public int findWordLower(String lowerMatch) {
        do {
            if (this.nextWord() != null) continue;
            return -1;
        } while (!lowerMatch.equals(this.lowerWord));
        return this.pos - this.lowerWord.length();
    }

    public boolean isMatch(String lowerMatch) {
        return lowerMatch.equals(this.lowerWord);
    }

    public String nextWord() {
        this.word = this.nextWordInternal();
        if (this.word != null) {
            this.lowerWord = this.word.toLowerCase();
        }
        return this.word;
    }

    private String nextWordInternal() {
        this.trimLeadingWhitespace();
        if (this.pos >= this.eof) {
            return null;
        }
        int start = this.pos;
        if (this.chars[this.pos] == '(') {
            this.moveToClose();
        } else {
            this.moveToEndOfWord();
        }
        return this.oql.substring(start, this.pos);
    }

    private void moveToClose() {
        ++this.pos;
        this.openParenthesisCount = 0;
        while (this.pos < this.eof) {
            char c = this.chars[this.pos];
            if (c == '(') {
                ++this.openParenthesisCount;
            } else if (c == ')') {
                if (this.openParenthesisCount > 0) {
                    --this.openParenthesisCount;
                } else {
                    ++this.pos;
                    return;
                }
            }
            ++this.pos;
        }
    }

    private void moveToEndOfWord() {
        char c = this.chars[this.pos];
        boolean isOperator = this.isOperator(c);
        while (this.pos < this.eof) {
            c = this.chars[this.pos];
            if (this.isWordTerminator(c, isOperator)) {
                return;
            }
            ++this.pos;
        }
    }

    private boolean isWordTerminator(char c, boolean isOperator) {
        if (Character.isWhitespace(c)) {
            return true;
        }
        if (this.isOperator(c)) {
            return !isOperator;
        }
        if (c == '(') {
            return true;
        }
        return isOperator;
    }

    private boolean isOperator(char c) {
        switch (c) {
            case '<': {
                return true;
            }
            case '>': {
                return true;
            }
            case '=': {
                return true;
            }
            case '!': {
                return true;
            }
        }
        return false;
    }

    private void trimLeadingWhitespace() {
        char c;
        while (this.pos < this.eof && Character.isWhitespace(c = this.chars[this.pos])) {
            ++this.pos;
        }
    }
}

