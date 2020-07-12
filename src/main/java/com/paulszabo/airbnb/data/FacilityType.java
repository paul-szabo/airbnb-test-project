package com.paulszabo.airbnb.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FacilityType {
    GUESTS_NUMBER(0),
    BEDROOMS_NUMBER(1);

    int value;
}
