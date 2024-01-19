package com.example.demo.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoInfoResponse {

    String x;

    String y;

    String address;

    String marketName;

}
