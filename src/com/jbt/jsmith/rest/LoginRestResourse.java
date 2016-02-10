/**
 * 
 */
package com.jbt.jsmith.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void doLogin(UserWrapper user,
						@Context HttpServletRequest httpServletRequest,
						@Context HttpServletResponse httpServletResponse) throws CouponSystemException, IOException {
		
		CouponSystem theCouponius=CouponSystem.getInstance();
		
		CouponClientFacade aFacade = theCouponius.login(user.getUserName(), user.getPassword(), ClientType.valueOf(user.getClientType()));
		httpServletRequest.getSession(true).setAttribute("userFacade", aFacade);
		
		String redirectUrl = httpServletRequest.getContextPath()+ "/index.html#/welcome?clientType="+user.getClientType();
		httpServletResponse.setContentType("text/json; charset=UTF-8");
		httpServletResponse.setStatus(200);

	    PrintWriter out = httpServletResponse.getWriter();

		out.println("{"+
				"\"redirectUrl\":"+"\""+redirectUrl+"\""+
		   "}");

	    out.flush();
	    out.close();
	    
//		httpServletResponse.sendRedirect("/coupon.web/index.html#/welcome?clientType="+user.getClientType());
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
