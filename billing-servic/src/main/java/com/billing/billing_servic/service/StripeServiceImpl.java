package com.billing.billing_servic.service;

import com.billing.billing_servic.entity.Invoice;
import com.billing.billing_servic.exception.InvoiceNotFoundException;
import com.billing.billing_servic.repository.InvoiceRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService{
    private final InvoiceRepository invoiceRepository;

    @Override
    public PaymentIntent createPaymentIntent(Long invoiceId) throws  StripeException{
        log.info("Creating Stripe payment intent for Invoice ID: {}", invoiceId);

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found for ID: " + invoiceId));

        // Stripe requires amount in cents
        long amountInCents = (long) (invoice.getTotalAmount() * 100);

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency("INR")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                        )
                        .build();

        return PaymentIntent.create(params);
    }
}
