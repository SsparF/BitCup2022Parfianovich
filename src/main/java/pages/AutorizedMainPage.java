package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AutorizedMainPage extends AbstractPage{
    public static final String URL = "https://www.saucedemo.com/inventory.html";

    @FindBy(xpath = "//span[@class='title']")
    private WebElement pageTitle;

    public boolean isPageLoaded() {
        return driver.getCurrentUrl().equals(URL) && pageTitle.isDisplayed();
    }
}
