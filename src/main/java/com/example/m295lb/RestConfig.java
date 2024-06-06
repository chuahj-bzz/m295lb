package com.example.m295lb;

import com.example.m295lb.services.OwnerController;
import com.example.m295lb.services.PetController;
import com.example.m295lb.utils.authentication.AuthenticationFilter;
import com.example.m295lb.utils.handlers.DeleteNotExistingModulHandler;
import com.example.m295lb.utils.handlers.FormatNotValidHandler;
import com.example.m295lb.utils.handlers.ModulNotCreatedExceptionHandler;
import com.example.m295lb.utils.handlers.ModulNotFoundExceptionHandler;
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
                        // Exception Handler
                        ModulNotFoundExceptionHandler.class,
                        ModulNotCreatedExceptionHandler.class,
                        DeleteNotExistingModulHandler.class,
                        FormatNotValidHandler.class,
                        // new project
                        AuthenticationFilter.class,
                        OwnerController.class,
                        PetController.class

                ));
    }
}