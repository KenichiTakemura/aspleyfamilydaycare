package com.ktiteng.fop;

import java.io.IOException;

import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;

public interface PdfGenerator {

	Receipt generateReceipt(PaymentSchedule paymentSchedule) throws IOException;
}
