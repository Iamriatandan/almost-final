package com.billing.billing_servic.controller;

import com.billing.billing_servic.dto.InvoiceRequestDTO;
import com.billing.billing_servic.dto.InvoiceRespomseDTO;
import com.billing.billing_servic.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public InvoiceRespomseDTO createInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        log.info("Creating invoice for reservationId: {}", requestDTO.getReservationId());
        return invoiceService.createInvoice(requestDTO);
    }

    @GetMapping("/{invoiceId}")
    public InvoiceRespomseDTO getInvoiceById(@PathVariable Long invoiceId) {
        log.info("Fetching invoice by ID: {}", invoiceId);
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping("/reservation/{reservationId}")
    public InvoiceRespomseDTO getInvoiceByReservationId(@PathVariable Long reservationId) {
        log.info("Fetching invoice for reservationId: {}", reservationId);
        return invoiceService.getInvoiceById(reservationId);
    }

    @GetMapping("/guest/{guestId}")
    public List<InvoiceRespomseDTO> getInvoicesByGuestId(@PathVariable Long guestId) {
        log.info("Fetching invoices for guestId: {}", guestId);
        return invoiceService.getInvoicesByGuestId(guestId);
    }

    @GetMapping
    public List<InvoiceRespomseDTO> getAllInvoices() {
        log.info("Fetching all invoices");
        return invoiceService.getAllInvoices();
    }
}