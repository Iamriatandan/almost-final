package com.billing.billing_servic.mapper;

import com.billing.billing_servic.dto.PaymentRequestDTO;
import com.billing.billing_servic.dto.PaymentResponseDTO;
import com.billing.billing_servic.entity.Payment;

public class PaymentMapper {

    public static Payment toEntity(PaymentRequestDTO dto) {
        return Payment.builder()
                .invoiceId(dto.getInvoiceId())
                .stripePaymentId(dto.getStripePaymentId())
                .amountPaid(dto.getAmountPaid())
                .paymentMethod(dto.getPaymentMethod())
                .paymentStatus("PENDING") // will update after processing
                .build();
    }

    public static PaymentResponseDTO toDTO(Payment payment) {
        return PaymentResponseDTO.builder()
                .paymentId(payment.getPaymentId())
                .invoiceId(payment.getInvoiceId())
                .stripePaymentId(payment.getStripePaymentId())
                .amountPaid(payment.getAmountPaid())
                .paymentStatus(payment.getPaymentStatus())
                .paymentMethod(payment.getPaymentMethod())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
