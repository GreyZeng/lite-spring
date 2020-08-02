package org.spring.beans.factory.annotation;

import org.spring.beans.BeanDefinition;
import org.spring.beans.factory.support.BeanDefinitionRegistry;
import org.spring.beans.factory.support.BeanNameGenerator;
import org.spring.context.annotation.AnnotationBeanNameGenerator;
import org.spring.context.annotation.ScannedGenericBeanDefinition;
import org.spring.core.io.Resource;
import org.spring.core.io.support.PackageResourceLoader;
import org.spring.core.type.classreading.MetadataReader;
import org.spring.core.type.classreading.SimpleMetadataReader;
import org.spring.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.spring.util.StringUtils.tokenizeToStringArray;

/**
 * @author zenghui
 * 2020/8/2
 */
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;
    private PackageResourceLoader resourceLoader = new PackageResourceLoader();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {
        String[] basePackages = tokenizeToStringArray(packagesToScan, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getID(), candidate);
            }
        }
        return beanDefinitions;
    }


    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources) {
                try {
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);
                    if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                } catch (Throwable ex) {
                    // TODO 异常处理
                   /* throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);*/
                    throw new RuntimeException("Failed to read candidate component class: " + resource);
                }
            }
        } catch (IOException ex) {
            // TODO 异常处理和封装
            //  throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
            throw new RuntimeException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }
}
