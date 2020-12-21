/*
 * Decompiled with CFR 0.150.
 */
package au.com.bytecode.opencsv;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CSVWriter
implements Closeable {
    public static final int INITIAL_STRING_SIZE = 128;
    private Writer rawWriter;
    private PrintWriter pw;
    private char separator;
    private char quotechar;
    private char escapechar;
    private String lineEnd;
    public static final char DEFAULT_ESCAPE_CHARACTER = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE_CHARACTER = '\"';
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    public static final char NO_ESCAPE_CHARACTER = '\u0000';
    public static final String DEFAULT_LINE_END = "\n";

    public CSVWriter(Writer writer) {
        this(writer, ',');
    }

    public CSVWriter(Writer writer, char separator) {
        this(writer, separator, '\"');
    }

    public CSVWriter(Writer writer, char separator, char quotechar) {
        this(writer, separator, quotechar, '\"');
    }

    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar) {
        this(writer, separator, quotechar, escapechar, DEFAULT_LINE_END);
    }

    public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd) {
        this(writer, separator, quotechar, '\"', lineEnd);
    }

    public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
        this.rawWriter = writer;
        this.pw = new PrintWriter(writer);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escapechar = escapechar;
        this.lineEnd = lineEnd;
    }

    public void writeAll(List<String[]> allLines) {
        for (String[] line : allLines) {
            this.writeNext(line);
        }
    }

    protected void writeColumnNames(ResultSetMetaData metadata) throws SQLException {
        int columnCount = metadata.getColumnCount();
        String[] nextLine = new String[columnCount];
        for (int i = 0; i < columnCount; ++i) {
            nextLine[i] = metadata.getColumnName(i + 1);
        }
        this.writeNext(nextLine);
    }

    public void writeAll(ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {
        ResultSetMetaData metadata = rs.getMetaData();
        if (includeColumnNames) {
            this.writeColumnNames(metadata);
        }
        int columnCount = metadata.getColumnCount();
        while (rs.next()) {
            String[] nextLine = new String[columnCount];
            for (int i = 0; i < columnCount; ++i) {
                nextLine[i] = CSVWriter.getColumnValue(rs, metadata.getColumnType(i + 1), i + 1);
            }
            this.writeNext(nextLine);
        }
    }

    private static String getColumnValue(ResultSet rs, int colType, int colIndex) throws SQLException, IOException {
        String value = "";
        switch (colType) {
            case -7: {
                Object bit = rs.getObject(colIndex);
                if (bit == null) break;
                value = String.valueOf(bit);
                break;
            }
            case 16: {
                boolean b = rs.getBoolean(colIndex);
                if (rs.wasNull()) break;
                value = Boolean.valueOf(b).toString();
                break;
            }
            case 2005: {
                Clob c = rs.getClob(colIndex);
                if (c == null) break;
                value = CSVWriter.read(c);
                break;
            }
            case -5: {
                long lv = rs.getLong(colIndex);
                if (rs.wasNull()) break;
                value = Long.toString(lv);
                break;
            }
            case 2: 
            case 3: 
            case 6: 
            case 7: 
            case 8: {
                BigDecimal bd = rs.getBigDecimal(colIndex);
                if (bd == null) break;
                value = bd.toString();
                break;
            }
            case -6: 
            case 4: 
            case 5: {
                int intValue = rs.getInt(colIndex);
                if (rs.wasNull()) break;
                value = Integer.toString(intValue);
                break;
            }
            case 2000: {
                Object obj = rs.getObject(colIndex);
                if (obj == null) break;
                value = String.valueOf(obj);
                break;
            }
            case 91: {
                Date date = rs.getDate(colIndex);
                if (date == null) break;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                value = dateFormat.format(date);
                break;
            }
            case 92: {
                Time t = rs.getTime(colIndex);
                if (t == null) break;
                value = t.toString();
                break;
            }
            case 93: {
                Timestamp tstamp = rs.getTimestamp(colIndex);
                if (tstamp == null) break;
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                value = timeFormat.format(tstamp);
                break;
            }
            case -1: 
            case 1: 
            case 12: {
                value = rs.getString(colIndex);
                break;
            }
            default: {
                value = "";
            }
        }
        if (value == null) {
            value = "";
        }
        return value;
    }

    private static String read(Clob c) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder((int)c.length());
        Reader r = c.getCharacterStream();
        char[] cbuf = new char[2048];
        int n = 0;
        while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
            if (n <= 0) continue;
            sb.append(cbuf, 0, n);
        }
        return sb.toString();
    }

    public void writeNext(String[] nextLine) {
        if (nextLine == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(128);
        for (int i = 0; i < nextLine.length; ++i) {
            String nextElement;
            if (i != 0) {
                sb.append(this.separator);
            }
            if ((nextElement = nextLine[i]) == null) continue;
            if (this.quotechar != '\u0000') {
                sb.append(this.quotechar);
            }
            sb.append(this.stringContainsSpecialCharacters(nextElement) ? this.processLine(nextElement) : nextElement);
            if (this.quotechar == '\u0000') continue;
            sb.append(this.quotechar);
        }
        sb.append(this.lineEnd);
        this.pw.write(sb.toString());
    }

    private boolean stringContainsSpecialCharacters(String line) {
        return line.indexOf(this.quotechar) != -1 || line.indexOf(this.escapechar) != -1;
    }

    private StringBuilder processLine(String nextElement) {
        StringBuilder sb = new StringBuilder(128);
        for (int j = 0; j < nextElement.length(); ++j) {
            char nextChar = nextElement.charAt(j);
            if (this.escapechar != '\u0000' && nextChar == this.quotechar) {
                sb.append(this.escapechar).append(nextChar);
                continue;
            }
            if (this.escapechar != '\u0000' && nextChar == this.escapechar) {
                sb.append(this.escapechar).append(nextChar);
                continue;
            }
            sb.append(nextChar);
        }
        return sb;
    }

    public void flush() throws IOException {
        this.pw.flush();
    }

    @Override
    public void close() throws IOException {
        this.pw.flush();
        this.pw.close();
        this.rawWriter.close();
    }
}

