package org.example;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/hello")
public class GreetingResource {


    @Inject
    GreetingResource(ApplePushNotificationConfing confing) {
        Logger.getLogger("org.example.GreetingResource").log(Level.INFO, () -> confing.server());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
}
