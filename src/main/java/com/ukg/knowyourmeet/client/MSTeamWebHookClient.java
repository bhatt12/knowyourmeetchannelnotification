package com.ukg.knowyourmeet.client;

import com.ukg.knowyourmeet.dto.AdaptiveCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MSTeamWebHookClient", url = "https://kronos.webhook.office.com/webhookb2/bc7ce7c7-5ba7-44fb-a173-94dcd8cd7dea@7b6f35d2-1f98-4e5e-82eb-e78f6ea5a1de/IncomingWebhook/e7086bd873564e02ac6bb0d72bc10a16/c0b50044-83fb-48a0-a742-a8123ba9f504")
public interface MSTeamWebHookClient {

    @PostMapping(headers = {"Content-Type=application/json"})
    void send(@RequestBody AdaptiveCardDto body);
}
