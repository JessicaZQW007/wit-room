package com.yhxc.utils.settings;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 对setting.xml进行读写操作类，为程序提供配置的所有的信息，并缓存起来
 *
 * @author alan.yan
 * @文件名称: SettingUtils.java
 * @since 21/08/2017.
 */
public class SettingUtils {

    /**
     * 使用CacheManager 创建并管理Cache 1.创建CacheManager有4种方式：A:使用默认配置文件创建
     */
    public static final CacheManager cacheManager = CacheManager.create();


    /**
     * 生成一个动态Bean管理对象,并对这个对象的几个关键类型进行重写
     */
    private static final BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new DimConverter());

    /**
     * 参数配置文件名称: /cweb.xml
     */
    public static String FILEPATH = "/config/setting.xml";


    /**
     * 配置的xml文件:要查询属性节点:/cweb/setting
     */
    public static String NODES_SETTING = "/web/setting";

    /**
     * 容器启动时加载系统参数配置文件并生成Setting缓存对象，方便业务调用。
     * 使用方法 Setting _setting = SettingUtils.get();
     * 取对应的属性：
     * _setting.getFileRoot();
     *
     * @return Setting对象，服务器启动参数
     */
    public static Setting get() {

        Ehcache _ehcache = cacheManager.getEhcache(Setting.CACHE_NAME);
        net.sf.ehcache.Element _element = _ehcache.get(Setting.CACHE_KEY);
        Setting _setting = null;
        if (_element != null) {
            _setting = (Setting) _element.getObjectValue();
        } else {
            _setting = new Setting();
            try {
                File _configFile = new ClassPathResource(FILEPATH).getFile();
                Document _document = new SAXReader().read(_configFile);
                List<?> _list = _document.selectNodes(NODES_SETTING);
                Iterator<?> localIterator = _list.iterator();
                while (localIterator.hasNext()) {
                    org.dom4j.Element localElement1 = (org.dom4j.Element) localIterator.next();
                    String name = localElement1.attributeValue("name");
                    String value = localElement1.attributeValue("value");
                    beanUtilsBean.setProperty(_setting, name, value);
                }

            } catch (Exception localException) {
                localException.printStackTrace();
            }
            _ehcache.put(new net.sf.ehcache.Element(Setting.CACHE_KEY, _setting));
        }
        return _setting;
    }
}

class DimConverter extends ConvertUtilsBean {

    public String convert(Object value) {
        if (value != null) {
            Class<? extends Object> _class = value.getClass();
            if ((_class.isEnum()) && (super.lookup(_class) == null)) {
                super.register(new EnumConverter(_class), _class);
            } else if ((_class.isArray()) && (_class.getComponentType().isEnum())) {
                if (super.lookup(_class) == null) {
                    ArrayConverter _arrConverter = new ArrayConverter(_class, new EnumConverter(_class.getComponentType()), 0);
                    _arrConverter.setOnlyFirstToString(false);
                    super.register((Converter) _arrConverter, _class);
                }
                Converter _converter = super.lookup(_class);
                return (String) _converter.convert(String.class, value);
            }
        }
        return super.convert(value);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(String value, Class clazz) {
        if ((clazz.isEnum()) && (super.lookup(clazz) == null)) {
            super.register(new EnumConverter(clazz), clazz);
        }
        return super.convert(value, clazz);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object convert(String[] values, Class clazz) {
        if ((clazz.isArray()) && (clazz.getComponentType().isEnum()) && (super.lookup(clazz.getComponentType()) == null)) {
            super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
        }
        return super.convert(values, clazz);
    }

    @SuppressWarnings("rawtypes")
    public Object convert(Object value, Class targetType) {
        if (super.lookup(targetType) == null) {
            if (targetType.isEnum()) {
                super.register(new EnumConverter(targetType), targetType);
            } else if ((targetType.isArray()) && (targetType.getComponentType().isEnum())) {
                ArrayConverter _arrConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
                _arrConverter.setOnlyFirstToString(false);
                super.register(_arrConverter, targetType);
            }
        }
        return super.convert(value, targetType);
    }

}
