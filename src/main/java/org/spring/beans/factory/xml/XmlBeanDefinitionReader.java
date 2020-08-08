package org.spring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.spring.aop.config.ConfigBeanDefinitionParser;
import org.spring.beans.BeanDefinition;
import org.spring.beans.ConstructorArgument;
import org.spring.beans.PropertyValue;
import org.spring.beans.factory.annotation.ClassPathBeanDefinitionScanner;
import org.spring.beans.factory.config.RuntimeBeanReference;
import org.spring.beans.factory.config.TypedStringValue;
import org.spring.beans.factory.support.BeanDefinitionRegistry;
import org.spring.beans.factory.support.GenericBeanDefinition;
import org.spring.core.io.Resource;
import org.spring.util.StringUtils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author zenghui
 * 2020/7/31
 */
public class XmlBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type";
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    public static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
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
                String namespaceUri = element.getNamespaceURI();
                if (this.isDefaultNamespace(namespaceUri)) {
                    //普通的bean
                    parseDefaultElement(element);
                } else if (this.isContextNamespace(namespaceUri)) {
                    //例如<context:component-scan>
                    parseComponentElement(element);
                } else if(this.isAOPNamespace(namespaceUri)){
                    parseAOPElement(element);  //例如 <aop:config>
                }
                /*bd = new GenericBeanDefinition(element.attribute("id").getValue(), element.attribute("class").getValue());
                if (element.attribute("scope") != null) {
                    // TODO 校验scope格式
                    bd.setScope(element.attribute("scope").getValue());
                }
                // 解析构造函数
                parseConstructorArgElements(element, bd);
                // 注入属性值到Bean中
                parsePropertyElement(element, bd);
                registry.registerBeanDefinition(element.attribute("id").getValue(), bd);*/
            }
        } catch (Exception e) {
            // TODO 封装异常
            throw new RuntimeException("parse bean definition file error", e);
        }
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isAOPNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || AOP_NAMESPACE_URI.equals(namespaceUri));
    }
    private void parseAOPElement(Element ele){
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(ele, this.registry);
    }
    private void parseComponentElement(Element element) {
        String basePackages = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element element) {
        BeanDefinition bd = new GenericBeanDefinition(element.attribute(ID_ATTRIBUTE).getValue(), element.attribute(CLASS_ATTRIBUTE).getValue());
        if (element.attribute(SCOPE_ATTRIBUTE) != null) {
            bd.setScope(element.attribute(SCOPE_ATTRIBUTE).getValue());
        }
        parseConstructorArgElements(element, bd);
        parsePropertyElement(element, bd);
        registry.registerBeanDefinition(element.attribute(ID_ATTRIBUTE).getValue(), bd);
    }

    private void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iter.hasNext()) {
            Element ele = (Element) iter.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(ele, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }

        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iter.hasNext()) {
            Element propElem = (Element) iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                //  logger.fatal("Tag 'property' must have a 'name' attribute");
                // TODO 日志支持
                System.out.println("Tag 'property' must have a 'name' attribute");
                return;
            }


            Object val = parsePropertyValue(propElem, propertyName);

            //
            PropertyValue pv = new PropertyValue(propertyName, val);

            bd.getPropertyValues().add(pv);
        }
    }

    private Object parsePropertyValue(Element ele, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";


        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            // 形如：
            //  <property name="accountDao" ref="accountDao"/>
            //  <property name="itemDao" ref="itemDao"/>
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                // TODO logger.error(elementName + " contains empty 'ref' attribute");
                System.out.println(elementName + " contains empty 'ref' attribute");
            }

            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute) {
            // 形如：
            //   <property name="owner" value="test"/>
            //   <property name="version" value="2"/>
            //   <property name="checked" value="on"/>

            return new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
        } else {

            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}
