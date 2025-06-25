package com.guest.guest_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestResponseDTO {
    private Long guestId;
    private String memberCode;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String company;
}
