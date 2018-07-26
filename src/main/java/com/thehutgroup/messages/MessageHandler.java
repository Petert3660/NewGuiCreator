package com.thehutgroup.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

    private MessageSource messageSource;

    @Autowired
    public MessageHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, null);
    }

    public String getMessage(String key, String[] params) {

        Object[] args = params;

        return messageSource.getMessage(key, args, null);
    }
}
