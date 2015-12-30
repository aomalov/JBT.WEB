/**
 * 
 */
package com.jbt.jsmith.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.jbt.jsmith.CouponSystem;
import com.jbt.jsmith.CouponSystem.ClientType;
import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.facade.CouponClientFacade;

/**
 * @author andrew
 *
 */
@Path("/login")
public class LoginRestResourse {

	@POST 
	@Path("/")
	public void doLogin(@FormParam("userName") String userName,
						@FormParam("password") String password,
						@FormParam("clientType") String clientType,
						@Context HttpServletRequest httpServletRequest,
						@Context HttpServletResponse httpServletResponse) throws CouponSystemException, IOException {
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		
		CouponClientFacade aFacade = theCouponius.login(userName, password, ClientType.valueOf(clientType));
		httpServletRequest.getSession(true).setAttribute("userFacade", aFacade);
		
//	    String redirectUrl = httpServletRequest.getContextPath()+ "/index.html";
//	    httpServletResponse.setContentType("text/json; charset=UTF-8");
//
//	    PrintWriter out = httpServletResponse.getWriter();
//
//		out.println("{"+
//				"\"messageText\":"+"\"You logged in as "+clientType+"!\","+  
//				"\"messageType\":"+"\"success\","+
//				"\"redirectUrl\":"+"\""+redirectUrl+"\""+
//		   "}");
//
//	    out.flush();
//	    out.close();
	    
		httpServletResponse.sendRedirect("/coupon.web/index.html#/welcome?clientType="+clientType);
	}
	
	
}
