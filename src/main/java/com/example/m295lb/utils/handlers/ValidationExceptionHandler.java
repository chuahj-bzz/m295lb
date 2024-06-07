package com.example.m295lb.utils.handlers;

import com.example.m295lb.utils.exceptions.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
