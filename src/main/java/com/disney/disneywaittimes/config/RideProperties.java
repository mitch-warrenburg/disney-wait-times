package com.disney.disneywaittimes.config;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author mitch-warrenburg
 */

@Data
@Component
@Validated
@NoArgsConstructor
@ConfigurationProperties("rides")
public class RideProperties {

  private List<String> preferred;
  private int notificationThresholdMinutes;
}
