package com.paulszabo.airbnb.airbnbpages;

import com.paulszabo.airbnb.data.PropertyDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.paulszabo.airbnb.utils.SeleniumUtils.isElementDisplayed;

public class MapPage extends BasePage {

    public MapPage(WebDriver driver) {
        super(driver);
    }

    public String getMapPropertyBackgroundColor(String propertyName) {
        return driver.findElement(By.xpath("//button[starts-with(@aria-label, ' "
                + propertyName + "')]/div/div")).getCssValue("background-color");
    }

    public boolean isPropertyDisplayedOnMap(String propertyName) {
        return isElementDisplayed(driver.findElement(By.xpath("//button[starts-with(@aria-label, ' "
                + propertyName + "')]")), driver);
    }

    public void clickOnMapProperty(String propertyName) {
        waitAndClick(driver.findElement(By.xpath("//button[starts-with(@aria-label, ' " + propertyName + "')]")));
    }

    public PropertyDto getPropertyDetailsDto(String propertyName) {
        List<WebElement> propertyDetailsList = driver.findElements(By.xpath("(//a[starts-with(@aria-label, '" +
                propertyName + "')])[3]/parent::div/div[2]/div"));
        String price = propertyDetailsList.get(3).getText();

        return PropertyDto.builder()
                .headerText(propertyDetailsList.get(1).getText().replace("·", "in"))
                .name(propertyDetailsList.get(2).getText())
                .price(price.substring(price.lastIndexOf("\n")).replace("\n", ""))
                .build();
    }

    public void openPropertyFromMapByName(String name) {
        waitAndClick(driver.findElement(By.xpath("//a[@aria-label='" + name + "' and @class='_15tommw']")));
    }
}
