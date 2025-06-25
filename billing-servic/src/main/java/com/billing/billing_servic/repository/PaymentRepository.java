package com.billing.billing_servic.repository;

import com.billing.billing_servic.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByStripePaymentId(String stripePaymentId);
}
