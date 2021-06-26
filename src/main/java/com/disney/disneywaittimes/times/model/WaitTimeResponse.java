package com.disney.disneywaittimes.times.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mitch-warrenburg
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitTimeResponse {

  private String name;
  private AttractionStatus status;
  private int waitTime;
  private boolean active;
  private boolean fastPass;
  private LocalDateTime lastUpdate;
  private Meta meta;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Meta {
    private boolean singleRider;
    private AttractionType type;
  }
}
