/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=2, d1={"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a\u00a2\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"0\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018GX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018\u00c7\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001c"}, d2={"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"})
@JvmName(name="JvmClassMappingKt")
public final class JvmClassMappingKt {
    public static /* synthetic */ void java$annotations(KClass kClass) {
    }

    @JvmName(name="getJavaClass")
    @NotNull
    public static final <T> Class<T> getJavaClass(@NotNull KClass<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<?> class_ = ((ClassBasedDeclarationContainer)((Object)$receiver)).getJClass();
        if (class_ == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        return class_;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Nullable
    public static final <T> Class<T> getJavaPrimitiveType(@NotNull KClass<T> $receiver) {
        block12: {
            Class<Object> class_;
            block14: {
                block17: {
                    block16: {
                        block13: {
                            block20: {
                                block18: {
                                    block19: {
                                        block15: {
                                            String string;
                                            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                                            Class<?> thisJClass = ((ClassBasedDeclarationContainer)((Object)$receiver)).getJClass();
                                            if (thisJClass.isPrimitive()) {
                                                Class<?> class_2 = thisJClass;
                                                if (class_2 != null) return class_2;
                                                throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
                                            }
                                            String string2 = string = thisJClass.getName();
                                            if (string2 == null) break block12;
                                            switch (string2.hashCode()) {
                                                case -527879800: {
                                                    if (!string.equals("java.lang.Float")) return null;
                                                    break block13;
                                                }
                                                case 399092968: {
                                                    if (!string.equals("java.lang.Void")) return null;
                                                    break block14;
                                                }
                                                case 155276373: {
                                                    if (!string.equals("java.lang.Character")) return null;
                                                    break block15;
                                                }
                                                case 398795216: {
                                                    if (!string.equals("java.lang.Long")) return null;
                                                    break block16;
                                                }
                                                case 761287205: {
                                                    if (!string.equals("java.lang.Double")) return null;
                                                    break block17;
                                                }
                                                case -515992664: {
                                                    if (!string.equals("java.lang.Short")) return null;
                                                    break block18;
                                                }
                                                case 344809556: {
                                                    if (!string.equals("java.lang.Boolean")) return null;
                                                    break;
                                                }
                                                case 398507100: {
                                                    if (!string.equals("java.lang.Byte")) return null;
                                                    break block19;
                                                }
                                                case -2056817302: {
                                                    if (!string.equals("java.lang.Integer")) return null;
                                                    break block20;
                                                }
                                            }
                                            class_ = Boolean.TYPE;
                                            return class_;
                                        }
                                        class_ = Character.TYPE;
                                        return class_;
                                    }
                                    class_ = Byte.TYPE;
                                    return class_;
                                }
                                class_ = Short.TYPE;
                                return class_;
                            }
                            class_ = Integer.TYPE;
                            return class_;
                        }
                        class_ = Float.TYPE;
                        return class_;
                    }
                    class_ = Long.TYPE;
                    return class_;
                }
                class_ = Double.TYPE;
                return class_;
            }
            class_ = Void.TYPE;
            return class_;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @NotNull
    public static final <T> Class<T> getJavaObjectType(@NotNull KClass<T> $receiver) {
        String string;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<?> thisJClass = ((ClassBasedDeclarationContainer)((Object)$receiver)).getJClass();
        if (!thisJClass.isPrimitive()) {
            Class<?> class_ = thisJClass;
            if (class_ != null) return class_;
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        String string2 = string = thisJClass.getName();
        if (string2 != null) {
            switch (string2.hashCode()) {
                case 64711720: {
                    if (!string.equals("boolean")) break;
                    return Boolean.class;
                }
                case 3625364: {
                    if (!string.equals("void")) break;
                    return Void.class;
                }
                case 3039496: {
                    if (!string.equals("byte")) break;
                    return Byte.class;
                }
                case -1325958191: {
                    if (!string.equals("double")) break;
                    return Double.class;
                }
                case 3052374: {
                    if (!string.equals("char")) break;
                    return Character.class;
                }
                case 109413500: {
                    if (!string.equals("short")) break;
                    return Short.class;
                }
                case 97526364: {
                    if (!string.equals("float")) break;
                    return Float.class;
                }
                case 104431: {
                    if (!string.equals("int")) break;
                    return Integer.class;
                }
                case 3327612: {
                    if (!string.equals("long")) break;
                    return Long.class;
                }
            }
        }
        Class<Object> class_ = thisJClass;
        if (class_ != null) return class_;
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
    }

    @JvmName(name="getKotlinClass")
    @NotNull
    public static final <T> KClass<T> getKotlinClass(@NotNull Class<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Reflection.getOrCreateKotlinClass($receiver);
    }

    @NotNull
    public static final <T> Class<T> getJavaClass(@NotNull T $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<?> class_ = $receiver.getClass();
        if (class_ == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        return class_;
    }

    @Deprecated(message="Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith=@ReplaceWith(imports={}, expression="(this as Any).javaClass"), level=DeprecationLevel.ERROR)
    public static /* synthetic */ void javaClass$annotations(KClass kClass) {
    }

    @JvmName(name="getRuntimeClassOfKClassInstance")
    @NotNull
    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(@NotNull KClass<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<KClass<T>> class_ = ((Object)$receiver).getClass();
        if (class_ == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
        }
        return class_;
    }

    private static final <T> boolean isArrayOf(@NotNull Object[] $receiver) {
        Intrinsics.reifiedOperationMarker(4, "T");
        return Object.class.isAssignableFrom($receiver.getClass().getComponentType());
    }

    @NotNull
    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(@NotNull T $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Class<? extends Annotation> class_ = $receiver.annotationType();
        Intrinsics.checkExpressionValueIsNotNull(class_, "(this as java.lang.annot\u2026otation).annotationType()");
        KClass<? extends Annotation> kClass = JvmClassMappingKt.getKotlinClass(class_);
        if (kClass == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
        }
        return kClass;
    }
}

