package com.billing.billing_servic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long invoiceId;
    private String stripePaymentId;
    private double amountPaid;
    private String paymentMethod;
}
