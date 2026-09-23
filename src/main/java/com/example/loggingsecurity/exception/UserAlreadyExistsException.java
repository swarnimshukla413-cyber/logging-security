
package com.example.loggingsecurity.exception;
 
public class UserAlreadyExistsException
       extends RuntimeException {
 
   public UserAlreadyExistsException(String message) {
       super(message);
   }
}