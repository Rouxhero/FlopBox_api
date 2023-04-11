package com.flopbox.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("status")
public class Status {


    @GET
    public String status() {
        return "<div style='background:#00000;color:#FFFFF'><h1>Welcome on flopbox api!</h1><br><h2>Server is running</h2></div>";
    }
}
