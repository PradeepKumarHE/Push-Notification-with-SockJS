package com.pradeep.websocketsockjs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.pradeep.websocketsockjs.model.Greeting;
import com.pradeep.websocketsockjs.model.HelloMessage;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
    
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
	public ResponseEntity<String>  publish(@RequestBody HelloMessage message) throws Exception {
    	System.out.println("Entered !!!");    	
		simpMessagingTemplate.convertAndSend("/topic/greetings",new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));
		return new ResponseEntity<String>("Push Notification sent to " + message.getName(), HttpStatus.OK);
	}
}