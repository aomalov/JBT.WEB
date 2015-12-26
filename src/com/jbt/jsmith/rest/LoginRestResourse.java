/**
 * 
 */
package com.jbt.jsmith.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * @author andrew
 *
 */
@Path("/login")
public class LoginRestResourse {


	@POST 
	public void doLogin(@FormParam( String userName,String password,String clientType,@Context HttpServletRequest httpServletRequest) {
		
	}
	
	
}
