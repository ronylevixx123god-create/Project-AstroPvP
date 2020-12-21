/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.MethodAdapter;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;
import com.avaje.ebean.enhance.asm.Type;

public class LocalVariablesSorter
extends MethodAdapter {
    private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
    private int[] mapping = new int[40];
    private Object[] newLocals = new Object[20];
    protected final int firstLocal;
    protected int nextLocal;
    private boolean changed;

    public LocalVariablesSorter(int access, String desc, MethodVisitor mv) {
        super(mv);
        Type[] args = Type.getArgumentTypes(desc);
        this.nextLocal = (8 & access) == 0 ? 1 : 0;
        for (int i = 0; i < args.length; ++i) {
            this.nextLocal += args[i].getSize();
        }
        this.firstLocal = this.nextLocal;
    }

    public void visitVarInsn(int opcode, int var) {
        Type type;
        switch (opcode) {
            case 22: 
            case 55: {
                type = Type.LONG_TYPE;
                break;
            }
            case 24: 
            case 57: {
                type = Type.DOUBLE_TYPE;
                break;
            }
            case 23: 
            case 56: {
                type = Type.FLOAT_TYPE;
                break;
            }
            case 21: 
            case 54: {
                type = Type.INT_TYPE;
                break;
            }
            case 25: 
            case 58: {
                type = OBJECT_TYPE;
                break;
            }
            default: {
                type = Type.VOID_TYPE;
            }
        }
        this.mv.visitVarInsn(opcode, this.remap(var, type));
    }

    public void visitIincInsn(int var, int increment) {
        this.mv.visitIincInsn(this.remap(var, Type.INT_TYPE), increment);
    }

    public void visitMaxs(int maxStack, int maxLocals) {
        this.mv.visitMaxs(maxStack, this.nextLocal);
    }

    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        int newIndex = this.remap(index, Type.getType(desc));
        this.mv.visitLocalVariable(name, desc, signature, start, end, newIndex);
    }

    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        int number;
        if (type != -1) {
            throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
        }
        if (!this.changed) {
            this.mv.visitFrame(type, nLocal, local, nStack, stack);
            return;
        }
        Object[] oldLocals = new Object[this.newLocals.length];
        System.arraycopy(this.newLocals, 0, oldLocals, 0, oldLocals.length);
        int index = 0;
        for (number = 0; number < nLocal; ++number) {
            int size;
            Object t = local[number];
            int n = size = t == Opcodes.LONG || t == Opcodes.DOUBLE ? 2 : 1;
            if (t != Opcodes.TOP) {
                this.setFrameLocal(this.remap(index, size), t);
            }
            index += size;
        }
        index = 0;
        number = 0;
        int i = 0;
        while (index < this.newLocals.length) {
            Object t;
            if ((t = this.newLocals[index++]) != null && t != Opcodes.TOP) {
                this.newLocals[i] = t;
                number = i + 1;
                if (t == Opcodes.LONG || t == Opcodes.DOUBLE) {
                    ++index;
                }
            } else {
                this.newLocals[i] = Opcodes.TOP;
            }
            ++i;
        }
        this.mv.visitFrame(type, number, this.newLocals, nStack, stack);
        this.newLocals = oldLocals;
    }

    public int newLocal(Type type) {
        Object t;
        switch (type.getSort()) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                t = Opcodes.INTEGER;
                break;
            }
            case 6: {
                t = Opcodes.FLOAT;
                break;
            }
            case 7: {
                t = Opcodes.LONG;
                break;
            }
            case 8: {
                t = Opcodes.DOUBLE;
                break;
            }
            case 9: {
                t = type.getDescriptor();
                break;
            }
            default: {
                t = type.getInternalName();
            }
        }
        int local = this.nextLocal;
        this.nextLocal += type.getSize();
        this.setLocalType(local, type);
        this.setFrameLocal(local, t);
        return local;
    }

    protected void setLocalType(int local, Type type) {
    }

    private void setFrameLocal(int local, Object type) {
        int l = this.newLocals.length;
        if (local >= l) {
            Object[] a = new Object[Math.max(2 * l, local + 1)];
            System.arraycopy(this.newLocals, 0, a, 0, l);
            this.newLocals = a;
        }
        this.newLocals[local] = type;
    }

    private int remap(int var, Type type) {
        int value;
        int size;
        if (var < this.firstLocal) {
            return var;
        }
        int key = 2 * var + type.getSize() - 1;
        if (key >= (size = this.mapping.length)) {
            int[] newMapping = new int[Math.max(2 * size, key + 1)];
            System.arraycopy(this.mapping, 0, newMapping, 0, size);
            this.mapping = newMapping;
        }
        if ((value = this.mapping[key]) == 0) {
            value = this.newLocalMapping(type);
            this.setLocalType(value, type);
            this.mapping[key] = value + 1;
        } else {
            --value;
        }
        if (value != var) {
            this.changed = true;
        }
        return value;
    }

    protected int newLocalMapping(Type type) {
        int local = this.nextLocal;
        this.nextLocal += type.getSize();
        return local;
    }

    private int remap(int var, int size) {
        int value;
        if (var < this.firstLocal || !this.changed) {
            return var;
        }
        int key = 2 * var + size - 1;
        int n = value = key < this.mapping.length ? this.mapping[key] : 0;
        if (value == 0) {
            throw new IllegalStateException("Unknown local variable " + var);
        }
        return value - 1;
    }
}

