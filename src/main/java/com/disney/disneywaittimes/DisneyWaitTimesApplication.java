package com.disney.disneywaittimes;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import static java.util.TimeZone.getTimeZone;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class DisneyWaitTimesApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(getTimeZone("America/Los_Angeles"));
    SpringApplication.run(DisneyWaitTimesApplication.class, args);
  }
}
