package com.paulszabo.airbnb.base;

import com.paulszabo.airbnb.airbnbpages.Homepage;
import com.paulszabo.airbnb.airbnbpages.MapPage;
import com.paulszabo.airbnb.airbnbpages.PropertyPage;
import com.paulszabo.airbnb.airbnbpages.ResultsPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import static com.paulszabo.airbnb.WebDriverManager.getWebDriver;
import static com.paulszabo.airbnb.data.Constants.URL;

public class BaseAbstract {

    protected WebDriver driver;
    protected Homepage homepage;
    protected ResultsPage resultsPage;
    protected PropertyPage propertyPage;
    protected MapPage mapPage;

    protected SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setupDriver() {
        driver = getWebDriver();
        homepage = new Homepage(driver);
        resultsPage = new ResultsPage(driver);
        propertyPage = new PropertyPage(driver);
        mapPage = new MapPage(driver);

        driver.get(URL);
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }
}
