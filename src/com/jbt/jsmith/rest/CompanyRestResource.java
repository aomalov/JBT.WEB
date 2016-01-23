package com.jbt.jsmith.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Company;
import com.jbt.jsmith.facade.AdminFacade;

@Path("/companies")
public class CompanyRestResource {
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getCompanies(@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Administrator for this operation");
		}
		return admin.getAllCompanies();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Administrator for this operation");
		}
		return admin.getCompany(ID);
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteCompany(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Administrator for this operation");
		}
		
		admin.removeCompany(admin.getCompany(ID));
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company newCompany,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Administrator for this operation");
		}
		
		admin.createCompany(newCompany);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company newCompany,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		AdminFacade admin;
		
		try {
			admin = (AdminFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Administrator for this operation");
		}
		
		admin.updateCompany(newCompany);
	}

}
