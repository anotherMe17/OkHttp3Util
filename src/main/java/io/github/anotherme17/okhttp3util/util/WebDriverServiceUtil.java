package io.github.anotherme17.okhttp3util.util;

import io.github.anotherme17.okhttp3util.entity.ProxyInfo;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lirenhao
 * date: 2017/11/14 下午7:44
 */
public class WebDriverServiceUtil {

    public static WebDriver getInstance(String userAgent, ProxyInfo proxy, String phantomjsPath) {
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjsPath);
        return getInstance(userAgent, proxy);
    }

    public static WebDriver getInstance(String userAgent, ProxyInfo proxy) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
        caps.setBrowserName("chrome");
        caps.setPlatform(Platform.MAC);
        caps.setVersion(DeviceInfoUtil.getVersion(userAgent));
        //设置属性,包括关闭安全策略,图片加载,开启缓存,忽略https错误,ssl加密协议
        List<String> cliArgsCap = new ArrayList<>(Arrays.asList("--web-security=false", "--disk-cache=true", "--ignore-ssl-errors=true", "--ssl-protocol=any"));

        if (proxy != null && StringUtils.isNotBlank(proxy.getIpaddr())) {
            String ipAndPort = proxy.getIpaddr() + ":" + proxy.getPort();
            cliArgsCap.add("--proxy=" + ipAndPort);
            if (StringUtils.isNotBlank(proxy.getUsername())) {
                String auth = proxy.getUsername() + ":" + proxy.getPassword();
                cliArgsCap.add("--proxy-auth=" + auth);
            } else {
                cliArgsCap.add("--proxy-auth=-:-");
            }
            cliArgsCap.add("--proxy-type=socks5");
        }
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                cliArgsCap);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
        WebDriver driver = new PhantomJSDriver(caps);
        driver.manage().window().setSize(new Dimension(2560, 1600));
        /*全局设置，关于JavaScript代码的异步处理的超时时间。AJAX请求。*/
        driver.manage().timeouts().setScriptTimeout(30L, TimeUnit.SECONDS);
        /*全局设置，页面加载的最长等待时间。*/
        driver.manage().timeouts().pageLoadTimeout(15L, TimeUnit.SECONDS);
        /*全局设置，当元素识别不到的时候，可以接受的最长等待时间。*/
        driver.manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);
        return driver;
    }

    public static WebDriver getChromeInstance(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        return getChromeInstance();
    }

    public static WebDriver getChromeInstance() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver webDriver = new ChromeDriver(capabilities);
        webDriver.manage().window().maximize();
        return webDriver;
    }

}
