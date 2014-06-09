package com.mycompany.resources;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author Brian
 */
@javax.ws.rs.ApplicationPath("webresources") 
public class ApplicationConfig extends Application 
{

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(JacksonFeature.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources)
    {
        resources.add(com.mycompany.resources.BatchResource.class);
        resources.add(com.mycompany.resources.JobResource.class);
    }

}
