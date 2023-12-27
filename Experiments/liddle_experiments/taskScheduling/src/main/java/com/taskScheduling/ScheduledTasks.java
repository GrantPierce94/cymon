package com.taskScheduling;

/** imports */
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/* 
 * example task scheduler class
 * @author David Liddle
 * @date 10/29/23
 */

@Component
public class ScheduledTasks {
	
	private static final Logger log                  = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {

		log.info("The time is now {}", dateFormat.format(new Date()));
	
	}

}
