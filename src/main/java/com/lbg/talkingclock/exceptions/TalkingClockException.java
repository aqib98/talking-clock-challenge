package com.lbg.talkingclock.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class TalkingClockException extends RuntimeException{

    @Getter
    @RequiredArgsConstructor
    public enum TalkingClockExceptionMessage {
        INPUT_NOT_NUMERIC("Given input is not a valid time"),
        APPLICATION_FAILED("APPLICATION_FAILED"),
        INVALID_TIME("Given input is not a valid time");

        final String description;
    }

    public TalkingClockException(final TalkingClockExceptionMessage exceptionMessage){
        super(exceptionMessage.description);
    }

    public TalkingClockException(final String exceptionMessage){
        super(exceptionMessage);
    }


}
