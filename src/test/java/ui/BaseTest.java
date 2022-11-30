package ui;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import webdriver.DriverManager;

public abstract class BaseTest {

    @BeforeTest(alwaysRun = true)
    public void setup() {
        DriverManager.getInstance();
    }

    @AfterTest(alwaysRun = true)
    public void quit() {
        DriverManager.shutDown();
    }
}
