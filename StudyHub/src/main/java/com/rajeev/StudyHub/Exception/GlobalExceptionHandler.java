package com.rajeev.StudyHub.Exception;

import com.rajeev.StudyHub.Payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
            String message = ex.getMessage();
            return new ResponseEntity<>(new ApiResponse( message, false), HttpStatus.NOT_FOUND);
        }

//        @ExceptionHandler(MethodArgumentNotValidException.class)
//        public ResponseEntity<Map<String,String>> methodArgsNotValidException(MethodArgumentNotValidException ex){
//            Map<String , String> resp = new HashMap<>();
//            ex.getBindingResult()
//                    .getAllErrors()
//                    .forEach((error)
//                            ->{
//                        String fieldName =  ((FieldError)error).getField();
//                        String message = error.getDefaultMessage();
//                        resp.put(fieldName,message);
//                    });
//            return  new ResponseEntity<>(resp , HttpStatus.BAD_REQUEST);
//        }
//
//        @ExceptionHandler(BadCredentialsException.class)
//        public ResponseEntity<ApiResponse> badCredentialException(BadCredentialsException ex){
//            String message = " Invalid Username and Password";
//
//            return new ResponseEntity<>(new ApiResponse(message , false),HttpStatus.UNAUTHORIZED);
//        }

}


