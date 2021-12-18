package com.remdesk.api;

import com.remdesk.api.api.environment.Environment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RemdeskApiApplication {

    private static ConfigurableApplicationContext context;


    public static void main( final String[] args ) {
        context = SpringApplication.run( RemdeskApiApplication.class, args );
    }


    @Bean
    public WebMvcConfigurer corsConfigurer( final Environment environment ) {


        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings( final CorsRegistry registry ) {

                final String pattern = "/**";
                final String origins = environment.getEnv( "request.allowed-origin" );

                registry.addMapping( pattern )
                        .allowedMethods( "GET", "POST", "PUT", "DELETE", "PATCH" )
                        .allowedOrigins( origins )
                        .exposedHeaders( "Location", "Authorization" );
            }
        };
    }


    public static void restart() {
        ApplicationArguments args = context.getBean( ApplicationArguments.class );

        Thread thread = new Thread( () -> {
            context.close();
            context = SpringApplication.run( RemdeskApiApplication.class, args.getSourceArgs() );
        } );

        thread.setDaemon( false );
        thread.start();
    }
}
