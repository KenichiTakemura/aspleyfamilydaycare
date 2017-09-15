package com.ktiteng.controller.service;

import java.io.IOException;

import org.w3c.dom.Document;

import com.ktiteng.afdc.InvoiceType;
import com.ktiteng.entity.service.Receipt;

public interface PdfGenerator {

	Receipt generateReceipt(Document source, String pdfLocation, InvoiceType type) throws IOException;

}
