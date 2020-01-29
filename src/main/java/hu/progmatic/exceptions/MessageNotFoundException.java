package hu.progmatic.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No message like that")  // 404
public class MessageNotFoundException extends RuntimeException {
}