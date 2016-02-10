package com.jbt.jsmith.rest;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jbt.ejb.beans.Income;
import com.jbt.ejb.delegate.IncomeDelegate;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.facade.AdminFacade;
import com.jbt.jsmith.facade.CompanyFacade;

@Path("/admin")
public class AdminReportsResource {

	@EJB
	IncomeDelegate delegateStub;
	
	@GET
	@Path("/allInvoices")
	@Produces(MediaType.APPLICATION_JSON)
	public Income[] getAllInvoices(@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Admin for this operation");
		}		
		
		return delegateStub.getAll().toArray(new Income[0]);
	}
	
	@GET
	@Path("/allInvoicesByCustomer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Income[] getAllInvoicesByCustomer(
			@PathParam("id") long custId,
			@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Admin for this operation");
		}		
		
		return delegateStub.getAllCustomerIncome(custId).toArray(new Income[0]);
	}
	
	@GET
	@Path("/allInvoicesByCompany/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Income[] getAllInvoicesByCompany(
			@PathParam("id") long compId,
			@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Admin for this operation");
		}		
		
		return delegateStub.getAllCompanyIncome(compId).toArray(new Income[0]);
	}

}
