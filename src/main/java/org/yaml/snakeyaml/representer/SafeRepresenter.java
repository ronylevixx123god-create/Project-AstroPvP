/*
 * Decompiled with CFR 0.150.
 */
package org.yaml.snakeyaml.representer;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class SafeRepresenter
extends BaseRepresenter {
    protected Map<Class<? extends Object>, Tag> classTags;
    public static Pattern BINARY_PATTERN = Pattern.compile("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]");

    public SafeRepresenter() {
        this.nullRepresenter = new RepresentNull();
        this.representers.put(String.class, new RepresentString());
        this.representers.put(Boolean.class, new RepresentBoolean());
        this.representers.put(Character.class, new RepresentString());
        this.representers.put(byte[].class, new RepresentByteArray());
        this.multiRepresenters.put(Number.class, new RepresentNumber());
        this.multiRepresenters.put(List.class, new RepresentList());
        this.multiRepresenters.put(Map.class, new RepresentMap());
        this.multiRepresenters.put(Set.class, new RepresentSet());
        this.multiRepresenters.put(Iterator.class, new RepresentIterator());
        this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray());
        this.multiRepresenters.put(Date.class, new RepresentDate());
        this.multiRepresenters.put(Enum.class, new RepresentEnum());
        this.multiRepresenters.put(Calendar.class, new RepresentDate());
        this.classTags = new HashMap<Class<? extends Object>, Tag>();
    }

    protected Tag getTag(Class<?> clazz, Tag defaultTag) {
        if (this.classTags.containsKey(clazz)) {
            return this.classTags.get(clazz);
        }
        return defaultTag;
    }

    public Tag addClassTag(Class<? extends Object> clazz, String tag) {
        return this.addClassTag(clazz, new Tag(tag));
    }

    public Tag addClassTag(Class<? extends Object> clazz, Tag tag) {
        if (tag == null) {
            throw new NullPointerException("Tag must be provided.");
        }
        return this.classTags.put(clazz, tag);
    }

    protected class RepresentByteArray
    implements Represent {
        protected RepresentByteArray() {
        }

        public Node representData(Object data) {
            char[] binary = Base64Coder.encode((byte[])data);
            return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), Character.valueOf('|'));
        }
    }

    protected class RepresentEnum
    implements Represent {
        protected RepresentEnum() {
        }

        public Node representData(Object data) {
            Tag tag = new Tag(data.getClass());
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum)data).name());
        }
    }

    protected class RepresentDate
    implements Represent {
        protected RepresentDate() {
        }

        public Node representData(Object data) {
            Calendar calendar;
            if (data instanceof Calendar) {
                calendar = (Calendar)data;
            } else {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTime((Date)data);
            }
            int years = calendar.get(1);
            int months = calendar.get(2) + 1;
            int days = calendar.get(5);
            int hour24 = calendar.get(11);
            int minutes = calendar.get(12);
            int seconds = calendar.get(13);
            int millis = calendar.get(14);
            StringBuilder buffer = new StringBuilder(String.valueOf(years));
            while (buffer.length() < 4) {
                buffer.insert(0, "0");
            }
            buffer.append("-");
            if (months < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(months));
            buffer.append("-");
            if (days < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(days));
            buffer.append("T");
            if (hour24 < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(hour24));
            buffer.append(":");
            if (minutes < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(minutes));
            buffer.append(":");
            if (seconds < 10) {
                buffer.append("0");
            }
            buffer.append(String.valueOf(seconds));
            if (millis > 0) {
                if (millis < 10) {
                    buffer.append(".00");
                } else if (millis < 100) {
                    buffer.append(".0");
                } else {
                    buffer.append(".");
                }
                buffer.append(String.valueOf(millis));
            }
            if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
                buffer.append("Z");
            } else {
                int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
                int minutesOffset = gmtOffset / 60000;
                int hoursOffset = minutesOffset / 60;
                int partOfHour = minutesOffset % 60;
                buffer.append((hoursOffset > 0 ? "+" : "") + hoursOffset + ":" + (partOfHour < 10 ? "0" + partOfHour : Integer.valueOf(partOfHour)));
            }
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
        }
    }

    protected class RepresentSet
    implements Represent {
        protected RepresentSet() {
        }

        public Node representData(Object data) {
            LinkedHashMap value = new LinkedHashMap();
            Set set = (Set)data;
            for (Object key : set) {
                value.put(key, null);
            }
            return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, null);
        }
    }

    protected class RepresentMap
    implements Represent {
        protected RepresentMap() {
        }

        public Node representData(Object data) {
            return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map)data, null);
        }
    }

    protected class RepresentArray
    implements Represent {
        protected RepresentArray() {
        }

        public Node representData(Object data) {
            Object[] array = (Object[])data;
            List<Object> list = Arrays.asList(array);
            return SafeRepresenter.this.representSequence(Tag.SEQ, list, null);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class IteratorWrapper
    implements Iterable<Object> {
        private Iterator<Object> iter;

        public IteratorWrapper(Iterator<Object> iter) {
            this.iter = iter;
        }

        @Override
        public Iterator<Object> iterator() {
            return this.iter;
        }
    }

    protected class RepresentIterator
    implements Represent {
        protected RepresentIterator() {
        }

        public Node representData(Object data) {
            Iterator iter = (Iterator)data;
            return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new IteratorWrapper(iter), null);
        }
    }

    protected class RepresentList
    implements Represent {
        protected RepresentList() {
        }

        public Node representData(Object data) {
            return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List)data, null);
        }
    }

    protected class RepresentNumber
    implements Represent {
        protected RepresentNumber() {
        }

        public Node representData(Object data) {
            String value;
            Tag tag;
            if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof BigInteger) {
                tag = Tag.INT;
                value = data.toString();
            } else {
                Number number = (Number)data;
                tag = Tag.FLOAT;
                value = number.equals(Double.NaN) ? ".NaN" : (number.equals(Double.POSITIVE_INFINITY) ? ".inf" : (number.equals(Double.NEGATIVE_INFINITY) ? "-.inf" : number.toString()));
            }
            return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
        }
    }

    protected class RepresentBoolean
    implements Represent {
        protected RepresentBoolean() {
        }

        public Node representData(Object data) {
            String value = Boolean.TRUE.equals(data) ? "true" : "false";
            return SafeRepresenter.this.representScalar(Tag.BOOL, value);
        }
    }

    protected class RepresentString
    implements Represent {
        protected RepresentString() {
        }

        public Node representData(Object data) {
            Tag tag = Tag.STR;
            Character style = null;
            String value = data.toString();
            if (BINARY_PATTERN.matcher(value).find()) {
                tag = Tag.BINARY;
                char[] binary = Base64Coder.encode(value.getBytes());
                value = String.valueOf(binary);
                style = Character.valueOf('|');
            }
            return SafeRepresenter.this.representScalar(tag, value, style);
        }
    }

    protected class RepresentNull
    implements Represent {
        protected RepresentNull() {
        }

        public Node representData(Object data) {
            return SafeRepresenter.this.representScalar(Tag.NULL, "null");
        }
    }
}

