package com.flopbox.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("status")
public class Status {


    @GET
    public String status() {
        return "<div style=\"background: black;color: white;width: 100%;height: 100%;position: fixed;top: 0;left: 0;text-align: center;\"><h1>Welcome on flopbox api!</h1><br><h2>Server is running</h2></div>";
    }
}
