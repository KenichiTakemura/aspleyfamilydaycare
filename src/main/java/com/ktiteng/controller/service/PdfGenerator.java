package com.ktiteng.controller.service;

import java.io.IOException;

import org.w3c.dom.Document;

import com.ktiteng.entity.service.Receipt;

public interface PdfGenerator {

	Receipt generateDepositReceipt(Document source, String pdfLocation) throws IOException;

	Receipt generateEnrollmentFeeReceipt(Document source, String pdfLocation) throws IOException;

	Receipt generateWeeksReceipt(Document source, String pdfLocation) throws IOException;
}
