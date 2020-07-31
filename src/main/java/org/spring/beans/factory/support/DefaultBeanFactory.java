package org.spring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.beans.BeanDefinition;
import org.spring.beans.factory.BeanFactory;
import org.spring.core.io.Resource;

import java.io.IOException;
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
    private static final Map<String, BeanDefinition> BEAN_MAP = new HashMap<>();

    public DefaultBeanFactory(Resource resource) {
        // TODO 配置文件检查
        // TODO 异常处理和封装

        SAXReader reader = new SAXReader();

        Document document;
        try {
            document = reader.read(resource.getInputStream());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return;
        }
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BEAN_MAP.put(id, new GenericBeanDefinition(id, beanClassName));
        }
    }

    @Override
    public Object getBean(String beanId) {
        // TODO bean存在与否判断
        // TODO 异常处理
        // TODO 构造函数带参数
        BeanDefinition definition = BEAN_MAP.get(beanId);
        Class target = null;
        try {
            target = Thread.currentThread().getContextClassLoader().loadClass(definition.getBeanClassName());
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
