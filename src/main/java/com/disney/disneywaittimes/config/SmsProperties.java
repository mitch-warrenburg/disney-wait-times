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
@ConfigurationProperties("sms")
public class SmsProperties {

  private String token;
  private String sid;
  private Numbers numbers;

  @Data
  @NoArgsConstructor
  public static class Numbers {
    private String from;
    private List<String> to;
  }
}
