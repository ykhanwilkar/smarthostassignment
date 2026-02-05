package com.smarthost.smartroomallocator.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class OccupancyResponse {

    private BigInteger usagePremium;
    private Double revenuePremium;
    private BigInteger usageEconomy;
    private Double revenueEconomy;
}
