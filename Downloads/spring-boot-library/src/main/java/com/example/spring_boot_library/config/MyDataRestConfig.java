package com.example.spring_boot_library.config;

import com.example.spring_boot_library.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    private String theAllowedOrigins = "http://localhost:3000";


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config
    , CorsRegistry corsRegistry) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT,HttpMethod.PATCH, HttpMethod.POST, HttpMethod.DELETE};


        config.exposeIdsFor(Book.class);

        disableHttpMethods(Book.class, config, theUnsupportedActions);

        /* Configure CORS Mapping*/
        corsRegistry.addMapping(config.getBasePath() +"/**")
                .allowedOrigins(theAllowedOrigins);

    }
    /// disable HTTP methods for Book: PUT, POST, DELETE AND PATCH
    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
            config.getExposureConfiguration()
                    .forDomainType(theClass)
                    .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions)));

    }

}
