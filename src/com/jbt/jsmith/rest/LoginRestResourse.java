/**
 * 
 */
package com.jbt.jsmith.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
@Path("/")
public class LoginRestResourse {

	@POST 
	@Path("login")
	public void doLogin(@FormParam("userName") String userName,
						@FormParam("password") String password,
						@FormParam("clientType") String clientType,
						@Context HttpServletRequest httpServletRequest,
						@Context HttpServletResponse httpServletResponse) throws CouponSystemException, IOException {
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		
		CouponClientFacade aFacade = theCouponius.login(userName, password, ClientType.valueOf(clientType));
		httpServletRequest.getSession(true).setAttribute("userFacade", aFacade);
	    
		httpServletResponse.sendRedirect("/coupon.web/index.html#/welcome?clientType="+clientType);
	}
	
	
	@GET
	@Path("logout")
	public void doLogout(@Context HttpServletRequest httpServletRequest,
						@Context HttpServletResponse httpServletResponse) throws IOException {
		HttpSession session = httpServletRequest.getSession(false);
		if(session!=null) {
			session.setAttribute("userFacade", null);
			session.invalidate();
		}
		httpServletResponse.sendRedirect("/coupon.web/index.html#/welcome?clientType=Guest");
	}
	
}
