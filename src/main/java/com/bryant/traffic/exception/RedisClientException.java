package com.bryant.traffic.exception;

public class RedisClientException extends RuntimeException{
    public RedisClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
