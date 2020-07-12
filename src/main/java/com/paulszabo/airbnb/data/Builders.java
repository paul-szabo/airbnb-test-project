package com.paulszabo.airbnb.data;

import static com.paulszabo.airbnb.data.Constants.EXACT_LOCATION;
import static com.paulszabo.airbnb.data.Constants.LOCATION;
import static com.paulszabo.airbnb.utils.SeleniumUtils.getDatePlusDays;

public class Builders {

    public static SearchDto buildSearchDto() {
        return SearchDto.builder()
                .location(LOCATION)
                .exactLocation(EXACT_LOCATION)
                .startDate(getDatePlusDays(7))
                .endDate(getDatePlusDays(14))
                .numberOfAdults(2)
                .numberOfChildren(1)
                .build();
    }
}
