package com.caijinfu.utcool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

/**
 * 测试生命周期，有2种方式，一种是用ActivityController，另一种是ActivityScenario
 *
 * @author 猿小蔡
 * @since 2022/7/8
 */
public class TestLifeCycleActivityTest extends BaseRobolectricTestCase {

    @Test
    public void testLifecycle() {
        useActivityController();
        useActivityScenario();
    }

    private void useActivityScenario() {
        ActivityScenario< TestLifeCycleActivity > launch =
            ActivityScenario.launch(TestLifeCycleActivity.class);

        launch.onActivity(activity -> {
            assertNotNull(activity);

            assertEquals("onResume", activity.getLifecycleState());

            launch.moveToState(Lifecycle.State.CREATED);
            assertEquals("onStop", activity.getLifecycleState());

            // 调用Activity的performResume方法
            launch.moveToState(Lifecycle.State.RESUMED);
            assertEquals("onResume", activity.getLifecycleState());

            // 调用Activity的performPause方法
            launch.moveToState(Lifecycle.State.STARTED);
            assertEquals("onPause", activity.getLifecycleState());

            // 调用Activity的performStop方法
            launch.moveToState(Lifecycle.State.CREATED);
            assertEquals("onStop", activity.getLifecycleState());

            // 调用Activity的performResume方法
            launch.moveToState(Lifecycle.State.RESUMED);
            assertEquals("onResume", activity.getLifecycleState());

            // 调用Activity的performDestroy方法
            launch.moveToState(Lifecycle.State.DESTROYED);
            assertEquals("onDestroy", activity.getLifecycleState());
        });
        // 测试用例执行完毕，Activity依然保持在某个状态运行，故建议在测试用例执行完毕，调用close
        launch.close();
    }

    private void useActivityController() {
        // 创建Activity控制器
        ActivityController< TestLifeCycleActivity > controller =
            Robolectric.buildActivity(TestLifeCycleActivity.class);
        TestLifeCycleActivity activity = controller.get();

        assertNotNull(activity);

        assertNull(activity.getLifecycleState());

        // 调用Activity的performCreate方法
        controller.create();
        assertEquals("onCreate", activity.getLifecycleState());

        // 调用Activity的performStart方法
        controller.start();
        assertEquals("onStart", activity.getLifecycleState());

        // 调用Activity的performResume方法
        controller.resume();
        assertEquals("onResume", activity.getLifecycleState());

        // 调用Activity的performPause方法
        controller.pause();
        assertEquals("onPause", activity.getLifecycleState());

        // 调用Activity的performStop方法
        controller.stop();
        assertEquals("onStop", activity.getLifecycleState());

        // 调用Activity的performRestart方法
        controller.restart();
        // 注意此处应该是onStart，因为performRestart不仅会调用restart，还会调用onStart
        assertEquals("onStart", activity.getLifecycleState());

        // 调用Activity的performDestroy方法
        controller.destroy();
        assertEquals("onDestroy", activity.getLifecycleState());

        // 测试用例执行完毕，Activity依然保持在某个状态运行，故建议在测试用例执行完毕，调用close
        controller.close();
    }

}