package com.guest.guest_service.service.impl;

import com.guest.guest_service.dto.GuestRequestDTO;
import com.guest.guest_service.dto.GuestResponseDTO;
import com.guest.guest_service.entity.Guest;
import com.guest.guest_service.exception.*;
import com.guest.guest_service.mapper.GuestMapper;
import com.guest.guest_service.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestService;

    private Guest guest;
    private GuestRequestDTO guestRequestDTO;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        guestRequestDTO = new GuestRequestDTO("John Doe", "john@example.com", "9876543210");
//        guest = new Guest(1L, "John Doe", "john@example.com", "9876543210", "MEM-12345678");
//    }

    @Test
    void testCreateGuest_Success() {
        when(guestRepository.existsByEmail(anyString())).thenReturn(false);
        when(guestRepository.existsByPhone(anyString())).thenReturn(false);
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        GuestResponseDTO response = guestService.createGuest(guestRequestDTO);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    void testCreateGuest_DuplicateEmail() {
        when(guestRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> guestService.createGuest(guestRequestDTO));
    }

    @Test
    void testGetAllGuests() {
        when(guestRepository.findAll()).thenReturn(Collections.singletonList(guest));

        List<GuestResponseDTO> guests = guestService.getAllGuests();

        assertEquals(1, guests.size());
    }

    @Test
    void testGetGuestById_Found() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        GuestResponseDTO response = guestService.getGuestsById(1L);

        assertEquals("John Doe", response.getName());
    }

    @Test
    void testGetGuestById_NotFound() {
        when(guestRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(GuestNotFoundException.class, () -> guestService.getGuestsById(2L));
    }

    @Test
    void testDeleteGuest_Success() {
        when(guestRepository.existsById(1L)).thenReturn(true);
        doNothing().when(guestRepository).deleteById(1L);

        boolean result = guestService.deleteGuest(1L);

        assertTrue(result);
    }

    @Test
    void testDeleteGuest_NotFound() {
        when(guestRepository.existsById(2L)).thenReturn(false);

        assertThrows(GuestNotFoundException.class, () -> guestService.deleteGuest(2L));
    }
}
