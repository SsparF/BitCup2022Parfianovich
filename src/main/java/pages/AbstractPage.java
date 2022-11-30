package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.DriverManager;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@Log4j2
public abstract class AbstractPage {

    private static final int TIME_OUT = 30;
    private static final int WAIT_TIME = 15;
    private static final int POLLING_TIME = 3;
    protected static final WebDriver driver = DriverManager.getInstance();
    protected FluentWait<WebDriver> fluentWait = defaultFluentWait();

    protected AbstractPage() {initialize();}
    protected AbstractPage(String url) {
        log.info("Opening {} page", url);
        driver.get(url);

        initialize();
    };

    private void initialize() {
        PageFactory.initElements(driver, this);
        waitFor(() -> isPageLoaded());
    }

    /**
     * creating Fluent wait instance
     */
    private FluentWait<WebDriver> defaultFluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(WAIT_TIME))
                .pollingEvery(Duration.ofSeconds(POLLING_TIME))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * This method should be overwritten in all extended pages
     *
     * @return true if body returns true
     */
    public boolean isPageLoaded() {return true;}

//    public void openWindow(String url) {
//        driver.get(url);
//    }

    protected void waitFor(Supplier<Boolean> supplier) {
        try {
            fluentWait.until(ignored -> supplier);
        } catch (TimeoutException e) {
            log.error(Thread.currentThread().getStackTrace()[2].toString() + "waiter for this calls TimeoutException");
            throw e;
        }
    }

    protected <V> V waitFor(ExpectedCondition<V> isTrue) { return fluentWait.until(isTrue); };

    protected String attributeValue(WebElement element, String attr) {
        return element.getAttribute(attr);
    }

    public void clickElement(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT))
                .until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
