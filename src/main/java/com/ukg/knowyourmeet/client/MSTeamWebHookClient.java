package com.ukg.knowyourmeet.client;

import com.ukg.knowyourmeet.dto.AdaptiveCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MSTeamWebHookClient", url = "${webhook.url}")
public interface MSTeamWebHookClient {

    @PostMapping(headers = {"Content-Type=application/json"})
    void send(@RequestBody AdaptiveCardDto body);
}
