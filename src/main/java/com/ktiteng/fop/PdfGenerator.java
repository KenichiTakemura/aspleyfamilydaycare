package com.ktiteng.fop;

import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;

public interface PdfGenerator {

	public Receipt generateReceipt(PaymentSchedule paymentSchedule);
}
