package com.paulszabo.airbnb.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeleniumUtils {
    private static WebDriverWait wait;

    public static void waitForElementClickable(WebElement element, WebDriver driver, int seconds) {
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementVisible(WebElement element, WebDriver driver, int seconds) {
        wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static String getDatePlusDays(int offset) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now().plusDays(offset);
        return today.format(FORMATTER);
    }

    /**
     * This methods transforms the month number into short month string (eg. 03 to Mar)
     *
     * @param month
     * @return
     */
    public static String getMonth(int month) {
        return new DateFormatSymbols().getShortMonths()[month - 1];
    }

    public static void scrollToElement(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static boolean isElementDisplayed(WebElement element, WebDriver driver) {
        try {
            waitForElementVisible(element, driver, 3);
            if (element.isDisplayed()) {
                return true;
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
