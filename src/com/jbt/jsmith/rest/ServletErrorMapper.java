package com.jbt.jsmith.rest;

import javax.servlet.ServletException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ServletErrorMapper  implements ExceptionMapper<ServletException> {

    @Override
    public Response toResponse(ServletException cause) {
    	GenericEntity<CouponRestErrorMessage> entity=new GenericEntity<CouponRestErrorMessage>(new CouponRestErrorMessage(cause.getMessage(),"danger")){};
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(entity).build();
    }

}
