package com.lbg.talkingclock.Controller;

import com.lbg.talkingclock.TalkingClockCliApplication;
import com.lbg.talkingclock.service.TimeConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@ConditionalOnProperty(prefix="typeOf",name="application",havingValue = "rest")
public class TalkingClockController {

    private static Logger LOG = LoggerFactory.getLogger(TalkingClockController.class);

    private TimeConverterService timeConverterService;


    public TalkingClockController(@Autowired  TimeConverterService timeConverterService) {
        this.timeConverterService = timeConverterService;
    }

    @RequestMapping("/talking-clock")
    public ResponseEntity<String> talkingClock() throws Exception {
        LOG.info("INFO: STARTING : TalkingClockController::talkingClock GET /talking-clock");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String inputTime = formatter.format(date);

        String result = timeConverterService.convertNumericTimeToHumanReadable(inputTime);

        LOG.info("INFO: COMPLETED : TalkingClockController::talkingClock GET /talking-clock");
        return new ResponseEntity<String>(result, HttpStatus.OK);

    }

    @RequestMapping("/talking-clock/{inputTime}")
    public ResponseEntity<String> talkingClockByInput(@PathVariable String inputTime)  throws Exception {
        LOG.info("INFO: STARTING : TalkingClockController::talkingClockByInput GET /talking-clock/{inputTime}");
        String result = timeConverterService.convertNumericTimeToHumanReadable(inputTime);
        LOG.info("INFO: COMPLETED : TalkingClockController::talkingClockByInput GET /talking-clock/{inputTime}");
        return new ResponseEntity<String>(result, HttpStatus.OK);


    }
}
