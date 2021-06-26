package com.disney.disneywaittimes.times.client;

import com.disney.disneywaittimes.times.model.WaitTimeResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author mitch-warrenburg
 */

@FeignClient(name = "waitTimesClient", url = "https://api.themeparks.wiki/preview")
public interface WaitTimesClient {

  @GetMapping("/parks/{parkId}/waittime")
  List<WaitTimeResponse> getWaitTimes(@PathVariable String parkId);
}
