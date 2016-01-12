package com.jbt.jsmith.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.facade.CompanyFacade;
import com.jbt.jsmith.facade.CustomerFacade;


@Path("/customer/coupons")
public class ClientCouponRestResource {

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons(@Context HttpServletRequest httpServletRequest,
										 @DefaultValue("yes") @QueryParam("owned") String showOwned) throws CouponSystemException {
		CustomerFacade customer;
		
		try {
			customer = (CustomerFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as a Customer for this operation");
		}
		if(showOwned.equals("no"))
			return customer.getCouponsOnSale();
		else 
			return customer.getAllPurchased();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CustomerFacade customer;
		
		try {
			customer = (CustomerFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as a Customer for this operation");
		}
		return customer.getCoupon(ID);
	}
	

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void purchaseCoupon(Coupon newCoupon,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CustomerFacade customer;
		
		try {
			customer = (CustomerFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as a Customer for this operation");
		}
		
		customer.purchaseCoupon(newCoupon);
	}
	
}
