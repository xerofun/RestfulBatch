/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.resources;

import com.mycompany.batch.service.BatchService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Brian
 */
@Path("batch")
public class BatchResource
{
    @Inject
    private BatchService batchService;
    

    @Context
    private UriInfo context;

    public BatchResource()
    {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.resources.BatchResource
     * @return an instance of javax.ws.rs.core.Response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson()
    {
        batchService.dumpDetails();
        
        return Response.ok(batchService.getJobs()).build();
    }

    /**
     * PUT method for updating or creating an instance of BatchResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Response content)
    {
    }
}
