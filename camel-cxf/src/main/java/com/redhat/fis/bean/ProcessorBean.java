package com.redhat.fis.bean;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.cxf.jaxrs.impl.ResponseImpl;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.fis.model.Account;
import com.redhat.fis.model.CorporateAccount;

public class ProcessorBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorBean.class);

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
	public void manageSOAPResponse(Exchange exchange) throws IOException {
		// Corporate Account from WS
		CorporateAccount ca = (CorporateAccount) exchange.getIn().getBody(MessageContentsList.class).get(0);

		LOGGER.info("CorporateAccount from CustomerWS: {}", ca);
		
		exchange.getIn().setBody(ca);
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
        ResponseImpl responseImpl = exchange.getIn().getBody(ResponseImpl.class);
        Account account = responseImpl.readEntity(Account.class);

		LOGGER.info("Account from CustomerRS: {}", account);
		
		exchange.getIn().setBody(account);
	}
	
	
	public Map<String, Object> defineNamedParameters(@Body Account account) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("CLIENT_ID", account.getClientId());
		map.put("SALES_CONTACT", account.getSalesRepresentative());
		map.put("COMPANY_NAME", account.getCompany().getName());
		map.put("COMPANY_GEO", account.getCompany().getGeo());
		map.put("COMPANY_ACTIVE", account.getCompany().isActive());
		map.put("CONTACT_FIRST_NAME", account.getContact().getFirstName());
		map.put("CONTACT_LAST_NAME", account.getContact().getLastName());
		map.put("CONTACT_ADDRESS", account.getContact().getStreetAddr());
		map.put("CONTACT_CITY", account.getContact().getCity());
		map.put("CONTACT_STATE", account.getContact().getState());
		map.put("CONTACT_ZIP", account.getContact().getZip());
		map.put("CONTACT_PHONE", account.getContact().getPhone());
		map.put("CREATION_DATE", getCurrentTime());
		map.put("CREATION_USER", "fuse_usecase");

		return map;
	}

	private static Timestamp getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		return currentTimestamp;
	}
}
