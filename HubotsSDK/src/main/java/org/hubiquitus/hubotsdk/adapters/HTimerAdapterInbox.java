/*
 * Copyright (c) Novedia Group 2012.
 *
 *    This file is part of Hubiquitus
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *    of the Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 *    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 *    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *    You should have received a copy of the MIT License along with Hubiquitus.
 *    If not, see <http://opensource.org/licenses/mit-license.php>.
 */


package org.hubiquitus.hubotsdk.adapters;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Timer;
import java.util.TimerTask;

import org.hubiquitus.hapi.exceptions.MissingAttrException;
import org.hubiquitus.hapi.hStructures.HAlert;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterInbox;
import org.hubiquitus.util.TimerClass;
import org.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTimerAdapterInbox extends AdapterInbox{

	final Logger logger = LoggerFactory.getLogger(HTimerAdapterInbox.class);
	
	private String mode;
	private String crontab;
	private int period;
	
	private Scheduler scheduler = null;
	private Timer timer = null;
		
	public HTimerAdapterInbox() {}
		
	public HTimerAdapterInbox(String actor) {
		this.actor = actor;
	}

	@Override
	public void start() {
		//Timer using millisecond
		if(mode.equalsIgnoreCase("millisecond"))
		{
			if(period > 0) {
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
				      sendMessage();
			        }
			    }, 0, period);
			} else {
				logger.error("crontab malformat");
			}
		}
		//Timer using crontab
		if(mode.equalsIgnoreCase("crontab")) {
			try {
				JobDataMap jdm = new JobDataMap(); // pass the sendMessage data to the job class.
				jdm.put("actor", actor);
				SchedulerFactory sf = new StdSchedulerFactory();
				scheduler = sf.getScheduler();
				// define the job and tie it to the TimerClass
				JobDetail job = newJob(TimerClass.class)
				    .withIdentity(actor + "timerJob", "group1")
				    .usingJobData(jdm)
				    .build();
				// Trigger the job to run now and use the crontab
				Trigger trigger = newTrigger()
				    .withIdentity(actor + "Trigger", "group1")
				    .startNow()
				    .withSchedule(cronSchedule(crontab))
				    .build();
				// Tell quartz to schedule the job using our trigger
				scheduler.scheduleJob(job, trigger);
				scheduler.start();
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
	}
	
	public void sendMessage() {
		HMessage timerMessage = new HMessage();
		timerMessage.setAuthor(actor);
		timerMessage.setType("hAlert");
		HAlert halert = new HAlert();
		try {
			halert.setAlert(actor);
		} catch (MissingAttrException e) {
			logger.error("message: ", e);
		}
		timerMessage.setPayload(halert);
		put(timerMessage);
	}
	
	@Override
	public void stop() {
		if(mode.equalsIgnoreCase("crontab") && scheduler != null) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				logger.error(e.toString());
			}
		}
		if(mode.equalsIgnoreCase("millisecond") && timer != null) {
			timer.cancel();
		}
	}


	@Override
	public void setProperties(JSONObject properties) {	
		if(properties != null) {
			try {
				if(properties.has("mode")){
					this.mode = properties.getString("mode");
				}
				if(properties.has("crontab")){
					this.crontab = properties.getString("crontab");
				}
				if(properties.has("period")){
					this.period = properties.getInt("period");
				}
			} catch (Exception e) {
				logger.debug("message: ", e);
			}
		}
	}
}
