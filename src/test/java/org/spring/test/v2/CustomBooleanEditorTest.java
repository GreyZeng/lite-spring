package org.spring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.spring.beans.propertyeditors.CustomBooleanEditor;

/**
 * @author zenghui
 * 2020/8/2
 */
public class CustomBooleanEditorTest {
    @Test
    public void testConvertStringToBoolean() {
        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("false");
        Assert.assertFalse((Boolean) editor.getValue());

        editor.setAsText("on");
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("off");
        Assert.assertFalse((Boolean) editor.getValue());


        editor.setAsText("yes");
        Assert.assertTrue((Boolean) editor.getValue());
        editor.setAsText("no");
        Assert.assertFalse((Boolean) editor.getValue());


        try {
            editor.setAsText("aabbcc");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();


    }
}
