package com.example.fibonacci;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utils {
  // ERRORS
  public static final String ERROR_NEGATIVE_NUMBER = "Number must be greather than -1";
  public static final String ERROR_GREATHER_90 = "Number must be lesser than 91";
  
  // SUCCESS
  public static final String SUCCESS = "Success!";

  // Devuleve Fibonacci(n)
  public static Long fibonacci(Integer n) {
    if (n < 2) return n.longValue();
    return fibonacci(n-1) + fibonacci(n-2);
  }

  public static ResponseEntity<Object> sendResponse(HttpStatus status, String message, Object data) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("message", message);
    response.put("status", status.value());
    response.put("data", data);

    return new ResponseEntity<>(response, status);
  }

}
