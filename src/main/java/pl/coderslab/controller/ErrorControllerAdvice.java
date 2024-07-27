package pl.coderslab.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, Model model, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("error");

        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace.length > 0) {
            // pobiera pierwszy element stack trace
            StackTraceElement element = stackTrace[0];
            String methodName = element.getMethodName();
            String className = element.getClassName();
            int lineNumber = element.getLineNumber();

            model.addAttribute("methodName", methodName);
            model.addAttribute("className", className);
            model.addAttribute("lineNumber", lineNumber);
            model.addAttribute("url", req.getRequestURL() );
        }

        model.addAttribute("exception", ex);

        return modelAndView;
    }
}











//package pl.coderslab.controller;
//
//import org.springframework.data.repository.query.ParameterOutOfBoundsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import javax.persistence.EntityNotFoundException;
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class ErrorControllerAdvice {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseStatus(value= HttpStatus.NOT_FOUND)
//    public String handleException1(HttpServletRequest req, Exception exception, Model model) {
//        model.addAttribute("exception", exception);
//        model.addAttribute("url", req.getRequestURL() );
//        model.addAttribute("errorMessage", exception.getMessage());
//        return "error";
//    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(value= HttpStatus.NOT_FOUND)
//    public String handleException2(HttpServletRequest req, Exception exception, Model model) {
//        model.addAttribute("exception", exception);


