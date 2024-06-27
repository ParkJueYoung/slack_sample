package com.example.slack_sample.sample;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackSampleService {

	@Value(value = "${slack.token}")
	String slackToken;
	
	public HashMap<String, Object> getChannelList() {
        HttpHeaders headers = new HttpHeaders();
        
        String slack_api_url = "https://slack.com/api/conversations.list";
        
        RestTemplate restTemplate = new RestTemplate();
        
        headers.set("Authorization", "Bearer " + slackToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(slack_api_url, HttpMethod.GET, entity, JsonNode.class);

        JsonNode body = response.getBody();
        HashMap<String, Object> resultMap = new HashMap<>();
        if (body != null) {
            resultMap.put("ok", body.get("ok").asBoolean());
            resultMap.put("channels", body.get("channels"));
        } else {
            resultMap.put("ok", false);
        }

        return resultMap;
    }

	public HashMap<String, Object> sendMsg(HashMap<String, Object> requestMap) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {

			MethodsClient methods = Slack.getInstance().methods(slackToken);

			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
					.channel(String.valueOf(requestMap.get("channel"))).text(String.valueOf(requestMap.get("msg")))
					.build();

			methods.chatPostMessage(request);

			resultMap.put("result", "success");
		} catch (Exception e) {
			resultMap.put("result", "error");
		}

		return resultMap;

	}

}
