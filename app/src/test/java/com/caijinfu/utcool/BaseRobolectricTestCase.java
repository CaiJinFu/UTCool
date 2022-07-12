package com.caijinfu.utcool;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;

/**
 * 测试的基类
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowLog.class}, sdk = 28, application = RobolectricApp.class)
@PowerMockIgnore({"com.caijinfu.utcool.*", "org.mockito.*", "org.robolectric.*", "androidx.*", "android.*",
    "org.json.*", "sun.security.*", "javax.net.*"})
public abstract class BaseRobolectricTestCase {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private static boolean hasInited = false;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        if (!hasInited) {
            hasInited = true;
        }
        MockitoAnnotations.initMocks(this);
    }

    public Application getApplication() {
        return RuntimeEnvironment.application;
    }

    public Context getContext() {
        return RuntimeEnvironment.application;
    }

    public String getString(int id) {
        return getApplication().getString(id);
    }

    public String getPkgName() {
        return getApplication().getPackageName();
    }

    public <T extends Activity> T startActivityForRobolectric(Class<T> activityClass) {
        return Robolectric.setupActivity(activityClass);
    }

    public <T extends Activity> ActivityScenario<T> startActivityForScenario(Class<T> activityClass) {
        return ActivityScenario.launch(activityClass);
    }

    public <T extends Fragment> FragmentScenario<T> startFragment(Class<T> fragment) {
        return FragmentScenario.launch(fragment);
    }

}
