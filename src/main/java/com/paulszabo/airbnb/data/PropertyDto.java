package com.paulszabo.airbnb.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PropertyDto {
    private String headerText;
    private String name;
    private String price;
}
