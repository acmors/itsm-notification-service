package octa.ticket.notification.service.exception.handle;

import octa.ticket.notification.service.dto.ErrorResponse;
import octa.ticket.notification.service.exception.EmailSendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<ErrorResponse> emailSendException(EmailSendException ex){
        ErrorResponse error = new ErrorResponse(
                "Unexpected error to send email: " + ex.getMessage(), LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalException(Exception ex){
        ErrorResponse error = new ErrorResponse(
                "Unavailable Server " , LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
