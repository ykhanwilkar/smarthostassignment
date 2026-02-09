package com.smarthost.smartroomallocator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class OccupancyRequest {
    private BigInteger premiumRooms;
    private BigInteger economyRooms;
    private List<Double> potentialGuests;
}
