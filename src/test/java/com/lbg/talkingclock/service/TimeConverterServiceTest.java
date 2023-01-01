package com.lbg.talkingclock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TimeConverterServiceTest {

    @Autowired
    TimeConverterService timeConverterService;

    @ParameterizedTest
    @CsvFileSource(resources = "/SysTimeAndHumanTime.csv",numLinesToSkip = 1)
    void testConvertSystemTimeToHumanReadable(String input, String expected) {
        String humanReadableTimeText = timeConverterService.convertNumericTimeToHumanReadable(input);
        assertEquals(humanReadableTimeText,expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ExceptionHandling.csv",numLinesToSkip = 1)
    void testTimeConverterServiceExceptionHandling(String input, String expected){
        Exception exception = assertThrows(RuntimeException.class, () -> {
            timeConverterService.convertNumericTimeToHumanReadable(input);
        });
        assertEquals(exception.getMessage(),expected);
    }
}