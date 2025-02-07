package com.courses.kafkacurse.emailnotificationservice.exceptions;

/*
This is the kind of exception that will make us send the message directly to the Dead Letter Topic
 */
public class NotRetryableException extends RuntimeException{

    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(Throwable cause) {
        super(cause);
    }
}
