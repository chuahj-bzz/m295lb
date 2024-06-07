package com.example.m295lb.utils.handlers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {
    @Override
    public Response toResponse(NotAuthorizedException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
    }
}
