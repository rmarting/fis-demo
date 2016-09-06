package com.redhat.fis.ws;

import javax.jws.WebService;

import com.redhat.fis.model.Account;
import com.redhat.fis.model.CorporateAccount;

@WebService
public interface CustomerWS {

    CorporateAccount updateAccount(Account account);

}
