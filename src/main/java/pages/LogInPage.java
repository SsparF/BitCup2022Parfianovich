package pages;

import cofiguration.PropertiesReader;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class LogInPage extends AbstractPage{
    public static final String URL = "https://www.saucedemo.com/";

    @FindBy(css = "[class='login_logo']")
    private WebElement siteLogo;

    @FindBy(id = "user-name")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement userPassInput;

    @FindBy(xpath = "//input[@type='submit']")
    @Getter
    private WebElement submitBtn;

    private By errorContainerLocator = By.xpath("//div[contains(@class,'error-message-container')]/h3");


    public LogInPage() {
        super(URL);
    }

    public LogInPage fillUserName() {
        String userName = PropertiesReader.getValueByKey("validUserName");
        log.info("Filling user name input {}", userName);

        userNameInput.clear();;
        userNameInput.click();
        userNameInput.sendKeys(userName);

        log.info("Check content of user name input");
        waitFor(() -> attributeValue(userNameInput, "value").equals(userName));
        return this;
    }

    private void fillUserPass(String userPass) {
        log.info("Filling user pass input {}", userPass);

        userPassInput.clear();;
        userPassInput.click();
        userPassInput.sendKeys(userPass);

        log.info("Check content of user pass input");
        waitFor(() -> attributeValue(userPassInput, "value").equals(userPass));
    }
    public LogInPage fillValidUserPass() {
        fillUserPass(PropertiesReader.getValueByKey("validUserPass"));
        return this;
    }
    public LogInPage fillInvalidUserPass() {
        fillUserPass(PropertiesReader.getValueByKey("invalidUserPass"));
        return this;
    }

    public AutorizedMainPage subBtnClick() {
        clickElement(submitBtn);

        return new AutorizedMainPage();
    }

    public String checkErrorRedMessage() {
        WebElement errorContainer = driver.findElement(errorContainerLocator);
        return errorContainer.getText();
    }

    public boolean isPageLoaded() {
        return driver.getCurrentUrl().equals(URL) && siteLogo.isDisplayed();
    }
}
