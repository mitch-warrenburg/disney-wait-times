package com.disney.disneywaittimes.notification.service;

import com.disney.disneywaittimes.config.SmsProperties;
import com.disney.disneywaittimes.times.model.WaitTimeResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * @author mitch-warrenburg
 */

@Log4j2
@Service
@AllArgsConstructor
public class TwilioSmsService {

  public static final String WAIT_TIME_SMS_FORMAT = "The wait time for %s is only %d minutes! 🎉🎉🎉";
  public static final String ATTRACTION_DOWN_SMS_FORMAT = "Oh shit! %s has broken down! 🔥🎢";
  public static final String ATTRACTION_OPERATING_SMS_FORMAT = "Get your running shoes on.  %s is back up! 🍻";

  private final SmsProperties properties;

  @PostConstruct
  public void init() {
    Twilio.init(properties.getSid(), properties.getToken());
  }

  public void sendWaitTimeNotification(WaitTimeResponse waitTime) {
    send(format(WAIT_TIME_SMS_FORMAT, waitTime.getName(), waitTime.getWaitTime()));
  }

  public void sendRideStatusNotification(WaitTimeResponse waitTime) {
    val messageFormat = waitTime.isActive()
        ? ATTRACTION_OPERATING_SMS_FORMAT
        : ATTRACTION_DOWN_SMS_FORMAT;

    send(format(messageFormat, waitTime.getName()));
  }

  public void send(String textMessage) {
    properties.getNumbers().getTo()
        .forEach(toNumber -> send(toNumber, textMessage));
  }

  private void send(String toNumber, String textMessage) {
    val message = Message.creator(
        new PhoneNumber(toNumber),
        new PhoneNumber(properties.getNumbers().getFrom()),
        textMessage)
        .create();

    log.info("Successfully sent message. [sid]: {}", message.getSid());
  }
}
