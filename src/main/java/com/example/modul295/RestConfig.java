package com.example.modul295;

import com.example.modul295.service.ModuleController;
import com.example.modul295.service.RaumController;
import com.example.modul295.util.authentication.AuthenticationFilter;
import com.example.modul295.util.expecton.DeleteNotExistingModuleException;
import com.example.modul295.util.handler.DeleteNotExistingModulHandler;
import com.example.modul295.util.handler.FormatNotValidHandler;
import com.example.modul295.util.handler.ModulNotCreatedExceptionHandler;
import com.example.modul295.util.handler.ModulNotFoundExceptionHandler;
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
                        ModuleController.class,
                        ModulNotFoundExceptionHandler.class,
                        ModulNotCreatedExceptionHandler.class,
                        DeleteNotExistingModulHandler.class,
                        FormatNotValidHandler.class,
                        AuthenticationFilter.class,
                        RaumController.class

                ));
    }
}