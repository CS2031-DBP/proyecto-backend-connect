package com.example.forutec2.controller;

import com.example.forutec2.Exception.ErrorResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> handleError(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        int status = (int) attributes.get("status");
        String message = (String) attributes.get("error");

        ErrorResponse errorResponse = new ErrorResponse(false, status, message);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    public String getErrorPath() {
        return "/error";
    }
}
