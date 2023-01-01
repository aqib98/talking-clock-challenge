package com.lbg.talkingclock.service;


import com.lbg.talkingclock.exceptions.TalkingClockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Service
public class TimeConverterService {

    private static Logger LOG = LoggerFactory.getLogger(TimeConverterService.class);


    private static String[] humanFriendlyNumbers = {"o'clock", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen", "twenty", "twenty one",
            "twenty two", "twenty three", "twenty four",
            "twenty five", "twenty six", "twenty seven",
            "twenty eight", "twenty nine"};

    private static String PAST = "past";
    private static String TO = "to";
    private static String SPACE = " ";
    private static String QUARTER = "Quarter";
    private static String HALF = "Half";

    public String convertNumericTimeToHumanReadable(String numericTime) {
        LOG.info("INFO: EXECUTING : TimeConverterService::convertNumericTimeToHumanReadable");

        StringBuilder humanReadableTextTime = new StringBuilder();
        int[] hours_mins = extractHoursAndMinsFromNumTime(numericTime);
        int hours = hours_mins[0];
        int mins = hours_mins[1];
        int hoursIn12HrFormat = hours % 12 == 0 ? 12 : hours % 12;

        if (mins == 0) {
            humanReadableTextTime.append(StringUtils.capitalize(humanFriendlyNumbers[hoursIn12HrFormat])).append(SPACE).append(humanFriendlyNumbers[mins]);
        } else if (mins == 1) {
            humanReadableTextTime.append(StringUtils.capitalize(humanFriendlyNumbers[mins])).append(PAST).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins == 59) {
            humanReadableTextTime.append(StringUtils.capitalize(humanFriendlyNumbers[60-mins])).append(TO).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins == 15) {
            humanReadableTextTime.append(QUARTER).append(SPACE).append(PAST).append(SPACE).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins == 30) {
            humanReadableTextTime.append(HALF).append(SPACE).append(PAST).append(SPACE).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins == 45) {
            humanReadableTextTime.append(QUARTER).append(SPACE).append(TO).append(SPACE).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins <= 30) {
            humanReadableTextTime.append(StringUtils.capitalize(humanFriendlyNumbers[mins])).append(SPACE).append(PAST).append(SPACE).append(humanFriendlyNumbers[hoursIn12HrFormat]);
        } else if (mins > 30) {
            humanReadableTextTime.append(StringUtils.capitalize(humanFriendlyNumbers[60 - mins])).append(SPACE).append(TO).append(SPACE).append(humanFriendlyNumbers[hoursIn12HrFormat+1]);
        }

        LOG.info("INFO: COMPLETED : TimeConverterService::convertNumericTimeToHumanReadable");

        return humanReadableTextTime.toString();


    }

    private int[] extractHoursAndMinsFromNumTime(String numericTime) {
        try {
            LocalTime.parse(numericTime);
            String[] systemTimeArr = numericTime.split(":");
            int[] hours_mins = {Integer.parseInt(systemTimeArr[0]), Integer.parseInt(systemTimeArr[1])};
            return hours_mins;
        } catch (final Exception e) {
            if (e instanceof NumberFormatException) {
                LOG.info("ERROR : {}",TalkingClockException.TalkingClockExceptionMessage.INPUT_NOT_NUMERIC);
                throw new TalkingClockException(TalkingClockException.TalkingClockExceptionMessage.INPUT_NOT_NUMERIC);
            } else if (e instanceof DateTimeParseException) {
                LOG.info("ERROR : {}",TalkingClockException.TalkingClockExceptionMessage.INVALID_TIME);
                throw new TalkingClockException(TalkingClockException.TalkingClockExceptionMessage.INVALID_TIME);
            } else {
                LOG.info("ERROR : {}",TalkingClockException.TalkingClockExceptionMessage.APPLICATION_FAILED);
                throw new TalkingClockException(TalkingClockException.TalkingClockExceptionMessage.APPLICATION_FAILED);
            }

        }
    }



}
