package io.apice.clientcomms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.crypto.Data;

public class OCMCMessage implements Serializable {
    private static final long serialVersionUID = -9284506830486503L;

    private String version;

    private String operation;

    private Map<String, Object> data;

    public OCMCMessage(String version, String operation, Map<String, Object> data) {
        this.version = version;
        this.operation = operation;
        this.data = data;
    }

    public OCMCMessage(String operation, Map<String, Object> data) {
        this("TBD", operation, data);
    }

    public String getVersion() {
        return this.version;
    }

    public String getOperation() {
        return this.operation;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public byte[] toBytes() {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            (new ObjectOutputStream(byteStream)).writeObject(this);
            return byteStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static final List<Class<?>> safeClasses = Arrays.asList(new Class[] {
            BitSet.class, ArrayList.class, OCMCMessage.class, Map.class, HashMap.class, String.class, int.class, Integer.class, double.class, Double.class,
            float.class, Float.class, boolean.class, Boolean.class, UUID.class, byte.class, Byte.class });

    public static OCMCMessage fromBytes(byte[] bytes) throws Exception {
        return safeReadObject(Data.class, safeClasses, 20L, 100L, new ByteArrayInputStream(bytes));
    }

    public static OCMCMessage safeReadObject(final Class<?> type, final List<Class<?>> safeClasses, final long maxObjects, final long maxBytes, InputStream in) throws IOException, ClassNotFoundException {
        InputStream lis = new FilterInputStream(in) {
            private long len = 0L;

            public int read() throws IOException {
                int val = super.read();
                if (val != -1) {
                    this.len++;
                    checkLength();
                }
                return val;
            }

            public int read(byte[] b, int off, int len) throws IOException {
                int val = super.read(b, off, len);
                if (val > 0) {
                    len += val;
                    checkLength();
                }
                return val;
            }

            private void checkLength() throws IOException {
                if (this.len > maxBytes)
                    throw new SecurityException("Security violation: attempt to deserialize too many bytes from stream. Limit is " + maxBytes);
            }
        };
        ObjectInputStream ois = new ObjectInputStream(lis) {
            private int objCount = 0;

            boolean b = enableResolveObject(true);

            protected Object resolveObject(Object obj) throws IOException {
                if (this.objCount++ > maxObjects)
                    throw new SecurityException("Security violation: attempt to deserialize too many objects from stream. Limit is " + maxObjects);
                Object object = super.resolveObject(obj);
                return object;
            }

            protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(osc);
                if (clazz
                        .isArray() || clazz
                        .equals(type) || clazz
                        .equals(String.class) || Number.class
                        .isAssignableFrom(clazz) || safeClasses
                        .contains(clazz))
                    return clazz;
                throw new SecurityException("Security violation: attempt to deserialize unauthorized " + clazz);
            }
        };
        return (OCMCMessage)ois.readObject();
    }
}
