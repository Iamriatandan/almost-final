package com.billing.billing_servic.repository;

import com.billing.billing_servic.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Optional<Invoice> findByReservationId(Long reservationId);
}
