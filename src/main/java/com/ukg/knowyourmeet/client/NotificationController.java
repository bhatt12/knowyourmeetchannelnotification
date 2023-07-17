package com.ukg.knowyourmeet.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/jira")
public class NotificationController {

    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getNotificationData(@RequestParam("id") String boardID) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://chatitout.atlassian.net/rest/api/2/search?jql=";


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cHJhZ255YS5tb2hhcGF0cmFAdWtnLmNvbTpBVEFUVDN4RmZHRjBKdGIyZWRiamhhV3Y4SjlEaFNwRXpSYTFwWFJSa24wTlVzd0Vza3BURXVKTGdkd1FDRHZIZXI3emxUNWZ4Mk9ITC1zTG1CejBHMVpIWmdXeEhJXzdzX2hyLWNkejFGb21yTTQzU29uck1SUEd3OVdORHUzLWdRd2xMbkFMeGliZTBUbk52SjhMR0haVW5BWFlrMmVzX0VOTzNkSXZUMklMSmNHdzZYMWRhQlE9MTQ4NDk4REY=");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<String>(){});

        List<Object> dataList = new ArrayList<>();
        JSONObject responseObj = new JSONObject((responseEntity.getBody()));
        JSONArray issuesArray = responseObj.getJSONArray("issues");

        for (int i = 0; i < issuesArray.length(); i++) {
            JSONObject object = issuesArray.getJSONObject(i);
            String key = (String) object.get("key");
            String summary = (String) object.getJSONObject("fields").get("summary");
            String assignee = (String) object.getJSONObject("fields").getJSONObject("assignee").get("displayName");
            String status = (String) object.getJSONObject("fields").getJSONObject("status").get("name");
            String priority = (String) object.getJSONObject("fields").getJSONObject("priority").get("name");

            Resource resource = new ClassPathResource("schema.json");
            try (InputStream inputStream = resource.getInputStream()) {
                String schemaJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                schemaJson = schemaJson.replace("{{STORY_ID}}",key);
                schemaJson = schemaJson.replace("{{STORY_DESC}}",summary);
                schemaJson = schemaJson.replace("{{STORY_ASSIGNEE}}",assignee);
                schemaJson = schemaJson.replace("{{STORY_STATUS}}",status);
                dataList.add(schemaJson);
//                System.out.println(schemaJson);
            }
        }

        System.out.println(dataList);
        return dataList;
    }

}
