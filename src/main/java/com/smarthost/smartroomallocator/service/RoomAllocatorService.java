package com.smarthost.smartroomallocator.service;

import com.smarthost.smartroomallocator.model.OccupancyRequest;
import com.smarthost.smartroomallocator.model.OccupancyResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RoomAllocatorService {

    public OccupancyResponse allocateRooms(OccupancyRequest input) {

        if (!isValidInput(input))
            return null;

        List<Double> guestList = input.getPotentialGuests();
        List<Double> premiumGuests = new ArrayList<>();
        List<Double> economyGuests = new ArrayList<>();
        Long roomsPremiumOccupied = 0L;
        Long roomsEconomyOccupied = 0L;
        Double revenuePremium = 0.0;
        Double revenueEconomy = 0.0;
        for (Double guest : guestList) {
            if (guest >= 100)
                premiumGuests.add(guest);
            else
                economyGuests.add(guest);
        }
        economyGuests.sort(Comparator.reverseOrder());
        if (premiumGuests.size() < input.getPremiumRooms().intValue()) {
            while (premiumGuests.size() < input.getPremiumRooms().intValue()) {
                premiumGuests.add(economyGuests.removeFirst());
            }
        }
        premiumGuests.sort(Comparator.reverseOrder());
        roomsEconomyOccupied = (long) economyGuests.size() > input.getEconomyRooms().intValue() ? input.getEconomyRooms().longValue() : (long) economyGuests.size();
        roomsPremiumOccupied = (long) premiumGuests.size() > input.getPremiumRooms().intValue() ? input.getPremiumRooms().longValue() : (long) premiumGuests.size();
        revenuePremium = premiumGuests.stream().mapToDouble(Double::doubleValue).limit(input.getPremiumRooms().longValue()).sum();
        revenueEconomy = economyGuests.stream().mapToDouble(Double::doubleValue).limit(input.getEconomyRooms().longValue()).sum();
        return prepareResponse(roomsPremiumOccupied, revenuePremium, roomsEconomyOccupied, revenueEconomy);
    }

    private OccupancyResponse prepareResponse (Long roomsPremium, Double revenuePremium, Long roomsEconomy, Double revenueEconomy) {
        return new OccupancyResponse(roomsPremium, revenuePremium, roomsEconomy, revenueEconomy);
    }

    private boolean isValidInput(OccupancyRequest input) {
        if (input == null || input.getPotentialGuests() == null || input.getPremiumRooms() == null || input.getEconomyRooms() == null || input.getPotentialGuests().isEmpty())
            return false;

        return input.getPremiumRooms().intValue() >= 0 && input.getEconomyRooms().intValue() >= 0 && input.getPotentialGuests().stream().noneMatch(invalidAmount -> invalidAmount < 0);
    }
}
