package com.courses.kafkacurse.emailnotificationservice.exceptions;

/*
A Retryable exception is that one that we can try to consume again if it has error, before goes to the DLT.
We need to take in consideration that not all the errors are retryables.
Also, we need to configure wait time and number of times to retry.
 */
public class RetryableException extends RuntimeException {

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
