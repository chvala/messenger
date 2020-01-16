package hu.progmatic.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class MyControllerAdvice
{
    @ExceptionHandler(IOException.class)
    public String handleErrors(IOException ex, Model model){
        model.addAttribute("IOExceptionMessage", ex.getMessage());
        model.addAttribute( "IOExceptionStackTrace" ,ex.getStackTrace());
        return "403Error";
    }
    @ExceptionHandler(Exception.class)
    public String handleErrors(Exception ex, Model model){
        model.addAttribute("exceptionMessage", ex.getMessage());
        model.addAttribute( "stackTrace" ,ex.getStackTrace());
        return "myError";
    }

}