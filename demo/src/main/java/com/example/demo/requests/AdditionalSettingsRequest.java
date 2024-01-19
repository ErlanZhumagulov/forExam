package com.example.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalSettingsRequest {

    private String criteria;

    private String criteriaValue;


}
