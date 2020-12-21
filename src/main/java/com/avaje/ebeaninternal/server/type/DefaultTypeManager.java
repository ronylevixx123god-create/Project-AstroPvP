/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joda.time.DateMidnight
 *  org.joda.time.DateTime
 *  org.joda.time.LocalDate
 *  org.joda.time.LocalDateTime
 *  org.joda.time.LocalTime
 */
package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.annotation.EnumMapping;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.CompoundTypeProperty;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import com.avaje.ebeaninternal.server.type.CtCompoundType;
import com.avaje.ebeaninternal.server.type.DefaultTypeFactory;
import com.avaje.ebeaninternal.server.type.EnumToDbValueMap;
import com.avaje.ebeaninternal.server.type.ScalarDataReader;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.ScalarTypeBigDecimal;
import com.avaje.ebeaninternal.server.type.ScalarTypeByte;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBinary;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesBlob;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesLongVarbinary;
import com.avaje.ebeaninternal.server.type.ScalarTypeBytesVarbinary;
import com.avaje.ebeaninternal.server.type.ScalarTypeChar;
import com.avaje.ebeaninternal.server.type.ScalarTypeCharArray;
import com.avaje.ebeaninternal.server.type.ScalarTypeClass;
import com.avaje.ebeaninternal.server.type.ScalarTypeClob;
import com.avaje.ebeaninternal.server.type.ScalarTypeCurrency;
import com.avaje.ebeaninternal.server.type.ScalarTypeDate;
import com.avaje.ebeaninternal.server.type.ScalarTypeDouble;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnumWithMapping;
import com.avaje.ebeaninternal.server.type.ScalarTypeFloat;
import com.avaje.ebeaninternal.server.type.ScalarTypeInteger;
import com.avaje.ebeaninternal.server.type.ScalarTypeJodaDateMidnight;
import com.avaje.ebeaninternal.server.type.ScalarTypeJodaDateTime;
import com.avaje.ebeaninternal.server.type.ScalarTypeJodaLocalDate;
import com.avaje.ebeaninternal.server.type.ScalarTypeJodaLocalDateTime;
import com.avaje.ebeaninternal.server.type.ScalarTypeJodaLocalTime;
import com.avaje.ebeaninternal.server.type.ScalarTypeLocale;
import com.avaje.ebeaninternal.server.type.ScalarTypeLong;
import com.avaje.ebeaninternal.server.type.ScalarTypeLongToTimestamp;
import com.avaje.ebeaninternal.server.type.ScalarTypeLongVarchar;
import com.avaje.ebeaninternal.server.type.ScalarTypeShort;
import com.avaje.ebeaninternal.server.type.ScalarTypeString;
import com.avaje.ebeaninternal.server.type.ScalarTypeTime;
import com.avaje.ebeaninternal.server.type.ScalarTypeTimeZone;
import com.avaje.ebeaninternal.server.type.ScalarTypeTimestamp;
import com.avaje.ebeaninternal.server.type.ScalarTypeURI;
import com.avaje.ebeaninternal.server.type.ScalarTypeURL;
import com.avaje.ebeaninternal.server.type.ScalarTypeUUID;
import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebeaninternal.server.type.TypeReflectHelper;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutable;
import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
import com.avaje.ebeaninternal.server.type.reflect.ImmutableMeta;
import com.avaje.ebeaninternal.server.type.reflect.ImmutableMetaFactory;
import com.avaje.ebeaninternal.server.type.reflect.KnownImmutable;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundType;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundTypeProperty;
import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedTypeBuilder;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DefaultTypeManager
implements TypeManager,
KnownImmutable {
    private static final Logger logger = Logger.getLogger(DefaultTypeManager.class.getName());
    private final ConcurrentHashMap<Class<?>, CtCompoundType<?>> compoundTypeMap;
    private final ConcurrentHashMap<Class<?>, ScalarType<?>> typeMap;
    private final ConcurrentHashMap<Integer, ScalarType<?>> nativeMap;
    private final DefaultTypeFactory extraTypeFactory;
    private final ScalarType<?> charType = new ScalarTypeChar();
    private final ScalarType<?> charArrayType = new ScalarTypeCharArray();
    private final ScalarType<?> longVarcharType = new ScalarTypeLongVarchar();
    private final ScalarType<?> clobType = new ScalarTypeClob();
    private final ScalarType<?> byteType = new ScalarTypeByte();
    private final ScalarType<?> binaryType = new ScalarTypeBytesBinary();
    private final ScalarType<?> blobType = new ScalarTypeBytesBlob();
    private final ScalarType<?> varbinaryType = new ScalarTypeBytesVarbinary();
    private final ScalarType<?> longVarbinaryType = new ScalarTypeBytesLongVarbinary();
    private final ScalarType<?> shortType = new ScalarTypeShort();
    private final ScalarType<?> integerType = new ScalarTypeInteger();
    private final ScalarType<?> longType = new ScalarTypeLong();
    private final ScalarType<?> doubleType = new ScalarTypeDouble();
    private final ScalarType<?> floatType = new ScalarTypeFloat();
    private final ScalarType<?> bigDecimalType = new ScalarTypeBigDecimal();
    private final ScalarType<?> timeType = new ScalarTypeTime();
    private final ScalarType<?> dateType = new ScalarTypeDate();
    private final ScalarType<?> timestampType = new ScalarTypeTimestamp();
    private final ScalarType<?> uuidType = new ScalarTypeUUID();
    private final ScalarType<?> urlType = new ScalarTypeURL();
    private final ScalarType<?> uriType = new ScalarTypeURI();
    private final ScalarType<?> localeType = new ScalarTypeLocale();
    private final ScalarType<?> currencyType = new ScalarTypeCurrency();
    private final ScalarType<?> timeZoneType = new ScalarTypeTimeZone();
    private final ScalarType<?> stringType = new ScalarTypeString();
    private final ScalarType<?> classType = new ScalarTypeClass();
    private final ScalarTypeLongToTimestamp longToTimestamp = new ScalarTypeLongToTimestamp();
    private final List<ScalarType<?>> customScalarTypes = new ArrayList();
    private final CheckImmutable checkImmutable;
    private final ImmutableMetaFactory immutableMetaFactory = new ImmutableMetaFactory();
    private final ReflectionBasedTypeBuilder reflectScalarBuilder;

    public DefaultTypeManager(ServerConfig config, BootupClasses bootupClasses) {
        int clobType = config == null ? 2005 : config.getDatabasePlatform().getClobDbType();
        int blobType = config == null ? 2004 : config.getDatabasePlatform().getBlobDbType();
        this.checkImmutable = new CheckImmutable(this);
        this.reflectScalarBuilder = new ReflectionBasedTypeBuilder(this);
        this.compoundTypeMap = new ConcurrentHashMap();
        this.typeMap = new ConcurrentHashMap();
        this.nativeMap = new ConcurrentHashMap();
        this.extraTypeFactory = new DefaultTypeFactory(config);
        this.initialiseStandard(clobType, blobType);
        this.initialiseJodaTypes();
        if (bootupClasses != null) {
            this.initialiseCustomScalarTypes(bootupClasses);
            this.initialiseScalarConverters(bootupClasses);
            this.initialiseCompoundTypes(bootupClasses);
        }
    }

    @Override
    public boolean isKnownImmutable(Class<?> cls) {
        if (cls == null) {
            return true;
        }
        if (cls.isPrimitive() || Object.class.equals(cls)) {
            return true;
        }
        ScalarDataReader<?> scalarDataReader = this.getScalarDataReader(cls);
        return scalarDataReader != null;
    }

    @Override
    public CheckImmutableResponse checkImmutable(Class<?> cls) {
        return this.checkImmutable.checkImmutable(cls);
    }

    private ScalarType<?> register(ScalarType<?> st) {
        this.add(st);
        logger.info("Registering ScalarType for " + st.getType() + " implemented using reflection");
        return st;
    }

    @Override
    public ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> cls) {
        ScalarDataReader<?> scalarReader = this.getScalarDataReader(cls);
        if (scalarReader != null) {
            return scalarReader;
        }
        ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
        if (!meta.isCompoundType()) {
            return this.register(this.reflectScalarBuilder.buildScalarType(meta));
        }
        ReflectionBasedCompoundType compoundType = this.reflectScalarBuilder.buildCompound(meta);
        Class<?> compoundTypeClass = compoundType.getCompoundType();
        return this.createCompoundScalarDataReader(compoundTypeClass, compoundType, " using reflection");
    }

    @Override
    public ScalarType<?> recursiveCreateScalarTypes(Class<?> cls) {
        ScalarType<?> scalarType = this.getScalarType(cls);
        if (scalarType != null) {
            return scalarType;
        }
        ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
        if (!meta.isCompoundType()) {
            return this.register(this.reflectScalarBuilder.buildScalarType(meta));
        }
        throw new RuntimeException("Not allowed compound types here");
    }

    @Override
    public void add(ScalarType<?> scalarType) {
        this.typeMap.put(scalarType.getType(), scalarType);
        this.logAdd(scalarType);
    }

    protected void logAdd(ScalarType<?> scalarType) {
        if (logger.isLoggable(Level.FINE)) {
            String msg = "ScalarType register [" + scalarType.getClass().getName() + "]";
            msg = msg + " for [" + scalarType.getType().getName() + "]";
            logger.fine(msg);
        }
    }

    @Override
    public CtCompoundType<?> getCompoundType(Class<?> type) {
        return this.compoundTypeMap.get(type);
    }

    @Override
    public ScalarType<?> getScalarType(int jdbcType) {
        return this.nativeMap.get(jdbcType);
    }

    @Override
    public <T> ScalarType<T> getScalarType(Class<T> type) {
        return this.typeMap.get(type);
    }

    public ScalarDataReader<?> getScalarDataReader(Class<?> propertyType, int sqlType) {
        if (sqlType == 0) {
            return this.recursiveCreateScalarDataReader(propertyType);
        }
        for (int i = 0; i < this.customScalarTypes.size(); ++i) {
            ScalarType<?> customScalarType = this.customScalarTypes.get(i);
            if (sqlType != customScalarType.getJdbcType() || !propertyType.equals(customScalarType.getType())) continue;
            return customScalarType;
        }
        String msg = "Unable to find a custom ScalarType with type [" + propertyType + "] and java.sql.Type [" + sqlType + "]";
        throw new RuntimeException(msg);
    }

    public ScalarDataReader<?> getScalarDataReader(Class<?> type) {
        ScalarDataReader reader = this.typeMap.get(type);
        if (reader == null) {
            reader = this.compoundTypeMap.get(type);
        }
        return reader;
    }

    @Override
    public <T> ScalarType<T> getScalarType(Class<T> type, int jdbcType) {
        ScalarType<?> scalarType = this.getLobTypes(jdbcType);
        if (scalarType != null) {
            return scalarType;
        }
        scalarType = this.typeMap.get(type);
        if (scalarType != null && (jdbcType == 0 || scalarType.getJdbcType() == jdbcType)) {
            return scalarType;
        }
        if (type.equals(Date.class)) {
            return this.extraTypeFactory.createUtilDate(jdbcType);
        }
        if (type.equals(Calendar.class)) {
            return this.extraTypeFactory.createCalendar(jdbcType);
        }
        String msg = "Unmatched ScalarType for " + type + " jdbcType:" + jdbcType;
        throw new RuntimeException(msg);
    }

    private ScalarType<?> getLobTypes(int jdbcType) {
        return this.getScalarType(jdbcType);
    }

    public Object convert(Object value, int toJdbcType) {
        if (value == null) {
            return null;
        }
        ScalarType<?> type = this.nativeMap.get(toJdbcType);
        if (type != null) {
            return type.toJdbcType(value);
        }
        return value;
    }

    private boolean isIntegerType(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private ScalarType<?> createEnumScalarType2(Class<?> enumType) {
        boolean integerType = true;
        HashMap<String, String> nameValueMap = new HashMap<String, String>();
        Field[] fields = enumType.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            EnumValue enumValue = fields[i].getAnnotation(EnumValue.class);
            if (enumValue == null) continue;
            nameValueMap.put(fields[i].getName(), enumValue.value());
            if (!integerType || this.isIntegerType(enumValue.value())) continue;
            integerType = false;
        }
        if (nameValueMap.isEmpty()) {
            return null;
        }
        return this.createEnumScalarType(enumType, nameValueMap, integerType, 0);
    }

    @Override
    public ScalarType<?> createEnumScalarType(Class<?> enumType) {
        EnumMapping enumMapping = enumType.getAnnotation(EnumMapping.class);
        if (enumMapping == null) {
            return this.createEnumScalarType2(enumType);
        }
        String nameValuePairs = enumMapping.nameValuePairs();
        boolean integerType = enumMapping.integerType();
        int dbColumnLength = enumMapping.length();
        Map<String, String> nameValueMap = StringHelper.delimitedToMap(nameValuePairs, ",", "=");
        return this.createEnumScalarType(enumType, nameValueMap, integerType, dbColumnLength);
    }

    private ScalarType<?> createEnumScalarType(Class enumType, Map<String, String> nameValueMap, boolean integerType, int dbColumnLength) {
        EnumToDbValueMap<?> beanDbMap = EnumToDbValueMap.create(integerType);
        int maxValueLen = 0;
        for (Map.Entry<String, String> entry : nameValueMap.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            maxValueLen = Math.max(maxValueLen, value.length());
            Object enumValue = Enum.valueOf(enumType, name.trim());
            beanDbMap.add(enumValue, value.trim());
        }
        if (dbColumnLength == 0 && !integerType) {
            dbColumnLength = maxValueLen;
        }
        return new ScalarTypeEnumWithMapping(beanDbMap, enumType, dbColumnLength);
    }

    protected void initialiseCustomScalarTypes(BootupClasses bootupClasses) {
        this.customScalarTypes.add(this.longToTimestamp);
        ArrayList<Class<?>> foundTypes = bootupClasses.getScalarTypes();
        for (int i = 0; i < foundTypes.size(); ++i) {
            Class cls = (Class)foundTypes.get(i);
            try {
                ScalarType scalarType = (ScalarType)cls.newInstance();
                this.add(scalarType);
                this.customScalarTypes.add(scalarType);
                continue;
            }
            catch (Exception e) {
                String msg = "Error loading ScalarType [" + cls.getName() + "]";
                logger.log(Level.SEVERE, msg, e);
            }
        }
    }

    protected void initialiseScalarConverters(BootupClasses bootupClasses) {
        ArrayList<Class<?>> foundTypes = bootupClasses.getScalarConverters();
        for (int i = 0; i < foundTypes.size(); ++i) {
            Class cls = (Class)foundTypes.get(i);
            try {
                Object[] paramTypes = TypeReflectHelper.getParams(cls, ScalarTypeConverter.class);
                if (paramTypes.length != 2) {
                    throw new IllegalStateException("Expected 2 generics paramtypes but got: " + Arrays.toString(paramTypes));
                }
                Class<?> logicalType = paramTypes[0];
                Class<?> persistType = paramTypes[1];
                ScalarType<?> wrappedType = this.getScalarType(persistType);
                if (wrappedType == null) {
                    throw new IllegalStateException("Could not find ScalarType for: " + paramTypes[1]);
                }
                ScalarTypeConverter converter = (ScalarTypeConverter)cls.newInstance();
                ScalarTypeWrapper stw = new ScalarTypeWrapper(logicalType, wrappedType, converter);
                logger.info("Register ScalarTypeWrapper from " + logicalType + " -> " + persistType + " using:" + cls);
                this.add(stw);
                continue;
            }
            catch (Exception e) {
                String msg = "Error loading ScalarType [" + cls.getName() + "]";
                logger.log(Level.SEVERE, msg, e);
            }
        }
    }

    protected void initialiseCompoundTypes(BootupClasses bootupClasses) {
        ArrayList<Class<?>> compoundTypes = bootupClasses.getCompoundTypes();
        for (int j = 0; j < compoundTypes.size(); ++j) {
            Class<?> type = compoundTypes.get(j);
            try {
                Object[] paramTypes = TypeReflectHelper.getParams(type, CompoundType.class);
                if (paramTypes.length != 1) {
                    throw new RuntimeException("Expecting 1 generic paramter type but got " + Arrays.toString(paramTypes) + " for " + type);
                }
                Class<?> compoundTypeClass = paramTypes[0];
                CompoundType compoundType = (CompoundType)type.newInstance();
                this.createCompoundScalarDataReader(compoundTypeClass, compoundType, "");
                continue;
            }
            catch (Exception e) {
                String msg = "Error initialising component " + type;
                throw new RuntimeException(msg, e);
            }
        }
    }

    protected CtCompoundType createCompoundScalarDataReader(Class<?> compoundTypeClass, CompoundType<?> compoundType, String info) {
        CtCompoundType<?> ctCompoundType = this.compoundTypeMap.get(compoundTypeClass);
        if (ctCompoundType != null) {
            logger.info("Already registered compound type " + compoundTypeClass);
            return ctCompoundType;
        }
        CompoundTypeProperty<?, ?>[] cprops = compoundType.getProperties();
        ScalarDataReader[] dataReaders = new ScalarDataReader[cprops.length];
        for (int i = 0; i < cprops.length; ++i) {
            Class<?> propertyType = this.getCompoundPropertyType(cprops[i]);
            ScalarDataReader<?> scalarDataReader = this.getScalarDataReader(propertyType, cprops[i].getDbType());
            if (scalarDataReader == null) {
                throw new RuntimeException("Could not find ScalarDataReader for " + propertyType);
            }
            dataReaders[i] = scalarDataReader;
        }
        CtCompoundType ctType = new CtCompoundType(compoundTypeClass, compoundType, dataReaders);
        logger.info("Registering CompoundType " + compoundTypeClass + " " + info);
        this.compoundTypeMap.put(compoundTypeClass, ctType);
        return ctType;
    }

    private Class<?> getCompoundPropertyType(CompoundTypeProperty<?, ?> prop) {
        if (prop instanceof ReflectionBasedCompoundTypeProperty) {
            return ((ReflectionBasedCompoundTypeProperty)prop).getPropertyType();
        }
        Object[] propParamTypes = TypeReflectHelper.getParams(prop.getClass(), CompoundTypeProperty.class);
        if (propParamTypes.length != 2) {
            throw new RuntimeException("Expecting 2 generic paramter types but got " + Arrays.toString(propParamTypes) + " for " + prop.getClass());
        }
        return propParamTypes[1];
    }

    protected void initialiseJodaTypes() {
        if (ClassUtil.isPresent("org.joda.time.LocalDateTime", this.getClass())) {
            String msg = "Registering Joda data types";
            logger.log(Level.INFO, msg);
            this.typeMap.put(LocalDateTime.class, new ScalarTypeJodaLocalDateTime());
            this.typeMap.put(LocalDate.class, new ScalarTypeJodaLocalDate());
            this.typeMap.put(LocalTime.class, new ScalarTypeJodaLocalTime());
            this.typeMap.put(DateTime.class, new ScalarTypeJodaDateTime());
            this.typeMap.put(DateMidnight.class, new ScalarTypeJodaDateMidnight());
        }
    }

    protected void initialiseStandard(int platformClobType, int platformBlobType) {
        ScalarType<Date> utilDateType = this.extraTypeFactory.createUtilDate();
        this.typeMap.put(Date.class, utilDateType);
        ScalarType<Calendar> calType = this.extraTypeFactory.createCalendar();
        this.typeMap.put(Calendar.class, calType);
        ScalarType<BigInteger> mathBigIntType = this.extraTypeFactory.createMathBigInteger();
        this.typeMap.put(BigInteger.class, mathBigIntType);
        ScalarType<Boolean> booleanType = this.extraTypeFactory.createBoolean();
        this.typeMap.put(Boolean.class, booleanType);
        this.typeMap.put(Boolean.TYPE, booleanType);
        this.nativeMap.put(16, booleanType);
        if (booleanType.getJdbcType() == -7) {
            this.nativeMap.put(-7, booleanType);
        }
        this.typeMap.put(Locale.class, this.localeType);
        this.typeMap.put(Currency.class, this.currencyType);
        this.typeMap.put(TimeZone.class, this.timeZoneType);
        this.typeMap.put(UUID.class, this.uuidType);
        this.typeMap.put(URL.class, this.urlType);
        this.typeMap.put(URI.class, this.uriType);
        this.typeMap.put(char[].class, this.charArrayType);
        this.typeMap.put(Character.TYPE, this.charType);
        this.typeMap.put(String.class, this.stringType);
        this.nativeMap.put(12, this.stringType);
        this.nativeMap.put(1, this.stringType);
        this.nativeMap.put(-1, this.longVarcharType);
        this.typeMap.put(Class.class, this.classType);
        if (platformClobType == 2005) {
            this.nativeMap.put(2005, this.clobType);
        } else {
            ScalarType<?> platClobScalarType = this.nativeMap.get(platformClobType);
            if (platClobScalarType == null) {
                throw new IllegalArgumentException("Type for dbPlatform clobType [" + this.clobType + "] not found.");
            }
            this.nativeMap.put(2005, platClobScalarType);
        }
        this.typeMap.put(byte[].class, this.varbinaryType);
        this.nativeMap.put(-2, this.binaryType);
        this.nativeMap.put(-3, this.varbinaryType);
        this.nativeMap.put(-4, this.longVarbinaryType);
        if (platformBlobType == 2004) {
            this.nativeMap.put(2004, this.blobType);
        } else {
            ScalarType<?> platBlobScalarType = this.nativeMap.get(platformBlobType);
            if (platBlobScalarType == null) {
                throw new IllegalArgumentException("Type for dbPlatform blobType [" + this.blobType + "] not found.");
            }
            this.nativeMap.put(2004, platBlobScalarType);
        }
        this.typeMap.put(Byte.class, this.byteType);
        this.typeMap.put(Byte.TYPE, this.byteType);
        this.nativeMap.put(-6, this.byteType);
        this.typeMap.put(Short.class, this.shortType);
        this.typeMap.put(Short.TYPE, this.shortType);
        this.nativeMap.put(5, this.shortType);
        this.typeMap.put(Integer.class, this.integerType);
        this.typeMap.put(Integer.TYPE, this.integerType);
        this.nativeMap.put(4, this.integerType);
        this.typeMap.put(Long.class, this.longType);
        this.typeMap.put(Long.TYPE, this.longType);
        this.nativeMap.put(-5, this.longType);
        this.typeMap.put(Double.class, this.doubleType);
        this.typeMap.put(Double.TYPE, this.doubleType);
        this.nativeMap.put(6, this.doubleType);
        this.nativeMap.put(8, this.doubleType);
        this.typeMap.put(Float.class, this.floatType);
        this.typeMap.put(Float.TYPE, this.floatType);
        this.nativeMap.put(7, this.floatType);
        this.typeMap.put(BigDecimal.class, this.bigDecimalType);
        this.nativeMap.put(3, this.bigDecimalType);
        this.nativeMap.put(2, this.bigDecimalType);
        this.typeMap.put(Time.class, this.timeType);
        this.nativeMap.put(92, this.timeType);
        this.typeMap.put(java.sql.Date.class, this.dateType);
        this.nativeMap.put(91, this.dateType);
        this.typeMap.put(Timestamp.class, this.timestampType);
        this.nativeMap.put(93, this.timestampType);
    }
}

