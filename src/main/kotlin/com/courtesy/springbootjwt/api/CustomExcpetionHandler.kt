package com.courtesy.springbootjwt.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.ConstraintViolationException


@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onConstraintValidationException(e: ConstraintViolationException): ErrorResponse? {
        val errors = e.constraintViolations
                .map { violation -> Error(violation.propertyPath.toString(), violation.message) }
        return ErrorResponse(errors)
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse? {
        val errors = e.bindingResult.fieldErrors
                .map { violation -> Error(violation.field, violation.defaultMessage) }
        return ErrorResponse(errors)
    }
}

data class ErrorResponse(
        val errors: List<Error>
)

data class Error(
        val field: String? = null,
        val message: String? = null
)