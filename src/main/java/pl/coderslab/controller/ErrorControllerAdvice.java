package pl.coderslab.controller;

import org.springframework.data.repository.query.ParameterOutOfBoundsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public String handleException1(HttpServletRequest req, Exception exception, Model model) {
        model.addAttribute("exception", exception);
        model.addAttribute("url", req.getRequestURL() );
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public String handleException2(HttpServletRequest req, Exception exception, Model model) {
        model.addAttribute("exception", exception);
        model.addAttribute("url", req.getRequestURL() );
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(ParameterOutOfBoundsException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public String handleException3(HttpServletRequest req, Exception exception, Model model) {
        model.addAttribute("exception", exception);
        model.addAttribute("url", req.getRequestURL() );
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

}
