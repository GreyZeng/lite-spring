package org.spring.beans.factory.annotation;

import java.util.List;

/**
 * @author zenghui
 * 2020/8/2
 */
public class InjectionMetadata {
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(List<InjectionElement> injectionElements) {
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement ele : injectionElements) {
            ele.inject(target);
        }
    }
}
