/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import java.util.HashSet;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DeployPropertyParser
extends DeployParser {
    private final BeanDescriptor<?> beanDescriptor;
    private final Set<String> includes = new HashSet<String>();

    public DeployPropertyParser(BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
    }

    @Override
    public Set<String> getIncludes() {
        return this.includes;
    }

    @Override
    public String getDeployWord(String expression) {
        ElPropertyDeploy elProp = this.beanDescriptor.getElPropertyDeploy(expression);
        if (elProp == null) {
            return null;
        }
        this.addIncludes(elProp.getElPrefix());
        return elProp.getElPlaceholder(this.encrypted);
    }

    @Override
    public String convertWord() {
        String r = this.getDeployWord(this.word);
        return r == null ? this.word : r;
    }

    private void addIncludes(String prefix) {
        if (prefix != null) {
            this.includes.add(prefix);
        }
    }
}

