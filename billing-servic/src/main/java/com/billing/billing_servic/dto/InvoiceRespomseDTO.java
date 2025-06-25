package com.billing.billing_servic.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRespomseDTO {
    private Long invoiceId;
    private Long reservationId;
    private Long guestId;
    private Long roomId;
    private double totalAmount;
    private LocalDateTime createdAt;
    private boolean isPaid;
}
