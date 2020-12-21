/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class Splitter {
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.NONE, Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }

    public static Splitter on(char separator) {
        return Splitter.on(CharMatcher.is(separator));
    }

    public static Splitter on(final CharMatcher separatorMatcher) {
        Preconditions.checkNotNull(separatorMatcher);
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit){

                    int separatorStart(int start) {
                        return separatorMatcher.indexIn(this.toSplit, start);
                    }

                    int separatorEnd(int separatorPosition) {
                        return separatorPosition + 1;
                    }
                };
            }
        });
    }

    public static Splitter on(final String separator) {
        Preconditions.checkArgument(separator.length() != 0, "The separator may not be the empty string.");
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit){

                    public int separatorStart(int start) {
                        int delimeterLength = separator.length();
                        int last = this.toSplit.length() - delimeterLength;
                        block0: for (int p = start; p <= last; ++p) {
                            for (int i = 0; i < delimeterLength; ++i) {
                                if (this.toSplit.charAt(i + p) != separator.charAt(i)) continue block0;
                            }
                            return p;
                        }
                        return -1;
                    }

                    public int separatorEnd(int separatorPosition) {
                        return separatorPosition + separator.length();
                    }
                };
            }
        });
    }

    @GwtIncompatible(value="java.util.regex")
    public static Splitter on(final Pattern separatorPattern) {
        Preconditions.checkNotNull(separatorPattern);
        Preconditions.checkArgument(!separatorPattern.matcher("").matches(), "The pattern may not match the empty string: %s", separatorPattern);
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
                final Matcher matcher = separatorPattern.matcher(toSplit);
                return new SplittingIterator(splitter, toSplit){

                    public int separatorStart(int start) {
                        return matcher.find(start) ? matcher.start() : -1;
                    }

                    public int separatorEnd(int separatorPosition) {
                        return matcher.end();
                    }
                };
            }
        });
    }

    @GwtIncompatible(value="java.util.regex")
    public static Splitter onPattern(String separatorPattern) {
        return Splitter.on(Pattern.compile(separatorPattern));
    }

    public static Splitter fixedLength(final int length) {
        Preconditions.checkArgument(length > 0, "The length may not be less than 1");
        return new Splitter(new Strategy(){

            public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
                return new SplittingIterator(splitter, toSplit){

                    public int separatorStart(int start) {
                        int nextChunkStart = start + length;
                        return nextChunkStart < this.toSplit.length() ? nextChunkStart : -1;
                    }

                    public int separatorEnd(int separatorPosition) {
                        return separatorPosition;
                    }
                };
            }
        });
    }

    @CheckReturnValue
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    @CheckReturnValue
    public Splitter limit(int limit) {
        Preconditions.checkArgument(limit > 0, "must be greater then zero: %s", limit);
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, limit);
    }

    @CheckReturnValue
    public Splitter trimResults() {
        return this.trimResults(CharMatcher.WHITESPACE);
    }

    @CheckReturnValue
    public Splitter trimResults(CharMatcher trimmer) {
        Preconditions.checkNotNull(trimmer);
        return new Splitter(this.strategy, this.omitEmptyStrings, trimmer, this.limit);
    }

    public Iterable<String> split(final CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return new Iterable<String>(){

            @Override
            public Iterator<String> iterator() {
                return Splitter.this.spliterator(sequence);
            }
        };
    }

    private Iterator<String> spliterator(CharSequence sequence) {
        return this.strategy.iterator(this, sequence);
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(String separator) {
        return this.withKeyValueSeparator(Splitter.on(separator));
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(Splitter keyValueSplitter) {
        return new MapSplitter(this, keyValueSplitter);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static abstract class AbstractIterator<T>
    implements Iterator<T> {
        State state = State.NOT_READY;
        T next;

        private AbstractIterator() {
        }

        protected abstract T computeNext();

        protected final T endOfData() {
            this.state = State.DONE;
            return null;
        }

        @Override
        public final boolean hasNext() {
            Preconditions.checkState(this.state != State.FAILED);
            switch (this.state) {
                case DONE: {
                    return false;
                }
                case READY: {
                    return true;
                }
            }
            return this.tryToComputeNext();
        }

        boolean tryToComputeNext() {
            this.state = State.FAILED;
            this.next = this.computeNext();
            if (this.state != State.DONE) {
                this.state = State.READY;
                return true;
            }
            return false;
        }

        @Override
        public final T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.state = State.NOT_READY;
            return this.next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        static enum State {
            READY,
            NOT_READY,
            DONE,
            FAILED;

        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static abstract class SplittingIterator
    extends AbstractIterator<String> {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset = 0;
        int limit;

        abstract int separatorStart(int var1);

        abstract int separatorEnd(int var1);

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        @Override
        protected String computeNext() {
            while (this.offset != -1) {
                int end;
                int start = this.offset;
                int separatorPosition = this.separatorStart(this.offset);
                if (separatorPosition == -1) {
                    end = this.toSplit.length();
                    this.offset = -1;
                } else {
                    end = separatorPosition;
                    this.offset = this.separatorEnd(separatorPosition);
                }
                while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
                    ++start;
                }
                while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                    --end;
                }
                if (this.omitEmptyStrings && start == end) continue;
                if (this.limit == 1) {
                    this.offset = -1;
                    for (end = this.toSplit.length(); end > start && this.trimmer.matches(this.toSplit.charAt(end - 1)); --end) {
                    }
                } else {
                    --this.limit;
                }
                return ((Object)this.toSplit.subSequence(start, end)).toString();
            }
            return (String)this.endOfData();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static interface Strategy {
        public Iterator<String> iterator(Splitter var1, CharSequence var2);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @Beta
    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter entrySplitter;

        private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
            this.outerSplitter = outerSplitter;
            this.entrySplitter = Preconditions.checkNotNull(entrySplitter);
        }

        public Map<String, String> split(CharSequence sequence) {
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            for (String entry : this.outerSplitter.split(sequence)) {
                Iterator entryFields = this.entrySplitter.spliterator(entry);
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                String key = (String)entryFields.next();
                Preconditions.checkArgument(!map.containsKey(key), "Duplicate key [%s] found.", key);
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                String value = (String)entryFields.next();
                map.put(key, value);
                Preconditions.checkArgument(!entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
            }
            return Collections.unmodifiableMap(map);
        }
    }
}

