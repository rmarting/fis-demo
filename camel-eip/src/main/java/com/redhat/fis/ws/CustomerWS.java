package com.redhat.fis.ws;

import javax.jws.WebService;

import org.globex.Account;
import org.globex.CorporateAccount;

@WebService
public interface CustomerWS {

    CorporateAccount updateAccount(Account account);

}
