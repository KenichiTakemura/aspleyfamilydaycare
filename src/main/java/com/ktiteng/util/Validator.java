package com.ktiteng.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validator {

	private Validator() {
	}

	public static String isValidEmailAddress(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			return null;
		}
		return email;
	}
}
