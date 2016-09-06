package com.redhat.fis.demo.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.fis.demo.model.Account;

@Path("/customerservice/")
public interface CustomerRest {
	
	// TODO Documented with swagger

    @POST
    @Path("/enrich")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Account enrich(Account customer);

}
