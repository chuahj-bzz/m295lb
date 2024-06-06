package com.example.m295lb;

import com.example.m295lb.services.OwnerController;
import com.example.m295lb.services.PetController;
import com.example.m295lb.utils.authentication.AuthenticationFilter;

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
                        // new project
                        AuthenticationFilter.class,
                        OwnerController.class,
                        PetController.class

                ));
    }
}