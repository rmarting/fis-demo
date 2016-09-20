package com.redhat.fis.demo.eip.processor;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.globex.Account;
import org.globex.Company;
import org.globex.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HelperProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelperProcessor.class);

	public void createAccount(Exchange exchange) throws IOException {
		// Company
		Company company = new Company();
		company.setName("Robocops");
		company.setGeo("NA");
		company.setActive(true);
		
		// Contact
		Contact contact = new Contact();
		contact.setFirstName("Bill");
		contact.setLastName("Smith");
		contact.setStreetAddr("100 N Park Ave.");
		contact.setCity("Phoenix");
		contact.setState("AZ");
		contact.setZip("85017");
		contact.setPhone("200-555-1000");
		
		// Account
		Account account = new Account();
		account.setCompany(company);
		account.setContact(contact);
		
		LOGGER.info("New Account: {}", account);
		
		// Updating Exchange
		exchange.getIn().setBody(account);
	}
	
	/**
	 * Converts the original Account value to its JSon representation
	 * 
	 * @param exchange Message with a Account
	 * 
	 * @throws IOException
	 */
	public void convertAccountToString(Exchange exchange) throws IOException {
		// Account
		Account account = exchange.getIn().getBody(Account.class);
		
		// Converting POJO to JSon
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(account);
		
		LOGGER.info("Account in JSON: {}", json);
		
		// Updating Exchange
		exchange.getIn().setBody(json);
	}

	/**
	 * Extracts from SOAP Response the CorporateAccount and updates the Exchange with it
	 * 
	 * @param exchange Message with the SOAP Response from CustomerWS
	 * 
	 * @throws IOException
	 */
	public void manageRESTResponse(Exchange exchange) throws IOException {
		// Getting Account POJO value from CustomerRS
		Account account = exchange.getIn().getBody(Account.class);

		LOGGER.info("Account from CustomerRS: {}", account);
		
		exchange.getIn().setBody(account);
	}
	
}
