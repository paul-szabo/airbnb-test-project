package com.paulszabo.airbnb.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SearchDto {
    private String location;
    private String exactLocation;
    private String startDate;
    private String endDate;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
}
