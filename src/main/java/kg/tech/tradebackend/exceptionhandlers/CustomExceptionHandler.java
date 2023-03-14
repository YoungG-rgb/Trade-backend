package kg.tech.tradebackend.exceptionhandlers;

import kg.tech.tradebackend.controllers.BaseController;
import kg.tech.tradebackend.domain.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends BaseController {

    @ExceptionHandler(Exception.class)
    public ResponseModel<String> handleAnyException(Exception exception) {
        log.error(exception.getMessage());
        return exceptionResponse(exception.getMessage());
    }
}
