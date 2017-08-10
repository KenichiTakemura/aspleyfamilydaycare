package com.ktiteng.controller;

import java.io.IOException;

import org.w3c.dom.Document;

import com.ktiteng.entity.Receipt;

public interface PdfGenerator {

	Receipt generateReceipt(Document source, String pdfLocation) throws IOException;
}
