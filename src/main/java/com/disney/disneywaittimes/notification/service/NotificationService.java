package com.disney.disneywaittimes.notification.service;

import com.disney.disneywaittimes.config.RideProperties;
import com.disney.disneywaittimes.times.converter.WaitTimesResponseToWaitTimeNotificationConverter;
import com.disney.disneywaittimes.times.model.WaitTimeResponse;
import com.disney.disneywaittimes.times.repository.WaitTimeNotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.disney.disneywaittimes.times.model.AttractionStatus.Down;
import static java.util.Objects.requireNonNull;

/**
 * @author mitch-warrenburg
 */

@Service
@Transactional
@AllArgsConstructor
public class NotificationService {

  private final RideProperties properties;
  private final TwilioSmsService smsService;
  private final WaitTimeNotificationRepository repository;
  private final WaitTimesResponseToWaitTimeNotificationConverter converter;

  public boolean isWaitTimeBelowThreshold(WaitTimeResponse waitTime) {
    return waitTime.getWaitTime() <= properties.getNotificationThresholdMinutes();
  }

  public boolean isWaitTimeAboveThreshold(WaitTimeResponse waitTime) {
    return waitTime.getWaitTime() > properties.getNotificationThresholdMinutes();
  }

  public boolean shouldNotifyStatusChanged(WaitTimeResponse waitTime) {
    boolean shouldNotifyDown = isAttractionDown(waitTime) && !wasAttractionDown(waitTime);
    boolean shouldNotifyUp = !isAttractionDown(waitTime) && wasAttractionDown(waitTime);
    return shouldNotifyDown || shouldNotifyUp;
  }

  public void notifyWaitTime(WaitTimeResponse waitTime) {
    if (!doesNotificationExist(waitTime)) {
      smsService.sendWaitTimeNotification(waitTime);
      saveNotification(waitTime);
    }
  }

  public void notifyStatusChanged(WaitTimeResponse waitTime) {
    clearNotification(waitTime);

    if (isAttractionDown(waitTime)) {
      saveNotification(waitTime);
    }

    smsService.sendRideStatusNotification(waitTime);
  }

  public void clearNotification(WaitTimeResponse waitTime) {
    repository.deleteAllByName(waitTime.getName());
    repository.flush();
  }

  private boolean isAttractionDown(WaitTimeResponse waitTime) {
    return waitTime.getStatus() == Down;
  }

  private boolean wasAttractionDown(WaitTimeResponse waitTime) {
    return repository.existsByNameAndStatus(waitTime.getName(), Down);
  }

  private void saveNotification(WaitTimeResponse waitTime) {
    repository.save(requireNonNull(converter.convert(waitTime)));
  }

  private boolean doesNotificationExist(WaitTimeResponse waitTime) {
    return repository.existsByName(waitTime.getName());
  }
}
