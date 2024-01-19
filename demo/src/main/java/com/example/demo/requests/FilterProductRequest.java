package com.example.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterProductRequest {

    Integer marketId;
    String marketName;
    String category;

    Integer maxPrice;
    String search;

    AdditionalSettingsRequest settings[];

}
