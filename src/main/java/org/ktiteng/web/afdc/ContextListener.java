package org.ktiteng.web.afdc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("init");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
