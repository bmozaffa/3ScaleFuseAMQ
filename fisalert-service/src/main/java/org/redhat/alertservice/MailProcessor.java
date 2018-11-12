package org.redhat.alertservice;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailProcessor implements Processor {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	Environment environment;

	@Autowired
	private UserInfo userInfo;

	@Override
	public void process(Exchange exchange) throws Exception {

		UserInfo userInfo = (UserInfo) exchange.getIn().getBody();

		String messageBody = AlertMessage.enumInIf(userInfo.getAlertType(), userInfo);

		System.out.println(userInfo.getEmail());
		System.out.println(userInfo.getAlertType());

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(environment.getProperty("mailFrom"));
		message.setTo(userInfo.getEmail());
		message.setSubject("Alert Notifcation");
		message.setText(messageBody);

		emailSender.send(message);

	}
}