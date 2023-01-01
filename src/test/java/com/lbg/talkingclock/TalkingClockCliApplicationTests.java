package com.lbg.talkingclock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TalkingClockCliApplicationTests {
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;
	@Autowired
	TalkingClockApplication talkingClockApplication;

	@Test
	void contextLoads() {
	}


	@AfterEach
	public void clear(){
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

	@Test
	void testCurrentTimeRun() throws Exception{

		String userInput = String.format("YES%s13:45",System.lineSeparator());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(inputStream);
		String expected = "Quarter to one";

		String log = captureStdOut(() -> {
			this.talkingClockApplication.main(new String[]{""});
		});
		String[] lines = log.split(System.lineSeparator());
		String lastLine = lines[lines.length-1];
		String actual = lastLine.substring(lastLine.lastIndexOf(":")+1).trim();
		// checkout output
		assertEquals(expected,actual);
	}




	public static String captureStdOut(Runnable r)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = System.out;
		try {
			System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8.name()));
			r.run();
			return new String(baos.toByteArray(), StandardCharsets.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("End of the world, Java doesn't recognise UTF-8");
		} finally {
			System.setOut(out);
		}
	}


}
