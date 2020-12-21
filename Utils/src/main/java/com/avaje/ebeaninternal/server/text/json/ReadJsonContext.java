/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonElement;
import com.avaje.ebean.text.json.JsonReadBeanVisitor;
import com.avaje.ebean.text.json.JsonReadOptions;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.text.json.PathStack;
import com.avaje.ebeaninternal.server.text.json.ReadJsonRawReader;
import com.avaje.ebeaninternal.server.text.json.ReadJsonSource;
import com.avaje.ebeaninternal.server.util.ArrayStack;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ReadJsonContext {
    private final ReadJsonSource src;
    private final Map<String, JsonReadBeanVisitor<?>> visitorMap;
    private final JsonValueAdapter valueAdapter;
    private final PathStack pathStack;
    private final ArrayStack<ReadBeanState> beanState;
    private ReadBeanState currentState;
    private char tokenStart;
    private String tokenKey;

    public ReadJsonContext(ReadJsonSource src, JsonValueAdapter dfltValueAdapter, JsonReadOptions options) {
        this.src = src;
        this.beanState = new ArrayStack();
        if (options == null) {
            this.valueAdapter = dfltValueAdapter;
            this.visitorMap = null;
            this.pathStack = null;
        } else {
            this.valueAdapter = this.getValueAdapter(dfltValueAdapter, options.getValueAdapter());
            this.visitorMap = options.getVisitorMap();
            this.pathStack = this.visitorMap == null || this.visitorMap.isEmpty() ? null : new PathStack();
        }
    }

    private JsonValueAdapter getValueAdapter(JsonValueAdapter dfltValueAdapter, JsonValueAdapter valueAdapter) {
        return valueAdapter == null ? dfltValueAdapter : valueAdapter;
    }

    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }

    public char getToken() {
        return this.tokenStart;
    }

    public String getTokenKey() {
        return this.tokenKey;
    }

    public boolean isTokenKey() {
        return '\"' == this.tokenStart;
    }

    public boolean isTokenObjectEnd() {
        return '}' == this.tokenStart;
    }

    public boolean readObjectBegin() {
        this.readNextToken();
        if ('{' == this.tokenStart) {
            return true;
        }
        if ('n' == this.tokenStart) {
            return false;
        }
        if (']' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected object begin at " + this.src.getErrorHelp());
    }

    public boolean readKeyNext() {
        this.readNextToken();
        if ('\"' == this.tokenStart) {
            return true;
        }
        if ('}' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected '\"' or '}' at " + this.src.getErrorHelp());
    }

    public boolean readValueNext() {
        this.readNextToken();
        if (',' == this.tokenStart) {
            return true;
        }
        if ('}' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected ',' or '}' at " + this.src.getErrorHelp() + " but got " + this.tokenStart);
    }

    public boolean readArrayBegin() {
        this.readNextToken();
        if ('[' == this.tokenStart) {
            return true;
        }
        if ('n' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected array begin at " + this.src.getErrorHelp());
    }

    public boolean readArrayNext() {
        this.readNextToken();
        if (',' == this.tokenStart) {
            return true;
        }
        if (']' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected ',' or ']' at " + this.src.getErrorHelp());
    }

    public String readScalarValue() {
        this.ignoreWhiteSpace();
        char prevChar = this.src.nextChar("EOF reading scalarValue?");
        if ('\"' == prevChar) {
            return this.readQuotedValue();
        }
        return this.readUnquotedValue(prevChar);
    }

    public void readNextToken() {
        this.ignoreWhiteSpace();
        this.tokenStart = this.src.nextChar("EOF finding next token");
        switch (this.tokenStart) {
            case '\"': {
                this.internalReadKey();
                break;
            }
            case '{': {
                break;
            }
            case '}': {
                break;
            }
            case '[': {
                break;
            }
            case ']': {
                break;
            }
            case ',': {
                break;
            }
            case ':': {
                break;
            }
            case 'n': {
                this.internalReadNull();
                break;
            }
            default: {
                throw new RuntimeException("Unexpected tokenStart[" + this.tokenStart + "] " + this.src.getErrorHelp());
            }
        }
    }

    protected String readQuotedValue() {
        boolean escape = false;
        StringBuilder sb = new StringBuilder();
        block4: while (true) {
            char ch = this.src.nextChar("EOF reading quoted value");
            if (escape) {
                sb.append(ch);
                continue;
            }
            switch (ch) {
                case '\\': {
                    escape = true;
                    continue block4;
                }
                case '\"': {
                    return sb.toString();
                }
            }
            sb.append(ch);
        }
    }

    protected String readUnquotedValue(char c) {
        String v = this.readUnquotedValueRaw(c);
        if ("null".equals(v)) {
            return null;
        }
        return v;
    }

    private String readUnquotedValueRaw(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        while (true) {
            this.tokenStart = this.src.nextChar("EOF reading unquoted value");
            switch (this.tokenStart) {
                case ',': {
                    this.src.back();
                    return sb.toString();
                }
                case '}': {
                    this.src.back();
                    return sb.toString();
                }
                case ' ': {
                    return sb.toString();
                }
                case '\t': {
                    return sb.toString();
                }
                case '\r': {
                    return sb.toString();
                }
                case '\n': {
                    return sb.toString();
                }
            }
            sb.append(this.tokenStart);
        }
    }

    private void internalReadNull() {
        StringBuilder sb = new StringBuilder(4);
        sb.append(this.tokenStart);
        for (int i = 0; i < 3; ++i) {
            char c = this.src.nextChar("EOF reading null ");
            sb.append(c);
        }
        if (!"null".equals(sb.toString())) {
            throw new TextException("Expected 'null' but got " + sb.toString() + " " + this.src.getErrorHelp());
        }
    }

    private void internalReadKey() {
        char c;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if ('\"' == (c = this.src.nextChar("EOF reading key"))) break;
            sb.append(c);
        }
        this.tokenKey = sb.toString();
        this.ignoreWhiteSpace();
        c = this.src.nextChar("EOF reading ':'");
        if (':' != c) {
            throw new TextException("Expected to find colon after key at " + (this.src.pos() - 1) + " but found [" + c + "]" + this.src.getErrorHelp());
        }
    }

    protected void ignoreWhiteSpace() {
        this.src.ignoreWhiteSpace();
    }

    public void pushBean(Object bean, String path, BeanDescriptor<?> beanDescriptor) {
        this.currentState = new ReadBeanState(bean, beanDescriptor);
        this.beanState.push(this.currentState);
        if (this.pathStack != null) {
            this.pathStack.pushPathKey(path);
        }
    }

    public ReadBeanState popBeanState() {
        if (this.pathStack != null) {
            String path = (String)this.pathStack.peekWithNull();
            JsonReadBeanVisitor<?> beanVisitor = this.visitorMap.get(path);
            if (beanVisitor != null) {
                this.currentState.visit(beanVisitor);
            }
            this.pathStack.pop();
        }
        ReadBeanState s = this.currentState;
        this.beanState.pop();
        this.currentState = this.beanState.peekWithNull();
        return s;
    }

    public void setProperty(String propertyName) {
        this.currentState.setLoaded(propertyName);
    }

    public JsonElement readUnmappedJson(String key) {
        ReadJsonRawReader rawReader = new ReadJsonRawReader(this);
        JsonElement rawJsonValue = rawReader.readUnknownValue();
        if (this.visitorMap != null) {
            this.currentState.addUnmappedJson(key, rawJsonValue);
        }
        return rawJsonValue;
    }

    protected char nextChar() {
        this.tokenStart = this.src.nextChar("EOF getting nextChar for raw json");
        return this.tokenStart;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class ReadBeanState
    implements PropertyChangeListener {
        private final Object bean;
        private final BeanDescriptor<?> beanDescriptor;
        private final EntityBeanIntercept ebi;
        private final Set<String> loadedProps;
        private Map<String, JsonElement> unmapped;

        private ReadBeanState(Object bean, BeanDescriptor<?> beanDescriptor) {
            this.bean = bean;
            this.beanDescriptor = beanDescriptor;
            if (bean instanceof EntityBean) {
                this.ebi = ((EntityBean)bean)._ebean_getIntercept();
                this.loadedProps = new HashSet<String>();
            } else {
                this.ebi = null;
                this.loadedProps = null;
            }
        }

        public String toString() {
            return this.bean.getClass().getSimpleName() + " loaded:" + this.loadedProps;
        }

        public void setLoaded(String propertyName) {
            if (this.ebi != null) {
                this.loadedProps.add(propertyName);
            }
        }

        private void addUnmappedJson(String key, JsonElement value) {
            if (this.unmapped == null) {
                this.unmapped = new LinkedHashMap<String, JsonElement>();
            }
            this.unmapped.put(key, value);
        }

        private <T> void visit(JsonReadBeanVisitor<T> beanVisitor) {
            if (this.ebi != null) {
                this.ebi.addPropertyChangeListener(this);
            }
            beanVisitor.visit(this.bean, this.unmapped);
            if (this.ebi != null) {
                this.ebi.removePropertyChangeListener(this);
            }
        }

        public void setLoadedState() {
            if (this.ebi != null) {
                this.beanDescriptor.setLoadedProps(this.ebi, this.loadedProps);
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propName = evt.getPropertyName();
            this.loadedProps.add(propName);
        }

        public Object getBean() {
            return this.bean;
        }
    }
}

