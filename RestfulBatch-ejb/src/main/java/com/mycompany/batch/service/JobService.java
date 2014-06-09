/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.batch.service;

import com.mycompany.jsl.Job;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brian
 */
@Singleton
@LocalBean
@Startup
public class JobService
{
    private final Logger logger = LoggerFactory.getLogger(JobService.class);
    
    private final Map<String, Job> jobs = new HashMap<>();
    
    @PostConstruct
    private void initialize()
    {
        logger.info("Initializing JobService");
        
        try
        {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources("META-INF/batch-jobs");
            
            if (resources.hasMoreElements())
            {
                URL batchJobsURL = resources.nextElement();
                File  batchJobsRoot = new File(batchJobsURL.toURI());
                File[] files = batchJobsRoot.listFiles();
     
                JAXBContext jaxbContext = JAXBContext.newInstance("com.mycompany.jsl");
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                
                for (File file : files)
                {
                    if (file.getPath().endsWith(".xml"))
                    {
                        String jobName = file.getName().replaceFirst("\\.xml$", "");
                        jobs.put(jobName, ((JAXBElement<Job>)unmarshaller.unmarshal(file)).getValue());
                    }
                }
            }
        }
        catch (JAXBException | URISyntaxException | IOException ex)
        {
            logger.error("Unable to load job definition files", ex);
        }

        logger.info("Done Initializing JobService");
    }
    
    public Map<String, Job> getJobs()
    {
        return jobs;
    }
    
    
    // TODO: Set up null object for this
    public Job getJobById(final String id)
    {
        return jobs.get(id);
    }
}
