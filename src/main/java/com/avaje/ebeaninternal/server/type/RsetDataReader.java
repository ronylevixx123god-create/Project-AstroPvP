/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebeaninternal.server.type.DataReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class RsetDataReader
implements DataReader {
    private static final int bufferSize = 512;
    static final int clobBufferSize = 512;
    static final int stringInitialSize = 512;
    private final ResultSet rset;
    protected int pos;

    public RsetDataReader(ResultSet rset) {
        this.rset = rset;
    }

    public void close() throws SQLException {
        this.rset.close();
    }

    public boolean next() throws SQLException {
        return this.rset.next();
    }

    public void resetColumnPosition() {
        this.pos = 0;
    }

    public void incrementPos(int increment) {
        this.pos += increment;
    }

    protected int pos() {
        return ++this.pos;
    }

    public Array getArray() throws SQLException {
        return this.rset.getArray(this.pos());
    }

    public InputStream getAsciiStream() throws SQLException {
        return this.rset.getAsciiStream(this.pos());
    }

    public BigDecimal getBigDecimal() throws SQLException {
        return this.rset.getBigDecimal(this.pos());
    }

    public InputStream getBinaryStream() throws SQLException {
        return this.rset.getBinaryStream(this.pos());
    }

    public Boolean getBoolean() throws SQLException {
        boolean v = this.rset.getBoolean(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }

    public Byte getByte() throws SQLException {
        byte v = this.rset.getByte(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }

    public byte[] getBytes() throws SQLException {
        return this.rset.getBytes(this.pos());
    }

    public Date getDate() throws SQLException {
        return this.rset.getDate(this.pos());
    }

    public Double getDouble() throws SQLException {
        double v = this.rset.getDouble(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }

    public Float getFloat() throws SQLException {
        float v = this.rset.getFloat(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return Float.valueOf(v);
    }

    public Integer getInt() throws SQLException {
        int v = this.rset.getInt(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }

    public Long getLong() throws SQLException {
        long v = this.rset.getLong(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }

    public Ref getRef() throws SQLException {
        return this.rset.getRef(this.pos());
    }

    public Short getShort() throws SQLException {
        short s = this.rset.getShort(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return s;
    }

    public String getString() throws SQLException {
        return this.rset.getString(this.pos());
    }

    public Time getTime() throws SQLException {
        return this.rset.getTime(this.pos());
    }

    public Timestamp getTimestamp() throws SQLException {
        return this.rset.getTimestamp(this.pos());
    }

    public String getStringFromStream() throws SQLException {
        Reader reader = this.rset.getCharacterStream(this.pos());
        if (reader == null) {
            return null;
        }
        return this.readStringLob(reader);
    }

    public String getStringClob() throws SQLException {
        Clob clob = this.rset.getClob(this.pos());
        if (clob == null) {
            return null;
        }
        Reader reader = clob.getCharacterStream();
        if (reader == null) {
            return null;
        }
        return this.readStringLob(reader);
    }

    protected String readStringLob(Reader reader) throws SQLException {
        char[] buffer = new char[512];
        int readLength = 0;
        StringBuilder out = new StringBuilder(512);
        try {
            while ((readLength = reader.read(buffer)) != -1) {
                out.append(buffer, 0, readLength);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new SQLException(Message.msg("persist.clob.io", e.getMessage()));
        }
        return out.toString();
    }

    public byte[] getBinaryBytes() throws SQLException {
        InputStream in = this.rset.getBinaryStream(this.pos());
        return this.getBinaryLob(in);
    }

    public byte[] getBlobBytes() throws SQLException {
        Blob blob = this.rset.getBlob(this.pos());
        if (blob == null) {
            return null;
        }
        InputStream in = blob.getBinaryStream();
        return this.getBinaryLob(in);
    }

    protected byte[] getBinaryLob(InputStream in) throws SQLException {
        try {
            int len;
            if (in == null) {
                return null;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[512];
            while ((len = in.read(buf, 0, buf.length)) != -1) {
                out.write(buf, 0, len);
            }
            byte[] data = out.toByteArray();
            if (data.length == 0) {
                data = null;
            }
            in.close();
            out.close();
            return data;
        }
        catch (IOException e) {
            throw new SQLException(e.getClass().getName() + ":" + e.getMessage());
        }
    }
}

