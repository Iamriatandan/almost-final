package com.billing.billing_servic.service;

import com.billing.billing_servic.dto.PaymentRequestDTO;
import com.billing.billing_servic.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    List<PaymentResponseDTO> getPaymentsByInvoiceId(Long invoiceId);
}
