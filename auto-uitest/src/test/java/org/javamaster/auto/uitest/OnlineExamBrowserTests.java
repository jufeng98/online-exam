package org.javamaster.auto.uitest;

import org.javamaster.auto.uitest.selenium.OnlineExamService;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>使用selenium做Chrome浏览器UI自动化测试,解放双手.</p>
 * <p>运行前说明,application.properties文件相关配置需要修改:</p>
 * <ul>
 *     <li>driver.path:Chrome驱动所在路径,根据Chrome内核版本下载对应驱动放到Chrome安装目录下,
 *     <a href="http://npm.taobao.org/mirrors/chromedriver/">下载地址</a>
 *     </li>
 *     <li>pic.paths:程序用到的图片路径,请修改成本机有效的图片路径</li>
 *     <li>其他配置依据需要修改</li>
 * </ul>
 * <p>此外,还需先启动后台SpringBoot微服务 b2c-parent/b2c-core CoreApplication, 以及vue前端系统 b2c-view</p>
 * <p>注意:浏览器窗口不能最小化运行,否则会出错</p>
 *
 * @author yudong
 * @date 2020/8/13
 */
public class OnlineExamBrowserTests {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd-HH-mm-ss");
    private Logger logger = LoggerFactory.getLogger(getClass());
    private OnlineExamService service = new OnlineExamService();

    @Before
    public void loginToSystem() {
        service.loginToSystem();
    }

    @After
    public void closeBrowser() throws Exception {
        File srcFile = service.getDriver().getScreenshotAs(OutputType.FILE);
        File picDir = new File("target", "screenshot");
        assert picDir.exists() || picDir.mkdirs();
        File targetFile = new File(picDir, LocalDateTime.now().format(formatter) + ".png");
        // 退出前保存浏览器截图
        Files.copy(srcFile.toPath(), targetFile.toPath());
        service.exitBrowser();
    }

    @Test
    public void addUser() {
        logger.info("addUser:{}", service.addUser());
    }

}
