package com.ktiteng.controller.service;

import java.io.IOException;

import org.w3c.dom.Document;

import com.ktiteng.controller.service.ReceiptController.ReceiptType;
import com.ktiteng.entity.service.Receipt;

public interface PdfGenerator {

	Receipt generateReceipt(Document source, String pdfLocation, ReceiptType type) throws IOException;

}
