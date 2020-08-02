package org.spring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PackageResourceLoaderTest.class,
        ClassReaderTest.class
})
public class V4AllTest {
}
