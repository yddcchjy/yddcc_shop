package com.ego.ms.task;


import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ego.commons.utils.DateUtils;

public class OrderTimeoutJobs extends QuartzJobBean
{
    private static final Log LOG_RECORD = LogFactory.getLog(OrderTimeoutJobs.class);

    @Autowired
    private CheckOrderTimeoutTask clusterQuartz;


    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        LOG_RECORD.info("begin to execute task," + DateUtils.dateToString(new Date()));

        clusterQuartz.CheckOrderTimeout(1);

        LOG_RECORD.info("end to execute task," + DateUtils.dateToString(new Date()));

    }
}

