package com.paulszabo.airbnb.airbnbpages;

import com.paulszabo.airbnb.data.SearchDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.paulszabo.airbnb.utils.SeleniumUtils.waitForElementClickable;

public class Homepage extends BasePage {

    @FindBy(xpath = "//input[@data-testid='structured-search-input-field-query']")
    private WebElement searchField;
    @FindBy(xpath = "//button[@data-testid='structured-search-input-field-guests-button']")
    private WebElement guestsButton;
    @FindBy(xpath = "//button[@aria-describedby='searchFlow-title-label-stepper-adults'][@aria-label='increase value']")
    private WebElement increaseNumberOfAdultsButton;
    @FindBy(xpath = "//button[@aria-describedby='searchFlow-title-label-stepper-children'][@aria-label='increase value']")
    private WebElement increaseNumberOfChildrenButton;
    @FindBy(xpath = "//button[@data-testid='structured-search-input-search-button']")
    private WebElement searchButton;

    public Homepage(WebDriver driver) {
        super(driver);
    }

    public void searchByText(String text) {
        waitAndSendKeys(searchField, text);
    }

    public void selectLocation(String location) {
        driver.findElement(By.xpath("//*[contains(text(), '" + location + "')]")).click();
    }

    public void selectDate(String date) {
        driver.findElement(By.xpath("//div[@data-testid='datepicker-day-" + date + "']")).click();
    }

    public void clickAddPersonsButton(WebElement element, int numberOfPersons) {
        waitForElementClickable(element, driver, 2);
        for (int i = 1; i <= numberOfPersons; i++) {
            waitAndClick(element);
        }
    }

    public void clickSearchButton() {
        waitAndClick(searchButton);
    }

    public void searchByStandardData(SearchDto searchDto) {
        searchByText(searchDto.getLocation());
        selectLocation(searchDto.getExactLocation());
        selectDate(searchDto.getStartDate());
        selectDate(searchDto.getEndDate());
        waitAndClick(guestsButton);
        clickAddPersonsButton(increaseNumberOfAdultsButton, searchDto.getNumberOfAdults());
        clickAddPersonsButton(increaseNumberOfChildrenButton, searchDto.getNumberOfChildren());
        guestsButton.submit();
    }
}
