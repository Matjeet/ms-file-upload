package com.example.ms.upload.files.domain.util;

public class FileConstants {

    private FileConstants() {}

    public static final Long MAX_FILE_SIZE = 10L * 1024 * 1024;

    public static final Integer MAX_RESERVATION_SEATS = 3;
    public static final Integer MAX_FILE_LINES = 5;

    public static final String UNDERSCORE = "_";
    public static final String FILENAME_SUSPECT_CHARS = "..";
    public static final String REGEX_EMAIL_VALIDATION = "^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-ID";
    public static final String CORRELATION_ID_MDC_KEY = "correlationId";
    public static final String REJECTED_STATUS = "REJECTED";
    public static final String ERROR_STATUS = "ERROR";
}
