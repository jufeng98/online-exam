package org.javamaster.auto.uitest.appium;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.lang3.RandomUtils;
import static org.javamaster.auto.uitest.utils.AppiumUtils.findById;
import org.javamaster.auto.uitest.utils.PropertiesUtils;
import static org.javamaster.auto.uitest.utils.SeleniumUtils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 碎片学习APP的UI自动化,针对Android 5.1.1系统编写
 *
 * @author yudong
 * @date 2020/8/13
 */
@SuppressWarnings("ALL")
public class FragmentLearningService {
    private AndroidDriver driver;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String APPIUM_SERVER_HOST = "127.0.0.1";
    public static final int APPIUM_SERVER_PORT = 4723;

    public FragmentLearningService() {
        DesiredCapabilities capabilities = capabilities();
        try {
            String appiumServerUrl = String.format("http://%s:%s/wd/hub", APPIUM_SERVER_HOST, APPIUM_SERVER_PORT);
            driver = new AndroidDriver<>(new URL(appiumServerUrl), capabilities);
            driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
        String angelCode = PropertiesUtils.getProp("user.code");
        String password = PropertiesUtils.getProp("user.password");
        tryToClick(driver, findById(driver, "org.javamaster.fragmentlearning:id/start_login_activity"));
        findById(driver, "org.javamaster.fragmentlearning:id/username").sendKeys(angelCode);
        findById(driver, "org.javamaster.fragmentlearning:id/password").sendKeys(password);
        // 点击登录
        tryToClick(driver, findById(driver, "org.javamaster.fragmentlearning:id/login"));
        driver.findElementsById("org.javamaster.fragmentlearning:id/holder_cover_img");
        sleep(2);
    }

    public void anticipateExam() {
        // 点击考试的tab
        tryToClick(driver, findById(driver, "org.javamaster.fragmentlearning:id/tab1"));
        AndroidElement element = (AndroidElement) driver.findElementsById("org.javamaster.fragmentlearning:id/start_exams").get(0);
        // 点击开始考试
        tryToClick(driver, element);
        element = (AndroidElement) driver.findElementById("org.javamaster.fragmentlearning:id/questions_radio_group");
        List<MobileElement> mobileElements = element.findElements(By.className("android.widget.RadioButton"));
        // 得到题目总条数
        int questionTotalNum = mobileElements.size();
        for (int i = 0; i < questionTotalNum; i++) {
            element = (AndroidElement) driver.findElementById("org.javamaster.fragmentlearning:id/question_options");
            if (existsElementByClassName(driver, element, "android.widget.RadioButton")) {
                // 单选
                mobileElements = element.findElements(By.className("android.widget.RadioButton"));
                // 随机点击一个选项
                MobileElement mobileElement = mobileElements.get(RandomUtils.nextInt(0, mobileElements.size()));
                tryToClick(driver, mobileElement);
            } else if (existsElementByClassName(driver, element, "android.widget.CheckBox")) {
                // 多选
                mobileElements = element.findElements(By.className("android.widget.CheckBox"));
                // 随机点击两个选项
                for (int j = 0; j < 2; j++) {
                    MobileElement mobileElement = mobileElements.get(RandomUtils.nextInt(0, mobileElements.size()));
                    tryToClick(driver, mobileElement);
                }
            }
            // 点击切换下一页
            tryToClick(driver, driver.findElementById("org.javamaster.fragmentlearning:id/next"));
            sleep(2);
        }
        // 点击提交考试答案
        tryToClick(driver, driver.findElementById("org.javamaster.fragmentlearning:id/submit"));
        sleep(3);
        driver.navigate().back();
    }

    private void switchToWebView(String name) {
        while (!existsWebView(name)) {
            sleep();
        }
        driver.context(name);
        sleep();
    }

    private boolean existsWebView(String name) {
        for (Object contextHandle : driver.getContextHandles()) {
            if (name.equals(contextHandle)) {
                return true;
            }
        }
        return false;
    }

    public void quit() {
        driver.quit();
    }

    private DesiredCapabilities capabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", "org.javamaster.fragmentlearning");
        capabilities.setCapability("appActivity", ".ui.activities.LauncherActivity");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", PropertiesUtils.getProp("appium.device.name"));
        capabilities.setCapability("platformVersion", PropertiesUtils.getProp("appium.platform.version"));
        capabilities.setCapability("chromedriverExecutable", PropertiesUtils.getProp("webview.driver.path"));
        return capabilities;
    }

}
