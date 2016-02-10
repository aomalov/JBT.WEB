package com.jbt.jsmith.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.jbt.ejb.beans.Income;
import com.jbt.ejb.delegate.IncomeDelegate;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.facade.CompanyFacade;

import com.jbt.ejb.beans.OperationType;


@Path("/company/coupons")
public class CouponRestResource {
	
	@EJB
	IncomeDelegate delegateStub;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCoupons(@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		return company.getAllCoupons();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		return company.getCoupon(ID);
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteCoupon(@PathParam("id") long ID,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		
		company.removeCoupon(company.getCoupon(ID));
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon newCoupon,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		
		company.createCoupon(newCoupon);
		delegateStub.storeIncome(new Income(company.getId(), OperationType.COMPANY_CREATE.getInvoiceSum(newCoupon.getPRICE()) , new Date(System.currentTimeMillis()), OperationType.COMPANY_CREATE, company.getName()));
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(Coupon newCoupon,@Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CompanyFacade company;
		
		System.out.println(newCoupon);
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		
		company.updateCoupon(newCoupon);
		delegateStub.storeIncome(new Income(company.getId(), OperationType.COMPANY_UPDATE.getInvoiceSum(newCoupon.getPRICE()) , new Date(System.currentTimeMillis()), OperationType.COMPANY_UPDATE, company.getName()));
	}

	@POST
    @Path("/upload-image")
    @Consumes("multipart/form-data")
    public void uploadFile(@MultipartForm FileUploadForm form,
    					   @Context HttpServletRequest httpServletRequest,
    					   @Context ServletContext servletContext, 
    					   @Context HttpServletResponse httpServletResponse ) throws IOException, CouponSystemException {
  
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
		
        String fileName = Long.toString (ThreadLocalRandom.current().nextLong (), 36) + "_" + (form.getFileName() == null ? "Unknown" : form.getFileName() );
        String messageType="success",messageText="Image uploaded";
        String redirectUrl = httpServletRequest.getContextPath()+ "/index.html#/company/coupons";
         
        String completeFilePath = servletContext.getInitParameter("imageFolder") +  fileName;
        try
        {
            //Save the file
            File file = new File(completeFilePath);
              
            if (!file.exists())
            {
                file.createNewFile();
            }
      
            FileOutputStream fos = new FileOutputStream(file);
      
            fos.write(form.getFileData());
            fos.flush();
            fos.close();
            
            //update Coupon image property
    		Coupon aCoupon=company.getCoupon(Long.parseLong(form.getCouponId()));
    		aCoupon.setIMAGE(fileName);
    		company.updateCoupon(aCoupon);
        }
        catch (IOException e)
        {
            messageType="danger";
            messageText="Error image upload";
        }
        //Build a response to return
        httpServletResponse.sendRedirect(redirectUrl);
//	    httpServletResponse.setContentType("text/json; charset=UTF-8");
//	    httpServletResponse.setStatus(200);
//
//	    PrintWriter out = httpServletResponse.getWriter();
//		out.println("{"+
//				"\"messageText\":"+"\""+messageText+"\","+  
//				"\"messageType\":"+"\""+messageType+"\","+
//				"\"redirectUrl\":"+"\""+redirectUrl+"\""+
//		   "}");
//
//	    out.flush();
//	    out.close();        
    }
	
	@GET
	@Path("/companyInvoices")
	@Produces(MediaType.APPLICATION_JSON)
	public Income[] getInvoicesByCompany(@Context HttpServletRequest httpServletRequest) throws CouponSystemException {
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}		
		
		return delegateStub.getAllCompanyIncome(company.getId()).toArray(new Income[0]);
	}
	
	
}
