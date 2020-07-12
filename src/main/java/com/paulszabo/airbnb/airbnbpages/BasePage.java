package com.paulszabo.airbnb.airbnbpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static com.paulszabo.airbnb.utils.SeleniumUtils.waitForElementVisible;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitAndSendKeys(WebElement element, String text){
        waitForElementVisible(element, driver, 1);
        element.sendKeys(text);
    }

    public void waitAndClick(WebElement element){
        waitForElementVisible(element, driver, 1);
        element.click();
    }
}
