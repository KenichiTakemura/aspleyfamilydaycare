package com.ktiteng.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktiteng.controller.bean.GmailSender;

public class Producer {

	@Produces
	@Log
	private Logger createLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}

//	@Produces
//	private GmailSender createGmailSender(InjectionPoint injectionPoint) {
//		return new GmailSender();
//	}

}
