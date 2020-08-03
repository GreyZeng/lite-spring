package org.spring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.spring.beans.factory.annotation.AutowiredFieldElement;
import org.spring.beans.factory.annotation.InjectionElement;
import org.spring.beans.factory.annotation.InjectionMetadata;
import org.spring.beans.factory.config.DependencyDescriptor;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.dao.v4.AccountDao;
import org.spring.dao.v4.ItemDao;
import org.spring.service.v4.UserService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author zenghui
 * @Date 2020/7/28
 */
public class AutowiredAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor){
            if(descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };



    @Test
    public void testGetInjectionMetadata(){

        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(UserService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements,"accountDao");
        assertFieldExists(elements,"itemDao");

        UserService petStore = new UserService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements ,String fieldName){
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldEle = (AutowiredFieldElement)ele;
            Field f = fieldEle.getField();
            if(f.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }
}
