/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Brian
 */
@Stateless
@LocalBean
public class BatchService
{

    public void dumpDetails()
    {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        
        System.out.println(jobOperator.getJobNames());
        
        Properties properties = new Properties();
        properties.setProperty("monthYear", "JAN-2013");
        
        jobOperator.start("PayrollJob", properties);
        
        System.out.println(jobOperator.getJobNames());
        
        for (JobInstance jobInstance : jobOperator.getJobInstances("payroll", 0, Integer.MAX_VALUE - 1))
        {
            for (JobExecution jobExecution : jobOperator.getJobExecutions(jobInstance))
            {
                System.out.println(
                        jobExecution.getJobName() + ", "
                        + jobExecution.getExecutionId() + ", "
                        + jobExecution.getBatchStatus() + ", "
                        + jobExecution.getExitStatus() + ", "
                        + jobExecution.getStartTime() + ", "
                        + jobExecution.getEndTime());
            }
        }
    }
    
    

    /**
     * Return the Set of Job IDs used to launch jobs by the Batch Runtime.
     * These job definitions live in META-INF/batch-jobs and follow the pattern
     * of {Job ID}.xml 
     * @return the job ids
     * 
     * TODO: Cache this result
     * TODO: Make this work for jar's
     * TODO: Add FileFilter for .xml files
     */
    public Set<String> getJobIDs()
    {
        Set<String> jobIDs = new HashSet<>();
        
        try
        {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources("META-INF/batch-jobs");
            
            if (resources.hasMoreElements())
            {
                URL batchJobsURL = resources.nextElement();
                File  batchJobsRoot = new File(batchJobsURL.toURI());
                String[] files = batchJobsRoot.list();
                
                for (String file : files)
                {
                    if (file.endsWith(".xml"))
                    {
                        jobIDs.add(file.replaceFirst("\\.xml$", ""));
                    }
                }
            }
        }
        catch (IOException | URISyntaxException ex)
        {
            Logger.getLogger(BatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jobIDs;
    }
}
