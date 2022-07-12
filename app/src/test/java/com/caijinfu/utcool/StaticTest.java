package com.caijinfu.utcool;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.annotation.Config;

import com.caijinfu.utcool.shadow.FileUtilsShadow;
import com.caijinfu.utcool.utils.FileUtils;
import com.caijinfu.utcool.utils.TestUtils;

/**
 * 测试静态类
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
@Config(shadows = {FileUtilsShadow.class})
@PrepareForTest(TestUtils.class)
public class StaticTest extends BaseRobolectricTestCase {

    @Test
    public void testStaticMock() {
        assertTrue(FileUtils.isSdCardAvailable());
        PowerMockito.mockStatic(TestUtils.class);
        Mockito.when(TestUtils.isA()).thenReturn(true);
        assertTrue(TestUtils.isA());
        // Different from Mockito, always use PowerMockito.verifyStatic(Class) first
        // to start verifying behavior
        PowerMockito.verifyStatic(TestUtils.class, Mockito.times(1));
        // IMPORTANT: Call the static method you want to verify
        TestUtils.isA();
    }

}