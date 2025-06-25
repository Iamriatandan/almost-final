package com.billing.billing_servic.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long paymentId;
    private Long invoiceId;
    private String stripePaymentId;
    private double amountPaid;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime paidAt;
}
