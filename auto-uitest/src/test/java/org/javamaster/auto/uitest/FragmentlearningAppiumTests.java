package org.javamaster.auto.uitest;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.javamaster.auto.uitest.appium.FragmentLearningService;
import static org.javamaster.auto.uitest.appium.FragmentLearningService.APPIUM_SERVER_HOST;
import static org.javamaster.auto.uitest.appium.FragmentLearningService.APPIUM_SERVER_PORT;
import static org.javamaster.auto.uitest.utils.SeleniumUtils.sleep;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * <p>使用Appium做Android UI自动化测试,解放双手.</p>
 * <p>运行前说明,application.properties文件相关配置需要修改:</p>
 * <ul>
 *     <li>需要安装Appium:<pre>cnpm install -g appium</pre></li>
 *     <li>需要安装Android SDK</li>
 *     <li>需要启动一个Android 模拟器(官方或第三方模拟器均可,只要能被adb连上)或者真机</li>
 *     <li>application.properties配置文件:
 *     <br/>
 *     appium.webview.driver.path:Chrome驱动所在路径,根据WebView的Chrome内核版本下载对应驱动,
 *     若不涉及到WebView,则可忽略此配置.
 *     <a href="http://npm.taobao.org/mirrors/chromedriver/">下载地址</a>;
 *     <br/>
 *     appium.device.name:实际运行的Android设备名称;
 *     <br/>
 *     appium.platform.version:实际运行的Android版本.
 *     </li>
 *     <li>最好下载Appium.exe的安装包安装,以便利用其GUI界面提供的功能准确定位Android应用的控件元素</li>
 * </ul>
 * <p>此外,还需先启动后台SpringBoot微服务 b2c-parent/b2c-core CoreApplication</p>
 * <p>注意:如果用的是Android 模拟器,则窗口不能最小化运行,否则会出错</p>
 *
 * @author yudong
 * @date 2020/8/9
 */
public class FragmentlearningAppiumTests {

    private static AppiumDriverLocalService service;

    @BeforeClass
    public static void initAndStartAppiumServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("noReset", "false");
        builder.withIPAddress(APPIUM_SERVER_HOST);
        builder.usingPort(APPIUM_SERVER_PORT);
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    @AfterClass
    public static void stopAppiumServer() {
        service.stop();
    }

    @Test
    public void webViewSearch() {
        FragmentLearningService appiumDeliveryService = new FragmentLearningService();
        appiumDeliveryService.login();
        appiumDeliveryService.webViewSearch();
        sleep(3);
        appiumDeliveryService.quit();
    }

    @Test
    public void anticipateExam() {
        FragmentLearningService appiumDeliveryService = new FragmentLearningService();
        appiumDeliveryService.login();
        appiumDeliveryService.anticipateExam();
        sleep(3);
        appiumDeliveryService.quit();
    }

}
