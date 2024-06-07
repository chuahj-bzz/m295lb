package com.example.m295lb;

import com.example.m295lb.services.PetController;
import com.example.m295lb.utils.authentication.AuthenticationFilter;

import com.example.m295lb.utils.handlers.InternalServerErrorExceptionHandler;
import com.example.m295lb.utils.handlers.NotAuthorizedExceptionHandler;
import com.example.m295lb.utils.handlers.ResourceNotFoundExceptionHandler;
import com.example.m295lb.utils.handlers.ValidationExceptionHandler;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/resources")
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(
                Arrays.asList(
                        AuthenticationFilter.class,
                        PetController.class,
                        // handlers
                        InternalServerErrorExceptionHandler.class,
                        NotAuthorizedExceptionHandler.class,
                        ResourceNotFoundExceptionHandler.class,
                        ValidationExceptionHandler.class

                ));
    }
}