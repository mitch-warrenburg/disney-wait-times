package com.disney.disneywaittimes.times.converter;

import com.disney.disneywaittimes.times.entity.WaitTimeNotification;
import com.disney.disneywaittimes.times.model.WaitTimeResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

/**
 * @author mitch-warrenburg
 */

@Component
public class WaitTimesResponseToWaitTimeNotificationConverter implements Converter<WaitTimeResponse, WaitTimeNotification> {

  @Override
  public WaitTimeNotification convert(WaitTimeResponse response) {
    return WaitTimeNotification.builder()
        .name(response.getName())
        .status(response.getStatus())
        .active(response.isActive())
        .waitTime(response.getWaitTime())
        .fastPass(response.isFastPass())
        .type(response.getMeta().getType())
        .lastUpdate(response.getLastUpdate())
        .singleRider(response.getMeta().isSingleRider())
        .createdTs(now())
        .modifiedTs(now())
        .build();
  }
}
