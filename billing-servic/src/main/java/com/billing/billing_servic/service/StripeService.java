package com.billing.billing_servic.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface StripeService {
    PaymentIntent createPaymentIntent(Long invoiceId) throws StripeException;
}
