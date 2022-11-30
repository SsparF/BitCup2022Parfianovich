package webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Log4j2
public class DriverManager {
    private static ThreadLocal<WebDriver> webDriverInstance = new ThreadLocal<>();

    public static WebDriver getInstance() {
        if(webDriverInstance.get() == null) {
            log.trace("creating web driver instance.");
            webDriverInstance.set(initDriver());
        }

        return webDriverInstance.get();
    }

    private static WebDriver initDriver() {
        WebDriver webDriver;
        String browser = System.getProperty("browser", Browser.CHROME.toString().toLowerCase());

        log.info("Creating chrome web driver.");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();

        webDriver.manage().deleteAllCookies();
        webDriver.manage().window().maximize();

        return webDriver;
    }

    public static void shutDown() {
        if(webDriverInstance.get() != null) {
            try {
                log.trace("Closing web driver instance.");
                webDriverInstance.get().quit();
            }
            finally {
                webDriverInstance.remove();
            }
        }
    }
}
