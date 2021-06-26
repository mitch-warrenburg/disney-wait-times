package com.disney.disneywaittimes.times.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mitch-warrenburg
 */

@Log4j2
@Component
@AllArgsConstructor
public class WaitTimesOrchestrator {

  private static final int THIRTY_SECONDS_MS = 30_000;
  private static final String DCA_PARK_NAME = "DisneylandResortCaliforniaAdventure";
  private static final String DISNEYLAND_PARK_NAME = "DisneylandResortMagicKingdom";

  private final WaitTimesService waitTimesService;

  @Scheduled(fixedRate = THIRTY_SECONDS_MS, initialDelay = THIRTY_SECONDS_MS)
  public void run() {
    log.info("Running wait times orchestrator.");
    waitTimesService.handleWaitTimes(DCA_PARK_NAME);
    waitTimesService.handleWaitTimes(DISNEYLAND_PARK_NAME);
    log.info("Successfully ran wait times orchestrator.");
  }
}
