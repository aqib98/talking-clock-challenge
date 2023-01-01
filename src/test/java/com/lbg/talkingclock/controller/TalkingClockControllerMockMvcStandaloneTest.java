package com.lbg.talkingclock.controller;

import com.lbg.talkingclock.Controller.TalkingClockController;
import com.lbg.talkingclock.exceptions.ExceptionHandler;
import com.lbg.talkingclock.service.TimeConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TalkingClockControllerMockMvcStandaloneTest {

    private MockMvc mockMvc;

    @Mock
    private TimeConverterService timeConverterService;

    @InjectMocks
    private TalkingClockController talkingClockController;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(talkingClockController)
                .setControllerAdvice(new ExceptionHandler())
                .build();

    }

    @Test
    public void canCallServiceAndSencResponseForCurrentTime() throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String inputTime = formatter.format(date);
        given(timeConverterService.convertNumericTimeToHumanReadable(inputTime)).willReturn("Ten past one");
        MockHttpServletResponse res = mockMvc.perform(MockMvcRequestBuilders.get("/talking-clock")).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo("Ten past one");

    }

    @Test
    public void canCallServiceAndSencResponseForGivenTime() throws Exception{

        given(timeConverterService.convertNumericTimeToHumanReadable("13:10")).willReturn("Ten past one");
        MockHttpServletResponse res = mockMvc.perform(MockMvcRequestBuilders.get("/talking-clock/{inputTime}","13:10")).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo("Ten past one");

    }
}
