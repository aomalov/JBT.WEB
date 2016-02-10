package com.jbt.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import com.jbt.jsmith.CouponSystemException;

import sun.security.krb5.SCDynamicStoreConfig;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter(filterName="sessionFilter" , urlPatterns = {"/rest/customers/*","/rest/admin/*","/rest/companies/*","/rest/company/*","/rest/customer/*"} )
public class SessionFilter implements Filter {


	public void init(FilterConfig fConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session=((HttpServletRequest)request).getSession(false);
		boolean failedRequest=false;

		if(session==null) failedRequest=true;
		else if (session.getAttribute("userFacade")==null) failedRequest=true;
		
		if(failedRequest) {
			// Set up your response here
		    HttpServletResponse hres = (HttpServletResponse) response;
		    String redirectUrl = ((HttpServletRequest)request).getContextPath()+ "/index.html#/login";
		    hres.setContentType("text/json; charset=UTF-8");
		    hres.setStatus(505);

		    PrintWriter out = hres.getWriter();

			out.println("{"+
					"\"messageText\":"+"\"You must log in!\","+  
					"\"messageType\":"+"\"danger\","+
					"\"redirectUrl\":"+"\""+redirectUrl+"\""+
			   "}");

		    out.flush();
		    out.close();
		}
		else
		  chain.doFilter(request, response); 
	}

	public void destroy() {}

}
