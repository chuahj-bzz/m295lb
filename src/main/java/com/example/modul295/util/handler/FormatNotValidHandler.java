package com.example.modul295.util.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class FormatNotValidHandler extends Throwable implements ExceptionMapper<FormatNotValidHandler> {

    @Override
    public Response toResponse(FormatNotValidHandler e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}