/**
 * 
 */
package com.jbt.jsmith.rest.exceptions;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.jbt.jsmith.CouponSystemException;
import com.jbt.jsmith.rest.beans.CouponRestErrorMessage;

/**
 * @author andrewm
 *
 */
@Provider
public class CouponErrorMapper  implements ExceptionMapper<CouponSystemException> {

    @Override
    public Response toResponse(CouponSystemException cause) {
    	GenericEntity<CouponRestErrorMessage> entity=new GenericEntity<CouponRestErrorMessage>(new CouponRestErrorMessage(cause.getMessage(),"danger")){};
    	Response resp=Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(entity).build();
        return resp;
    }

}