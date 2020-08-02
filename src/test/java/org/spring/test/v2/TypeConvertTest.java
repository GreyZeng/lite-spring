package org.spring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.spring.beans.SimpleTypeConverter;
import org.spring.beans.TypeConverter;

import static org.junit.Assert.fail;

/**
 * @author zenghui
 * 2020/8/2
 */
public class TypeConvertTest {
    @Test
    public void testConvertStringToInt() {

        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (Exception e) {
            // TODO 抽象出TypeMismatchException
            return;
        }
        fail();
    }

    @Test
    public void testConvertStringToBoolean() {
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try {
            converter.convertIfNecessary("xxxyyyzzz", Boolean.class);
        } catch (Exception e) {
            // TODO 抽象出TypeMismatchException
            return;
        }
        fail();
    }
}
