/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.api;

import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.expression.FilterExprPath;

public interface SpiExpressionFactory
extends ExpressionFactory {
    public ExpressionFactory createExpressionFactory(FilterExprPath var1);
}

