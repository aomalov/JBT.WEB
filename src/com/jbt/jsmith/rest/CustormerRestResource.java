package com.jbt.jsmith.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Customer;
import com.jbt.jsmith.facade.AdminFacade;

@Path("/customer")
public class CustormerRestResource {
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getCustomers(@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
		
		return admin.getAllCustomers();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
		
		return admin.getCustomer(ID);
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteCustomer(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
		
		admin.removeCustomer(admin.getCustomer(ID));
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Customer newCustomer,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		AdminFacade admin=(AdminFacade)theCouponius.login("Admin", "1234", ClientType.Admin);
		
		admin.createCustomer(newCustomer);
	}

}
