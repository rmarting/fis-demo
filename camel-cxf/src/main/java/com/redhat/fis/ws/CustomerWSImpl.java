package com.redhat.fis.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.fis.model.Account;
import com.redhat.fis.model.CorporateAccount;

@WebService(endpointInterface = "com.redhat.fis.ws.CustomerWS")
public class CustomerWSImpl implements CustomerWS {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWSImpl.class);

	List<String> sales;

	public CustomerWSImpl() {
		sales = new ArrayList<String>();
		sales.add("James Strachan");
		sales.add("Claus Ibsen");
		sales.add("Hiram Chirino");
		sales.add("Jeff Bride");
		sales.add("Chad Darby");
		sales.add("Rachel Cassidy");
		sales.add("Bernard Tison");
		sales.add("Nandan Joshi");
		sales.add("Rob Davies");
		sales.add("Guillaume Nodet");
		sales.add("Marc Little");
		sales.add("Mario Fusco");
		sales.add("James Hetfield");
		sales.add("Kirk Hammett");
		sales.add("Steve Perry");
	}

	@WebResult(name = "CorporateAccount")
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	public CorporateAccount updateAccount(Account account) {
		LOGGER.info("Updating Account: {}", account);

		// Create new Corporate Account
		CorporateAccount ca = new CorporateAccount();
		ca.setCompany(account.getCompany());
		ca.setContact(account.getContact());
		ca.setId(genRandom());
		ca.setSalesContact(getRandomSales(sales));
		
		LOGGER.info("New CorporateAccount created: {}", ca);

		return ca;
	}

	public static int genRandom() {
		return new Random().nextInt(100);
	}

	public static String getRandomSales(List<String> list) {
		// 0-11
		int index = new Random().nextInt(list.size());
		return list.get(index);
	}

}
