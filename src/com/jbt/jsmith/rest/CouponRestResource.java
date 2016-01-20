package com.jbt.jsmith.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.dto.Coupon;
import com.jbt.jsmith.facade.CompanyFacade;


@Path("/company/coupons")
public class CouponRestResource {

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
	}

//	@POST
//	@Path("/{id}/imgupload")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public void uploadCouponImage(@PathParam("id") long ID,
//								  @Context HttpServletRequest httpServletRequest) throws CouponSystemException{
//		CompanyFacade company;
//		
//		try {
//			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
//		}
//		catch (Exception e) {
//			throw new CouponSystemException("You must log in as an Company for this operation");
//		}
//		Coupon aCoupon=company.getCoupon(ID);
//		aCoupon.setIMAGE(input.);
		
//		company.updateCoupon(aCoupon);
		
//		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//		List<InputPart> inputParts = uploadForm.get("uploadedFile");
//
//		for (InputPart inputPart : inputParts) {
//
//		 try {
//
//			MultivaluedMap<String, String> header = inputPart.getHeaders();
//			String fileName = getFileName(header);

//			//convert the uploaded file to inputstream
//			InputStream inputStream = (InputStream) inputPart.getBody(InputStream.class,null);
//
//			byte [] bytes = IOUtils.toByteArray(inputStream);
//				
//			//constructs upload file path
//			fileName = UPLOADED_FILE_PATH + fileName;
//				
//			writeFile(bytes,fileName);
//				
//			System.out.println("Done");

//		  } catch (Exception e) {
//			e.printStackTrace();
//		  }
//
//		}
//	}
	
	
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
	
	
}
