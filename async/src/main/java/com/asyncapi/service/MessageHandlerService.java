package com.asyncapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerService.class);

    
      
    /**
     * The league to create
     */
    public void handleCreateLeaguePub(final Message<?> message) {
        LOGGER.info("handler createLeague");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The league to delete
     */
    public void handleDeleteLeaguePub(final Message<?> message) {
        LOGGER.info("handler deleteLeague");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    

}
