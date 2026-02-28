package com.example.ms.upload.files.configuration.exceptionhandle;

import com.example.ms.upload.files.domain.exception.InvalidUserDataException;
import com.example.ms.upload.files.domain.exception.MaxReservationSeatsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.ms.upload.files.domain.util.FileMessageConstants.*;

@ControllerAdvice
public class FileExceptionHandle {

    Map<String, String> responseException = new HashMap<>();

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(
            IOException ioException
    )
    {
        responseException.put(ERROR_MESSAGE_KEY, ioException.getMessage());
        responseException.put(ERROR_CODE_MESSAGE_KEY, UNEX_ERR_1);
        responseException.put(ERROR_EXCEPTION_NAME_KEY, ioException.getClass().getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

    @ExceptionHandler(MaxReservationSeatsException.class)
    public ResponseEntity<Map<String, String>> handleMaxReservationSeatsException(
            MaxReservationSeatsException maxReservationSeatsException
    )
    {
        responseException.put(ERROR_MESSAGE_KEY, maxReservationSeatsException.getMessage());
        responseException.put(ERROR_CODE_MESSAGE_KEY, RUL_ERR_1);
        responseException.put(ERROR_EXCEPTION_NAME_KEY, maxReservationSeatsException.getClass().getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUserDataException(
            InvalidUserDataException invalidUserDataException
    )
    {
        responseException.put(ERROR_MESSAGE_KEY, invalidUserDataException.getMessage());
        responseException.put(ERROR_CODE_MESSAGE_KEY, RUL_ERR_1);
        responseException.put(ERROR_EXCEPTION_NAME_KEY, invalidUserDataException.getClass().getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointerException(
            NullPointerException nullPointerException
    )
    {
        responseException.put(ERROR_MESSAGE_KEY, nullPointerException.getMessage());
        responseException.put(ERROR_CODE_MESSAGE_KEY, RUL_ERR_1);
        responseException.put(ERROR_EXCEPTION_NAME_KEY, nullPointerException.getClass().getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }
}
