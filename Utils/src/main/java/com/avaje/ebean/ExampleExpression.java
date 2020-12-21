/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import com.avaje.ebean.Expression;

public interface ExampleExpression
extends Expression {
    public ExampleExpression includeZeros();

    public ExampleExpression caseInsensitive();

    public ExampleExpression useStartsWith();

    public ExampleExpression useContains();

    public ExampleExpression useEndsWith();

    public ExampleExpression useEqualTo();
}

