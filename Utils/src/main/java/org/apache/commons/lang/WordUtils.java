/*
 * Decompiled with CFR 0.150.
 */
package org.apache.commons.lang;

import org.apache.commons.lang.SystemUtils;

public class WordUtils {
    public static String wrap(String str, int wrapLength) {
        return WordUtils.wrap(str, wrapLength, null, false);
    }

    public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
        if (str == null) {
            return null;
        }
        if (newLineStr == null) {
            newLineStr = SystemUtils.LINE_SEPARATOR;
        }
        if (wrapLength < 1) {
            wrapLength = 1;
        }
        int inputLineLength = str.length();
        int offset = 0;
        StringBuffer wrappedLine = new StringBuffer(inputLineLength + 32);
        while (inputLineLength - offset > wrapLength) {
            if (str.charAt(offset) == ' ') {
                ++offset;
                continue;
            }
            int spaceToWrapAt = str.lastIndexOf(32, wrapLength + offset);
            if (spaceToWrapAt >= offset) {
                wrappedLine.append(str.substring(offset, spaceToWrapAt));
                wrappedLine.append(newLineStr);
                offset = spaceToWrapAt + 1;
                continue;
            }
            if (wrapLongWords) {
                wrappedLine.append(str.substring(offset, wrapLength + offset));
                wrappedLine.append(newLineStr);
                offset += wrapLength;
                continue;
            }
            spaceToWrapAt = str.indexOf(32, wrapLength + offset);
            if (spaceToWrapAt >= 0) {
                wrappedLine.append(str.substring(offset, spaceToWrapAt));
                wrappedLine.append(newLineStr);
                offset = spaceToWrapAt + 1;
                continue;
            }
            wrappedLine.append(str.substring(offset));
            offset = inputLineLength;
        }
        wrappedLine.append(str.substring(offset));
        return wrappedLine.toString();
    }

    public static String capitalize(String str) {
        return WordUtils.capitalize(str, null);
    }

    public static String capitalize(String str, char[] delimiters) {
        int delimLen;
        int n = delimLen = delimiters == null ? -1 : delimiters.length;
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; ++i) {
            char ch = str.charAt(i);
            if (WordUtils.isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
                continue;
            }
            if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
                continue;
            }
            buffer.append(ch);
        }
        return buffer.toString();
    }

    public static String capitalizeFully(String str) {
        return WordUtils.capitalizeFully(str, null);
    }

    public static String capitalizeFully(String str, char[] delimiters) {
        int delimLen;
        int n = delimLen = delimiters == null ? -1 : delimiters.length;
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        str = str.toLowerCase();
        return WordUtils.capitalize(str, delimiters);
    }

    public static String uncapitalize(String str) {
        return WordUtils.uncapitalize(str, null);
    }

    public static String uncapitalize(String str, char[] delimiters) {
        int delimLen;
        int n = delimLen = delimiters == null ? -1 : delimiters.length;
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean uncapitalizeNext = true;
        for (int i = 0; i < strLen; ++i) {
            char ch = str.charAt(i);
            if (WordUtils.isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                uncapitalizeNext = true;
                continue;
            }
            if (uncapitalizeNext) {
                buffer.append(Character.toLowerCase(ch));
                uncapitalizeNext = false;
                continue;
            }
            buffer.append(ch);
        }
        return buffer.toString();
    }

    public static String swapCase(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        StringBuffer buffer = new StringBuffer(strLen);
        boolean whitespace = true;
        char ch = '\u0000';
        char tmp = '\u0000';
        for (int i = 0; i < strLen; ++i) {
            ch = str.charAt(i);
            tmp = Character.isUpperCase(ch) ? Character.toLowerCase(ch) : (Character.isTitleCase(ch) ? Character.toLowerCase(ch) : (Character.isLowerCase(ch) ? (whitespace ? Character.toTitleCase(ch) : Character.toUpperCase(ch)) : ch));
            buffer.append(tmp);
            whitespace = Character.isWhitespace(ch);
        }
        return buffer.toString();
    }

    public static String initials(String str) {
        return WordUtils.initials(str, null);
    }

    public static String initials(String str, char[] delimiters) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (delimiters != null && delimiters.length == 0) {
            return "";
        }
        int strLen = str.length();
        char[] buf = new char[strLen / 2 + 1];
        int count = 0;
        boolean lastWasGap = true;
        for (int i = 0; i < strLen; ++i) {
            char ch = str.charAt(i);
            if (WordUtils.isDelimiter(ch, delimiters)) {
                lastWasGap = true;
                continue;
            }
            if (!lastWasGap) continue;
            buf[count++] = ch;
            lastWasGap = false;
        }
        return new String(buf, 0, count);
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        int isize = delimiters.length;
        for (int i = 0; i < isize; ++i) {
            if (ch != delimiters[i]) continue;
            return true;
        }
        return false;
    }
}

