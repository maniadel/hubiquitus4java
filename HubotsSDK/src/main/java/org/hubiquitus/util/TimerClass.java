package org.hubiquitus.util;

import org.hubiquitus.hubotsdk.adapters.HTimerAdapterInbox;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimerClass implements Job {
	final  Logger logger = LoggerFactory.getLogger(HTimerAdapterInbox.class);
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			//create a copy of timer adapter 
			HTimerAdapterInbox temp = new HTimerAdapterInbox();
			//set the data the function sendMessage needs.	
			temp.setActor(context.getJobDetail().getJobDataMap().getString("actor"));
			temp.sendMessage();
		} catch (Exception e) {
			logger.info("message: ",e);
		}
	}

}
