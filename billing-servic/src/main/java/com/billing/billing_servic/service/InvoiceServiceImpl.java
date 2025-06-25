package com.billing.billing_servic.service;

import com.billing.billing_servic.dto.InvoiceRequestDTO;
import com.billing.billing_servic.dto.InvoiceRespomseDTO;
import com.billing.billing_servic.entity.Invoice;
import com.billing.billing_servic.exception.InvoiceNotFoundException;
import com.billing.billing_servic.mapper.InvoiceMapper;
import com.billing.billing_servic.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    //create invoice
    @Override
    public InvoiceRespomseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        log.info("Creating invoice for reservationId: {}", requestDTO.getReservationId());
        Invoice invoice = InvoiceMapper.toEntity(requestDTO);
        Invoice saved = invoiceRepository.save(invoice);
        log.debug("Invoice created: {}", saved);
        return InvoiceMapper.toDto(saved);
    }

    @Override
    public InvoiceRespomseDTO getInvoiceById(Long invoiceId) {
        log.info("Fetching invoice with ID: {}", invoiceId);
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> {
                    log.error("Invoice not found: {}", invoiceId);
                    return new InvoiceNotFoundException("Invoice not found with ID: " + invoiceId);
                });
        return InvoiceMapper.toDto(invoice);
    }

    @Override
    public List<InvoiceRespomseDTO> getAllInvoices() {
        log.info("Fetching all invoices");
        return invoiceRepository.findAll().stream()
                .map(InvoiceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean markInvoiceAsPaid(Long invoiceId) {
        log.info("Marking invoice as paid: {}", invoiceId);
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> {
                    log.error("Invoice not found while marking as paid: {}", invoiceId);
                    return new InvoiceNotFoundException("Invoice not found with ID: " + invoiceId);
                });
        invoice.setPaid(true);
        invoiceRepository.save(invoice);
        log.debug("Invoice marked as paid: {}", invoiceId);
        return true;
    }

    @Override
    public List<InvoiceRespomseDTO> getInvoicesByGuestId(Long guestId) {
        return null;
    }
}
