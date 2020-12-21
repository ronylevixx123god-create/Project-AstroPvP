/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lib.util;

import com.avaje.ebeaninternal.server.lib.util.Dnode;
import com.avaje.ebeaninternal.server.lib.util.DnodeParser;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class DnodeReader {
    public Dnode parseXml(String str) {
        try {
            int len;
            ByteArrayOutputStream bao = new ByteArrayOutputStream(str.length());
            OutputStreamWriter osw = new OutputStreamWriter(bao);
            StringReader sr = new StringReader(str);
            int charBufferSize = 1024;
            char[] buf = new char[charBufferSize];
            while ((len = sr.read(buf, 0, buf.length)) != -1) {
                osw.write(buf, 0, len);
            }
            sr.close();
            osw.flush();
            osw.close();
            bao.flush();
            bao.close();
            ByteArrayInputStream is = new ByteArrayInputStream(bao.toByteArray());
            return this.parseXml(is);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Dnode parseXml(InputStream in) {
        try {
            InputSource inSource = new InputSource(in);
            DnodeParser parser = new DnodeParser();
            XMLReader myReader = XMLReaderFactory.createXMLReader();
            myReader.setContentHandler(parser);
            myReader.parse(inSource);
            return parser.getRoot();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

