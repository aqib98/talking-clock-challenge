package com.lbg.talkingclock;


import com.lbg.talkingclock.exceptions.TalkingClockException;
import com.lbg.talkingclock.service.TimeConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Component
@ConditionalOnProperty(prefix="typeOf",name="application",havingValue = "cli")
public class TalkingClockCliApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(TalkingClockCliApplication.class);


	private TimeConverterService timeConverterService;


	public TalkingClockCliApplication(@Autowired TimeConverterService timeConverterService) {
		this.timeConverterService = timeConverterService;
	}


	@Override
	public void run(String... args) throws Exception {
		Scanner in = new Scanner(System.in);
		try{

			LOG.info("INFO : Welcome");
			LOG.info("INFO : Would you like to provide time(yes) or convert current time(no) : yes or no");
			String decision = in.nextLine();
			String timeinHHmm = new SimpleDateFormat("HH:mm").format(new Date());
			boolean checking = true;
			boolean exit = false;

			while(checking){
				switch (decision.toLowerCase()) {
					case "yes":
						checking = false;
						LOG.info("INFO : Please provide time in HH:mm format Eg: 13:30");
						String providedTime = in.nextLine();
						timeinHHmm = providedTime;
						break;
					case "no":
						checking = false;
						break;
					case "exit":
						checking = false;
						System.exit(0);
						break;
					default:
						LOG.info("INFO : Please provide decision in either yes or no : yes or no - or exit to exit application");
						decision = in.nextLine();
						break;
				}
			}


			LOG.info("EXECUTING : command line runner");

			String humanReadableDateTime = timeConverterService.convertNumericTimeToHumanReadable(timeinHHmm);
			LOG.info("RESULT : Human readable time is");
			LOG.info(humanReadableDateTime);
		} catch (final Exception e){
			LOG.info("ERROR : {}",e.getMessage());
			throw new TalkingClockException(e.getMessage());
		} finally {
			in.close();
		}

	}


}
