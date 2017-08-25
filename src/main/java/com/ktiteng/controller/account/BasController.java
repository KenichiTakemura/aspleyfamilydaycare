package com.ktiteng.controller.account;

import java.time.LocalDate;

import com.ktiteng.entity.account.BasQuarterly;

public interface BasController {
	BasQuarterly getBasYearly(LocalDate year);

	BasQuarterly getBasQuarterly(LocalDate quarter);
}
