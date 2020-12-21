/*
 * Decompiled with CFR 0.150.
 */
package au.com.bytecode.opencsv;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CSVReader
implements Closeable {
    private BufferedReader br;
    private boolean hasNext = true;
    private final char separator;
    private final char quotechar;
    private final char escape;
    private int skipLines;
    private boolean linesSkiped;
    public static final char DEFAULT_SEPARATOR = ',';
    public static final int INITIAL_READ_SIZE = 64;
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    public static final int DEFAULT_SKIP_LINES = 0;

    public CSVReader(Reader reader) {
        this(reader, ',');
    }

    public CSVReader(Reader reader, char separator) {
        this(reader, separator, '\"', '\\');
    }

    public CSVReader(Reader reader, char separator, char quotechar) {
        this(reader, separator, quotechar, '\\', 0);
    }

    public CSVReader(Reader reader, char separator, char quotechar, char escape) {
        this(reader, separator, quotechar, escape, 0);
    }

    public CSVReader(Reader reader, char separator, char quotechar, int line) {
        this(reader, separator, quotechar, '\\', line);
    }

    public CSVReader(Reader reader, char separator, char quotechar, char escape, int line) {
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escape = escape;
        this.skipLines = line;
    }

    public List<String[]> readAll() throws IOException {
        ArrayList<String[]> allElements = new ArrayList<String[]>();
        while (this.hasNext) {
            String[] nextLineAsTokens = this.readNext();
            if (nextLineAsTokens == null) continue;
            allElements.add(nextLineAsTokens);
        }
        return allElements;
    }

    public String[] readNext() throws IOException {
        String nextLine = this.getNextLine();
        return this.hasNext ? this.parseLine(nextLine) : null;
    }

    private String getNextLine() throws IOException {
        String nextLine;
        if (!this.linesSkiped) {
            for (int i = 0; i < this.skipLines; ++i) {
                this.br.readLine();
            }
            this.linesSkiped = true;
        }
        if ((nextLine = this.br.readLine()) == null) {
            this.hasNext = false;
        }
        return this.hasNext ? nextLine : null;
    }

    private String[] parseLine(String nextLine) throws IOException {
        if (nextLine == null) {
            return null;
        }
        ArrayList<String> tokensOnThisLine = new ArrayList<String>();
        StringBuilder sb = new StringBuilder(64);
        boolean inQuotes = false;
        do {
            if (inQuotes) {
                sb.append("\n");
                nextLine = this.getNextLine();
                if (nextLine == null) break;
            }
            for (int i = 0; i < nextLine.length(); ++i) {
                char c = nextLine.charAt(i);
                if (c == this.escape) {
                    if (this.isEscapable(nextLine, inQuotes, i)) {
                        sb.append(nextLine.charAt(i + 1));
                        ++i;
                        continue;
                    }
                    ++i;
                    continue;
                }
                if (c == this.quotechar) {
                    if (this.isEscapedQuote(nextLine, inQuotes, i)) {
                        sb.append(nextLine.charAt(i + 1));
                        ++i;
                        continue;
                    }
                    boolean bl = inQuotes = !inQuotes;
                    if (i <= 2 || nextLine.charAt(i - 1) == this.separator || nextLine.length() <= i + 1 || nextLine.charAt(i + 1) == this.separator) continue;
                    sb.append(c);
                    continue;
                }
                if (c == this.separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuilder(64);
                    continue;
                }
                sb.append(c);
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString());
        return tokensOnThisLine.toArray(new String[0]);
    }

    private boolean isEscapedQuote(String nextLine, boolean inQuotes, int i) {
        return inQuotes && nextLine.length() > i + 1 && nextLine.charAt(i + 1) == this.quotechar;
    }

    private boolean isEscapable(String nextLine, boolean inQuotes, int i) {
        return inQuotes && nextLine.length() > i + 1 && (nextLine.charAt(i + 1) == this.quotechar || nextLine.charAt(i + 1) == this.escape);
    }

    @Override
    public void close() throws IOException {
        this.br.close();
    }
}

