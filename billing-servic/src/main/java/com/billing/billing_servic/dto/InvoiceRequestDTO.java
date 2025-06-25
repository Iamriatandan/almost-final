package com.billing.billing_servic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequestDTO {
    private Long reservationId;
    private Long guestId;
    private Long roomId;
    private double totalAmount;

}
