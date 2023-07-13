package com.ukg.knowyourmeet.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/jira")
public class NotificationController {

    @GetMapping(value = "/notification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSSOTemplateData(@RequestParam("id") String boardID) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://chatitout.atlassian.net/rest/api/2/search?jql=";


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic cHJhZ255YS5tb2hhcGF0cmFAdWtnLmNvbTpBVEFUVDN4RmZHRjBKdGIyZWRiamhhV3Y4SjlEaFNwRXpSYTFwWFJSa24wTlVzd0Vza3BURXVKTGdkd1FDRHZIZXI3emxUNWZ4Mk9ITC1zTG1CejBHMVpIWmdXeEhJXzdzX2hyLWNkejFGb21yTTQzU29uck1SUEd3OVdORHUzLWdRd2xMbkFMeGliZTBUbk52SjhMR0haVW5BWFlrMmVzX0VOTzNkSXZUMklMSmNHdzZYMWRhQlE9MTQ4NDk4REY=");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<String>(){});

        JSONObject obj = new JSONObject((responseEntity.getBody()));

        JSONArray arr = new JSONArray(obj.get("issues"));
        System.out.println(arr.get(0));

//        list.stream().forEach(i -> {System.out.print(i + " ");});

        return responseEntity;
    }

}
