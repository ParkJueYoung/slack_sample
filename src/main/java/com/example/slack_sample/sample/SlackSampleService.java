package com.example.slack_sample.sample;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SlackSampleService {

	@Value(value = "${slack.token}")
	String slackToken;

	public HashMap<String, Object> sendMsg(HashMap<String, Object> requestMap) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		try {

			MethodsClient methods = Slack.getInstance().methods(slackToken);

			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
					.channel(String.valueOf(requestMap.get("channel")))
					.text(String.valueOf(requestMap.get("msg"))).build();
			

			methods.chatPostMessage(request);

			resultMap.put("result", "success");
		} catch (Exception e) {
			resultMap.put("result", "error");
		}

		return resultMap;

	}

}
