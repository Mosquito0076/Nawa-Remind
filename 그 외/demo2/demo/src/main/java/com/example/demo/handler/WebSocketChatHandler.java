package com.example.demo.handler;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;

//@XSlf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final static Logger LOG = Logger.getGlobal();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        LOG.info("payload" + payload);
        TextMessage textMessage = new TextMessage("Welcome chatting server~ ^^");
        session.sendMessage(textMessage);
    }
}
