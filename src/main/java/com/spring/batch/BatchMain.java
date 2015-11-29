/*
 * Copyright 1999-29 Nov 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.spring.batch;


import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangbolin 29 Nov 2015 8:49:19 pm
 */
public class BatchMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-batch.xml");
        JobParametersBuilder jobPara = new JobParametersBuilder();  //设置文件路径参数 
        
        Job job = (Job)ctx.getBean("sampleFlow"); 
        JobLauncher launcher = (JobLauncher)ctx.getBean("jobLauncher");  
        
        
        JobExecution result = null;  
        
        try {
            result = launcher.run(job, jobPara.toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } 
        
        ExitStatus es = result.getExitStatus();  
        if(es.getExitCode().equals(ExitStatus.COMPLETED.getExitCode()))  {
            System.out.println("finished");
        } else {
            System.out.println("failed");
        }
    }
}
