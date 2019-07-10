package com.yhxc.utils.settings;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * 为BeanUtilsBean 提供枚举类型的转换
 * @author alan.yan
 *
 */
public class EnumConverter extends AbstractConverter {

    /**
     * 转换对象要返回的默认类型
     */
    private final Class<?> defaultType;

    /**
     * 单个对象的转换成枚举对象
     * @param enumClass 要进行转换的枚举对象类
     */
    public EnumConverter(Class<?> enumClass) {
        this(enumClass, null);
    }

    /**
     * 指定对象及类型转换成枚举对象
     * @param enumClass 要进行转换的枚举对象类
     * @param defaultValue 默认类型
     */
    public EnumConverter(Class<?> enumClass, Object defaultValue) {
        super(defaultValue);
        this.defaultType = enumClass;
    }

    /**
     * 返回已经转换后的默认类型
     */
    protected Class<?> getDefaultType() {
        return this.defaultType;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Object convertToType(Class type, Object value) {
        String str = value.toString().trim();
        return Enum.valueOf(type, str);//在使用枚举时，必须用类型名作前缀，比如Number.One，而不能直接用One。
    }


    protected String convertToString(Object value) {
        return value.toString();
    }

}