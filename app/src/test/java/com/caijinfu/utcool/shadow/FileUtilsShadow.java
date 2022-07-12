package com.caijinfu.utcool.shadow;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import com.caijinfu.utcool.utils.FileUtils;

/**
 * 创建{@link FileUtils}的影子类
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
@Implements(FileUtils.class)
public class FileUtilsShadow {

    @Implementation
    public static boolean isSdCardAvailable() {
        return true;
    }

}
