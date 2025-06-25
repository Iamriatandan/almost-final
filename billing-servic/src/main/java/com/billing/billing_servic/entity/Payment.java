package com.billing.billing_servic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long invoiceId;

    @Column(nullable = false, unique = true)
    private String stripePaymentId;

    @Column(nullable = false)
    private double amountPaid;

    @Column(nullable = false)
    private String paymentStatus; // Enum can be added later

    @Column(nullable = false)
    private String paymentMethod; // Enum can be added later

    @Column(nullable = false)
    private LocalDateTime paidAt;
}
