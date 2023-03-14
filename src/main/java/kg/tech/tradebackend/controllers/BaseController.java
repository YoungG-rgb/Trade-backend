package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.enums.ResultCode;
import kg.tech.tradebackend.domain.response.ResponseModel;

public abstract class BaseController {

    protected <T> ResponseModel<T> successResponse(T result) {
        return new ResponseModel<>(result, ResultCode.SUCCESS);
    }

    protected ResponseModel<String> exceptionResponse(String details) {
        return new ResponseModel<>(null, ResultCode.EXCEPTION, details);
    }

    protected ResponseModel<String> badRequestResponse() {
        return new ResponseModel<>(null, ResultCode.BAD_REQUEST);
    }
}
