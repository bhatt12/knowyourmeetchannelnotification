package com.ukg.knowyourmeet.service.impl;

import com.google.gson.Gson;
import com.ukg.knowyourmeet.dto.AdaptiveCardDto;
import com.ukg.knowyourmeet.service.KnowYourMeetService;
import lombok.extern.slf4j.Slf4j;
import com.ukg.knowyourmeet.client.MSTeamWebHookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KnowYourMeetServiceImpl implements KnowYourMeetService {

    MSTeamWebHookClient teamClient;

    @Autowired
    public KnowYourMeetServiceImpl(MSTeamWebHookClient teamClient) {
        this.teamClient = teamClient;
    }

    @Override
    @Scheduled(cron = "${cron.dsm}")
    public void sendNotification() {

        log.info("Sending notification...");
        try {
            String json = "{\"@type\":\"MessageCard\",\"@context\":\"http://schema.org/extensions\"," +
                    "\"themeColor\":\"0076D7\",\"summary\":\"Larry Bryant created a new task\",\"sections\":[{\"activityTitle\":\"Larry Bryant created a new task\",\"activitySubtitle\":\"On Project Tango\",\"activityImage\":\"https://teamsnodesample.azurewebsites.net/static/img/image5.png\",\"facts\":[{\"name\":\"Assigned to\",\"value\":\"Unassigned\"},{\"name\":\"Due date\",\"value\":\"Mon May 01 2017 17:07:18 GMT-0700 (Pacific Daylight Time)\"},{\"name\":\"Status\",\"value\":\"Not started\"}],\"markdown\":true}],\"potentialAction\":[{\"@type\":\"ActionCard\",\"name\":\"Add a comment\",\"inputs\":[{\"@type\":\"TextInput\",\"id\":\"comment\",\"isMultiline\":false,\"title\":\"Add a comment here for this task\"}],\"actions\":[{\"@type\":\"HttpPOST\",\"name\":\"Add comment\",\"target\":\"https://learn.microsoft.com/outlook/actionable-messages\"}]},{\"@type\":\"ActionCard\",\"name\":\"Set due date\",\"inputs\":[{\"@type\":\"DateInput\",\"id\":\"dueDate\",\"title\":\"Enter a due date for this task\"}],\"actions\":[{\"@type\":\"HttpPOST\",\"name\":\"Save\",\"target\":\"https://learn.microsoft.com/outlook/actionable-messages\"}]},{\"@type\":\"OpenUri\",\"name\":\"Learn More\",\"targets\":[{\"os\":\"default\",\"uri\":\"https://learn.microsoft.com/outlook/actionable-messages\"}]},{\"@type\":\"ActionCard\",\"name\":\"Change status\",\"inputs\":[{\"@type\":\"MultichoiceInput\",\"id\":\"list\",\"title\":\"Select a status\",\"isMultiSelect\":\"false\",\"choices\":[{\"display\":\"In Progress\",\"value\":\"1\"},{\"display\":\"Active\",\"value\":\"2\"},{\"display\":\"Closed\",\"value\":\"3\"}]}],\"actions\":[{\"@type\":\"HttpPOST\",\"name\":\"Save\",\"target\":\"https://learn.microsoft.com/outlook/actionable-messages\"}]}]}";
            Gson gson = new Gson();
            AdaptiveCardDto messageCardDTO = gson.fromJson(json, AdaptiveCardDto.class);

            teamClient.send(messageCardDTO);
        }catch (Exception err){
           log.error("{}", err);
        }
    }
}
