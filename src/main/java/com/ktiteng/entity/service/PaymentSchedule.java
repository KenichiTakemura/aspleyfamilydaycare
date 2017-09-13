package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.ktiteng.controller.account.BillingWeek;
import com.ktiteng.util.Utils;

public class PaymentSchedule extends Payable<PaymentSchedule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate billingStartDate;
	private LocalDate billingEndDate;

	public PaymentSchedule() {
		setId(Utils.getId());
	}

	@Override
	public PaymentSchedule setAmountInvoiced(double amountInvoiced) {
		super.setAmountInvoiced(amountInvoiced);
		return self();
	}

	public LocalDate getBillingStartDate() {
		return billingStartDate;
	}

	public PaymentSchedule setBillingStartDate(LocalDate billingStartDate) {
		this.billingStartDate = billingStartDate;
		setBillingEndDate(billingStartDate.plusDays(BillingWeek.numberOfDays));
		return self();
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public PaymentSchedule setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
		return self();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof PaymentSchedule)) {
			return false;
		}
		PaymentSchedule other = (PaymentSchedule) obj;
		return billingStartDate.equals(other.billingStartDate) &&
				billingEndDate.equals(other.billingEndDate);
	}

}
