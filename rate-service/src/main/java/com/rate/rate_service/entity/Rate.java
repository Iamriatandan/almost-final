package com.rate.rate_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rates")
@Data
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateId;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private double firstNightPrice;

    @Column(nullable = false)
    private double extensionNightPrice;

    private Long roomId; //foreign key linking
}
