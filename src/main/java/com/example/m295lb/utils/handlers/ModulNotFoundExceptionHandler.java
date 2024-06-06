package com.example.m295lb.utils.handlers;

import com.example.m295lb.utils.expections.ModulNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ModulNotFoundExceptionHandler implements ExceptionMapper<ModulNotFoundException> {

    @Override
    public Response toResponse(ModulNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
