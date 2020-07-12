package com.paulszabo.airbnb.airbnbpages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.paulszabo.airbnb.utils.SeleniumUtils.scrollToElement;
import static com.paulszabo.airbnb.utils.SeleniumUtils.waitForElementVisible;

public class PropertyPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'amenities')]")
    private WebElement showAllAmeninitesButton;
    @FindBy(xpath = "//h3[contains(text(), 'Facilities')]")
    private WebElement facilitiesLabel;
    @FindBy(xpath = "//div[contains(text(), 'Pool')]\n")
    private WebElement poolLabel;
    @FindBy(tagName = "h1")
    private WebElement propertyName;
    @FindBy(xpath = "(//span[@class='_pgfqnw'])[2]")
    private WebElement propertyPrice;
    @FindBy(xpath = "//div[@class='_tqmy57']/div[2]")
    private WebElement propertyOptions;

    public PropertyPage(WebDriver driver) {
        super(driver);
    }

    public PropertyPage clickShowAllAmenitiesButton() {
        try {
            waitAndClick(showAllAmeninitesButton);
        } catch (StaleElementReferenceException ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void scrollToFacilitiesLabel() {
        scrollToElement(facilitiesLabel, driver);
    }

    public boolean isPoolLabelDisplayed() {
        return poolLabel.isDisplayed();
    }

    public String getPropertyName() {
        waitForElementVisible(propertyName, driver, 2);
        return propertyName.getText();
    }

    public String getPropertyPrice(){
        return propertyPrice.getText().replaceAll("[^\\d.]", "");
    }

    public String getPropertyOptions(){
        return propertyOptions.getText();
    }
}
