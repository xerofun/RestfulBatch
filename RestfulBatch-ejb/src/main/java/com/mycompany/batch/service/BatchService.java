/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batch.service;

import com.mycompany.jsl.Job;
import java.util.Map;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Brian
 */
@Stateless
@LocalBean
public class BatchService
{
    @Inject private JobService jobService;
    
    private final Logger logger = LoggerFactory.getLogger(BatchService.class);

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
     * TODO: Migrate this to JobService
     */
    public Map<String, Job> getJobs()
    {
        return jobService.getJobs();
    }
}
