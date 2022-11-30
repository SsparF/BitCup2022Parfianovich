package ui;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AutorizedMainPage;
import pages.LogInPage;

@Log4j2
public class LogInTests extends BaseTest{
    LogInPage logInPage;
    @BeforeClass
    public void init() {
        log.info("Opening window...");
        logInPage = new LogInPage();
    }

    @Test(description = "Login with valid data")
    public void loginWithValidData() {
        SoftAssert softAssert = new SoftAssert();

        //this lines could be transfer to Before class method
        softAssert.assertTrue(logInPage.isPageLoaded());
        AutorizedMainPage autorizedMainPage = logInPage
                .fillUserName()
                .fillValidUserPass()
                .subBtnClick();

        softAssert.assertTrue(autorizedMainPage.isPageLoaded());

        softAssert.assertAll();
    }

    @Test(description = "Login with invalid data")
    public void loginWithInvalidData() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(logInPage.isPageLoaded());
        AutorizedMainPage autorizedMainPage = logInPage
                .fillUserName()
                .fillInvalidUserPass()
                .subBtnClick();

        softAssert.assertEquals(logInPage.checkErrorRedMessage(),"Epic sadface: Username and password do " +
                "not match any user in this service");

        softAssert.assertAll();
    }
}
