package com.paulszabo.airbnb.airbnb;

import com.paulszabo.airbnb.base.BaseAbstract;
import com.paulszabo.airbnb.data.FacilityType;
import com.paulszabo.airbnb.data.PropertyDto;
import com.paulszabo.airbnb.data.SearchDto;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.paulszabo.airbnb.data.Builders.buildSearchDto;
import static com.paulszabo.airbnb.data.Constants.*;
import static com.paulszabo.airbnb.utils.CustomMatchers.areResultsCorrect;
import static org.hamcrest.MatcherAssert.assertThat;

public class AirbnbTests extends BaseAbstract {

    private SearchDto searchDto = buildSearchDto();

    @BeforeMethod
    public void searchForProperties() {
        homepage.searchByStandardData(searchDto);
    }

    @AfterMethod
    public void deleteCookiesAndNavigateToHomepage() {
        driver.manage().deleteAllCookies();
        driver.get(URL);
    }

    @Test
    public void searchResultsMatchSearchInputTest() {
        assertThat("The Result summary info isn't the same with the one from the search.",
                resultsPage, areResultsCorrect(searchDto));
        assertThat("The results don't have the expected number of guests",
                resultsPage.areResultsWithExpectedNumberOfSearchedFacilities(searchDto.getNumberOfAdults() +
                        searchDto.getNumberOfChildren(), FacilityType.GUESTS_NUMBER));
    }

    @Test
    public void moreFiltersAndFacilitiesAddTest() {
        int numberOfBedrooms = 5;
        resultsPage.clickMoreFiltersButton()
                .clickAddBedroomButton(numberOfBedrooms)
                .checkPoolFacility()
                .clickShowStaysButton();

        softAssert.assertTrue(resultsPage.areResultsWithExpectedNumberOfSearchedFacilities(numberOfBedrooms,
                FacilityType.BEDROOMS_NUMBER), "The results don't have the expected number of bedrooms.");

        resultsPage.openFirstProperty()
                .switchToFirstPropertyTab();
        propertyPage.clickShowAllAmenitiesButton()
                .scrollToFacilitiesLabel();

        softAssert.assertTrue(propertyPage.isPoolLabelDisplayed(), "Pool facility is not displayed.");
        resultsPage.closeTabAndSwitchToInitialOne();
        softAssert.assertAll();
    }

    @Test
    public void propertyIsCorrectlyDisplayedOnTheMapTest() {
        String propertyFullName = resultsPage.getPropertyNameByIndex(1);
        resultsPage.hoverTheFirstProperty();

        softAssert.assertTrue(mapPage.isPropertyDisplayedOnMap(propertyFullName), "Selected property not" +
                " displayed on the map.");
        softAssert.assertEquals(mapPage.getMapPropertyBackgroundColor(propertyFullName), BLACK_COLOR_BACKGROUND,
                "The property color isn't changed on the map.");

        PropertyDto expectedPropertyDto = PropertyDto.builder()
                .headerText(resultsPage.getPropertyHeaderText(FIRST_PROPERTY_INDEX))
                .name(resultsPage.getPropertyNameByIndex(FIRST_PROPERTY_INDEX))
                .price(resultsPage.getPropertyPrice(FIRST_PROPERTY_INDEX))
                .build();

        mapPage.clickOnMapProperty(propertyFullName);
        softAssert.assertEquals(mapPage.getPropertyDetailsDto(propertyFullName), expectedPropertyDto,
                "The property details from the results are different than the ones from the map.");
        softAssert.assertAll();
    }

    @Test
    public void lowestPricePropertyDataFromResultsAndDetailsMatchTest() {
        int lowestPrice = resultsPage.getPropertyWithLowestPrice();
        String lowestPricePropertyName = resultsPage.getPropertyNameBasedOnLowestPrice(lowestPrice);
        resultsPage.hoverPropertyWithLowestPrice(lowestPrice);

        PropertyDto expectedPropertyDto = PropertyDto.builder()
                .headerText("")
                .name(lowestPricePropertyName)
                .price(String.valueOf(lowestPrice))
                .build();

        List<String> expectedOptionsAndFacilitiesList = resultsPage.getPropertyOptionsAndFacilitiesListByIndex(
                lowestPricePropertyName, lowestPrice);

        mapPage.clickOnMapProperty(lowestPricePropertyName);
        mapPage.openPropertyFromMapByName(lowestPricePropertyName);
        resultsPage.switchToFirstPropertyTab();

        PropertyDto actualPropertyDto = PropertyDto.builder()
                .headerText("")
                .name(propertyPage.getPropertyName())
                .price(propertyPage.getPropertyPrice())
                .build();

        softAssert.assertEquals(actualPropertyDto, expectedPropertyDto, "Property details are different!");
        softAssert.assertEquals(propertyPage.getPropertyOptions(), expectedOptionsAndFacilitiesList.get(0),
                "Options are different");

        resultsPage.closeTabAndSwitchToInitialOne();
        softAssert.assertAll();
    }
}