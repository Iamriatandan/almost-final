package com.billing.billing_servic.service;

import com.billing.billing_servic.dto.PaymentRequestDTO;
import com.billing.billing_servic.dto.PaymentResponseDTO;
import com.billing.billing_servic.entity.Payment;
import com.billing.billing_servic.exception.InvoiceNotFoundException;
import com.billing.billing_servic.mapper.PaymentMapper;
import com.billing.billing_servic.repository.InvoiceRepository;
import com.billing.billing_servic.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public PaymentResponseDTO makePayment(PaymentRequestDTO requestDTO) {
        log.info("Processing payment for invoiceId: {}", requestDTO.getInvoiceId());

        if (!invoiceRepository.existsById(requestDTO.getInvoiceId())) {
            log.error("Invoice not found for payment: {}", requestDTO.getInvoiceId());
            throw new InvoiceNotFoundException("Invoice not found with ID: " + requestDTO.getInvoiceId());
        }

        Payment payment = PaymentMapper.toEntity(requestDTO);
        payment.setPaymentStatus("SUCCESS"); // Simulated logic
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);
        log.debug("Payment processed: {}", saved);
        return PaymentMapper.toDTO(saved);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {
        log.info("Fetching payment by ID: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> {
                    log.error("Payment not found: {}", paymentId);
                    return new RuntimeException("Payment not found with ID: " + paymentId);
                });
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByInvoiceId(Long invoiceId) {
        log.info("Fetching all payments for invoiceId: {}", invoiceId);
        return paymentRepository.findByStripePaymentId(String.valueOf(invoiceId)).stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
