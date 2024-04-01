package com.example.spring.Security.application.user.servicesImpl;

import com.example.spring.Security.application.user.entiry.User;
import com.example.spring.Security.application.user.repository.UserRepository;
import com.example.spring.Security.shared.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDaoService {

  private final UserRepository repository;

  public void saveUser(User user1) {
    try {
      repository.save(user1);
    } catch (DataIntegrityViolationException e) {
      throw new ServiceException(e.getMostSpecificCause().getMessage(),e, HttpStatus.BAD_REQUEST);
    }
  }


}
