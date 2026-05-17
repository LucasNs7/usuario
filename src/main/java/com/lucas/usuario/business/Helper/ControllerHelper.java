package com.lucas.usuario.business.Helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ControllerHelper {

    public <T> ResponseEntity<?> tryCatchFunction(Supplier<T> action,
                  Class<? extends Exception> exceptionClass, HttpStatus statusCode ){
        try {
            return ResponseEntity.ok(action.get());
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                return ResponseEntity.status(statusCode).body(e.getMessage());
            }
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro no servidor");
        }
    }
}
