/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final Type VOID_TYPE = new Type(0);
    public static final Type BOOLEAN_TYPE = new Type(1);
    public static final Type CHAR_TYPE = new Type(2);
    public static final Type BYTE_TYPE = new Type(3);
    public static final Type SHORT_TYPE = new Type(4);
    public static final Type INT_TYPE = new Type(5);
    public static final Type FLOAT_TYPE = new Type(6);
    public static final Type LONG_TYPE = new Type(7);
    public static final Type DOUBLE_TYPE = new Type(8);
    private final int sort;
    private final char[] buf;
    private final int off;
    private final int len;

    private Type(int sort) {
        this(sort, null, 0, 1);
    }

    private Type(int sort, char[] buf, int off, int len) {
        this.sort = sort;
        this.buf = buf;
        this.off = off;
        this.len = len;
    }

    public static Type getType(String typeDescriptor) {
        return Type.getType(typeDescriptor.toCharArray(), 0);
    }

    public static Type getObjectType(String internalName) {
        char[] buf = internalName.toCharArray();
        return new Type(buf[0] == '[' ? 9 : 10, buf, 0, buf.length);
    }

    public static Type getType(Class c) {
        if (c.isPrimitive()) {
            if (c == Integer.TYPE) {
                return INT_TYPE;
            }
            if (c == Void.TYPE) {
                return VOID_TYPE;
            }
            if (c == Boolean.TYPE) {
                return BOOLEAN_TYPE;
            }
            if (c == Byte.TYPE) {
                return BYTE_TYPE;
            }
            if (c == Character.TYPE) {
                return CHAR_TYPE;
            }
            if (c == Short.TYPE) {
                return SHORT_TYPE;
            }
            if (c == Double.TYPE) {
                return DOUBLE_TYPE;
            }
            if (c == Float.TYPE) {
                return FLOAT_TYPE;
            }
            return LONG_TYPE;
        }
        return Type.getType(Type.getDescriptor(c));
    }

    public static Type[] getArgumentTypes(String methodDescriptor) {
        char car;
        char[] buf = methodDescriptor.toCharArray();
        int off = 1;
        int size = 0;
        while ((car = buf[off++]) != ')') {
            if (car == 'L') {
                while (buf[off++] != ';') {
                }
                ++size;
                continue;
            }
            if (car == '[') continue;
            ++size;
        }
        Type[] args = new Type[size];
        off = 1;
        size = 0;
        while (buf[off] != ')') {
            args[size] = Type.getType(buf, off);
            off += args[size].len + (args[size].sort == 10 ? 2 : 0);
            ++size;
        }
        return args;
    }

    public static Type[] getArgumentTypes(Method method) {
        Class<?>[] classes = method.getParameterTypes();
        Type[] types = new Type[classes.length];
        for (int i = classes.length - 1; i >= 0; --i) {
            types[i] = Type.getType(classes[i]);
        }
        return types;
    }

    public static Type getReturnType(String methodDescriptor) {
        char[] buf = methodDescriptor.toCharArray();
        return Type.getType(buf, methodDescriptor.indexOf(41) + 1);
    }

    public static Type getReturnType(Method method) {
        return Type.getType(method.getReturnType());
    }

    private static Type getType(char[] buf, int off) {
        switch (buf[off]) {
            case 'V': {
                return VOID_TYPE;
            }
            case 'Z': {
                return BOOLEAN_TYPE;
            }
            case 'C': {
                return CHAR_TYPE;
            }
            case 'B': {
                return BYTE_TYPE;
            }
            case 'S': {
                return SHORT_TYPE;
            }
            case 'I': {
                return INT_TYPE;
            }
            case 'F': {
                return FLOAT_TYPE;
            }
            case 'J': {
                return LONG_TYPE;
            }
            case 'D': {
                return DOUBLE_TYPE;
            }
            case '[': {
                int len = 1;
                while (buf[off + len] == '[') {
                    ++len;
                }
                if (buf[off + len] == 'L') {
                    ++len;
                    while (buf[off + len] != ';') {
                        ++len;
                    }
                }
                return new Type(9, buf, off, len + 1);
            }
        }
        int len = 1;
        while (buf[off + len] != ';') {
            ++len;
        }
        return new Type(10, buf, off + 1, len - 1);
    }

    public int getSort() {
        return this.sort;
    }

    public int getDimensions() {
        int i = 1;
        while (this.buf[this.off + i] == '[') {
            ++i;
        }
        return i;
    }

    public Type getElementType() {
        return Type.getType(this.buf, this.off + this.getDimensions());
    }

    public String getClassName() {
        switch (this.sort) {
            case 0: {
                return "void";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "char";
            }
            case 3: {
                return "byte";
            }
            case 4: {
                return "short";
            }
            case 5: {
                return "int";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "double";
            }
            case 9: {
                StringBuffer b = new StringBuffer(this.getElementType().getClassName());
                for (int i = this.getDimensions(); i > 0; --i) {
                    b.append("[]");
                }
                return b.toString();
            }
        }
        return new String(this.buf, this.off, this.len).replace('/', '.');
    }

    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }

    public String getDescriptor() {
        StringBuffer buf = new StringBuffer();
        this.getDescriptor(buf);
        return buf.toString();
    }

    public static String getMethodDescriptor(Type returnType, Type[] argumentTypes) {
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < argumentTypes.length; ++i) {
            argumentTypes[i].getDescriptor(buf);
        }
        buf.append(')');
        returnType.getDescriptor(buf);
        return buf.toString();
    }

    private void getDescriptor(StringBuffer buf) {
        switch (this.sort) {
            case 0: {
                buf.append('V');
                return;
            }
            case 1: {
                buf.append('Z');
                return;
            }
            case 2: {
                buf.append('C');
                return;
            }
            case 3: {
                buf.append('B');
                return;
            }
            case 4: {
                buf.append('S');
                return;
            }
            case 5: {
                buf.append('I');
                return;
            }
            case 6: {
                buf.append('F');
                return;
            }
            case 7: {
                buf.append('J');
                return;
            }
            case 8: {
                buf.append('D');
                return;
            }
            case 9: {
                buf.append(this.buf, this.off, this.len);
                return;
            }
        }
        buf.append('L');
        buf.append(this.buf, this.off, this.len);
        buf.append(';');
    }

    public static String getInternalName(Class c) {
        return c.getName().replace('.', '/');
    }

    public static String getDescriptor(Class c) {
        StringBuffer buf = new StringBuffer();
        Type.getDescriptor(buf, c);
        return buf.toString();
    }

    public static String getConstructorDescriptor(Constructor c) {
        Class<?>[] parameters = c.getParameterTypes();
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            Type.getDescriptor(buf, parameters[i]);
        }
        return buf.append(")V").toString();
    }

    public static String getMethodDescriptor(Method m) {
        Class<?>[] parameters = m.getParameterTypes();
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            Type.getDescriptor(buf, parameters[i]);
        }
        buf.append(')');
        Type.getDescriptor(buf, m.getReturnType());
        return buf.toString();
    }

    private static void getDescriptor(StringBuffer buf, Class c) {
        Class<?> d = c;
        while (true) {
            if (d.isPrimitive()) {
                int car = d == Integer.TYPE ? 73 : (d == Void.TYPE ? 86 : (d == Boolean.TYPE ? 90 : (d == Byte.TYPE ? 66 : (d == Character.TYPE ? 67 : (d == Short.TYPE ? 83 : (d == Double.TYPE ? 68 : (d == Float.TYPE ? 70 : 74)))))));
                buf.append((char)car);
                return;
            }
            if (!d.isArray()) break;
            buf.append('[');
            d = d.getComponentType();
        }
        buf.append('L');
        String name = d.getName();
        int len = name.length();
        for (int i = 0; i < len; ++i) {
            char car = name.charAt(i);
            buf.append(car == '.' ? (char)'/' : (char)car);
        }
        buf.append(';');
    }

    public int getSize() {
        return this.sort == 7 || this.sort == 8 ? 2 : 1;
    }

    public int getOpcode(int opcode) {
        if (opcode == 46 || opcode == 79) {
            switch (this.sort) {
                case 1: 
                case 3: {
                    return opcode + 5;
                }
                case 2: {
                    return opcode + 6;
                }
                case 4: {
                    return opcode + 7;
                }
                case 5: {
                    return opcode;
                }
                case 6: {
                    return opcode + 2;
                }
                case 7: {
                    return opcode + 1;
                }
                case 8: {
                    return opcode + 3;
                }
            }
            return opcode + 4;
        }
        switch (this.sort) {
            case 0: {
                return opcode + 5;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                return opcode;
            }
            case 6: {
                return opcode + 2;
            }
            case 7: {
                return opcode + 1;
            }
            case 8: {
                return opcode + 3;
            }
        }
        return opcode + 4;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        Type t = (Type)o;
        if (this.sort != t.sort) {
            return false;
        }
        if (this.sort == 10 || this.sort == 9) {
            if (this.len != t.len) {
                return false;
            }
            int i = this.off;
            int j = t.off;
            int end = i + this.len;
            while (i < end) {
                if (this.buf[i] != t.buf[j]) {
                    return false;
                }
                ++i;
                ++j;
            }
        }
        return true;
    }

    public int hashCode() {
        int hc = 13 * this.sort;
        if (this.sort == 10 || this.sort == 9) {
            int i;
            int end = i + this.len;
            for (i = this.off; i < end; ++i) {
                hc = 17 * (hc + this.buf[i]);
            }
        }
        return hc;
    }

    public String toString() {
        return this.getDescriptor();
    }
}

