package com.smarthost.smartroomallocator;

import com.smarthost.smartroomallocator.controller.RoomController;
import com.smarthost.smartroomallocator.model.OccupancyRequest;
import com.smarthost.smartroomallocator.model.OccupancyResponse;
import com.smarthost.smartroomallocator.service.RoomAllocatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmartRoomAllocatorApplicationTests {

    @Autowired
    private RoomAllocatorService roomAllocatorService;

    @Autowired
    private RoomController roomController;

    private OccupancyRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new OccupancyRequest(
                BigInteger.valueOf(6),
                BigInteger.valueOf(1),
                Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.99, 100.0, 101.0)
        );
    }

    @Test
    void testAllocateRoomsWithValidInput() {
        OccupancyResponse response = roomAllocatorService.allocateRooms(validRequest);

        assertNotNull(response);
        assertEquals(6, response.getUsagePremium());
        assertEquals(874.99, response.getRevenuePremium());
        assertEquals(1, response.getUsageEconomy());
        assertEquals(23.0, response.getRevenueEconomy());
    }

    @Test
    void testAllocateRoomsWithNullRequest() {
        OccupancyResponse response = roomAllocatorService.allocateRooms(null);
        assertNull(response);
    }

    @Test
    void testAllocateRoomsWithNegativeGuestFare() {
        OccupancyRequest request = new OccupancyRequest(
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                Arrays.asList(50.0, -10.0, 150.0)
                );
        OccupancyResponse response = roomAllocatorService.allocateRooms(request);
        assertNull(response);
    }

    @Test
    void testAllocateRoomsWithEmptyGuestList() {
        OccupancyRequest request = new OccupancyRequest(
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                Collections.emptyList()
        );
        OccupancyResponse response = roomAllocatorService.allocateRooms(request);

        assertNull(response);
    }

    @Test
    void testAllocateRoomsWithOnlyEconomyGuests() {
        OccupancyRequest request = new OccupancyRequest(
                BigInteger.valueOf(2),
                BigInteger.valueOf(2),
                Arrays.asList(10.0, 20.0, 30.0, 40.0)
        );
        OccupancyResponse response = roomAllocatorService.allocateRooms(request);

        assertNotNull(response);
        assertEquals(2, response.getUsagePremium());
        assertEquals(70.0, response.getRevenuePremium());
        assertEquals(2, response.getUsageEconomy());
        assertEquals(30.0, response.getRevenueEconomy());
    }

    @Test
    void testAllocateRoomsSampleInput2() {
        OccupancyRequest request = new OccupancyRequest(
                BigInteger.valueOf(4),
                BigInteger.valueOf(5),
                Arrays.asList(23.0,45.0,155.0,320.0,122.5,65.0,80.0,102.0,101.8,71.0,95.0)
        );
        OccupancyResponse response = roomAllocatorService.allocateRooms(request);

        assertNotNull(response);
        assertEquals(4, response.getUsagePremium());
        assertEquals(699.5, response.getRevenuePremium());
        assertEquals(5, response.getUsageEconomy());
        assertEquals(356.0, response.getRevenueEconomy());
    }

    @Test
    void testControllerAllocateRoomsWithNullRequest() {
        OccupancyResponse response = roomController.allocateRooms(null);
        assertNull(response);
    }

    @Test
    void testControllerAllocateRoomsWithInvalidInput() {
        OccupancyRequest invalidRequest = new OccupancyRequest(
                BigInteger.valueOf(1),
                BigInteger.valueOf(1),
                Arrays.asList(50.0, -100.0)
        );
        OccupancyResponse response = roomController.allocateRooms(invalidRequest);
        assertNull(response);
    }
}
