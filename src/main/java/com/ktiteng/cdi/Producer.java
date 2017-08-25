package com.ktiteng.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ktiteng.controller.AfdcConfig;
import com.ktiteng.controller.bean.AfdcConfigBean;

public class Producer {

	@Produces
	@Log
	public Logger createLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

	@Produces
	@Config
	public AfdcConfig createConfig(InjectionPoint injectionPoint) {
		String configName = injectionPoint.getAnnotated().getAnnotation(Config.class).configName();
		return new AfdcConfigBean(configName);
	}

}
