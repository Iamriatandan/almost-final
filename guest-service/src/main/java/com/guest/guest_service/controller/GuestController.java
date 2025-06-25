package com.guest.guest_service.controller;

import com.guest.guest_service.dto.GuestRequestDTO;
import com.guest.guest_service.dto.GuestResponseDTO;
import com.guest.guest_service.service.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/guests")
public class GuestController {
    @Autowired
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    //create new guest
    @PostMapping("/createGuest")
    public ResponseEntity<GuestResponseDTO> createGuest(@RequestBody GuestRequestDTO guestRequestDTO){
        log.info("Received request to create a new guest with email: {}", guestRequestDTO.getEmail());
        GuestResponseDTO created = guestService.createGuest(guestRequestDTO);
        log.info("Successfully created guest with email: {}", guestRequestDTO.getEmail());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //Get all guests
    @GetMapping("/allGuests")
    public ResponseEntity<List<GuestResponseDTO>> getAllGuests(){
        log.info("Fetching all guests...");
        List<GuestResponseDTO> allGuests = guestService.getAllGuests();
        log.info("Successfully fetched {} guests.", allGuests.size());
        return new ResponseEntity<>(allGuests, HttpStatus.OK);
    }

    //get Guest BY id
    @GetMapping("/getGuestById/{guestId}")
    public ResponseEntity<GuestResponseDTO> getGuestById(@PathVariable Long guestId) {
        log.info("Fetching guest with ID: {}", guestId);
        GuestResponseDTO guestById = guestService.getGuestsById(guestId);
        log.info("Successfully fetched guest with ID: {}", guestId);
        return new ResponseEntity<>(guestById, HttpStatus.OK);
    }


    //update a guest
    @PutMapping("/updateGuestById/{id}")
    public ResponseEntity<GuestResponseDTO> updateGuest(@PathVariable Long id, @RequestBody GuestRequestDTO guestRequestDTO) {
        log.info("Received request to update guest with ID: {}", id);
        GuestResponseDTO updated = guestService.updateGuest(id, guestRequestDTO);
        log.info("Successfully updated guest with ID: {}", id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //delete a guest
    @DeleteMapping("/deleteGuestById/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        log.info("Received request to delete guest with ID: {}", id);
        boolean deleted = guestService.deleteGuest(id);
        if (deleted) {
            log.info("Successfully deleted guest with ID: {}", id);
            return new ResponseEntity<>("Guest with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            log.error("Failed to delete guest with ID: {}", id);
            return new ResponseEntity<>("Guest with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
