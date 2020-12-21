/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.CheckReturnValue;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
@GwtCompatible
public abstract class CharMatcher
implements Predicate<Character> {
    private static final String BREAKING_WHITESPACE_CHARS = "\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000";
    private static final String NON_BREAKING_WHITESPACE_CHARS = "\u00a0\u180e\u202f";
    public static final CharMatcher WHITESPACE = CharMatcher.anyOf("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000\u00a0\u180e\u202f").or(CharMatcher.inRange('\u2000', '\u200a')).precomputed();
    public static final CharMatcher BREAKING_WHITESPACE = CharMatcher.anyOf("\t\n\u000b\f\r \u0085\u1680\u2028\u2029\u205f\u3000").or(CharMatcher.inRange('\u2000', '\u2006')).or(CharMatcher.inRange('\u2008', '\u200a')).precomputed();
    public static final CharMatcher ASCII = CharMatcher.inRange('\u0000', '\u007f');
    public static final CharMatcher DIGIT;
    public static final CharMatcher JAVA_WHITESPACE;
    public static final CharMatcher JAVA_DIGIT;
    public static final CharMatcher JAVA_LETTER;
    public static final CharMatcher JAVA_LETTER_OR_DIGIT;
    public static final CharMatcher JAVA_UPPER_CASE;
    public static final CharMatcher JAVA_LOWER_CASE;
    public static final CharMatcher JAVA_ISO_CONTROL;
    public static final CharMatcher INVISIBLE;
    public static final CharMatcher SINGLE_WIDTH;
    public static final CharMatcher ANY;
    public static final CharMatcher NONE;

    public static CharMatcher is(final char match) {
        return new CharMatcher(){

            public boolean matches(char c) {
                return c == match;
            }

            public String replaceFrom(CharSequence sequence, char replacement) {
                return ((Object)sequence).toString().replace(match, replacement);
            }

            public CharMatcher and(CharMatcher other) {
                return other.matches(match) ? this : NONE;
            }

            public CharMatcher or(CharMatcher other) {
                return other.matches(match) ? other : super.or(other);
            }

            public CharMatcher negate() {
                return 8.isNot(match);
            }

            void setBits(LookupTable table) {
                table.set(match);
            }

            public CharMatcher precomputed() {
                return this;
            }
        };
    }

    public static CharMatcher isNot(final char match) {
        return new CharMatcher(){

            public boolean matches(char c) {
                return c != match;
            }

            public CharMatcher and(CharMatcher other) {
                return other.matches(match) ? super.and(other) : other;
            }

            public CharMatcher or(CharMatcher other) {
                return other.matches(match) ? ANY : this;
            }

            public CharMatcher negate() {
                return 9.is(match);
            }
        };
    }

    public static CharMatcher anyOf(CharSequence sequence) {
        switch (sequence.length()) {
            case 0: {
                return NONE;
            }
            case 1: {
                return CharMatcher.is(sequence.charAt(0));
            }
            case 2: {
                final char match1 = sequence.charAt(0);
                final char match2 = sequence.charAt(1);
                return new CharMatcher(){

                    public boolean matches(char c) {
                        return c == match1 || c == match2;
                    }

                    void setBits(LookupTable table) {
                        table.set(match1);
                        table.set(match2);
                    }

                    public CharMatcher precomputed() {
                        return this;
                    }
                };
            }
        }
        final char[] chars = ((Object)sequence).toString().toCharArray();
        Arrays.sort(chars);
        return new CharMatcher(){

            public boolean matches(char c) {
                return Arrays.binarySearch(chars, c) >= 0;
            }

            void setBits(LookupTable table) {
                for (char c : chars) {
                    table.set(c);
                }
            }
        };
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return CharMatcher.anyOf(sequence).negate();
    }

    public static CharMatcher inRange(final char startInclusive, final char endInclusive) {
        Preconditions.checkArgument(endInclusive >= startInclusive);
        return new CharMatcher(){

            public boolean matches(char c) {
                return startInclusive <= c && c <= endInclusive;
            }

            void setBits(LookupTable table) {
                char c;
                char c2 = startInclusive;
                do {
                    table.set(c2);
                    c = c2;
                    c2 = (char)(c2 + '\u0001');
                } while (c != endInclusive);
            }

            public CharMatcher precomputed() {
                return this;
            }
        };
    }

    public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
        Preconditions.checkNotNull(predicate);
        if (predicate instanceof CharMatcher) {
            return (CharMatcher)predicate;
        }
        return new CharMatcher(){

            public boolean matches(char c) {
                return predicate.apply(Character.valueOf(c));
            }

            public boolean apply(Character character) {
                return predicate.apply(Preconditions.checkNotNull(character));
            }
        };
    }

    public abstract boolean matches(char var1);

    public CharMatcher negate() {
        final CharMatcher original = this;
        return new CharMatcher(){

            public boolean matches(char c) {
                return !original.matches(c);
            }

            public boolean matchesAllOf(CharSequence sequence) {
                return original.matchesNoneOf(sequence);
            }

            public boolean matchesNoneOf(CharSequence sequence) {
                return original.matchesAllOf(sequence);
            }

            public int countIn(CharSequence sequence) {
                return sequence.length() - original.countIn(sequence);
            }

            public CharMatcher negate() {
                return original;
            }
        };
    }

    public CharMatcher and(CharMatcher other) {
        return new And(Arrays.asList(this, Preconditions.checkNotNull(other)));
    }

    public CharMatcher or(CharMatcher other) {
        return new Or(Arrays.asList(this, Preconditions.checkNotNull(other)));
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    CharMatcher precomputedInternal() {
        final LookupTable table = new LookupTable();
        this.setBits(table);
        return new CharMatcher(){

            public boolean matches(char c) {
                return table.get(c);
            }

            public CharMatcher precomputed() {
                return this;
            }
        };
    }

    void setBits(LookupTable table) {
        char c;
        char c2 = '\u0000';
        do {
            if (this.matches(c2)) {
                table.set(c2);
            }
            c = c2;
            c2 = (char)(c2 + '\u0001');
        } while (c != 65535);
    }

    public boolean matchesAnyOf(CharSequence sequence) {
        return !this.matchesNoneOf(sequence);
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; --i) {
            if (this.matches(sequence.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return this.indexIn(sequence) == -1;
    }

    public int indexIn(CharSequence sequence) {
        int length = sequence.length();
        for (int i = 0; i < length; ++i) {
            if (!this.matches(sequence.charAt(i))) continue;
            return i;
        }
        return -1;
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; ++i) {
            if (!this.matches(sequence.charAt(i))) continue;
            return i;
        }
        return -1;
    }

    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; --i) {
            if (!this.matches(sequence.charAt(i))) continue;
            return i;
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); ++i) {
            if (!this.matches(sequence.charAt(i))) continue;
            ++count;
        }
        return count;
    }

    @CheckReturnValue
    public String removeFrom(CharSequence sequence) {
        String string = ((Object)sequence).toString();
        int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        int spread = 1;
        block0: while (true) {
            ++pos;
            while (pos != chars.length) {
                if (!this.matches(chars[pos])) {
                    chars[pos - spread] = chars[pos];
                    ++pos;
                    continue;
                }
                ++spread;
                continue block0;
            }
            break;
        }
        return new String(chars, 0, pos - spread);
    }

    @CheckReturnValue
    public String retainFrom(CharSequence sequence) {
        return this.negate().removeFrom(sequence);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = ((Object)sequence).toString();
        int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; ++i) {
            if (!this.matches(chars[i])) continue;
            chars[i] = replacement;
        }
        return new String(chars);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return this.removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return this.replaceFrom(sequence, replacement.charAt(0));
        }
        String string = ((Object)sequence).toString();
        int pos = this.indexIn(string);
        if (pos == -1) {
            return string;
        }
        int len = string.length();
        StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
        int oldpos = 0;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
        } while ((pos = this.indexIn(string, oldpos = pos + 1)) != -1);
        buf.append(string, oldpos, len);
        return buf.toString();
    }

    @CheckReturnValue
    public String trimFrom(CharSequence sequence) {
        int last;
        int first;
        int len = sequence.length();
        for (first = 0; first < len && this.matches(sequence.charAt(first)); ++first) {
        }
        for (last = len - 1; last > first && this.matches(sequence.charAt(last)); --last) {
        }
        return ((Object)sequence.subSequence(first, last + 1)).toString();
    }

    @CheckReturnValue
    public String trimLeadingFrom(CharSequence sequence) {
        int first;
        int len = sequence.length();
        for (first = 0; first < len && this.matches(sequence.charAt(first)); ++first) {
        }
        return ((Object)sequence.subSequence(first, len)).toString();
    }

    @CheckReturnValue
    public String trimTrailingFrom(CharSequence sequence) {
        int last;
        int len = sequence.length();
        for (last = len - 1; last >= 0 && this.matches(sequence.charAt(last)); --last) {
        }
        return ((Object)sequence.subSequence(0, last + 1)).toString();
    }

    @CheckReturnValue
    public String collapseFrom(CharSequence sequence, char replacement) {
        int first = this.indexIn(sequence);
        if (first == -1) {
            return ((Object)sequence).toString();
        }
        StringBuilder builder = new StringBuilder(sequence.length()).append(sequence.subSequence(0, first)).append(replacement);
        boolean in = true;
        for (int i = first + 1; i < sequence.length(); ++i) {
            char c = sequence.charAt(i);
            if (this.apply(Character.valueOf(c))) {
                if (in) continue;
                builder.append(replacement);
                in = true;
                continue;
            }
            builder.append(c);
            in = false;
        }
        return builder.toString();
    }

    @CheckReturnValue
    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        int first = this.negate().indexIn(sequence);
        if (first == -1) {
            return "";
        }
        StringBuilder builder = new StringBuilder(sequence.length());
        boolean inMatchingGroup = false;
        for (int i = first; i < sequence.length(); ++i) {
            char c = sequence.charAt(i);
            if (this.apply(Character.valueOf(c))) {
                inMatchingGroup = true;
                continue;
            }
            if (inMatchingGroup) {
                builder.append(replacement);
                inMatchingGroup = false;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    @Override
    public boolean apply(Character character) {
        return this.matches(character.charValue());
    }

    static {
        CharMatcher digit = CharMatcher.inRange('0', '9');
        String zeroes = "\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
        for (char base : zeroes.toCharArray()) {
            digit = digit.or(CharMatcher.inRange(base, (char)(base + 9)));
        }
        DIGIT = digit.precomputed();
        JAVA_WHITESPACE = CharMatcher.inRange('\t', '\r').or(CharMatcher.inRange('\u001c', ' ')).or(CharMatcher.is('\u1680')).or(CharMatcher.is('\u180e')).or(CharMatcher.inRange('\u2000', '\u2006')).or(CharMatcher.inRange('\u2008', '\u200b')).or(CharMatcher.inRange('\u2028', '\u2029')).or(CharMatcher.is('\u205f')).or(CharMatcher.is('\u3000')).precomputed();
        JAVA_DIGIT = new CharMatcher(){

            public boolean matches(char c) {
                return Character.isDigit(c);
            }
        };
        JAVA_LETTER = new CharMatcher(){

            public boolean matches(char c) {
                return Character.isLetter(c);
            }
        };
        JAVA_LETTER_OR_DIGIT = new CharMatcher(){

            public boolean matches(char c) {
                return Character.isLetterOrDigit(c);
            }
        };
        JAVA_UPPER_CASE = new CharMatcher(){

            public boolean matches(char c) {
                return Character.isUpperCase(c);
            }
        };
        JAVA_LOWER_CASE = new CharMatcher(){

            public boolean matches(char c) {
                return Character.isLowerCase(c);
            }
        };
        JAVA_ISO_CONTROL = CharMatcher.inRange('\u0000', '\u001f').or(CharMatcher.inRange('\u007f', '\u009f'));
        INVISIBLE = CharMatcher.inRange('\u0000', ' ').or(CharMatcher.inRange('\u007f', '\u00a0')).or(CharMatcher.is('\u00ad')).or(CharMatcher.inRange('\u0600', '\u0603')).or(CharMatcher.anyOf("\u06dd\u070f\u1680\u17b4\u17b5\u180e")).or(CharMatcher.inRange('\u2000', '\u200f')).or(CharMatcher.inRange('\u2028', '\u202f')).or(CharMatcher.inRange('\u205f', '\u2064')).or(CharMatcher.inRange('\u206a', '\u206f')).or(CharMatcher.is('\u3000')).or(CharMatcher.inRange('\ud800', '\uf8ff')).or(CharMatcher.anyOf("\ufeff\ufff9\ufffa\ufffb")).precomputed();
        SINGLE_WIDTH = CharMatcher.inRange('\u0000', '\u04f9').or(CharMatcher.is('\u05be')).or(CharMatcher.inRange('\u05d0', '\u05ea')).or(CharMatcher.is('\u05f3')).or(CharMatcher.is('\u05f4')).or(CharMatcher.inRange('\u0600', '\u06ff')).or(CharMatcher.inRange('\u0750', '\u077f')).or(CharMatcher.inRange('\u0e00', '\u0e7f')).or(CharMatcher.inRange('\u1e00', '\u20af')).or(CharMatcher.inRange('\u2100', '\u213a')).or(CharMatcher.inRange('\ufb50', '\ufdff')).or(CharMatcher.inRange('\ufe70', '\ufeff')).or(CharMatcher.inRange('\uff61', '\uffdc')).precomputed();
        ANY = new CharMatcher(){

            public boolean matches(char c) {
                return true;
            }

            public int indexIn(CharSequence sequence) {
                return sequence.length() == 0 ? -1 : 0;
            }

            public int indexIn(CharSequence sequence, int start) {
                int length = sequence.length();
                Preconditions.checkPositionIndex(start, length);
                return start == length ? -1 : start;
            }

            public int lastIndexIn(CharSequence sequence) {
                return sequence.length() - 1;
            }

            public boolean matchesAllOf(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return true;
            }

            public boolean matchesNoneOf(CharSequence sequence) {
                return sequence.length() == 0;
            }

            public String removeFrom(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return "";
            }

            public String replaceFrom(CharSequence sequence, char replacement) {
                char[] array = new char[sequence.length()];
                Arrays.fill(array, replacement);
                return new String(array);
            }

            public String replaceFrom(CharSequence sequence, CharSequence replacement) {
                StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
                for (int i = 0; i < sequence.length(); ++i) {
                    retval.append(replacement);
                }
                return retval.toString();
            }

            public String collapseFrom(CharSequence sequence, char replacement) {
                return sequence.length() == 0 ? "" : String.valueOf(replacement);
            }

            public String trimFrom(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return "";
            }

            public int countIn(CharSequence sequence) {
                return sequence.length();
            }

            public CharMatcher and(CharMatcher other) {
                return Preconditions.checkNotNull(other);
            }

            public CharMatcher or(CharMatcher other) {
                Preconditions.checkNotNull(other);
                return this;
            }

            public CharMatcher negate() {
                return NONE;
            }

            public CharMatcher precomputed() {
                return this;
            }
        };
        NONE = new CharMatcher(){

            public boolean matches(char c) {
                return false;
            }

            public int indexIn(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return -1;
            }

            public int indexIn(CharSequence sequence, int start) {
                int length = sequence.length();
                Preconditions.checkPositionIndex(start, length);
                return -1;
            }

            public int lastIndexIn(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return -1;
            }

            public boolean matchesAllOf(CharSequence sequence) {
                return sequence.length() == 0;
            }

            public boolean matchesNoneOf(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return true;
            }

            public String removeFrom(CharSequence sequence) {
                return ((Object)sequence).toString();
            }

            public String replaceFrom(CharSequence sequence, char replacement) {
                return ((Object)sequence).toString();
            }

            public String replaceFrom(CharSequence sequence, CharSequence replacement) {
                Preconditions.checkNotNull(replacement);
                return ((Object)sequence).toString();
            }

            public String collapseFrom(CharSequence sequence, char replacement) {
                return ((Object)sequence).toString();
            }

            public String trimFrom(CharSequence sequence) {
                return ((Object)sequence).toString();
            }

            public int countIn(CharSequence sequence) {
                Preconditions.checkNotNull(sequence);
                return 0;
            }

            public CharMatcher and(CharMatcher other) {
                Preconditions.checkNotNull(other);
                return this;
            }

            public CharMatcher or(CharMatcher other) {
                return Preconditions.checkNotNull(other);
            }

            public CharMatcher negate() {
                return ANY;
            }

            void setBits(LookupTable table) {
            }

            public CharMatcher precomputed() {
                return this;
            }
        };
    }

    private static final class LookupTable {
        int[] data = new int[2048];

        private LookupTable() {
        }

        void set(char index) {
            int n = index >> 5;
            this.data[n] = this.data[n] | '\u0001' << index;
        }

        boolean get(char index) {
            return (this.data[index >> 5] & '\u0001' << index) != 0;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Or
    extends CharMatcher {
        List<CharMatcher> components;

        Or(List<CharMatcher> components) {
            this.components = components;
        }

        @Override
        public boolean matches(char c) {
            for (CharMatcher matcher : this.components) {
                if (!matcher.matches(c)) continue;
                return true;
            }
            return false;
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            ArrayList<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
            newComponents.add(Preconditions.checkNotNull(other));
            return new Or(newComponents);
        }

        @Override
        void setBits(LookupTable table) {
            for (CharMatcher matcher : this.components) {
                matcher.setBits(table);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class And
    extends CharMatcher {
        List<CharMatcher> components;

        And(List<CharMatcher> components) {
            this.components = components;
        }

        @Override
        public boolean matches(char c) {
            for (CharMatcher matcher : this.components) {
                if (matcher.matches(c)) continue;
                return false;
            }
            return true;
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            ArrayList<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
            newComponents.add(Preconditions.checkNotNull(other));
            return new And(newComponents);
        }
    }
}

