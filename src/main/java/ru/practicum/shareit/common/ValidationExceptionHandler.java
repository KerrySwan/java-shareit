package ru.practicum.shareit.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.shareit.common.exception.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, (Object) null, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<?> handleNotFoundError(AlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DoesNotExistsException.class)
    protected ResponseEntity<?> handleNotFoundError(DoesNotExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleNotFoundError(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> handleNotFoundError(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemIsAnavailableException.class)
    protected ResponseEntity<?> handleNotFoundError(ItemIsAnavailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    protected ResponseEntity<?> handleNotFoundError(InvalidDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedStatusException.class)
    protected ResponseEntity<?> handleNotFoundError(UnsupportedStatusException ex) {
        return new ResponseEntity<>(new ErrorWrapper(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    protected ResponseEntity<?> handleNotFoundError(InvalidUserException ex) {
        return new ResponseEntity<>(new ErrorWrapper(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessException.class)
    protected ResponseEntity<?> handleNotFoundError(AccessException ex) {
        return new ResponseEntity<>(new ErrorWrapper(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
