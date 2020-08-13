package org.javamaster.auto.uitest.selenium;

import org.apache.commons.lang3.RandomStringUtils;
import org.javamaster.auto.uitest.utils.PropertiesUtils;
import static org.javamaster.auto.uitest.utils.SeleniumUtils.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author yudong
 * @date 2020/8/13
 */
public class OnlineExamService {

    private ChromeDriver driver;

    public OnlineExamService() {
        System.setProperty("webdriver.chrome.driver", PropertiesUtils.getProp("driver.path"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    public void exitBrowser() {
        driver.quit();
    }

    public void loginToSystem() {
        String username = PropertiesUtils.getProp("user.code");
        String password = PropertiesUtils.getProp("user.password");
        driver.get(PropertiesUtils.getProp("base.url"));
        driver.findElementByXPath("//input[@placeholder='用户名']").sendKeys(username);
        driver.findElementByXPath("//input[@placeholder='密码']").sendKeys(password);
        sleep();
        // 点击登录
        tryToClick(driver, driver.findElementById("login"));
    }

    public String addUser() {
        clickMenu("系统管理-", true);
        clickMenu("用户管理-/#/usersManage", false);
        // 切换到用户管理的iframe
        switchToTargetFrameBySrc(driver, "/#/usersManage");
        // 点击新增
        tryToClick(driver, driver.findElementById("addUser"));
        // 填充相关信息字段
        String username = RandomStringUtils.randomNumeric(8) + "yu";
        driver.findElementByXPath("//input[@placeholder='请填写用户名，5-20个字符']")
                .sendKeys(username);
        tryToClick(driver, driver.findElementById("gender"));
        tryToClick(driver, driver.findElementById(PropertiesUtils.getProp("gender")));
        driver.findElementByXPath("//input[@placeholder='请填写密码，5-20个字符']").sendKeys("admin");
        driver.findElementByXPath("//input[@placeholder='请填写别名']").sendKeys(username);
        driver.findElementByXPath("//input[@placeholder='请填写手机号码']")
                .sendKeys("188" + RandomStringUtils.randomNumeric(8));
        driver.findElementByXPath("//input[@placeholder='请填写邮箱']").sendKeys(username + "@qq.com");
        findsByXpath(driver, "//textarea[@placeholder='备注信息不超过50个字符']").get(0)
                .sendKeys(RandomStringUtils.randomAlphanumeric(3) + "这是备注信息呃");
        // 点击上传头像
        tryToClick(driver, findsByCssSelect(driver, "div.el-upload.el-upload--text").get(0));
        // 文件选择框选择图片
        choosePic(PropertiesUtils.getPropListRandomOne("pic.paths"));
        // 等待上传完成
        waitEleShowById(driver, "uploadShowImage");
        // 点击保存
        tryToClick(driver, driver.findElementById("saveCreateOrEditUses"));
        sleep(3);
        switchToDefaultFrame(driver);
        clickMenu("系统管理-", true);
        return username;
    }

    public ChromeDriver getDriver() {
        return driver;
    }

    private void clickMenu(String menuTag, boolean hasChild) {
        if (hasChild) {
            String cssSelect = String.format("li#%s>div", menuTag);
            tryToClick(driver, driver.findElementByCssSelector(cssSelect));
        } else {
            tryToClick(driver, driver.findElementById(menuTag));
        }
        sleep(2);
    }
}
