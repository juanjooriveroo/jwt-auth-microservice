   package com.juanjooriveroo.jwtauthservice.config;

   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.springframework.boot.context.event.ApplicationReadyEvent;
   import org.springframework.context.ApplicationListener;
   import org.springframework.stereotype.Component;

   @Component
   public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

       private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

       @Override
       public void onApplicationEvent(ApplicationReadyEvent event) {
           logger.info("Swagger listo en: http://localhost:8080/swagger-ui.html");
           logger.info("API Docs disponibles en: http://localhost:8080/api-docs");
       }
   }