package org.javamaster.auto.uitest.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.PointOption;
import static org.javamaster.auto.uitest.utils.SeleniumUtils.sleep;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yudong
 * @date 2020/8/7
 */
@SuppressWarnings("ALL")
public class AppiumUtils {

    private static Logger logger = LoggerFactory.getLogger(AppiumUtils.class);


    public static AndroidElement findById(RemoteWebDriver driver, String id) {
        AndroidElement element;
        while (true) {
            try {
                element = (AndroidElement) driver.findElementById(id);
                break;
            } catch (Exception e) {
                logger.error("findById error:" + id);
                sleep(2);
            }
        }
        return element;
    }

    public static String findByIdAndGetText(RemoteWebDriver driver, String id) {
        String s;
        while (true) {
            try {
                s = driver.findElementById(id).getText();
                break;
            } catch (Exception e) {
                logger.error("findByIdAndGetText error:" + id);
                sleep(2);
            }
        }
        return s;
    }

    public static List<String> findsByIdAndGetText(RemoteWebDriver driver, String id) {
        List<String> strings = new ArrayList<>();
        while (true) {
            try {
                for (WebElement webElement : driver.findElementsById(id)) {
                    strings.add(webElement.getText());
                }
                if (strings.isEmpty()) {
                    throw new IllegalStateException("list empty!");
                }
                break;
            } catch (Exception e) {
                logger.error("findsByIdAndGetText error:" + id);
                sleep(2);
            }
        }
        return strings;
    }

    public static void swipeUp(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int height = size.height;
        int width = size.width;
        try {
            LongPressOptions longPressOptions = LongPressOptions
                    .longPressOptions()
                    .withPosition(PointOption.point(width / 2, height - 100))
                    .withDuration(Duration.ofMillis(100));
            new TouchAction<>(driver)
                    .longPress(longPressOptions)
                    .moveTo(PointOption.point(width / 2, 100))
                    .release()
                    .perform();
        } catch (Exception e) {
            logger.error("swipeUp error:{},{}", e.getClass().getSimpleName(), e.getMessage().substring(0, 100));
        }
    }

}
