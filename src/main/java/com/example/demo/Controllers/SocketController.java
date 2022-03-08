package com.example.demo.Controllers;

import com.example.demo.SocketModel.Event;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("newUpdate")
    @SendTo("/topic/newUpdate")
    public Event newUpdate(Event event) throws Exception {
        Thread.sleep(1000);
        return event;
    }
}
