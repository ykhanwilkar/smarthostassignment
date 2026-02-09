package com.smarthost.smartroomallocator.controller;

import com.smarthost.smartroomallocator.model.OccupancyRequest;
import com.smarthost.smartroomallocator.model.OccupancyResponse;
import com.smarthost.smartroomallocator.service.RoomAllocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.smarthost.smartroomallocator.constants.ApplicationLiterals.OCCUPANCY_REQUEST_URI;

@RestController
public class RoomController {

    private final RoomAllocatorService roomAllocatorService;

    @Autowired
    public RoomController(RoomAllocatorService roomAllocatorService) {
        this.roomAllocatorService = roomAllocatorService;
    }

    @PostMapping(OCCUPANCY_REQUEST_URI)
    public OccupancyResponse allocateRooms(@RequestBody OccupancyRequest request) {
        return roomAllocatorService.allocateRooms(request);
    }
}
