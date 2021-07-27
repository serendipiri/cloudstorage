package com.udacity.jwdnd.course1.cloudstorage.common;

public class CloudStorageException extends RuntimeException {

    public CloudStorageException(String message) {
        super(message);
    }

    public CloudStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
