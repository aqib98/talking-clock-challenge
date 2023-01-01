package com.lbg.talkingclock.exceptions;

import com.lbg.talkingclock.service.TimeConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(TimeConverterService.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(TalkingClockException.class)
    public ResponseEntity<String> handleExceptions(TalkingClockException e){
        LOG.error("ERROR: {}",e.getMessage());
        HttpStatus status;
        if(e.getMessage().equals(TalkingClockException.TalkingClockExceptionMessage.APPLICATION_FAILED)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<String>(e.getMessage(),status);
    }
}
