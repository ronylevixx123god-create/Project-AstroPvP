/*
 * Decompiled with CFR 0.150.
 */
package javax.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.QueryHint;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface NamedQuery {
    public String name() default "";

    public String query();

    public QueryHint[] hints() default {};
}

