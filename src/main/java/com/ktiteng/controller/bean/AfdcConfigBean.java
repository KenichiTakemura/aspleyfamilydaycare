package com.ktiteng.controller.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Properties;

import javax.inject.Inject;

import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.AfdcConfig;

public class AfdcConfigBean implements AfdcConfig {
	@Inject
	@Log
	private Logger log;

	private Properties props;
	private String configName;
	public static Path configPath = Paths.get(System.getProperty("user.home"), ".afdc", "conf");

	public AfdcConfigBean(String configName) {
		this.configName = configName;
		props = new Properties();
		File configFile = Paths.get(configPath.toString(), this.configName).toFile();
		try {
			props.load(new FileInputStream(configFile));
		} catch (IOException e) {
			throw new RuntimeException("Cannot read config file.", e);
		}
	}

	@Override
	public String getKaAdminEmail() {
		return props.getProperty("kaadminemail");
	}

	@Override
	public String getAfdcAdminEmail() {
		return props.getProperty("afdcadminemail");
	}

	@Override
	public String getGamilUserName() {
		return props.getProperty("gmailusername");
	}

	@Override
	public char[] getGmailPassword() {
		return props.getProperty("gmailpassword").toCharArray();
	}

	@Override
	public String getEmailContents() {
		int num = Integer.parseInt(props.getProperty("emailContentNum"));
		String[] contents = new String[num];
		for (int i = 0; i < num; i++) {
			contents[i] = props.getProperty("emailContent" + (i + 1));
		}
		return MessageFormat.format(props.getProperty("emailContent"), contents);
	}

}
