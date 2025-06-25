package com.guest.guest_service.mapper;

import com.guest.guest_service.dto.GuestRequestDTO;
import com.guest.guest_service.dto.GuestResponseDTO;
import com.guest.guest_service.entity.Guest;

public class GuestMapper {
    //convert entity to dto
    public static Guest toEntity(GuestRequestDTO dto) {
        return Guest.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhone())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .company(dto.getCompany())
                .build();
    }

    //convert dto to entity
    public static GuestResponseDTO toDto(Guest guest) {
        return GuestResponseDTO.builder()
                .guestId(guest.getGuestId())
                .memberCode(guest.getMemberCode())
                .name(guest.getName())
                .email(guest.getEmail())
                .phone(guest.getPhoneNumber())
                .address(guest.getAddress())
                .gender(guest.getGender())
                .company(guest.getCompany())
                .build();
    }
}
