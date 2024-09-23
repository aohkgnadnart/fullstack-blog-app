package com.springboot.blog.exception;

import com.springboot.blog.payload.ErrorDetailsDto;
import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.PessimisticLockException;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

    // handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetailsDto> handleBlogAPIException(BlogAPIException exception,
                                                                  WebRequest webRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }

    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception exception,
                                                                 WebRequest webRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatusCode status,
//                                                                  WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                              WebRequest webRequest){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = fieldName + " " + error.getDefaultMessage(); // dinh dang thong bao loi tuy chinh
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDetailsDto> handleAuthorizationDeniedException(AuthorizationDeniedException exception,
                                                                              WebRequest webRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.UNAUTHORIZED);
    }


    // exception lien quan khoa Bi Quan (Pessimistic Locking)
    @ExceptionHandler({CannotAcquireLockException.class, LockTimeoutException.class})
    public ResponseEntity<String> handleLockTimeoutException(Exception ex) {
        return new ResponseEntity<>("Could not acquire lock within the specified timeout.", HttpStatus.REQUEST_TIMEOUT);
    }

    // exception lien quan khoa Bi Quan (Pessimistic Locking)
    @ExceptionHandler(PessimisticLockException.class)
    public ResponseEntity<String> handlePessimisticLockException(PessimisticLockException ex) {
        return new ResponseEntity<>("A pessimistic lock exception occurred.", HttpStatus.CONFLICT);
    }

    // exception lien quan khoa Bi Quan (Pessimistic Locking)
    @ExceptionHandler(LockAcquisitionException.class)
    public ResponseEntity<String> handleLockAcquisitionException(LockAcquisitionException ex) {
        return new ResponseEntity<>("Failed to acquire lock on the database.", HttpStatus.CONFLICT);
    }

    // exception lien quan khoa Bi Quan (Pessimistic Locking)
    @ExceptionHandler(DeadlockLoserDataAccessException.class)
    public ResponseEntity<String> handleDeadlockException(DeadlockLoserDataAccessException ex) {
        return new ResponseEntity<>("A deadlock occurred, please try again.", HttpStatus.CONFLICT);
    }
}
