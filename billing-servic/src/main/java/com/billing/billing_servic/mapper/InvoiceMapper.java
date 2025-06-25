package com.billing.billing_servic.mapper;

import com.billing.billing_servic.dto.InvoiceRequestDTO;
import com.billing.billing_servic.dto.InvoiceRespomseDTO;
import com.billing.billing_servic.entity.Invoice;

public class InvoiceMapper {
    //dto to entity
    public static Invoice toEntity(InvoiceRequestDTO dto){
        return Invoice.builder()
                .reservationId(dto.getReservationId())
                .guestId(dto.getGuestId())
                .roomId(dto.getRoomId())
                .totalAmount(dto.getTotalAmount())
                .isPaid(false)
                .build();
    }

    //entity to dto
    public static InvoiceRespomseDTO toDto(Invoice invoice){
        return InvoiceRespomseDTO.builder()
                .invoiceId(invoice.getInvoiceId())
                .reservationId(invoice.getReservationId())
                .guestId(invoice.getGuestId())
                .roomId(invoice.getRoomId())
                .totalAmount(invoice.getTotalAmount())
                .createdAt(invoice.getCreatedAt())
                .isPaid(invoice.isPaid())
                .build();
    }
}
