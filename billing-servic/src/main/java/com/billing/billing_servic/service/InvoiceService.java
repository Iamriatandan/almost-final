package com.billing.billing_servic.service;

import com.billing.billing_servic.dto.InvoiceRequestDTO;
import com.billing.billing_servic.dto.InvoiceRespomseDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceRespomseDTO createInvoice(InvoiceRequestDTO requestDTO);

    InvoiceRespomseDTO getInvoiceById(Long invoiceId);

    List<InvoiceRespomseDTO> getAllInvoices();

    boolean markInvoiceAsPaid(Long invoiceId);

    List<InvoiceRespomseDTO> getInvoicesByGuestId(Long guestId);
}
