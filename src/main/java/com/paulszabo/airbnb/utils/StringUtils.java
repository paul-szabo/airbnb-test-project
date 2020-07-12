package com.paulszabo.airbnb.utils;

import com.paulszabo.airbnb.data.FacilityType;
import org.openqa.selenium.WebElement;

public class StringUtils {
    /**
     *  This method does substring and replaces the 0 at the beginning in the day and month format
     *
     * @param text the date for which the above mentioned transformation is needed
     * @param startIndex exact starting index to locate the day or month in a date string
     * @param endIndex exact ending index to locate the day or month in a date string
     * @return day and month without beginning 0
     */
    public static String doSubstringAndDeleteFirstCharacterIfZero(String text, int startIndex, int endIndex){
        return text.substring(startIndex, endIndex).replaceFirst("0", "");
    }

    /**
     *  This method splits a text in 2 subtext, replaces all non numeric characters and transforms the string to int
     *
     * @param element WebElement for which to do the transformation
     * @param facilityType which of the 2 subtrings to be transformed (0 for GUESTS_NUMBER and 1 for BEDROOMS_NUMBER)
     * @return the number of a facility displayed (eg. 2 bedrooms)
     */
    public static int getNumberOfFacilityFromProperty(WebElement element, FacilityType facilityType){
        return Integer.parseInt(element.getText().split("·")[facilityType.getValue()].replaceAll("[^\\d.]", ""));
    }
}
