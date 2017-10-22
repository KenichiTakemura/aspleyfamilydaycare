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
import com.ktiteng.cdi.Config;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.AfdcConfig;
import com.ktiteng.controller.bean.GmailSender;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.manager.PersistenceManager;
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

	@Inject
	@Config
	AfdcConfig config;

	@Inject
	GmailSender gmailSender;

	@Inject
	PersistenceManager pm;

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public String confirmDeposit(String childId, String amount, String receivedDate) throws IOException {
		Deposit deposit = new Deposit().setAmountInvoiced(Double.parseDouble(amount))
				.setDateReceived(toDate(receivedDate)).setReceived(true);
		deposit.setGeneratedAt();
		pc.addDeposit(childId, deposit);
		rc.issueReceipt(childId, deposit);
		pc.updatePayment(childId);
		return deposit.id();
	}

	@Override
	public void sendReceipt(String childId, String payableId) throws IOException {
		Payable payable = pc.find(childId, payableId);
		if (payable != null) {
			rc.sendReceipt(childId, payable.id());
		} else {
			throw new IOException("Not found.");
		}

	}

	@Override
	public String confirmBiWeeklyPayment(String childId, String weekStart, String amount, String receivedDate)
			throws IOException {
		PaymentSchedule paymentSchedule = new PaymentSchedule().setAmountInvoiced(Double.parseDouble(amount))
				.setBillingStartDate(toDate(weekStart)).setDateReceived(toDate(receivedDate)).setReceived(true);
		paymentSchedule.setGeneratedAt();
		pc.addPaymentSchedule(childId, paymentSchedule);
		rc.issueReceipt(childId, paymentSchedule);
		pc.updatePayment(childId);
		return paymentSchedule.id();
	}

	@Override
	public String confirmEntollmentFee(String childId, String amount, String receivedDate) throws IOException {
		EnrollmentFee enrollmentFee = new EnrollmentFee().setAmountInvoiced(Double.parseDouble(amount))
				.setDateReceived(toDate(receivedDate)).setReceived(true);
		enrollmentFee.setGeneratedAt();
		pc.addEnrollmentFee(childId, enrollmentFee);
		rc.issueReceipt(childId, enrollmentFee);
		pc.updatePayment(childId);
		return enrollmentFee.id();
	}

	@Override
	public void sendNewsLetter(String month, String filename) throws IOException {
		cc.getAllParents().forEach(p -> {
			String to = p.getEmailAddress();
			gmailSender.sendEmail(to, null, "KA Family Day Care " + month + " News Letter",
					"Dear parents\n\nPlease find " + month + " News Letter from KA Family Day Care.\n\n"
							+ "To unsubscribe from this News Letter, reply with Unsubscribe to this email.\n\n"
							+ "Aspley Family Day Care\n"
							+ "OH IN KWON(ANN)\n"
							+ "Email: aspleyfamilydaycare@gmail.com",
					String.format("%s/newsletter/%s", pm.getServicePath().toString(), filename));
		});
	}

}
