/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DetectScala {
    private static final Logger logger = Logger.getLogger(DetectScala.class.getName());
    private static Class<?> scalaOptionClass = DetectScala.initScalaOptionClass();
    private static boolean hasScalaSupport = scalaOptionClass != null;

    private static Class<?> initScalaOptionClass() {
        try {
            return ClassUtil.forName("scala.Option");
        }
        catch (ClassNotFoundException e) {
            logger.fine("Scala type 'scala.Option' not found. Scala Support disabled.");
            return null;
        }
    }

    public static boolean hasScalaSupport() {
        return hasScalaSupport;
    }

    public static Class<?> getScalaOptionClass() {
        return scalaOptionClass;
    }
}

