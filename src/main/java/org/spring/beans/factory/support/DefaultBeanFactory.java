package org.spring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.beans.factory.BeanFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Grey
 * 2020/7/31
 */
public class DefaultBeanFactory implements BeanFactory {
    /**
     * Key beanId
     * Value bean class 的全路径，例如：org.spring.service.v1.UserService
     */
    private static final Map<String, String> BEAN_MAP = new HashMap<>();

    public DefaultBeanFactory(String configPath) {
        // TODO 配置文件检查
        // TODO 异常处理和封装
        URL url = Thread.currentThread().getContextClassLoader().getResource(configPath);
        File file = new File(url.getPath());
        SAXReader reader = new SAXReader();

        Document document;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
            return;
        }
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            BEAN_MAP.put(element.attribute("id").getValue(), element.attribute("class").getValue());
        }
    }

    @Override
    public Object getBean(String beanId) {
        // TODO bean存在与否判断
        // TODO 异常处理
        // TODO 构造函数带参数
        String beanClassName = BEAN_MAP.get(beanId);
        Class target = null;
        try {
            target = Thread.currentThread().getContextClassLoader().loadClass(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return target.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
