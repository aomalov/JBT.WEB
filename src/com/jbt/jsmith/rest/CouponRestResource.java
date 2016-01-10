package com.jbt.jsmith.rest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.MultivaluedMap;

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

	@POST
	@Path("/{id}/imgupload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadCouponImage(MultipartFormDataInput input,
								  @PathParam("id") long ID,
								  @Context HttpServletRequest httpServletRequest) throws CouponSystemException{
		CompanyFacade company;
		
		try {
			company = (CompanyFacade) httpServletRequest.getSession(false).getAttribute("userFacade");
		}
		catch (Exception e) {
			throw new CouponSystemException("You must log in as an Company for this operation");
		}
//		Coupon aCoupon=company.getCoupon(ID);
//		aCoupon.setIMAGE(input.);
		
//		company.updateCoupon(aCoupon);
		
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {

		 try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			String fileName = getFileName(header);

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

		  } catch (Exception e) {
			e.printStackTrace();
		  }

		}

	}
	
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");
				
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
}
