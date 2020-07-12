package com.paulszabo.airbnb.utils;

import com.paulszabo.airbnb.airbnbpages.ResultsPage;
import com.paulszabo.airbnb.data.SearchDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;

import static com.paulszabo.airbnb.utils.SeleniumUtils.getMonth;
import static com.paulszabo.airbnb.utils.StringUtils.doSubstringAndDeleteFirstCharacterIfZero;


public class CustomMatchers {

    public static Matcher<ResultsPage> areResultsCorrect(SearchDto searchDto) {
        return new TypeSafeDiagnosingMatcher<ResultsPage>() {
            @Override
            protected boolean matchesSafely(ResultsPage resultsPage, Description description) {
                List<String> incorrectResponseFields = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();

                if (!resultsPage.getLocation().contains(searchDto.getExactLocation())) {
                    getIncorrectValueMessage(incorrectResponseFields, stringBuilder, "location",
                            searchDto.getExactLocation(), resultsPage.getLocation());
                }

                String formattedStartDay = doSubstringAndDeleteFirstCharacterIfZero(searchDto.getStartDate(), 8, 10);
                String formattedEndDay = doSubstringAndDeleteFirstCharacterIfZero(searchDto.getEndDate(), 8, 10);
                int formattedStartMonth = Integer.parseInt(doSubstringAndDeleteFirstCharacterIfZero(
                        searchDto.getStartDate(), 5, 7));
                int formattedEndMonth = Integer.parseInt(doSubstringAndDeleteFirstCharacterIfZero(
                        searchDto.getStartDate(), 5, 7));

                if (!resultsPage.getDates().contains(formattedStartDay) && resultsPage.getDates().contains(formattedEndDay)
                        && resultsPage.getDates().contains(getMonth(formattedStartMonth)) &&
                        resultsPage.getDates().contains(getMonth(formattedEndMonth))) {
                    getIncorrectValueMessage(incorrectResponseFields, stringBuilder, "date",
                            searchDto.getExactLocation(), resultsPage.getLocation());
                }

                if (!(Integer.parseInt(resultsPage.getNumberOfGuests().replaceAll("[^\\d.]", "")) ==
                        (searchDto.getNumberOfAdults() + searchDto.getNumberOfChildren()))) {
                    getIncorrectValueMessage(incorrectResponseFields, stringBuilder, "number of guests",
                            searchDto.getExactLocation(), resultsPage.getLocation());
                }
                description.appendText(stringBuilder.toString());
                return incorrectResponseFields.size() == 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("\nLocation, exact location, start and end date and number of guests are " +
                        "correctly displayed on the summary from the Results page.");
            }
        };
    }

    public static void getIncorrectValueMessage(List<String> incorrectResponseFields, StringBuilder stringBuilder,
                                                String fieldValue, Object expectedValue, Object actualValue) {
        String incorrectMessage = "\n" + fieldValue + " was: " + actualValue
                + ", but expected was: " + expectedValue + ".";
        incorrectResponseFields.add(incorrectMessage);
        stringBuilder.append(incorrectMessage);
    }
}
