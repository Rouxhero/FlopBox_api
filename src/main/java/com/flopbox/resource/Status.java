package com.flopbox.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("status")
public class Status {


    @GET
    public String status() {
        return "<h1>Welcome on flopbox api!</h1><br><h2>Server is running</h2>";
    }
}
