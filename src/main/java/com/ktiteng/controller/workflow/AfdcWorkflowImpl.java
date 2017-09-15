package com.ktiteng.controller.workflow;

import static com.ktiteng.util.Utils.toDate;
import java.io.IOException;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.afdc.AfdcWorkflow;
import com.ktiteng.afdc.InvoiceType;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.PaymentSchedule;

@Default
@ApplicationScoped
public class AfdcWorkflowImpl implements AfdcWorkflow {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;

	@Inject
	PaymentController pc;

	@Inject
	ReceiptController rc;

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public void confirmPayment(String childId, InvoiceType type, String weekStart, String amount, String receivedDate)
			throws IOException {
		double _amount = Double.parseDouble(amount);
		Payable payable = null;
		if (type == InvoiceType.DEPOSIT) {
			Deposit deposit = new Deposit().setAmountInvoiced(_amount).setDateReceived(toDate(receivedDate))
					.setReceived(true);
			deposit.setGeneratedAt();
			pc.addDeposit(childId, deposit);
			payable = deposit;
		} else if (type == InvoiceType.ENROLLMENT) {
			EnrollmentFee enrollmentFee = new EnrollmentFee().setAmountInvoiced(_amount)
					.setDateReceived(toDate(receivedDate)).setReceived(true);
			enrollmentFee.setGeneratedAt();
			pc.addEnrollmentFee(childId, enrollmentFee);
			payable = enrollmentFee;
		} else if (type == InvoiceType.WEEKS) {
			PaymentSchedule paymentSchedule = new PaymentSchedule().setAmountInvoiced(_amount)
					.setBillingStartDate(toDate(weekStart)).setDateReceived(toDate(receivedDate)).setReceived(true);
			paymentSchedule.setGeneratedAt();
			pc.addPaymentSchedule(childId, paymentSchedule);
			payable = paymentSchedule;
		}
		rc.issueReceipt(childId, payable);
		pc.updatePayment(childId);
	}

	@Override
	public void sendReceipt(String childId, String payableId) throws IOException {
		Payable payable = pc.find(childId, payableId);
		if (payable != null) {
			rc.sendReceipt(childId, payable.getId());
		} else {
			throw new IOException("Not found.");
		}
	}

}
