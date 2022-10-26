package br.com.wszd.jboard.service;

import br.com.wszd.jboard.dto.SessaoDTO;
import br.com.wszd.jboard.dto.UserLoginDTO;
import br.com.wszd.jboard.dto.UserRoleDTO;
import br.com.wszd.jboard.exceptions.ResourceBadRequestException;
import br.com.wszd.jboard.model.*;
import br.com.wszd.jboard.repository.LogRepository;
import br.com.wszd.jboard.repository.UserRepository;
import br.com.wszd.jboard.security.JWTCreator;
import br.com.wszd.jboard.security.JWTObject;
import br.com.wszd.jboard.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogService {

  @Autowired
  LogRepository repository;

  public void createLog(LogTable log) {
    repository.save(log);
  }
}
