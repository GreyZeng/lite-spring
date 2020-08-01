package org.spring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.beans.BeanDefinition;
import org.spring.beans.factory.support.BeanDefinitionRegistry;
import org.spring.beans.factory.support.GenericBeanDefinition;
import org.spring.core.io.Resource;

import java.io.InputStream;
import java.util.List;

/**
 * @author zenghui
 * 2020/7/31
 */
public class XmlBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        SAXReader reader = new SAXReader();
        try (InputStream is = resource.getInputStream()) {
            Document document = reader.read(is);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            BeanDefinition bd;
            for (Element element : elements) {
                bd = new GenericBeanDefinition(element.attribute("id").getValue(), element.attribute("class").getValue());
                if (element.attribute("scope") != null) {
                    // TODO 校验scope格式
                    bd.setScope(element.attribute("scope").getValue());
                }
                registry.registerBeanDefinition(element.attribute("id").getValue(), bd);
            }
        } catch (Exception e) {
            // TODO 封装异常
            throw new RuntimeException("parse bean definition file error", e);
        }
    }
}
