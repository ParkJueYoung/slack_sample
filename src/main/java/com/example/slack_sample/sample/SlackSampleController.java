package com.example.slack_sample.sample;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("slack/sample")
public class SlackSampleController {

	@Autowired
	private SlackSampleService service;

	@GetMapping
	public String samplePage() {
		return "sample";
	}
	
	@GetMapping("/channels")
	public ResponseEntity<HashMap<String, Object>> getChannelList() {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> responseMap = new HashMap<String, Object>();

		try {

			responseMap = service.getChannelList();

			entity = new ResponseEntity<HashMap<String, Object>>(responseMap, HttpStatus.OK);

		} catch (Exception e) {
			entity = new ResponseEntity<HashMap<String, Object>>(HttpStatus.BAD_REQUEST);
		}

		return entity;

	}

	@PostMapping
	public ResponseEntity<HashMap<String, Object>> sample(@RequestBody HashMap<String, Object> param) {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> responseMap = new HashMap<String, Object>();

		try {

			HashMap<String, Object> requestMap = param;

			responseMap = service.sendMsg(requestMap);

			entity = new ResponseEntity<HashMap<String, Object>>(responseMap, HttpStatus.OK);

		} catch (Exception e) {
			entity = new ResponseEntity<HashMap<String, Object>>(HttpStatus.BAD_REQUEST);
		}

		return entity;

	}

}
