package com.guest.guest_service.service;

import com.guest.guest_service.dto.GuestRequestDTO;
import com.guest.guest_service.dto.GuestResponseDTO;

import java.util.List;

public interface GuestService {
    //all guest methods
    GuestResponseDTO createGuest(GuestRequestDTO guestRequestDTO);
    List<GuestResponseDTO> getAllGuests();
    GuestResponseDTO getGuestsById(Long guestId);
    GuestResponseDTO updateGuest(Long guestId, GuestRequestDTO guestRequestDTO);
    boolean deleteGuest(Long guestId);
}
