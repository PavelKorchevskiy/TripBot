package com.telegram.controller;

import com.telegram.exception.CityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CityNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(CityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String cityNotFoundHandler(CityNotFoundException exception) {
    log.error("Ошибка - " + exception.getMessage());
    return exception.getMessage();
  }
}
