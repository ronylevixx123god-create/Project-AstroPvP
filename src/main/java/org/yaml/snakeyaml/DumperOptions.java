/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml;

import java.util.Map;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Tag;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DumperOptions {
    private ScalarStyle defaultStyle = ScalarStyle.PLAIN;
    private FlowStyle defaultFlowStyle = FlowStyle.AUTO;
    private boolean canonical = false;
    private boolean allowUnicode = true;
    private boolean allowReadOnlyProperties = false;
    private int indent = 2;
    private int bestWidth = 80;
    private LineBreak lineBreak = LineBreak.UNIX;
    private boolean explicitStart = false;
    private boolean explicitEnd = false;
    private Tag explicitRoot = null;
    private Version version = null;
    private Map<String, String> tags = null;
    private Boolean prettyFlow = false;

    public boolean isAllowUnicode() {
        return this.allowUnicode;
    }

    public void setAllowUnicode(boolean allowUnicode) {
        this.allowUnicode = allowUnicode;
    }

    public ScalarStyle getDefaultScalarStyle() {
        return this.defaultStyle;
    }

    public void setDefaultScalarStyle(ScalarStyle defaultStyle) {
        if (defaultStyle == null) {
            throw new NullPointerException("Use ScalarStyle enum.");
        }
        this.defaultStyle = defaultStyle;
    }

    public void setIndent(int indent) {
        if (indent < 1) {
            throw new YAMLException("Indent must be at least 1");
        }
        if (indent > 10) {
            throw new YAMLException("Indent must be at most 10");
        }
        this.indent = indent;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setCanonical(boolean canonical) {
        this.canonical = canonical;
    }

    public boolean isCanonical() {
        return this.canonical;
    }

    public void setPrettyFlow(boolean prettyFlow) {
        this.prettyFlow = prettyFlow;
    }

    public boolean isPrettyFlow() {
        return this.prettyFlow;
    }

    public void setWidth(int bestWidth) {
        this.bestWidth = bestWidth;
    }

    public int getWidth() {
        return this.bestWidth;
    }

    public LineBreak getLineBreak() {
        return this.lineBreak;
    }

    public void setDefaultFlowStyle(FlowStyle defaultFlowStyle) {
        if (defaultFlowStyle == null) {
            throw new NullPointerException("Use FlowStyle enum.");
        }
        this.defaultFlowStyle = defaultFlowStyle;
    }

    public FlowStyle getDefaultFlowStyle() {
        return this.defaultFlowStyle;
    }

    public Tag getExplicitRoot() {
        return this.explicitRoot;
    }

    public void setExplicitRoot(String expRoot) {
        this.setExplicitRoot(new Tag(expRoot));
    }

    public void setExplicitRoot(Tag expRoot) {
        if (expRoot == null) {
            throw new NullPointerException("Root tag must be specified.");
        }
        this.explicitRoot = expRoot;
    }

    public void setLineBreak(LineBreak lineBreak) {
        if (lineBreak == null) {
            throw new NullPointerException("Specify line break.");
        }
        this.lineBreak = lineBreak;
    }

    public boolean isExplicitStart() {
        return this.explicitStart;
    }

    public void setExplicitStart(boolean explicitStart) {
        this.explicitStart = explicitStart;
    }

    public boolean isExplicitEnd() {
        return this.explicitEnd;
    }

    public void setExplicitEnd(boolean explicitEnd) {
        this.explicitEnd = explicitEnd;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public ScalarStyle calculateScalarStyle(ScalarAnalysis analysis, ScalarStyle style) {
        return style;
    }

    public boolean isAllowReadOnlyProperties() {
        return this.allowReadOnlyProperties;
    }

    public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties) {
        this.allowReadOnlyProperties = allowReadOnlyProperties;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Version {
        V1_0(new Integer[]{1, 0}),
        V1_1(new Integer[]{1, 1});

        private Integer[] version;

        private Version(Integer[] version) {
            this.version = version;
        }

        public Integer[] getArray() {
            return this.version;
        }

        public String toString() {
            return "Version: " + this.version[0] + "." + this.version[1];
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum LineBreak {
        WIN("\r\n"),
        MAC("\r"),
        UNIX("\n");

        private String lineBreak;

        private LineBreak(String lineBreak) {
            this.lineBreak = lineBreak;
        }

        public String getString() {
            return this.lineBreak;
        }

        public String toString() {
            return "Line break: " + this.name();
        }

        public static LineBreak getPlatformLineBreak() {
            String platformLineBreak = System.getProperty("line.separator");
            for (LineBreak lb : LineBreak.values()) {
                if (!lb.lineBreak.equals(platformLineBreak)) continue;
                return lb;
            }
            return UNIX;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum FlowStyle {
        FLOW(Boolean.TRUE),
        BLOCK(Boolean.FALSE),
        AUTO(null);

        private Boolean styleBoolean;

        private FlowStyle(Boolean flowStyle) {
            this.styleBoolean = flowStyle;
        }

        public Boolean getStyleBoolean() {
            return this.styleBoolean;
        }

        public String toString() {
            return "Flow style: '" + this.styleBoolean + "'";
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum ScalarStyle {
        DOUBLE_QUOTED(new Character('\"')),
        SINGLE_QUOTED(new Character('\'')),
        LITERAL(new Character('|')),
        FOLDED(new Character('>')),
        PLAIN(null);

        private Character styleChar;

        private ScalarStyle(Character style) {
            this.styleChar = style;
        }

        public Character getChar() {
            return this.styleChar;
        }

        public String toString() {
            return "Scalar style: '" + this.styleChar + "'";
        }

        public static ScalarStyle createStyle(Character style) {
            if (style == null) {
                return PLAIN;
            }
            switch (style.charValue()) {
                case '\"': {
                    return DOUBLE_QUOTED;
                }
                case '\'': {
                    return SINGLE_QUOTED;
                }
                case '|': {
                    return LITERAL;
                }
                case '>': {
                    return FOLDED;
                }
            }
            throw new YAMLException("Unknown scalar style character: " + style);
        }
    }
}

