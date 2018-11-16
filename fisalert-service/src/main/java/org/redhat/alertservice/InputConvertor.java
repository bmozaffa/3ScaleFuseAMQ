package org.redhat.alertservice;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputConvertor implements Processor {

	@Autowired
	private UserInfo userInfo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		
		System.out.println(" exchange.getIn().getHeader(\"firstName\")"+ exchange.getIn().getHeader("firstName"));
		

		userInfo.setFirstName((String) exchange.getIn().getHeader("firstName"));
		userInfo.setLastName((String) exchange.getIn().getHeader("lastName"));
		userInfo.setDate((String) exchange.getIn().getHeader("accidentdate"));
		userInfo.setDescription((String) exchange.getIn().getHeader("description"));
		userInfo.setEmail((String) exchange.getIn().getHeader("email"));
		userInfo.setPhone((String) exchange.getIn().getHeader("phone"));
		userInfo.setAlertType((String) exchange.getIn().getHeader("alertType"));
		
		System.out.println("userInfo"+userInfo);
		 

		exchange.getIn().setBody(userInfo);

	}

}
