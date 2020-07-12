package com.paulszabo.airbnb.airbnbpages;

import com.paulszabo.airbnb.data.FacilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

import static com.paulszabo.airbnb.utils.SeleniumUtils.scrollToElement;
import static com.paulszabo.airbnb.utils.StringUtils.getNumberOfFacilityFromProperty;

public class ResultsPage extends BasePage {

    @FindBys(@FindBy(xpath = "//div[@data-testid='little-search']/button"))
    private List<WebElement> searchSummaryList;
    @FindBys(@FindBy(xpath = "//div[@class='_kqh46o']"))
    private List<WebElement> resultsDetailsList;
    @FindBy(xpath = "//div[@data-testid='menuItemButton-dynamicMoreFilters']")
    private WebElement moreFiltersButton;
    @FindBy(xpath = "//button[@aria-describedby='title-label-filterItem-stepper-min_bedrooms-0'][@aria-label='increase value']")
    private WebElement increaseNumberOfBedroomsButton;
    @FindBy(xpath = "//label[@data-testid='filterItem-checkbox-amenities-7']")
    private WebElement poolFacilityCheckbox;
    @FindBy(xpath = "//button[@data-testid='more-filters-modal-submit-button']")
    private WebElement showStaysButton;
    @FindBy(xpath = "(//div[@itemprop='itemListElement'])[1]")
    private WebElement firstResult;
    @FindBys(@FindBy(xpath = "//span[@class='_1p7iugi']"))
    private List<WebElement> propertyPricesList;

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    public String getLocation() {
        return searchSummaryList.get(0).getText();
    }

    public String getDates() {
        return searchSummaryList.get(1).getText();
    }

    public String getNumberOfGuests() {
        return searchSummaryList.get(2).getText();
    }

    /**
     * This method checks whether the number of a facility type is the one expected
     */
    public boolean areResultsWithExpectedNumberOfSearchedFacilities(int expectedNumber, FacilityType facilityType) {
        try {
            for (WebElement element : driver.findElements(By.xpath("(//div[@class='_kqh46o']" +
                    "[@style='margin-top: 9px;'])"))) {
                if (!(getNumberOfFacilityFromProperty(element, facilityType) >= expectedNumber)) {
                    return false;
                }
            }
        } catch (StaleElementReferenceException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public ResultsPage clickMoreFiltersButton() {
        waitAndClick(moreFiltersButton);
        return this;
    }

    public ResultsPage clickAddBedroomButton(int numberOfBedrooms) {
        for (int i = 1; i <= numberOfBedrooms; i++) {
            waitAndClick(increaseNumberOfBedroomsButton);
        }
        return this;
    }

    public ResultsPage checkPoolFacility() {
        scrollToElement(poolFacilityCheckbox, driver);
        waitAndClick(poolFacilityCheckbox);
        return this;
    }

    public void clickShowStaysButton() {
        waitAndClick(showStaysButton);
    }

    public ResultsPage openFirstProperty() {
        waitAndClick(firstResult);
        return this;
    }

    public void switchToFirstPropertyTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void closeTabAndSwitchToInitialOne() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }

    public void hoverTheFirstProperty() {
        scrollToElement(firstResult, driver);
    }

    public String getPropertyNameByIndex(int index) {
        String propertyName = driver.findElement(By.xpath("(//div[@class='_1c2n35az'])[" + index + "]")).getText();
        if (propertyName.contains("/")) {
            return propertyName.substring(0, propertyName.indexOf("/"));
        }
        return propertyName;
    }

    public String getPropertyHeaderText(int index) {
        return driver.findElements(By.xpath("(//div[@class='_1jzvdu8'])[" + index + "]/div")).get(0).getText();
    }

    public String getPropertyPrice(int index) {
        String price = driver.findElement(By.xpath("(//span[@class='_1p7iugi'])[" + index + "]")).getText();
        return price.substring(price.lastIndexOf("\n") + 1) + " / night";
    }

    public int getPropertyWithLowestPrice() {
        int lowestPrice = 10000;
        int actualPrice;
        for (WebElement price : propertyPricesList) {
            actualPrice = Integer.parseInt(price.getText().substring(price.getText().lastIndexOf("\n")
                    + 1).replaceAll("[^\\d.]", ""));
            if (lowestPrice > actualPrice) {
                lowestPrice = actualPrice;
            }

        }
        return lowestPrice;
    }

    public int getIndexByPrice(int price) {
        for(int i = 1; i <= propertyPricesList.size(); i++){
            if(propertyPricesList.get(i).getText().contains(String.valueOf(price)))
                return i;
        }
        return 0;
    }

    public void hoverPropertyWithLowestPrice(int price) {
        scrollToElement(driver.findElement(By.xpath("//span[contains(text(), '" + price + "')]")), driver);
    }

    public String getPropertyNameBasedOnLowestPrice(int price) {
        return driver.findElement(By.xpath("//span[contains(text(), '" + price +
                "')]/ancestor::div[@class='_3gn0lkf']/a")).getAttribute("aria-label");
    }

    public List<String> getPropertyOptionsAndFacilitiesListByIndex(String propertyName, int price) {
        List<String> optionsAndFacilitiesList = new ArrayList<>();
        WebElement propertyElement = driver.findElement(By.xpath("(//a[@aria-label='" + propertyName + "'])[1]"));
        optionsAndFacilitiesList.add(propertyElement.findElement(By.xpath("(//div[@class='_kqh46o'])[" +
                (2 * getIndexByPrice(price) + 1) +"]")).getText());
        optionsAndFacilitiesList.add(propertyElement.findElement(By.xpath("(//div[@class='_kqh46o'])[2]")).getText());

        return optionsAndFacilitiesList;
    }
}
