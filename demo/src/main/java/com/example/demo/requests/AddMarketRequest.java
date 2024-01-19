package com.example.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMarketRequest {

    private String marketName;

    private String marketDescription;

    private String address;

    private Double x;
    private Double y;

}
