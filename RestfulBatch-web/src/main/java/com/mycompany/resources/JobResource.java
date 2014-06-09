package com.mycompany.resources;

import com.mycompany.batch.service.JobService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Brian
 */
@Path("job")
@RequestScoped
public class JobResource
{
    @Inject private JobService jobService;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of JobResource
     */
    public JobResource()
    {
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobs()
    {
        return Response.ok(jobService.getJobs()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Response content)
    {
        throw new UnsupportedOperationException();
    }
}
