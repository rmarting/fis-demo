package com.redhat.fis.demo.eip.aggregator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.globex.Account;
import org.globex.CorporateAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aggregator implementation which extract the id and salescontact
 * from CorporateAccount and update the Account
 */
public class AccountAggregator implements AggregationStrategy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountAggregator.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        
        // Account from CustomerRS
        Account account = oldExchange.getIn().getBody(Account.class);
        // Corporate Account from CustomerWS
        CorporateAccount corporateAccount = newExchange.getIn().getBody(CorporateAccount.class);

        // Adding to Account data from Corporate Account
        account.setClientId(corporateAccount.getId());
        account.setSalesRepresentative(corporateAccount.getSalesContact());
        
        // Updating exchange
        oldExchange.getIn().setBody(account);
        
        LOGGER.info("Account data updated. {}", account);
        
        return oldExchange;
    }
    
}