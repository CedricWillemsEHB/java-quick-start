package com.ehb.dnd.model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/json/user")
public class JSONService {

    @GET
    @Path("/get")
    @Produces("application/json")
    public User getUserInJSON() {

        User user = new User();
        user.setEmail("iPad 3");
        user.setPassword(""+999);

        return user;

    }
}
