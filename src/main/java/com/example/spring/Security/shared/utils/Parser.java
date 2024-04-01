package com.example.spring.Security.shared.utils;

import com.example.spring.Security.shared.exception.ServiceException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Slf4j
public class Parser {

  public <T> T parseResponseToType(String request, Class<T> responseType) {
    if (Objects.isNull(request)) {
      log.error("No Response found");
      throw new ServiceException("UNABLE_TO_PROCESS_ERR", null, HttpStatus.NO_CONTENT);
    }
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      return  objectMapper.readValue(request, responseType);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new ServiceException("UNABLE_TO_PROCESS_ERR", e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
