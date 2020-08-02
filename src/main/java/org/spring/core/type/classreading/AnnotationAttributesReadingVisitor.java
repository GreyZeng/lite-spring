package org.spring.core.type.classreading;

import org.objectweb.asm.AnnotationVisitor;
import org.spring.beans.factory.annotation.AnnotationAttributes;

import java.util.Map;

/**
 * @author zenghui
 * 2020/8/2
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        // TODO
        //super(SpringAsmInfo.ASM_VERSION);
        super(327680);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;

    }

    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }


}
