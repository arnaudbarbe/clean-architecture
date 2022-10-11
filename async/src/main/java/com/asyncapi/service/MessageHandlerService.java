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
    public void handleCreateLeaguePub(Message<?> message) {
        LOGGER.info("handler createLeague");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The league to update
     */
    public void handleUpdateLeaguePub(Message<?> message) {
        LOGGER.info("handler updateLeague");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The league to delete
     */
    public void handleDeleteLeaguePub(Message<?> message) {
        LOGGER.info("handler deleteLeague");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The player to create
     */
    public void handleCreatePlayerPub(Message<?> message) {
        LOGGER.info("handler createPlayer");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The player to update
     */
    public void handleUpdatePlayerPub(Message<?> message) {
        LOGGER.info("handler updatePlayer");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    
      
    /**
     * The player to delete
     */
    public void handleDeletePlayerub(Message<?> message) {
        LOGGER.info("handler deletePlayer");
        LOGGER.info(String.valueOf(message.getPayload().toString()));
    }
      
    

}
