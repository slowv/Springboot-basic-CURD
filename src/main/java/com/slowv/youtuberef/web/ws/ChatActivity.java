package com.slowv.youtuberef.web.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/chat")
public class ChatActivity {

    @MessageMapping("/lobby/{lobbyId}/message")
    @SendTo("/chat/lobby/{lobbyId}/message/data")
    public String joinLobby(
            @Payload String message
    ) {
        return message;
    }
}
