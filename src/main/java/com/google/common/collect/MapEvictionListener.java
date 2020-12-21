/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Deprecated
@Beta
public interface MapEvictionListener<K, V> {
    public void onEviction(@Nullable K var1, @Nullable V var2);
}

