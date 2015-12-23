/**
 * 
 */
package com.jbt.jsmith.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.jbt.jsmith.CouponSystemException;

/**
 * @author andrewm
 *
 */
@Provider
public class CouponErrorMapper  implements ExceptionMapper<CouponSystemException> {

    @Override
    public Response toResponse(CouponSystemException cause) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new CouponRestErrorMessage(cause.getMessage())).build();
    }

}