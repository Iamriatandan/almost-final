package com.guest.guest_service.service.impl;

import com.guest.guest_service.dto.GuestRequestDTO;
import com.guest.guest_service.dto.GuestResponseDTO;
import com.guest.guest_service.entity.Guest;
import com.guest.guest_service.exception.*;
import com.guest.guest_service.mapper.GuestMapper;
import com.guest.guest_service.repository.GuestRepository;
import com.guest.guest_service.service.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    private final GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    //add a guest
    @Override
    public GuestResponseDTO createGuest(GuestRequestDTO guestRequestDTO) {
        log.info("Creating guest with email: {}", guestRequestDTO.getEmail());

        // Check if guest already exists
        if(guestRepository.existsByEmail(guestRequestDTO.getEmail()) ||
                guestRepository.existsByPhoneNumber(guestRequestDTO.getPhone())) {
            log.error("Guest already exists with email: {} or phone: {}", guestRequestDTO.getEmail(), guestRequestDTO.getPhone());
            throw new DuplicateEmailException("Guest with given email already exists.");
        }

        // Create Guest entity from DTO
        Guest guest = GuestMapper.toEntity(guestRequestDTO);

        // Generate and set memberCode before saving
        String memberCode = "MEM-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        guest.setMemberCode(memberCode);

        // Save the guest entity
        Guest saved = guestRepository.save(guest);

        log.info("Guest with email: {} successfully created.", guestRequestDTO.getEmail());

        // Return the response DTO
        return GuestMapper.toDto(saved);
    }


    //get all guests
    @Override
    public List<GuestResponseDTO> getAllGuests() {
        log.info("Fetching all guests...");
        //find-stream-map-store as list
        return guestRepository.findAll().stream().map(GuestMapper::toDto).collect(Collectors.toList());
    }

    //get guest by id
    @Override
    public GuestResponseDTO getGuestsById(Long guestId) {
        log.info("Fetching guest with ID: {}", guestId);
        Guest guest = guestRepository.findById(guestId).orElseThrow(()-> new GuestNotFoundException("Guest with id : " + guestId + " not found."));
        return GuestMapper.toDto(guest);
    }

    //update a guest by id
    @Override
    public GuestResponseDTO updateGuest(Long guestId, GuestRequestDTO guestRequestDTO) {
        log.info("Attempting to update guest with ID: {}", guestId);

        //First check if guest exists or not
        Guest existingGuest = guestRepository.findById(guestId).orElseThrow(()-> {
            log.error("Guest with ID: {} not found.", guestId);
        return new GuestNotFoundException("Guest with id : " + guestId + " not found.");
        });

        //check if duplicate email entering again
        if (!existingGuest.getEmail().equals(guestRequestDTO.getEmail()) &&
                guestRepository.existsByEmail(guestRequestDTO.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + guestRequestDTO.getEmail());
        }

        //Check if duplicate phone-Number is entering again
        if(!existingGuest.getPhoneNumber().equals(guestRequestDTO.getPhone())
                && guestRepository.existsByPhoneNumber(guestRequestDTO.getPhone()))
        {
            throw new DuplicatePhoneNumberException("Phone number already exists " + guestRequestDTO.getPhone());
        }

        Guest guestToUpdate = GuestMapper.toEntity(guestRequestDTO);
        guestToUpdate.setGuestId(guestId);
        Guest update = guestRepository.save(guestToUpdate);
        log.info("Guest with ID: {} successfully updated.", guestId);
        return GuestMapper.toDto(update);
    }

    //delete a guest
    @Override
    public boolean deleteGuest(Long guestId) {
        log.info("Attempting to delete guest with ID: {}", guestId);

        if(!guestRepository.existsById(guestId)){
            log.error("Guest with ID: {} not found, cannot delete.", guestId);
            throw new GuestNotFoundException("Guest with id :" +  guestId + " not found.");
        }
        guestRepository.deleteById(guestId);
        log.info("Guest with ID: {} successfully deleted.", guestId);
        return true;
    }
}
