package com.smarthost.smartroomallocator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OccupancyResponse {

    private Long usagePremium;
    private Double revenuePremium;
    private Long usageEconomy;
    private Double revenueEconomy;
}
