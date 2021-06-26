package com.disney.disneywaittimes.times.service;

import com.disney.disneywaittimes.config.RideProperties;
import com.disney.disneywaittimes.notification.service.NotificationService;
import com.disney.disneywaittimes.times.client.WaitTimesClient;
import com.disney.disneywaittimes.times.model.AttractionStatus;
import com.disney.disneywaittimes.times.model.WaitTimeResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.disney.disneywaittimes.times.model.AttractionStatus.Down;
import static com.disney.disneywaittimes.times.model.AttractionStatus.Operating;

/**
 * @author mitch-warrenburg
 */

@Service
@AllArgsConstructor
public class WaitTimesService {

  private static final List<AttractionStatus> ATTRACTION_OPEN_STATUSES = List.of(
      Down,
      Operating
  );

  private final WaitTimesClient client;
  private final RideProperties properties;
  private final NotificationService notificationService;

  public void handleWaitTimes(String parkName) {
    client.getWaitTimes(parkName).stream()
        .filter(this::isEligibleForNotifications)
        .forEach(this::handleWaitTime);
  }

  private void handleWaitTime(WaitTimeResponse waitTime) {
    if (notificationService.shouldNotifyStatusChanged(waitTime)) {
      notificationService.notifyStatusChanged(waitTime);
    } else if (notificationService.isWaitTimeAboveThreshold(waitTime)) {
      notificationService.clearNotification(waitTime);
    } else if (notificationService.isWaitTimeBelowThreshold(waitTime)) {
      notificationService.notifyWaitTime(waitTime);
    }
  }

  private boolean isEligibleForNotifications(WaitTimeResponse waitTime) {
    boolean isPreferred = properties.getPreferred().contains(waitTime.getName());
    boolean isOpen = ATTRACTION_OPEN_STATUSES.contains(waitTime.getStatus());
    return isPreferred && isOpen;
  }
}
